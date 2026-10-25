package com.example.ClimaAPI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimaService {

    /* Jeito de fazer com constantes
    private final String apiUrlBH = 
    "https://api.open-meteo.com/v1/forecast?latitude=-19.9167&longitude=-43.9345&current=temperature_2m";
    private final String apiUrlCidade = "https://geocoding-api.open-meteo.com/v1/search?name=";
    */

    // Jeito de fazer com application.properties
    @Value("${api.url.bh}")
    private String apiUrlBH;

    @Value("${api.url.cidade}")
    private String apiUrlCidade;

    private String consultarURL(String apiUrl){
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dados = responseEntity.getBody();
        } else {
            dados = "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
        return dados;
    }
    public String preverTempoBH() {
        return consultarURL(apiUrlBH);
    }
    public String preverTempo(String cidade) {
        cidade = cidade.replace("-", " ");
        return consultarURL(apiUrlCidade + cidade + "&count=1&language=pt&format=json");
    }

}
