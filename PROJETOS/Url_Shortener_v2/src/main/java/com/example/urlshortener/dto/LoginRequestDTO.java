package com.example.urlshortener.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  private String password;

  // Construtor padrão
  public LoginRequestDTO() {
  }

  // Construtor com parâmetros
  public LoginRequestDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }

  // Getters
  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  // Setters
  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "LoginRequestDTO{" +
            "email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}