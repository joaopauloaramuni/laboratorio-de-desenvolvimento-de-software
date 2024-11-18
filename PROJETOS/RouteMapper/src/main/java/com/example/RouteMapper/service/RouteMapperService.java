package com.example.RouteMapper.service;

import com.example.RouteMapper.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RouteMapperService {

	@Autowired
	private ApiConfig apiConfig;

	public String getRoute(String start, String end) {
		String apiUrl = String.format(
				"https://api.openrouteservice.org/v2/directions/driving-car?api_key=%s&start=%s&end=%s",
				apiConfig.getApiKey(),
				start,
				end
		);

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(apiUrl, String.class);
	}
}
