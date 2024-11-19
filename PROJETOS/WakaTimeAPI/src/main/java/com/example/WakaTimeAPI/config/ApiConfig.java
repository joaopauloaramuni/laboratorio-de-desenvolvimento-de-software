package com.example.WakaTimeAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

	@Value("${waka.api.key}")
	private String apiKey;

	@Value("${wapa.api.url.alltime}")
	private String urlAllTime;

	public String getApiKey() {
		return apiKey;
	}

	public String getUrlAllTime() {
		return urlAllTime;
	}
}
