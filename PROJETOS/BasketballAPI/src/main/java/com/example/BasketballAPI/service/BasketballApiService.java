package com.example.BasketballAPI.service;

import com.example.BasketballAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@Service
public class BasketballApiService {

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getGames(String date) {
        String url = "https://v2.nba.api-sports.io/games?date=" + date;

        // Configuração dos headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "v2.nba.api-sports.io");
        headers.set("x-rapidapi-key", apiConfig.getApiKey());

        // Inclui os headers na requisição
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Realiza a requisição GET
        return restTemplate.exchange(url, HttpMethod.GET, entity, Map.class).getBody();
    }
}
