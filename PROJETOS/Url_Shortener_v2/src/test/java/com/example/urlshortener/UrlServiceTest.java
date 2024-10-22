package com.example.urlshortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.urlshortener.exception.UrlExpiredException;
import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.service.UrlService;

public class UrlServiceTest {
  @Mock
  private UrlRepository urlRepository;

  @InjectMocks
  private UrlService urlService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGenerateShortUrl() {
    String originalUrl = "https://www.example.com";
    LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);

    when(urlRepository.findByShortUrl(anyString())).thenReturn(Optional.empty());

    when(urlRepository.save(any(Url.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Url result = urlService.generateShortUrl(originalUrl, expirationDate);

    assertNotNull(result);
    assertEquals(originalUrl, result.getOriginalUrl());
    assertNotNull(result.getShortUrl());
    assertEquals(expirationDate, result.getExpirationDate());

    verify(urlRepository, times(1)).save(any(Url.class));
  }

  @Test
  void testGetAndValidateOriginalUrl_Success() {
    String shortUrl = "abc123";
    String originalUrl = "https://www.example.com";
    LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);

    Url url = new Url();
    url.setShortUrl(shortUrl);
    url.setOriginalUrl(originalUrl);
    url.setExpirationDate(expirationDate);

    when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(url));

    String result = urlService.getAndValidateOriginalUrl(shortUrl);

    assertEquals(originalUrl, result);
    verify(urlRepository, times(1)).findByShortUrl(shortUrl);
  }

  @Test
  void testGetAndValidateOriginalUrl_Expired() {
    String shortUrl = "abc123";
    String originalUrl = "https://www.example.com";
    LocalDateTime expirationDate = LocalDateTime.now().minusDays(1);

    Url url = new Url();
    url.setShortUrl(shortUrl);
    url.setOriginalUrl(originalUrl);
    url.setExpirationDate(expirationDate);

    when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(url));

    assertThrows(UrlExpiredException.class, () -> {
      urlService.getAndValidateOriginalUrl(shortUrl);
    });

    verify(urlRepository, times(1)).findByShortUrl(shortUrl);
  }

  @Test
  void testGetAndValidateOriginalUrl_NotFound() {
    String shortUrl = "abc123";

    when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

    assertThrows(UrlNotFoundException.class, () -> {
      urlService.getAndValidateOriginalUrl(shortUrl);
    });

    verify(urlRepository, times(1)).findByShortUrl(shortUrl);
  }
}
