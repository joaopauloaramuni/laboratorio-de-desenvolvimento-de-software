package com.example.IBGE_Locations.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class IbgeLocationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbgeLocationsApplication.class, args);
	}

}
