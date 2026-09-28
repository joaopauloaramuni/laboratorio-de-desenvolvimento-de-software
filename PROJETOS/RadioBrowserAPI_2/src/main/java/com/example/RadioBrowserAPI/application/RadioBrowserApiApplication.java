package com.example.RadioBrowserAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EntityScan(basePackages = "com.example.RadioBrowserAPI.model")
@EnableJpaRepositories(basePackages = "com.example.RadioBrowserAPI.repository")
public class RadioBrowserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadioBrowserApiApplication.class, args);
	}

}
