package com.example.NASA.service;

import com.example.NASA.config.ApiConfig;
import com.example.NASA.dtos.NasaApodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaService {

    @Autowired
    private ApiConfig apiConfig;

    public NasaApodDTO buscarImagemDoDia() {

        String url = apiConfig.getApiUrl() + "?api_key=" + apiConfig.getApiKey();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NasaApodDTO> response = restTemplate.getForEntity(url, NasaApodDTO.class);

        return response.getBody();

    }
}
