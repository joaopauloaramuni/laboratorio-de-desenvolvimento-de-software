package com.example.WakaTimeAPI.controller;

import com.example.WakaTimeAPI.model.WakaTimeApiResponse;
import com.example.WakaTimeAPI.service.WakaTimeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WakaTimeApiController {

	@Autowired
	private WakaTimeApiService wakaTimeApiService;

	@GetMapping("/")
	public String home(Model model) {
		WakaTimeApiResponse response = wakaTimeApiService.getWakatimeData();
		model.addAttribute("data", response.getData()); // Passando apenas a parte 'data' do objeto response
		return "home";
	}
}
