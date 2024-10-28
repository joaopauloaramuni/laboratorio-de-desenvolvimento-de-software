package com.example.SecureLogin.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class SecureLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureLoginApplication.class, args);
	}

}
