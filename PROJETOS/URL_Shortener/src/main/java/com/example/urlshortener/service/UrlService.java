package com.example.urlshortener.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;

@Service
public class UrlService {
  @Autowired
  private UrlRepository urlRepository;

  public Url generateShortUrl(String originalUrl, LocalDateTime expirationDate) {
    String shortUrl = generateRandomString(6);
    Url url = new Url();
    url.setOriginalUrl((originalUrl));
    url.setShortUrl(shortUrl);
    url.setCreatedAt(LocalDateTime.now());
    url.setExpirationDate(expirationDate);

    return urlRepository.save(url);
  }

  public Optional<Url> getOriginalUrl(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl);
  }

  public String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random rng = new Random();
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < length; i++) {
      sb.append(characters.charAt(rng.nextInt(characters.length())));
    }

    return sb.toString();
  }
}
