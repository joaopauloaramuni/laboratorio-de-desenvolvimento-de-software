package com.example.RedditAPIWithUI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class RedditApiWithUIApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditApiWithUIApplication.class, args);
	}

}
