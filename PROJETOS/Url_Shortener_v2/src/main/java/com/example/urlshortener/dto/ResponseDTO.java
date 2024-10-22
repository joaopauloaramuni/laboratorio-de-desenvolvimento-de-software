package com.example.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;

public class ResponseDTO {
  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotBlank(message = "Token cannot be blank")
  private String token;

  // Construtor padrão
  public ResponseDTO() {
  }

  // Construtor com parâmetros
  public ResponseDTO(String name, String token) {
    this.name = name;
    this.token = token;
  }

  // Getters
  public String getName() {
    return name;
  }

  public String getToken() {
    return token;
  }

  // Setters
  public void setName(String name) {
    this.name = name;
  }

  public void setToken(String token) {
    this.token = token;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "ResponseDTO{" +
            "name='" + name + '\'' +
            ", token='" + token + '\'' +
            '}';
  }
}