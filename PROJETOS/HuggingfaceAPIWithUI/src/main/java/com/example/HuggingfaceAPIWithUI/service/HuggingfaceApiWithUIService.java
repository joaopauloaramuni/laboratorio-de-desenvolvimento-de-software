package com.example.HuggingfaceAPIWithUI.service;
import com.example.HuggingfaceAPIWithUI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class HuggingfaceApiWithUIService {

    @Autowired
    private ApiConfig apiConfig;

    public String gerarImagem(String texto, String diretorio) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Configurar cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiConfig.getApiToken());

        // Criar o corpo da requisição
        String jsonPayload = String.format("{\"inputs\":\"%s\"}", texto);
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        // Fazer a requisição POST
        ResponseEntity<byte[]> response = restTemplate.exchange(apiConfig.getApiUrl(), HttpMethod.POST, entity, byte[].class);

        // Verificar se a resposta é válida
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            byte[] imageBytes = response.getBody();
            // Salvar a imagem no diretório especificado pelo usuário
            try (FileOutputStream fos = new FileOutputStream(diretorio + "/imagem_gerada2.png")) {
                fos.write(imageBytes);
                return "Imagem salva como '" + diretorio + "/imagem_gerada2.png'.";
            } catch (IOException e) {
                throw new IOException("Erro ao salvar a imagem: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Erro ao chamar a API: " + response.getStatusCode());
        }
    }
}
