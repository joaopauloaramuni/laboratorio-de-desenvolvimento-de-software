package com.example.TextToGif.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GiphyApiConfig {

    @Value("${giphy.api.key}")
    private String apiKey;

    @Value("${giphy.api.url}")
    private String apiUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}

