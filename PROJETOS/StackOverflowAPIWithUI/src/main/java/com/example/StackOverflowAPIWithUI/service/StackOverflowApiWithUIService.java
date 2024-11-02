package com.example.StackOverflowAPIWithUI.service;

import com.example.StackOverflowAPIWithUI.config.ApiConfig;
import com.example.StackOverflowAPIWithUI.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

@Service
public class StackOverflowApiWithUIService {

    @Autowired
    private ApiConfig apiConfig;

    public List<Question> searchQuestions(String query) {
        String searchUrl = apiConfig.getSearchUrl() + "?order=desc&sort=activity&site=stackoverflow&intitle=" + query;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(searchUrl, Map.class);

        return extractQuestions(response.getBody());
    }

    private List<Question> extractQuestions(Map<String, Object> json) {
        List<Question> questions = new ArrayList<>();

        // Verifica se a resposta contém os dados esperados
        if (json != null && json.containsKey("items")) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) json.get("items");

            for (Map<String, Object> item : items) {
                // Extrai as informações relevantes para Question
                String title = (String) item.get("title");
                String link = (String) item.get("link");
                int viewCount = (int) item.get("view_count");
                int answerCount = (int) item.get("answer_count");
                int score = (int) item.get("score");
                boolean isAnswered = (boolean) item.get("is_answered");
                int questionId = (int) item.get("question_id");
                List<String> tags = (List<String>) item.get("tags");

                // Converte as datas em milissegundos
                long lastActivityDate = ((Number) item.get("last_activity_date")).longValue() * 1000;
                long creationDate = ((Number) item.get("creation_date")).longValue() * 1000;
                long lastEditDate = item.containsKey("last_edit_date")
                        ? ((Number) item.get("last_edit_date")).longValue() * 1000
                        : 0;

                // Formata as datas (caso necessário para fins de exibição)
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedLastActivityDate = sdf.format(new Date(lastActivityDate));
                String formattedCreationDate = sdf.format(new Date(creationDate));
                String formattedLastEditDate = lastEditDate > 0
                        ? sdf.format(new Date(lastEditDate))
                        : "N/A";

                // Cria o objeto Question com os dados extraídos
                questions.add(new Question(tags, isAnswered, viewCount, answerCount, score,
                        formattedLastActivityDate, formattedCreationDate, formattedLastEditDate, questionId,
                        link, title));
            }
        }
        return questions;
    }
}
