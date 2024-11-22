package com.example.FootballAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${football.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}