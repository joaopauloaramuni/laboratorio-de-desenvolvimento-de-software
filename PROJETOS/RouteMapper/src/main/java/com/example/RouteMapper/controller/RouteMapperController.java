package com.example.RouteMapper.controller;

import com.example.RouteMapper.service.RouteMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteMapperController {

	@Autowired
	private RouteMapperService routeMapperService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/route")
	public String getRoute(@RequestParam String start, @RequestParam String end, Model model) {
		String routeData = routeMapperService.getRoute(start, end);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("routeData", routeData);
		return "home";
	}
}

