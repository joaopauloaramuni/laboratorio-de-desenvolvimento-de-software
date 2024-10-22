package com.example.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.dto.LoginRequestDTO;
import com.example.urlshortener.dto.RegisterRequestDTO;
import com.example.urlshortener.dto.ResponseDTO;
import com.example.urlshortener.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  // Construtor
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
    ResponseDTO response = authService.login(body);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
    ResponseDTO response = authService.register(body);
    return ResponseEntity.ok(response);
  }
}
