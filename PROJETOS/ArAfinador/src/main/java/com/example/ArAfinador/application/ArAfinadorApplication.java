package com.example.ArAfinador.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class ArAfinadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArAfinadorApplication.class, args);
	}

}
