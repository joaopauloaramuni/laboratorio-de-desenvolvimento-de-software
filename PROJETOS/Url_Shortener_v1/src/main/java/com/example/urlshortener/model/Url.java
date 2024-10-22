package com.example.urlshortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Url extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String originalUrl;

  @Column(unique=true)
  private String shortUrl;

  private LocalDateTime expirationDate;

  // Getters e Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDateTime expirationDate) {
    this.expirationDate = expirationDate;
  }

  // Método toString()
  @Override
  public String toString() {
    return "Url{" +
            "id=" + id +
            ", originalUrl='" + originalUrl + '\'' +
            ", shortUrl='" + shortUrl + '\'' +
            ", expirationDate=" + expirationDate +
            ", createdAt=" + getCreatedAt() +
            ", updatedAt=" + getUpdatedAt() +
            '}';
  }
}
