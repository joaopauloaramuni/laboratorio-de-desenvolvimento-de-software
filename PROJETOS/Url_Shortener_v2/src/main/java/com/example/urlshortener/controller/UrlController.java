package com.example.urlshortener.controller;

import com.example.urlshortener.dto.UrlRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlService;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@Validated @RequestBody UrlRequestDTO urlRequestDTO) {
        Url shortUrl = urlService.generateShortUrl(urlRequestDTO.getUrl(), urlRequestDTO.getExpirationDate());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getAndValidateOriginalUrl(shortUrl);
        return ResponseEntity.status(302).header("Location", originalUrl).build();
    }
}
