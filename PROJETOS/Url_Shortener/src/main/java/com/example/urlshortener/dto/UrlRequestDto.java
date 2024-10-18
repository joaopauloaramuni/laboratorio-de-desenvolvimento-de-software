package com.example.urlshortener.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UrlRequestDto {

  @NotBlank(message = "URL cannot be blank")
  @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
  private String url;

  private LocalDateTime expirationDate;

  // Getters e Setters
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDateTime expirationDate) {
    this.expirationDate = expirationDate;
  }
}
