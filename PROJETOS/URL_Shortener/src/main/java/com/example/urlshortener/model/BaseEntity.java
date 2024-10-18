package com.example.urlshortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {
  private LocalDateTime createdAt;
  
  private LocalDateTime updatedAt;
}
