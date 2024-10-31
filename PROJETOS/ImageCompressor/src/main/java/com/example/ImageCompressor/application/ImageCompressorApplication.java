package com.example.ImageCompressor.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class ImageCompressorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageCompressorApplication.class, args);
	}

}
