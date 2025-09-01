package com.example.HuggingfaceAPI.service;
import com.example.HuggingfaceAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class HuggingfaceApiService {

    @Autowired
    private ApiConfig apiConfig;

    public String gerarImagem(String texto) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Configurar cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiConfig.getApiToken());
        headers.setAccept(List.of(MediaType.IMAGE_PNG));
        
        // Criar o corpo da requisição
        String jsonPayload = String.format("{\"inputs\":\"%s\"}", texto);
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        // Fazer a requisição POST
        // http://localhost:8080/api/generate?texto=Astronaut%20riding%20a%20horse
        // http://localhost:8080/api/generate?texto=Bearded%20software%20engineering%20professor
        ResponseEntity<byte[]> response = restTemplate.exchange(apiConfig.getApiUrl(), HttpMethod.POST, entity, byte[].class);

        // Verificar se a resposta é válida
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            byte[] imageBytes = response.getBody();
            // Salvar a imagem
            try (FileOutputStream fos = new FileOutputStream("imagens/imagem_gerada2.png")) {
                fos.write(imageBytes);
                return "Imagem salva como 'imagens/imagem_gerada2.png'.";
            } catch (IOException e) {
                // Tratar erro ao salvar a imagem
                throw new IOException("Erro ao salvar a imagem: " + e.getMessage(), e);
            }
        } else {
            // Lançar uma exceção mais adequada em caso de erro de API
            throw new RuntimeException("Erro ao chamar a API: " + response.getStatusCode());
        }
    }
}
