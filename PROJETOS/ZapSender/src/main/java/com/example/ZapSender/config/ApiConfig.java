package com.example.ZapSender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${zapsender.verify.token}")
    private String verifyToken;
    @Value("${zapsender.access.token}")
    private String accessToken;
    @Value("${zapsender.phone.number.id}")
    private String phoneNumberId;
    @Value("${zapsender.api.url}")
    private String apiUrl;

    public String getVerifyToken() {
        return verifyToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public String getPhoneNumberId() {
        return phoneNumberId;
    }
    public String getApiUrl() {
        return apiUrl;
    }

}
