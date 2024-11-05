package com.example.RadioBrowserAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${radio.search.api.base.url}")
    private String searchUrl;

    public String getSearchUrl() {
        return searchUrl;
    }
}