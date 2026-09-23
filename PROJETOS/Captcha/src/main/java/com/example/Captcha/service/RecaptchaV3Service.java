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
 * GOOGLE reCAPTCHA v3
 * ---------------------------------------------------------
 * O v3 não mostra desafio nenhum. O Google devolve uma
 * pontuação (score) de 0.0 (robô) a 1.0 (humano) e VOCÊ
 * decide a partir de quanto aceita a requisição.
 *
 * Além do "success", confira sempre:
 *  - score  >= score mínimo configurado
 *  - action == a ação usada no grecaptcha.execute(...)
 *
 * Campo enviado pelo formulário: g-recaptcha-response
 * =========================================================
 */
@Service
public class RecaptchaV3Service {

    private static final String URL_VERIFICACAO =
            "https://www.google.com/recaptcha/api/siteverify";

    /** Resultado da validação, com o score para exibir na tela. */
    public record ResultadoV3(boolean valido, double score, String motivo) {
    }

    @Value("${recaptcha.v3.secret-key}")
    private String secretKey;

    @Value("${recaptcha.v3.score-minimo}")
    private double scoreMinimo;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    public RecaptchaV3Service(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResultadoV3 validar(String token, String acaoEsperada) {

        if (token == null || token.isBlank()) {
            return new ResultadoV3(false, 0.0, "Token não enviado.");
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

            // Resposta: { "success": true, "score": 0.9, "action": "contato", ... }
            JsonNode json = objectMapper.readTree(response.body());

            boolean sucesso = json.path("success").asBoolean(false);
            double score = json.path("score").asDouble(0.0);
            String acao = json.path("action").asText("");

            if (!sucesso) {
                return new ResultadoV3(false, score,
                        "Token inválido ou expirado: " + json.path("error-codes"));
            }
            if (!acaoEsperada.equals(acao)) {
                return new ResultadoV3(false, score,
                        "Ação diferente da esperada (" + acao + ").");
            }
            if (score < scoreMinimo) {
                return new ResultadoV3(false, score,
                        "Score abaixo do mínimo de " + scoreMinimo + ".");
            }
            return new ResultadoV3(true, score, "OK");

        } catch (Exception e) {
            System.out.println("Erro ao validar reCAPTCHA v3: " + e.getMessage());
            return new ResultadoV3(false, 0.0, "Erro ao falar com o Google.");
        }
    }
}
