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
 * GOOGLE reCAPTCHA v2 (CHECKBOX e INVISÍVEL)
 * ---------------------------------------------------------
 * As duas versões do v2 são validadas do mesmo jeito no
 * servidor. A única diferença é o par de chaves.
 *
 * Se o seu projeto usa só uma delas, apague o método e a
 * chave da outra.
 *
 * Campo enviado pelo formulário: g-recaptcha-response
 * =========================================================
 */
@Service
public class RecaptchaV2Service {

    private static final String URL_VERIFICACAO =
            "https://www.google.com/recaptcha/api/siteverify";

    @Value("${recaptcha.v2.checkbox.secret-key}")
    private String secretKeyCheckbox;

    @Value("${recaptcha.v2.invisivel.secret-key}")
    private String secretKeyInvisivel;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    public RecaptchaV2Service(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean validarCheckbox(String token) {
        return validar(token, secretKeyCheckbox);
    }

    public boolean validarInvisivel(String token) {
        return validar(token, secretKeyInvisivel);
    }

    private boolean validar(String token, String secretKey) {

        // O usuário não resolveu o captcha
        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            String body = "secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8)
                    + "&response=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_VERIFICACAO))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Resposta do Google: { "success": true, "hostname": "...", ... }
            JsonNode json = objectMapper.readTree(response.body());

            return json.path("success").asBoolean(false);

        } catch (Exception e) {
            System.out.println("Erro ao validar reCAPTCHA v2: " + e.getMessage());
            return false;
        }
    }
}
