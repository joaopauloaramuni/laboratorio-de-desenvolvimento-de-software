package com.example.TwilioSMSMessenger.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class TwilioSMSMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwilioSMSMessengerApplication.class, args);
	}

}
