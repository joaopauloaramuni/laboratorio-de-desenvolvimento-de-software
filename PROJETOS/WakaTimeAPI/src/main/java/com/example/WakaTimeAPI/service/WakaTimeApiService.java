package com.example.WakaTimeAPI.service;

import com.example.WakaTimeAPI.config.ApiConfig;
import com.example.WakaTimeAPI.model.WakaTimeApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WakaTimeApiService {

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
	private RestTemplate restTemplate;

	public WakaTimeApiResponse getWakatimeData() {
		return restTemplate.getForObject(apiConfig.getUrlAllTime() + "?api_key=" + apiConfig.getApiKey(), WakaTimeApiResponse.class);
	}
}

