package com.example.WakaTimeAPI.service;

import com.example.WakaTimeAPI.config.ApiConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Chamadas à API do WakaTime com o token OAuth, usadas pelo painel
 * (WakaTimeApiService) e pelo Codando agora (CodingNowService).
 *
 * O token vai no cabeçalho "Authorization: Bearer <token>", como pede a
 * documentação de autenticação do WakaTime (https://wakatime.com/developers#authentication).
 *
 * Token expirado ou revogado (HTTP 401): se o App ID, o App Secret e o
 * refresh token estiverem configurados, pede um token novo e tenta de novo.
 * O token novo fica só na memória; para valer de vez, rode o
 * scripts/WakaTimeOAuth.java de novo e troque o access token.
 */
@Service
public class WakaTimeClient {

	private static final Logger log = LoggerFactory.getLogger(WakaTimeClient.class);

	private static final String GENERATE = "Gere outro com: java scripts/WakaTimeOAuth.java";

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
	private RestTemplate restTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	// Token renovado nesta execução (null = usar o do application.properties)
	private volatile String renewedToken;

	/**
	 * GET em {wakatime.api.url}{path}, ex.: get("/stats/all_time", ...).
	 *
	 * @param scope escopo que o endpoint exige, para a mensagem do HTTP 403
	 * @throws WakaTimeException token faltando, inválido ou sem o escopo
	 */
	public <T> T get(String path, Class<T> type, String scope) {
		if (!apiConfig.hasAccessToken()) {
			throw new WakaTimeException(WakaTimeException.Reason.MISSING_TOKEN,
					"Configure o wakatime.oauth.access-token no application.properties. "
							+ "Para gerar: java scripts/WakaTimeOAuth.java");
		}

		String url = apiConfig.getApiUrl() + path;
		String token = currentToken();
		try {
			return request(url, token, type);
		} catch (HttpClientErrorException.Unauthorized e) {
			if (!apiConfig.canRenewToken()) {
				throw invalidToken(e);
			}
			try {
				return request(url, renewToken(token), type);
			} catch (HttpClientErrorException.Unauthorized again) {
				throw invalidToken(again);
			} catch (HttpClientErrorException.Forbidden again) {
				throw forbidden(scope, again);
			}
		} catch (HttpClientErrorException.Forbidden e) {
			throw forbidden(scope, e);
		}
	}

	/**
	 * GET sem autenticação, para o perfil público (wakatime.username).
	 *
	 * @throws WakaTimeException perfil privado, período indisponível ou usuário inexistente
	 */
	public <T> T getPublic(String url, Class<T> type) {
		try {
			return restTemplate.getForObject(url, type);
		} catch (HttpClientErrorException.NotFound e) {
			throw new WakaTimeException(WakaTimeException.Reason.PUBLIC_PROFILE,
					"Usuário do WakaTime não encontrado. Confira o wakatime.username no application.properties.", e);
		} catch (HttpClientErrorException e) {
			throw new WakaTimeException(WakaTimeException.Reason.PUBLIC_PROFILE,
					"Perfil privado (ou período indisponível no perfil público). Deixe as estatísticas públicas em "
							+ "wakatime.com/settings/profile ou configure o wakatime.oauth.access-token.", e);
		}
	}

	private String currentToken() {
		String renewed = renewedToken;
		return renewed != null ? renewed : apiConfig.getAccessToken();
	}

	private <T> T request(String url, String token, Class<T> type) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), type).getBody();
	}

	/**
	 * Troca o refresh token por um access token novo. synchronized: se várias
	 * requisições receberem 401 ao mesmo tempo, só a primeira renova.
	 */
	private synchronized String renewToken(String expiredToken) {
		String renewed = renewedToken;
		if (renewed != null && !renewed.equals(expiredToken)) {
			return renewed; // outra requisição já renovou
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("client_id", apiConfig.getAppId());
		form.add("client_secret", apiConfig.getAppSecret());
		form.add("grant_type", "refresh_token");
		form.add("refresh_token", apiConfig.getRefreshToken());

		String body;
		try {
			body = restTemplate.postForObject(apiConfig.getTokenUrl(), new HttpEntity<>(form, headers), String.class);
		} catch (RestClientException e) {
			log.error("WakaTime: não foi possível renovar o token: {}", e.getMessage());
			throw new WakaTimeException(WakaTimeException.Reason.INVALID_TOKEN,
					"O token do WakaTime expirou e a renovação falhou. " + GENERATE, e);
		}

		String accessToken = parseTokenResponse(body).get("access_token");
		if (accessToken == null || accessToken.isBlank()) {
			throw new WakaTimeException(WakaTimeException.Reason.INVALID_TOKEN,
					"O WakaTime renovou o token, mas a resposta veio sem access_token. " + GENERATE);
		}

		renewedToken = accessToken;
		log.warn("WakaTime: token renovado. Rode java scripts/WakaTimeOAuth.java para atualizar o application.properties.");
		return accessToken;
	}

	// O WakaTime pode responder em JSON ou em form-urlencoded
	private Map<String, String> parseTokenResponse(String body) {
		Map<String, String> values = new HashMap<>();
		if (body == null) {
			return values;
		}
		try {
			JsonNode json = objectMapper.readTree(body);
			json.fields().forEachRemaining(entry -> values.put(entry.getKey(), entry.getValue().asText()));
		} catch (Exception notJson) {
			for (String pair : body.split("&")) {
				String[] parts = pair.split("=", 2);
				if (parts.length == 2) {
					values.put(URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
							URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
				}
			}
		}
		return values;
	}

	private WakaTimeException invalidToken(Exception cause) {
		return new WakaTimeException(WakaTimeException.Reason.INVALID_TOKEN,
				"Token OAuth do WakaTime inválido ou revogado. " + GENERATE, cause);
	}

	private WakaTimeException forbidden(String scope, Exception cause) {
		return new WakaTimeException(WakaTimeException.Reason.FORBIDDEN,
				"Sem permissão: o token precisa do escopo " + scope + ". " + GENERATE, cause);
	}
}
