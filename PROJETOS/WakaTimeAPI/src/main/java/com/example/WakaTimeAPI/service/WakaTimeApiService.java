package com.example.WakaTimeAPI.service;

import com.example.WakaTimeAPI.config.ApiConfig;
import com.example.WakaTimeAPI.model.Data;
import com.example.WakaTimeAPI.model.StatsItem;
import com.example.WakaTimeAPI.model.StatsView;
import com.example.WakaTimeAPI.model.WakaTimeApiResponse;
import com.example.WakaTimeAPI.util.WakaTimeFormat;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * Estatísticas do painel. Autenticado sempre que der, para funcionar mesmo
 * com o perfil PRIVADO:
 *   GET /users/current/stats/{periodo}    (token, escopo read_stats)
 *   GET /users/current/status_bar/today   (token, escopo read_summaries, indicador "Hoje")
 *
 * Sem o token, ou se ele falhar (inválido, sem escopo), e com o
 * wakatime.username configurado, usa o perfil público, sem autenticação:
 *   GET /users/{username}/stats/{periodo}
 * Nesse modo não há "Hoje" (dado privado) e o perfil precisa estar público
 * em wakatime.com/settings/profile.
 *
 * Cada período fica 10 minutos em cache: recarregar a página ou trocar de
 * período e voltar não chama o WakaTime de novo.
 */
@Service
public class WakaTimeApiService {

	private static final Logger log = LoggerFactory.getLogger(WakaTimeApiService.class);

	/** Períodos aceitos pela API do WakaTime → rótulo do botão. */
	public static final Map<String, String> RANGES = new LinkedHashMap<>();

	static {
		RANGES.put("last_7_days", "7 dias");
		RANGES.put("last_30_days", "30 dias");
		RANGES.put("last_6_months", "6 meses");
		RANGES.put("last_year", "1 ano");
		RANGES.put("all_time", "Desde sempre");
	}

	private static final long CACHE_MS = 10 * 60 * 1000;
	private static final int MIN_SECONDS = 60; // menos de 1 minuto não aparece

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
	private WakaTimeClient wakaTimeClient;

	@Autowired
	private WakaTimeFormat fmt;

	private record CacheEntry(long at, StatsView stats) {
	}

	private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

	/** Período válido: o pedido, se estiver em RANGES; senão, o padrão. */
	public String validRange(String range) {
		if (range != null && RANGES.containsKey(range)) {
			return range;
		}
		return RANGES.containsKey(apiConfig.getDefaultRange()) ? apiConfig.getDefaultRange() : "all_time";
	}

	/**
	 * @throws WakaTimeException token faltando, inválido ou sem escopo
	 */
	public StatsView getStats(String range) {
		CacheEntry cached = cache.get(range);
		if (cached != null && System.currentTimeMillis() - cached.at() < CACHE_MS) {
			return cached.stats();
		}

		String source = "oauth";
		WakaTimeApiResponse response = null;
		if (apiConfig.hasAccessToken()) {
			try {
				response = wakaTimeClient.get("/stats/" + range, WakaTimeApiResponse.class, "read_stats");
			} catch (WakaTimeException e) {
				if (!apiConfig.hasUsername()) {
					throw e;
				}
				log.warn("WakaTime (stats com token), usando o perfil público: {}", e.getMessage());
				source = "public";
			}
		} else if (apiConfig.hasUsername()) {
			source = "public";
		} else {
			throw new WakaTimeException(WakaTimeException.Reason.MISSING_TOKEN,
					"Configure o wakatime.oauth.access-token no application.properties "
							+ "(ou o wakatime.username, para usar o perfil público). "
							+ "Para gerar o token: java scripts/WakaTimeOAuth.java");
		}
		if ("public".equals(source)) {
			response = wakaTimeClient.getPublic(apiConfig.getPublicStatsUrl(range), WakaTimeApiResponse.class);
		}
		Data data = response == null ? null : response.getData();

		// HTTP 202: o WakaTime ainda está calculando esse período (não vai para o cache)
		if (data == null || "pending_update".equals(data.getStatus()) || data.getLanguages() == null) {
			StatsView pending = new StatsView();
			pending.setRange(range);
			pending.setPending(true);
			return pending;
		}

		StatsView stats = normalize(range, data);
		stats.setSource(source);
		if ("oauth".equals(source)) {
			stats.setTodaySeconds(fetchTodaySeconds());
		}
		cache.put(range, new CacheEntry(System.currentTimeMillis(), stats));
		return stats;
	}

