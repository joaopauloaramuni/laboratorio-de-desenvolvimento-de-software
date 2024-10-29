package com.example.MoviesWithTMDBApi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class MoviesWithTMDBApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesWithTMDBApiApplication.class, args);
	}

}
