package com.example.urlshortener.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
public class Url extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String originalUrl;

  @Column(unique = true)
  private String shortUrl;

  private LocalDateTime expirationDate;

  // Construtor padrão
  public Url() {
  }

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

  // Implementando equals e hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Url)) return false;
    if (!super.equals(o)) return false;

    Url url = (Url) o;

    if (!id.equals(url.id)) return false;
    if (!originalUrl.equals(url.originalUrl)) return false;
    return shortUrl != null ? shortUrl.equals(url.shortUrl) : url.shortUrl == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + id.hashCode();
    result = 31 * result + originalUrl.hashCode();
    result = 31 * result + (shortUrl != null ? shortUrl.hashCode() : 0);
    return result;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "Url{" +
            "id=" + id +
            ", originalUrl='" + originalUrl + '\'' +
            ", shortUrl='" + shortUrl + '\'' +
            ", expirationDate=" + expirationDate +
            "} " + super.toString();
  }
}
