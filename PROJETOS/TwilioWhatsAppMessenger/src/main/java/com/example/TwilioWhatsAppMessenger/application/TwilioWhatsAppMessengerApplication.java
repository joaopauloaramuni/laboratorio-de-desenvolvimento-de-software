package com.example.TwilioWhatsAppMessenger.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class TwilioWhatsAppMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwilioWhatsAppMessengerApplication.class, args);
	}

}
