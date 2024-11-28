package com.example.MP_Checkout_Pro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
