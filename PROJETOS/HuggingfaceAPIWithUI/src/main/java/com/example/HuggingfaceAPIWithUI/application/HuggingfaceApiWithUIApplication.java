package com.example.HuggingfaceAPIWithUI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class HuggingfaceApiWithUIApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuggingfaceApiWithUIApplication.class, args);
	}

}