	// "Hoje" (status_bar/today). Se falhar (ex.: token sem read_summaries), o
	// painel mostra o editor principal no lugar.
	private Long fetchTodaySeconds() {
		try {
			JsonNode json = wakaTimeClient.get("/status_bar/today", JsonNode.class, "read_summaries");
			JsonNode seconds = json == null ? null : json.path("data").path("grand_total").path("total_seconds");
			return seconds != null && seconds.isNumber() ? Math.round(seconds.asDouble()) : null;
		} catch (RuntimeException e) {
			log.warn("WakaTime (hoje): {}", e.getMessage());
			return null;
		}
	}

	private StatsView normalize(String range, Data data) {
		StatsView stats = new StatsView();
		stats.setRange(range);

		// As porcentagens do WakaTime são sobre o total "including other language"
		double total = data.getTotal_seconds_including_other_language() > 0
				? data.getTotal_seconds_including_other_language() : data.getTotal_seconds();
		double average = data.getDaily_average_including_other_language() > 0
				? data.getDaily_average_including_other_language() : data.getDaily_average();
		stats.setTotalSeconds(Math.round(total));
		stats.setDailyAverage(Math.round(average));
		stats.setActiveDays(data.getDays_minus_holidays() > 0 ? data.getDays_minus_holidays() : null);
		stats.setStart(data.getStart());

		if (data.getBest_day() != null && data.getBest_day().getDate() != null) {
			stats.setBestDayDate(data.getBest_day().getDate());
			stats.setBestDaySeconds(Math.round(data.getBest_day().getTotal_seconds()));
		}

		// Linguagens: sem as escondidas, com a cor de cada uma
		Set<String> hidden = apiConfig.getHiddenLanguages();
		List<StatsItem> languages = data.getLanguages().stream()
				.filter(lang -> lang.getTotal_seconds() >= MIN_SECONDS && !hidden.contains(lang.getName()))
				.map(lang -> new StatsItem(lang.getName(), Math.round(lang.getTotal_seconds()),
						lang.getPercent(), fmt.color(lang.getName())))
				.toList();
		int max = Math.max(1, apiConfig.getMaxLanguages());
		List<StatsItem> others = languages.size() > max ? languages.subList(max, languages.size()) : List.of();
		stats.setLanguages(languages);
		stats.setTopLanguages(languages.subList(0, Math.min(max, languages.size())));
		stats.setRestCount(others.size());
		stats.setRestSeconds(others.stream().mapToLong(StatsItem::getSeconds).sum());
		stats.setRestPercent(others.stream().mapToDouble(StatsItem::getPercent).sum());

		stats.setEditors(toItems(data.getEditors(), 6, e -> e.getName(), e -> e.getTotal_seconds(), e -> e.getPercent()));
		stats.setOperatingSystems(toItems(data.getOperating_systems(), 5,
				os -> fmt.osName(os.getName()), os -> os.getTotal_seconds(), os -> os.getPercent()));
		stats.setCategories(toItems(data.getCategories(), 6,
				c -> fmt.categoryName(c.getName()), c -> c.getTotal_seconds(), c -> c.getPercent()));
		if (apiConfig.isShowProjects()) {
			stats.setProjects(toItems(data.getProjects(), 10, p -> p.getName(), p -> p.getTotal_seconds(), p -> p.getPercent()));
		}
		return stats;
	}

	// Editor, OperatingSystem, Category e Project têm os mesmos campos, mas
	// não uma interface em comum: quem chama diz de onde vem cada valor.
	private static <T> List<StatsItem> toItems(List<T> list, int limit, Function<T, String> name,
											   ToDoubleFunction<T> seconds, ToDoubleFunction<T> percent) {
		if (list == null) {
			return List.of();
		}
		return list.stream()
				.filter(item -> seconds.applyAsDouble(item) >= MIN_SECONDS)
				.limit(limit)
				.map(item -> new StatsItem(name.apply(item), Math.round(seconds.applyAsDouble(item)),
						percent.applyAsDouble(item), null))
				.toList();
	}
}
