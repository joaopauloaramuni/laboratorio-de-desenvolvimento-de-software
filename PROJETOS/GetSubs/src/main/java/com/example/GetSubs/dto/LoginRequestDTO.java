package com.example.GetSubs.dto;

public class LoginRequestDTO {
    private String username;
    private String password;

    // Construtor padrão
    public LoginRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
