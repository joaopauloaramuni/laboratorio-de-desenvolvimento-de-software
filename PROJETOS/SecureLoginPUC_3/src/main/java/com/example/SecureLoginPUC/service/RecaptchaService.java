package com.example.SecureLoginPUC.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.example.SecureLoginPUC.config.UserConfig;

@Service
public class RecaptchaService {

    private final UserConfig userConfig;

    private final HttpClient httpClient =
            HttpClient.newHttpClient();


    public RecaptchaService(UserConfig userConfig) {
        this.userConfig = userConfig;
    }


    public boolean validate(String captchaResponse) {

        if (captchaResponse == null
                || captchaResponse.isBlank()) {

            return false;
        }

        try {

            String body =
                    "secret="
                            + URLEncoder.encode(
                                    userConfig.getRecaptchaSecretKey(),
                                    StandardCharsets.UTF_8)
                            + "&response="
                            + URLEncoder.encode(
                                    captchaResponse,
                                    StandardCharsets.UTF_8);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(
                                    "https://www.google.com/recaptcha/api/siteverify"))
                            .header(
                                    "Content-Type",
                                    "application/x-www-form-urlencoded")
                            .POST(
                                    HttpRequest.BodyPublishers.ofString(body))
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString());

            return response.body()
                    .contains("\"success\": true")
                    || response.body()
                    .contains("\"success\":true");

        } catch (Exception e) {

            System.out.println(
                    "Erro ao validar reCAPTCHA: "
                            + e.getMessage());

            return false;
        }
    }
}