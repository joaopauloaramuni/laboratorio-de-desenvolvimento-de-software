package com.example.urlshortener.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UrlRequestDTO {
  @NotBlank(message = "URL cannot be blank")
  @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
  private String url;

  private LocalDateTime expirationDate;

  // Construtor padrão
  public UrlRequestDTO() {
  }

  // Construtor com parâmetros
  public UrlRequestDTO(String url, LocalDateTime expirationDate) {
    this.url = url;
    this.expirationDate = expirationDate;
  }

  // Getters
  public String getUrl() {
    return url;
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  // Setters
  public void setUrl(String url) {
    this.url = url;
  }

  public void setExpirationDate(LocalDateTime expirationDate) {
    this.expirationDate = expirationDate;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "UrlRequestDTO{" +
            "url='" + url + '\'' +
            ", expirationDate=" + expirationDate +
            '}';
  }
}
