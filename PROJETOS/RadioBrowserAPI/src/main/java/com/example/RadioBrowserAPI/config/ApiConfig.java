package com.example.RadioBrowserAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${radio.api.base.url}")
    private String baseUrl;

    public String getSearchUrl() {
        return baseUrl + "/stations/search";
    }

    public String getCountriesUrl() {
        return baseUrl + "/countries";
    }

    public String getStatesUrl() {
        return baseUrl + "/states";
    }
}