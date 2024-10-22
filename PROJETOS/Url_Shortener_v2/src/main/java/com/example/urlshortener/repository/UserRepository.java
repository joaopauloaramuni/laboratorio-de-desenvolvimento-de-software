package com.example.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}