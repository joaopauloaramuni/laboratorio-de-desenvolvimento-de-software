package com.example.urlshortener.model;

import java.time.LocalDateTime;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Construtor padrão
  public BaseEntity() {
    // Inicializa createdAt e updatedAt com a data/hora atual
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Getters
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  // Setters
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "BaseEntity{" +
            "createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}
