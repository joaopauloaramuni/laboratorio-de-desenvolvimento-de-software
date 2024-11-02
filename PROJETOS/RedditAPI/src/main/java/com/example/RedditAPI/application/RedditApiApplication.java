package com.example.RedditAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class RedditApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditApiApplication.class, args);
	}

}
