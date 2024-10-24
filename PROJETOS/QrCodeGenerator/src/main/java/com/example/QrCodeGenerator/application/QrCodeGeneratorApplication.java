package com.example.QrCodeGenerator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class QrCodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeGeneratorApplication.class, args);
	}

}
