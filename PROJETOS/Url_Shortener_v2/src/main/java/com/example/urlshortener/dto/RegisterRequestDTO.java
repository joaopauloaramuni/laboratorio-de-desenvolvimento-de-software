package com.example.urlshortener.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequestDTO {
  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  private String password;

  // Construtor padrão
  public RegisterRequestDTO() {
  }

  // Construtor com parâmetros
  public RegisterRequestDTO(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  // Getters
  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  // Setters
  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "RegisterRequestDTO{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}