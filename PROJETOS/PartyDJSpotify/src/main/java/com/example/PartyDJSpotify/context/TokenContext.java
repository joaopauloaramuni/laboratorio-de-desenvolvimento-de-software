package com.example.PartyDJSpotify.context;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenContext {
    private String accessToken;
    private Instant tokenExpiryTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.tokenExpiryTime = Instant.now().plusSeconds(expiresIn);
    }

    public boolean isTokenExpired() {
        return Instant.now().isAfter(tokenExpiryTime);
    }
}
