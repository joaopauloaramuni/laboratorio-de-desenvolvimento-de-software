package com.example.BarCodeGenerator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class BarCodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarCodeGeneratorApplication.class, args);
	}

}
