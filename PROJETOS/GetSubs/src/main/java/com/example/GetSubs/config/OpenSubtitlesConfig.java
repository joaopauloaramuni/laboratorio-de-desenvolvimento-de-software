package com.example.GetSubs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSubtitlesConfig {

    @Value("${opensubtitles.apiUrl}")
    private String apiUrl;

    @Value("${opensubtitles.apiKey}")
    private String apiKey;

    @Value("${opensubtitles.username}")
    private String username;

    @Value("${opensubtitles.password}")
    private String password;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
