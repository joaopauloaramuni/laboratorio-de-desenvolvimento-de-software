package com.example.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
  Optional<Url> findByShortUrl(String shortUrl);
}
