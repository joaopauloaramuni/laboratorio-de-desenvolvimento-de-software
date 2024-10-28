package com.example.YouTubeVideoDownloader.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class YouTubeVideoDownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouTubeVideoDownloaderApplication.class, args);
	}

}
