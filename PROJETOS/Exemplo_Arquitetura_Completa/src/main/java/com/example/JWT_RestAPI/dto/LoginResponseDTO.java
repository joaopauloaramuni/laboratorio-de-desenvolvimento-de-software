package com.example.JWT_RestAPI.dto;

import java.util.List;

public class LoginResponseDTO {

    private String token;
    private List<String> roles;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}