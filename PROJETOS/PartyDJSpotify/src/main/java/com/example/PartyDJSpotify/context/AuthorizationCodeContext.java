package com.example.PartyDJSpotify.context;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationCodeContext {
    private String code;

    // Método para definir o código de autorização
    public void setAuthorizationCode(String code) {
        this.code = code;
    }

    // Método para obter o código de autorização
    public String getAuthorizationCode() {
        return code;
    }
}
