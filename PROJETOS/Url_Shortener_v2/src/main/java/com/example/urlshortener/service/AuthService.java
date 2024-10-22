package com.example.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.urlshortener.dto.LoginRequestDTO;
import com.example.urlshortener.dto.RegisterRequestDTO;
import com.example.urlshortener.dto.ResponseDTO;
import com.example.urlshortener.exception.UserAlreadyExistsException;
import com.example.urlshortener.exception.UserNotFoundException;
import com.example.urlshortener.infra.security.TokenService;
import com.example.urlshortener.model.User;
import com.example.urlshortener.repository.UserRepository;

@Service
public class AuthService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TokenService tokenService;

  // Construtor para injeção de dependências
  public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  public ResponseDTO login(LoginRequestDTO body) {
    User user = repository.findByEmail(body.getEmail())
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

    if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Credenciais inválidas");
    }

    String token = tokenService.generateToken(user);
    return new ResponseDTO(user.getName(), token);
  }

  public ResponseDTO register(RegisterRequestDTO body) {
    if (repository.findByEmail(body.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("Usuário já existe");
    }

    User newUser = new User();
    newUser.setName(body.getName());
    newUser.setEmail(body.getEmail());
    newUser.setPassword(passwordEncoder.encode(body.getPassword()));
    repository.save(newUser);

    String token = tokenService.generateToken(newUser);
    return new ResponseDTO(newUser.getName(), token);
  }
}
