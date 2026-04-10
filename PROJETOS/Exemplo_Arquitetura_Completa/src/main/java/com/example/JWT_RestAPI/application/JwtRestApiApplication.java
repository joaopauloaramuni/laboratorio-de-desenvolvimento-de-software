package com.example.JWT_RestAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableJpaRepositories("com.example.JWT_RestAPI.repository")
@EntityScan("com.example.JWT_RestAPI.model")
public class JwtRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(JwtRestApiApplication.class, args);
	}
}
