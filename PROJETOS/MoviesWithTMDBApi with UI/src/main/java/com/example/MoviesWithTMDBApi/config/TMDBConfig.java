package com.example.MoviesWithTMDBApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TMDBConfig {

    @Value("${tmdb.api.url}")
    private String baseUrl;

    @Value("${tmdb.api.authorization}")
    private String authorizationHeader;

    @Value("${tmdb.api.key}")
    private String apiKey;


    @Bean
    public String getBaseUrl() {
        return baseUrl;
    }

    @Bean
    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    @Bean
    public String getApiKey() {
        return apiKey;
    }
}
