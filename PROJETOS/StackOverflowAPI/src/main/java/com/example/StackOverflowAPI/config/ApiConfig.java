package com.example.StackOverflowAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${stackoverflow.search.api.base.url}")
    private String searchUrl;

    public String getSearchUrl() {
        return searchUrl;
    }
}