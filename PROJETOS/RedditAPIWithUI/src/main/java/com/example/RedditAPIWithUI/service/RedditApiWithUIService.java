package com.example.RedditAPIWithUI.service;

import com.example.RedditAPIWithUI.config.ApiConfig;
import com.example.RedditAPIWithUI.model.Discussion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RedditApiWithUIService {

    @Autowired
    private ApiConfig apiConfig;

    public List<Discussion> searchDiscussions(String query) {
        String searchUrl = apiConfig.getSearchUrl() + "brdev/search.json";
        String url = searchUrl + "?q=" + query + "&sort=relevance";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        return extractDiscussions(response.getBody());
    }

    public List<Discussion> searchDiscussionsByCommunity(String community, String query) {
        String searchUrl = apiConfig.getSearchUrl() + community + "/search.json";
        String url = searchUrl + "?q=" + query + "&sort=relevance";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        return extractDiscussions(response.getBody());
    }

    private List<Discussion> extractDiscussions(Map<String, Object> json) {
        List<Discussion> discussions = new ArrayList<>();

        // Verifica se a resposta contém os dados esperados
        if (json != null && json.containsKey("data")) {
            Map<String, Object> data = (Map<String, Object>) json.get("data");
            List<Map<String, Object>> children = (List<Map<String, Object>>) data.get("children");

            for (Map<String, Object> child : children) {
                Map<String, Object> discussionData = (Map<String, Object>) child.get("data");
                String title = (String) discussionData.get("title");
                String url = (String) discussionData.get("url");
                String author = (String) discussionData.get("author");
                String selftext = (String) discussionData.get("selftext");

                // Converte a data em milissegundos
                double timestamp = (Double) discussionData.get("created");
                long milliseconds = (long) (timestamp * 1000); // Multiplica por 1000 para converter de segundos para milissegundos

                // Formata a data
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String createdDate = sdf.format(new Date(milliseconds)); // Formata a data

                discussions.add(new Discussion(title, url, author, selftext, createdDate));
            }
        }

        return discussions;
    }
}
