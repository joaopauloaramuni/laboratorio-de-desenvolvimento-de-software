package com.example.PdfTranslator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class PdfTranslatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfTranslatorApplication.class, args);
	}

}
