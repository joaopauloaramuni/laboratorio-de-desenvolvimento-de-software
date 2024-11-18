package com.example.AIChatBotAPI.service;

import com.example.AIChatBotAPI.config.ApiConfig;
import com.example.AIChatBotAPI.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatBotApiService {

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public ApiResponse getResponseFromAI(String question) {
        try {
            // Novo endpoint para a API de chat
            String endpoint = "https://api.openai.com/v1/chat/completions";

            // Monta o corpo da requisição com formato adequado
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");  // Modelo atualizado
            // O campo 'messages' agora é um array de objetos
            requestBody.put("messages", List.of(
                    Map.of("role", "system", "content", "Você é um assistente útil."),
                    Map.of("role", "user", "content", question)
            ));
            requestBody.put("max_tokens", 150);
            requestBody.put("temperature", 0.7);

            // Configura os headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiConfig.getApiKey());
            headers.set("Content-Type", "application/json");

            // Cria a requisição com corpo e cabeçalhos
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Faz a requisição POST
            ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, request, Map.class);

            // Extrai a resposta
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                // Extrai o conteúdo da primeira escolha da resposta
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    return new ApiResponse(choices.get(0).get("message").toString().trim(), true);
                }
            }
            return new ApiResponse("Nenhuma resposta foi obtida.", false);
        } catch (Exception e) {
            // Se o erro for de quota excedida
            if (e.getMessage().contains("insufficient_quota")) {
                return new ApiResponse("Erro: Você excedeu sua cota de requisições. Verifique sua conta e limite de uso.", "insufficient_quota");
            }
            // Para outros erros
            return new ApiResponse("Erro ao conectar com o serviço de IA: " + e.getMessage(), "unknown_error");
        }
    }
}
