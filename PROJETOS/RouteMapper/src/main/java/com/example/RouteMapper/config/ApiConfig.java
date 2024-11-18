package com.example.RouteMapper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${openrouteservice.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}

