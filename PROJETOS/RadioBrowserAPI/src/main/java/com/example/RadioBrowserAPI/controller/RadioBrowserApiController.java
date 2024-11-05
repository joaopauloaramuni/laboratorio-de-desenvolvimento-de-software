package com.example.RadioBrowserAPI.controller;

import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.service.RadioBrowserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RadioBrowserApiController {

    @Autowired
    private RadioBrowserApiService radioBrowserApiService;

    @GetMapping("/")
    public String listRadioStations(Model model) {
        List<RadioStation> radioStations = radioBrowserApiService.listRadioStations();
        model.addAttribute("stations", radioStations);
        return "home";
    }
}
