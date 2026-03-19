package com.example.NASA.service;

import com.example.NASA.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaService {

    @Autowired
    private ApiConfig apiConfig;

    public String buscarImagemDoDia() {

        String url = apiConfig.getApiUrl() + "?api_key=" + apiConfig.getApiKey();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();

    }
}
