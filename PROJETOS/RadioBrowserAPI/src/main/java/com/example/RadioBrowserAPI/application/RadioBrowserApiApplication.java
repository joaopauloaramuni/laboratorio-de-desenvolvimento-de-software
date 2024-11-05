package com.example.RadioBrowserAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class RadioBrowserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadioBrowserApiApplication.class, args);
	}

}
