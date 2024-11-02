package com.example.StackOverflowAPIWithUI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class StackOverflowApiWithUIApplication {
	public static void main(String[] args) {
		SpringApplication.run(StackOverflowApiWithUIApplication.class, args);
	}
}
