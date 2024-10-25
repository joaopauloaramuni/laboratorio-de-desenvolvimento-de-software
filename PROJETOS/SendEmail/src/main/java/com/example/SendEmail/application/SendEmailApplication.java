package com.example.SendEmail.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class SendEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendEmailApplication.class, args);
	}

}
