// Gera o access token e o refresh token do WakaTime para este projeto.
// Uso (na raiz do projeto): java scripts/WakaTimeOAuth.java
//
// É um arquivo Java único, rodado direto pelo java (Java 11+), sem compilar e
// sem dependências; não faz parte da aplicação Spring Boot.
//
// Precisa do App ID e do App Secret (wakatime.oauth.app-id e
// wakatime.oauth.app-secret no application.properties) e do Redirect URI
// http://localhost:8787/callback cadastrado no app em https://wakatime.com/apps.
// Abre a autorização no navegador, recebe o callback aqui e imprime os tokens
// para colar no application.properties.
//
// Por quê: a partir de 01/11/2026 a API key do WakaTime só serve para os
// plugins mandarem dados. Para ler as estatísticas e os heartbeats (Codando
// agora), a API exige um token OAuth.

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WakaTimeOAuth {

	static final Path PROPERTIES = Path.of("src/main/resources/application.properties");
	static final int PORTA = 8787;
	static final String REDIRECT_URI = "http://localhost:" + PORTA + "/callback";

	// Escopos separados por vírgula (só leitura):
	//   read_stats      → GET /users/current/stats/{periodo}       (painel)
	//   read_summaries  → GET /users/current/status_bar/today      (indicador "Hoje")
	//   read_heartbeats → GET /users/current/heartbeats e /user_agents (Codando agora)
	// Se alguma rota der 403, acrescente o que a documentação de OAuth do
	// WakaTime pedir e rode de novo.
	static final String SCOPE = "read_stats,read_summaries,read_heartbeats";

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		if (!Files.exists(PROPERTIES)) {
			System.err.println("Não achei o " + PROPERTIES + ". Rode na raiz do projeto.");
			System.exit(1);
		}
		try (InputStream in = Files.newInputStream(PROPERTIES)) {
			props.load(new java.io.InputStreamReader(in, StandardCharsets.UTF_8));
		}

		String clientId = valor(props, "wakatime.oauth.app-id");
		String clientSecret = valor(props, "wakatime.oauth.app-secret");
		if (clientId.isEmpty() || clientSecret.isEmpty()) {
			System.err.println("Preencha wakatime.oauth.app-id e wakatime.oauth.app-secret no application.properties.");
			System.exit(1);
		}

		byte[] bytes = new byte[16];
		new SecureRandom().nextBytes(bytes);
		String state = HexFormat.of().formatHex(bytes);

		String url = "https://wakatime.com/oauth/authorize?" + form(Map.of(
				"client_id", clientId,
				"response_type", "code",
				"redirect_uri", REDIRECT_URI,
				"scope", SCOPE,
				"state", state));

		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress("localhost", PORTA), 0);
		} catch (BindException e) {
			System.err.println("A porta " + PORTA + " está em uso. Feche o que estiver usando e rode de novo.");
			System.exit(1);
			return;
		}

		server.createContext("/callback", exchange -> {
			Map<String, String> query = parseForm(exchange.getRequestURI().getRawQuery());
			try {
				if (!state.equals(query.get("state"))) {
					responder(exchange, 400, "State inválido. Rode o script de novo.");
					return;
				}
				String code = query.get("code");
				if (code == null || code.isEmpty()) {
					responder(exchange, 400, "Autorização negada: " + query.getOrDefault("error", "sem code"));
					return;
				}

				Map<String, String> dados = trocarCodePorToken(clientId, clientSecret, code);

				System.out.println();
				System.out.println("Cole no application.properties:");
				System.out.println();
				System.out.println("wakatime.oauth.access-token=" + dados.get("access_token"));
				System.out.println("wakatime.oauth.refresh-token=" + dados.get("refresh_token"));
				System.out.println();
				System.out.println("Escopo: " + dados.getOrDefault("scope", "?") + "  |  expira em: "
						+ dados.getOrDefault("expires_at", dados.getOrDefault("expires_in", "?")));
				System.out.println();
				responder(exchange, 200, "Pronto! Os tokens estão no terminal. Pode fechar esta aba.");
			} catch (Exception e) {
				System.err.println("Erro ao trocar o code pelo token: " + e.getMessage());
				responder(exchange, 500, "Deu erro. Veja o terminal.");
			} finally {
				server.stop(0);
			}
		});
		server.start();

		System.out.println("Abrindo a autorização no navegador...");
		System.out.println("Se não abrir, acesse:");
		System.out.println(url);
		System.out.println();
		abrirNavegador(url);
	}

	// Lê a propriedade. SEU_..._AQUI (não preenchido) conta como vazio.
	static String valor(Properties props, String chave) {
		String bruto = props.getProperty(chave, "").trim();
		return bruto.matches("SEU_.*_AQUI") ? "" : bruto;
	}

	static Map<String, String> trocarCodePorToken(String clientId, String clientSecret, String code)
			throws IOException, InterruptedException {
		Map<String, String> corpo = new LinkedHashMap<>();
		corpo.put("client_id", clientId);
		corpo.put("client_secret", clientSecret);
		corpo.put("redirect_uri", REDIRECT_URI);
		corpo.put("grant_type", "authorization_code");
		corpo.put("code", code);

		HttpRequest req = HttpRequest.newBuilder(URI.create("https://wakatime.com/oauth/token"))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Accept", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(form(corpo)))
				.build();
		HttpResponse<String> resp = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
		if (resp.statusCode() / 100 != 2) {
			throw new IOException("HTTP " + resp.statusCode() + ": " + resp.body());
		}
		Map<String, String> dados = parseTokenResponse(resp.body());
		if (!dados.containsKey("access_token")) {
			throw new IOException("Resposta sem access_token: " + resp.body());
		}
		return dados;
	}

	// O WakaTime pode responder em JSON ou em form-urlencoded. O JSON é simples
	// (um objeto sem aninhamento), então uma expressão regular basta.
	static Map<String, String> parseTokenResponse(String body) {
		String texto = body.trim();
		if (!texto.startsWith("{")) {
			return parseForm(texto);
		}
		Map<String, String> dados = new LinkedHashMap<>();
		Matcher m = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(\"((?:[^\"\\\\]|\\\\.)*)\"|[^,}\\s]+)").matcher(texto);
		while (m.find()) {
			dados.put(m.group(1), m.group(3) != null ? m.group(3) : m.group(2));
		}
		return dados;
	}

	static Map<String, String> parseForm(String texto) {
		Map<String, String> dados = new LinkedHashMap<>();
		if (texto == null || texto.isEmpty()) {
			return dados;
		}
		for (String par : texto.split("&")) {
			String[] partes = par.split("=", 2);
			dados.put(URLDecoder.decode(partes[0], StandardCharsets.UTF_8),
					partes.length > 1 ? URLDecoder.decode(partes[1], StandardCharsets.UTF_8) : "");
		}
		return dados;
	}

	static String form(Map<String, String> valores) {
		StringBuilder sb = new StringBuilder();
		valores.forEach((k, v) -> {
			if (sb.length() > 0) sb.append('&');
			sb.append(URLEncoder.encode(k, StandardCharsets.UTF_8)).append('=')
					.append(URLEncoder.encode(v, StandardCharsets.UTF_8));
		});
		return sb.toString();
	}

	static void responder(HttpExchange exchange, int status, String mensagem) throws IOException {
		byte[] corpo = mensagem.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
		exchange.sendResponseHeaders(status, corpo.length);
		try (OutputStream out = exchange.getResponseBody()) {
			out.write(corpo);
		}
	}

	static void abrirNavegador(String url) {
		String os = System.getProperty("os.name", "").toLowerCase();
		String[] comando = os.contains("win") ? new String[]{"rundll32", "url.dll,FileProtocolHandler", url}
				: os.contains("mac") ? new String[]{"open", url}
				: new String[]{"xdg-open", url};
		try {
			new ProcessBuilder(comando)
					.redirectOutput(ProcessBuilder.Redirect.DISCARD)
					.redirectError(ProcessBuilder.Redirect.DISCARD)
					.start();
		} catch (IOException e) {
			// Sem navegador (ex.: servidor): o link já foi impresso acima
		}
	}
}
