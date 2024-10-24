package com.example.BoletoGenerator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class BoletoGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoletoGeneratorApplication.class, args);
	}

}
