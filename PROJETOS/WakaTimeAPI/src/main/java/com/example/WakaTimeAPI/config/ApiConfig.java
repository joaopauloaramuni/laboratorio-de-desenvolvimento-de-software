package com.example.WakaTimeAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Configurações da API do WakaTime, lidas do application.properties.
 *
 * A partir de 01/11/2026 a API key do WakaTime só serve para os plugins
 * mandarem dados; para ler, a API exige um token OAuth, enviado no cabeçalho
 * "Authorization: Bearer <token>". O token é gerado pelo
 * scripts/WakaTimeOAuth.java (ver README.md), com os escopos:
 *   read_stats      → GET /users/current/stats/{periodo}   (painel)
 *   read_summaries  → GET /users/current/status_bar/today  (indicador "Hoje")
 *   read_heartbeats → GET /users/current/heartbeats e /user_agents (Codando agora)
 *
 * Sem o token (ou se ele falhar), o painel usa o perfil público de
 * wakatime.username, sem autenticação: GET /users/{username}/stats/{periodo}.
 * Nesse modo o "Hoje" e o "Codando agora" não aparecem (dados privados).
 *
 * Valores que ainda estão como SEU_..._AQUI (o modelo do
 * application.properties) contam como vazios.
 */
@Configuration
public class ApiConfig {

	/* =====================================================================
	   API e OAuth
	   ===================================================================== */

	// https://api.wakatime.com/api/v1/users/current (a conta dona do token)
	@Value("${wakatime.api.url}")
	private String apiUrl;

	@Value("${wakatime.oauth.access-token:}")
	private String accessToken;

	@Value("${wakatime.oauth.app-id:}")
	private String appId;

	@Value("${wakatime.oauth.app-secret:}")
	private String appSecret;

	@Value("${wakatime.oauth.refresh-token:}")
	private String refreshToken;

	@Value("${wakatime.oauth.token-url:https://wakatime.com/oauth/token}")
	private String tokenUrl;

	// Opcional: usuário com perfil público, usado só sem o token (ou se ele falhar)
	@Value("${wakatime.username:}")
	private String username;

	/* =====================================================================
	   Painel de estatísticas
	   ===================================================================== */

	// Período aberto ao carregar a página
	@Value("${wakatime.panel.default-range:all_time}")
	private String defaultRange;

	// Link do seu perfil público (vazio esconde o botão)
	@Value("${wakatime.panel.profile-url:}")
	private String profileUrl;

	// Quantas linguagens aparecem na lista (o resto vira "+ N outras")
	@Value("${wakatime.panel.max-languages:10}")
	private int maxLanguages;

	// Linguagens que não aparecem, separadas por vírgula (ex.: Text,Other)
	@Value("${wakatime.panel.hidden-languages:}")
	private String hiddenLanguages;

	// false esconde os nomes dos projetos (no painel e no Codando agora)
	@Value("${wakatime.panel.show-projects:true}")
	private boolean showProjects;

	/* =====================================================================
	   Codando agora (heartbeats)
	   ===================================================================== */

	// Fuso da sua conta no WakaTime: é por ele que a API separa os dias
	@Value("${wakatime.coding-now.timezone:America/Sao_Paulo}")
	private String timezone;

	// Último heartbeat há até tantos minutos = "codando agora"
	@Value("${wakatime.coding-now.active-minutes:10}")
	private int activeMinutes;

	// Pausa maior que isso encerra a sessão (mesmo timeout padrão do WakaTime)
	@Value("${wakatime.coding-now.break-minutes:15}")
	private int breakMinutes;

	// De quanto em quanto tempo a página pergunta de novo
	@Value("${wakatime.coding-now.refresh-seconds:60}")
	private int refreshSeconds;

	// Projetos que aparecem pelo nome (com a branch), separados por vírgula.
	// "*" = todos. Os outros viram "projeto privado".
	@Value("${wakatime.coding-now.public-projects:*}")
	private String publicProjects;

	// Projeto fora da lista: true = "projeto privado"; false = o cartão some
	@Value("${wakatime.coding-now.show-private:true}")
	private boolean showPrivate;

	/* ---------- API e OAuth ---------- */

	public String getApiUrl() {
		return apiUrl;
	}

	public String getAccessToken() {
		return clean(accessToken);
	}

	public String getAppId() {
		return clean(appId);
	}

	public String getAppSecret() {
		return clean(appSecret);
	}

	public String getRefreshToken() {
		return clean(refreshToken);
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	/** Sem o access token não há como ler os dados. */
	public boolean hasAccessToken() {
		return !getAccessToken().isEmpty();
	}

	public String getUsername() {
		return clean(username);
	}

	/** Perfil público configurado (reserva para quando não há token). */
	public boolean hasUsername() {
		return !getUsername().isEmpty();
	}

	/**
	 * Estatísticas do perfil público, sem token:
	 * https://api.wakatime.com/api/v1/users/{username}/stats/{periodo}
	 */
	public String getPublicStatsUrl(String range) {
		String base = apiUrl.replaceFirst("/current/?$", "");
		return base + "/" + URLEncoder.encode(getUsername(), StandardCharsets.UTF_8) + "/stats/" + range;
	}

	/** A renovação automática só funciona com as três juntas. */
	public boolean canRenewToken() {
		return !getAppId().isEmpty() && !getAppSecret().isEmpty() && !getRefreshToken().isEmpty();
	}

	/* ---------- Painel ---------- */

	public String getDefaultRange() {
		return defaultRange;
	}

	public String getProfileUrl() {
		return clean(profileUrl);
	}

	public int getMaxLanguages() {
		return maxLanguages;
	}

	public Set<String> getHiddenLanguages() {
		return splitList(hiddenLanguages).stream().collect(Collectors.toSet());
	}

	public boolean isShowProjects() {
		return showProjects;
	}

	/* ---------- Codando agora ---------- */

	public ZoneId getZoneId() {
		return ZoneId.of(timezone);
	}

	public int getActiveMinutes() {
		return activeMinutes;
	}

	public int getBreakMinutes() {
		return breakMinutes;
	}

	public int getRefreshSeconds() {
		return refreshSeconds;
	}

	/** Em minúsculas, para comparar sem diferenciar maiúsculas. */
	public Set<String> getPublicProjects() {
		return splitList(publicProjects).stream().map(String::toLowerCase).collect(Collectors.toSet());
	}

	public boolean isShowPrivate() {
		return showPrivate;
	}

	/* ---------- Auxiliares ---------- */

	// "  abc " → "abc"; null ou "SEU_ACCESS_TOKEN_AQUI" (não preenchido) → ""
	private static String clean(String value) {
		if (value == null) {
			return "";
		}
		String trimmed = value.trim();
		return trimmed.matches("SEU_.*_AQUI") ? "" : trimmed;
	}

	// "a, b,,c" → [a, b, c]
	private static List<String> splitList(String value) {
		if (value == null) {
			return List.of();
		}
		return Arrays.stream(value.split(","))
				.map(String::trim)
				.filter(item -> !item.isEmpty())
				.toList();
	}
}
