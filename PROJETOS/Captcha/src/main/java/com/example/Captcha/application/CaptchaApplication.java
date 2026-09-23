package com.example.Captcha.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class CaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaApplication.class, args);
	}

}
