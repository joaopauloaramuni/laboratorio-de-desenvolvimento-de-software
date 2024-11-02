package com.example.RedditAPI.service;

import com.example.RedditAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RedditApiService {

    @Autowired
    private ApiConfig apiConfig;

    public String searchDiscussions(String query) {
        String searchUrl = apiConfig.getSearchUrl() + "brdev/search.json";
        RestTemplate restTemplate = new RestTemplate();
        String url = searchUrl + "?q=" + query + "&sort=relevance";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String searchDiscussionsByCommunity(String community, String query) {
        String searchUrl = apiConfig.getSearchUrl() + community + "/search.json";
        RestTemplate restTemplate = new RestTemplate();
        String url = searchUrl + "?q=" + query + "&sort=relevance";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}

