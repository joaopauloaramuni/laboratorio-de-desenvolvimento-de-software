package com.example.RouteMapper.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class RouteMapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteMapperApplication.class, args);
	}

}
