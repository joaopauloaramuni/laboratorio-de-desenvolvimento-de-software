package com.example.ZapSender.service;

import com.example.ZapSender.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZapSenderService {

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Envia uma mensagem de template via WhatsApp Cloud API.
     */
    public String enviarTemplate(String numeroDestino, String nomeTemplate, String codigoIdioma) {

        // ================================
        // 1️⃣ Monta o corpo (payload)
        // ================================
        Map<String, Object> language = new HashMap<>();
        language.put("code", codigoIdioma);

        Map<String, Object> template = new HashMap<>();
        template.put("name", nomeTemplate);
        template.put("language", language);

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", numeroDestino);
        payload.put("type", "template");
        payload.put("template", template);

        // ================================
        // 2️⃣ Configura headers HTTP
        // ================================
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiConfig.getAccessToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        // ================================
        // 3️⃣ Envia a requisição
        // ================================
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiConfig.getApiUrl(), request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "✅ Template '" + nomeTemplate + "' enviado com sucesso!\n" + response.getBody();
            } else {
                return "⚠️ Erro ao enviar template.\nStatus: " + response.getStatusCode() + "\nResposta: " + response.getBody();
            }

        } catch (Exception e) {
            return "❌ Exceção ao enviar template: " + e.getMessage();
        }
    }
}
