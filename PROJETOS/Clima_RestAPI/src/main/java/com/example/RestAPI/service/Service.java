package com.example.RestAPI.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Service {

    @Value("${climatempo.api.token}")
    private String token;

    @Value("${climatempo.api.url}")
    private String apiUrl;

    public String preverTempo() {

        String urlFinal = apiUrl + "?token=" + token;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlFinal, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return "Falha ao obter dados meteorológicos. Código: " + responseEntity.getStatusCode();
        }
    }
}
