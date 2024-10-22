package com.example.urlshortener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import com.example.urlshortener.dto.UrlRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.urlshortener.controller.UrlController;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlService;

public class UrlControllerTest {
  private MockMvc mockMvc;

  @Mock
  private UrlService urlService;

  @InjectMocks
  private UrlController urlController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
  }

  @Test
  void testShortenUrl() throws Exception {
    String originalUrl = "https://www.example.com";
    UrlRequestDTO urlRequestDto = new UrlRequestDTO();
    urlRequestDto.setUrl(originalUrl);

    Url url = new Url();
    url.setOriginalUrl(originalUrl);
    url.setShortUrl("abc123");

    when(urlService.generateShortUrl(eq(originalUrl), any())).thenReturn(url);

    mockMvc.perform(post("/api/shorten")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"url\":\"" + originalUrl + "\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.originalUrl").value(originalUrl))
        .andExpect(jsonPath("$.shortUrl").value("abc123"));

    verify(urlService, times(1)).generateShortUrl(eq(originalUrl), any());
  }

  @Test
  void testRedirectToOriginalUrl() throws Exception {
    String shortUrl = "abc123";
    String originalUrl = "https://www.example.com";

    when(urlService.getAndValidateOriginalUrl(shortUrl)).thenReturn(originalUrl);

    mockMvc.perform(get("/api/" + shortUrl))
        .andExpect(status().isFound())
        .andExpect(header().string("Location", originalUrl));

    verify(urlService, times(1)).getAndValidateOriginalUrl(shortUrl);
  }

}
