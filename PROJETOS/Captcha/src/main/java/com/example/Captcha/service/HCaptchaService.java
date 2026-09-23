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
 * hCAPTCHA
 * ---------------------------------------------------------
 * Alternativa ao reCAPTCHA v2 com foco em privacidade.
 * A validação é praticamente igual à do Google, mudando
 * só a URL e o nome do campo.
 *
 * Campo enviado pelo formulário: h-captcha-response
 * =========================================================
 */
@Service
public class HCaptchaService {

    private static final String URL_VERIFICACAO = "https://api.hcaptcha.com/siteverify";

    @Value("${hcaptcha.secret-key}")
    private String secretKey;

    @Value("${hcaptcha.site-key}")
    private String siteKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    public HCaptchaService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean validar(String token) {

        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            // O "sitekey" é opcional, mas garante que o token veio do SEU widget
            String body = "secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8)
                    + "&response=" + URLEncoder.encode(token, StandardCharsets.UTF_8)
                    + "&sitekey=" + URLEncoder.encode(siteKey, StandardCharsets.UTF_8);

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
            System.out.println("Erro ao validar hCaptcha: " + e.getMessage());
            return false;
        }
    }
}
