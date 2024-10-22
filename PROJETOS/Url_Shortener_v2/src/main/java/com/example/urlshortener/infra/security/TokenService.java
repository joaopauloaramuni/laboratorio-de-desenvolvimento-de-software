package com.example.urlshortener.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.urlshortener.model.User;

@Service
public class TokenService {
  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      String token = JWT.create()
        .withIssuer("url-shortener")
        .withSubject(user.getEmail())
        .withExpiresAt(this.generateExpirationDate())
        .sign(algorithm);
      
      return token;
    } catch (JWTCreationException exception) {
        throw new RuntimeException("Error creating token", exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      
      return JWT.require(algorithm)
        .withIssuer("url-shortener")
        .build()
        .verify(token)
        .getSubject();
    } catch (JWTVerificationException exception) {
        return null;
    }
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}