package com.example.WakaTimeAPI.service;

import com.example.WakaTimeAPI.config.ApiConfig;
import com.example.WakaTimeAPI.model.CodingNow;
import com.example.WakaTimeAPI.model.Heartbeat;
import com.example.WakaTimeAPI.model.HeartbeatsResponse;
import com.example.WakaTimeAPI.model.UserAgent;
import com.example.WakaTimeAPI.model.UserAgentsResponse;
import com.example.WakaTimeAPI.util.WakaTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * "Codando agora": em que projeto você está mexendo no editor NESTE momento.
 *
 * De onde vem: a extensão do WakaTime (VS Code, IntelliJ...) manda um
 * heartbeat a cada ~2 min enquanto você digita, com projeto, linguagem,
 * branch e arquivo. Aqui lemos os heartbeats do dia
 * (GET /users/current/heartbeats?date=AAAA-MM-DD) e olhamos o último: se ele
 * chegou há até wakatime.coding-now.active-minutes, você está codando.
 *
 * Heartbeats são PRIVADOS: precisam do token OAuth com o escopo
 * read_heartbeats (ver WakaTimeClient).
 *
 * Privacidade:
 *   • o arquivo (entity) nunca sai daqui: nem é lido (ver model/Heartbeat);
 *   • projeto fora de wakatime.coding-now.public-projects vira "projeto
 *     privado", sem nome e sem branch (ou some, com show-private=false);
 *   • só heartbeats de arquivo (type "file") contam: os de navegador
 *     (domínios visitados) são descartados.
 *
 * Custo: a resposta é igual para todo mundo, então fica 60 s em memória. O
 * token é usado no máximo uma vez por minuto, por mais visitantes que estejam
 * com a página aberta.
 */
@Service
public class CodingNowService {

	private static final Logger log = LoggerFactory.getLogger(CodingNowService.class);

	private static final long CACHE_MS = 60 * 1000;
	private static final long EDITORS_CACHE_MS = 60 * 60 * 1000;
	private static final String SCOPE = "read_heartbeats";

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
	private WakaTimeClient wakaTimeClient;

	@Autowired
	private WakaTimeFormat fmt;

	private CodingNow cached;
	private long cachedAt;

	private Map<String, String> editors = Map.of();
	private long editorsAt;

	/**
	 * synchronized: vários visitantes no mesmo instante fazem uma chamada só.
	 *
	 * @throws WakaTimeException token faltando, inválido ou sem read_heartbeats
	 */
	public synchronized CodingNow getCodingNow() {
		if (cached != null && System.currentTimeMillis() - cachedAt < CACHE_MS) {
			return cached;
		}
		cached = build();
		cachedAt = System.currentTimeMillis();
		return cached;
	}

	private CodingNow build() {
		ZoneId zone = apiConfig.getZoneId();
		long breakSeconds = apiConfig.getBreakMinutes() * 60L;
		ZonedDateTime now = ZonedDateTime.now(zone);
		LocalDate today = now.toLocalDate();

		List<Heartbeat> ofToday = heartbeatsOfDay(today);
		Map<String, String> editorById = editorsMap();

		// Logo depois da meia-noite, a sessão pode ter começado ontem
		List<Heartbeat> heartbeats = ofToday;
		int minutesSinceMidnight = now.getHour() * 60 + now.getMinute();
		if (minutesSinceMidnight < apiConfig.getBreakMinutes() + apiConfig.getActiveMinutes()) {
			heartbeats = new ArrayList<>(heartbeatsOfDay(today.minusDays(1)));
			heartbeats.addAll(ofToday);
		}

		long todaySeconds = sumSeconds(ofToday, breakSeconds);
		if (heartbeats.isEmpty()) {
			return new CodingNow(false, todaySeconds, null);
		}
		Heartbeat last = heartbeats.get(heartbeats.size() - 1);

		Set<String> allowed = apiConfig.getPublicProjects();
		boolean isPublic = apiConfig.isShowProjects()
				&& last.getProject() != null
				&& (allowed.contains("*") || allowed.contains(last.getProject().toLowerCase()));
		if (!isPublic && !apiConfig.isShowPrivate()) {
			return new CodingNow(false, todaySeconds, null);
		}

		double nowSeconds = Instant.now().toEpochMilli() / 1000.0;
		boolean active = nowSeconds - last.getTime() <= apiConfig.getActiveMinutes() * 60L;

		// Tempo de hoje só neste projeto (sem os heartbeats de ontem)
		List<Heartbeat> sameProject = ofToday.stream()
				.filter(hb -> last.getProject() != null && last.getProject().equals(hb.getProject()))
				.toList();

		CodingNow.Last info = new CodingNow.Last(
				isPublic,
				isPublic ? last.getProject() : null,
				isPublic && last.getBranch() != null && !last.getBranch().isBlank() ? last.getBranch() : null,
				last.getLanguage() != null && !last.getLanguage().isBlank() ? last.getLanguage() : null,
				editorById.get(last.getUser_agent_id()),
				Math.round(last.getTime() * 1000),
				Math.round(sessionStart(heartbeats, breakSeconds) * 1000),
				sumSeconds(sameProject, breakSeconds));

		return new CodingNow(active, todaySeconds, info);
	}

	// Heartbeats de arquivo de um dia, em ordem de tempo
	private List<Heartbeat> heartbeatsOfDay(LocalDate day) {
		HeartbeatsResponse response = wakaTimeClient.get("/heartbeats?date=" + day, HeartbeatsResponse.class, SCOPE);
		if (response == null || response.getData() == null) {
			return List.of();
		}
		return response.getData().stream()
				.filter(hb -> "file".equals(hb.getType()) && hb.getTime() > 0)
				.sorted(Comparator.comparingDouble(Heartbeat::getTime))
				.toList();
	}

	// id do user agent → nome do editor. Muda pouco: 1 h de cache.
	// Se falhar, o cartão só fica sem o nome do editor.
	private Map<String, String> editorsMap() {
		if (System.currentTimeMillis() - editorsAt < EDITORS_CACHE_MS) {
			return editors;
		}
		try {
			UserAgentsResponse response = wakaTimeClient.get("/user_agents", UserAgentsResponse.class, SCOPE);
			Map<String, String> map = new HashMap<>();
			if (response != null && response.getData() != null) {
				for (UserAgent agent : response.getData()) {
					if (agent.getId() != null && agent.getEditor() != null) {
						map.put(agent.getId(), fmt.editorName(agent.getEditor()));
					}
				}
			}
			editors = map;
			editorsAt = System.currentTimeMillis();
		} catch (RuntimeException e) {
			log.warn("Codando agora (editores): {}", e.getMessage());
		}
		return editors;
	}

	// Tempo somado como o WakaTime faz: o intervalo entre dois heartbeats
	// seguidos conta se for menor que a pausa. É aproximado (o dashboard pode
	// diferir em alguns minutos).
	private static long sumSeconds(List<Heartbeat> heartbeats, long breakSeconds) {
		double total = 0;
		for (int i = 1; i < heartbeats.size(); i++) {
			double gap = heartbeats.get(i).getTime() - heartbeats.get(i - 1).getTime();
			if (gap < breakSeconds) {
				total += gap;
			}
		}
		return Math.round(total);
	}

	// Começo da sessão atual: volta do último heartbeat até achar uma pausa
	private static double sessionStart(List<Heartbeat> heartbeats, long breakSeconds) {
		int i = heartbeats.size() - 1;
		while (i > 0 && heartbeats.get(i).getTime() - heartbeats.get(i - 1).getTime() < breakSeconds) {
			i--;
		}
		return heartbeats.get(i).getTime();
	}
}
