package com.example.BasketballAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${nba.api.key}")
    private String apiKey;

    @Value("${nba.api.date}")
    private String date;

    public String getApiKey() {
        return apiKey;
    }

    public String getDate() {
        return date;
    }
}