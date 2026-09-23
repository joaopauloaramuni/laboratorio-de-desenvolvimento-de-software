package com.example.Captcha.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * =========================================================
 * CLOUDFLARE TURNSTILE
 * ---------------------------------------------------------
 * Na maioria das vezes o usuário não precisa fazer nada:
 * o widget verifica o navegador sozinho. É gratuito e não
 * exige que o site use a Cloudflare.
 *
 * Campo enviado pelo formulário: cf-turnstile-response
 * =========================================================
 */
@Service
public class TurnstileService {

    private static final String URL_VERIFICACAO =
            "https://challenges.cloudflare.com/turnstile/v0/siteverify";

    @Value("${turnstile.secret-key}")
    private String secretKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    public TurnstileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean validar(String token, String ipDoUsuario) {

        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            // "remoteip" é opcional e ajuda a Cloudflare a detectar abuso
            String body = "secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8)
                    + "&response=" + URLEncoder.encode(token, StandardCharsets.UTF_8)
                    + "&remoteip=" + URLEncoder.encode(ipDoUsuario, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_VERIFICACAO))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode json = objectMapper.readTree(response.body());

            return json.path("success").asBoolean(false);

        } catch (Exception e) {
            System.out.println("Erro ao validar Turnstile: " + e.getMessage());
            return false;
        }
    }
}
