package com.example.StackOverflowAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class StackOverflowApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StackOverflowApiApplication.class, args);
	}
}
