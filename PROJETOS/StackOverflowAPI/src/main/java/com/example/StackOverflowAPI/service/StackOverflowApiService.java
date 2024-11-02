package com.example.StackOverflowAPI.service;

import com.example.StackOverflowAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StackOverflowApiService {

    @Autowired
    private ApiConfig apiConfig;

    public String searchQuestions(String query) {
        String searchUrl = apiConfig.getSearchUrl() + "?order=desc&sort=activity&site=stackoverflow&intitle=" + query;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);
        return response.getBody();
    }
}
