package com.example.JWT_RestAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class JwtRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(JwtRestApiApplication.class, args);
	}
}
