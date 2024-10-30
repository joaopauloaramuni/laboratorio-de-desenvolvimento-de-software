package com.example.HuggingfaceAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class HuggingfaceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuggingfaceApiApplication.class, args);
	}

}
