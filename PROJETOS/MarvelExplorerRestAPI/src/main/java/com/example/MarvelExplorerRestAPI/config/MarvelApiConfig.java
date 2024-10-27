package com.example.MarvelExplorerRestAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarvelApiConfig {

    @Value("${marvel.api.public-key}")
    private String publicKey;

    @Value("${marvel.api.private-key}")
    private String privateKey;

    @Value("${marvel.api.base-url}")
    private String baseUrl;


    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
