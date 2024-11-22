package com.example.BasketballAPI.controller;

import com.example.BasketballAPI.config.ApiConfig;
import com.example.BasketballAPI.service.BasketballApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class BasketballApiController {
    @Autowired
    private BasketballApiService basketballApiService;

    @Autowired
    private ApiConfig apiConfig;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String date, Model model) {
        /*if (date == null || date.isEmpty()) {
            date = apiConfig.getDate();
        }*/
        if (date == null || date.isEmpty()) {
            // Pega a data atual no formato yyyy-MM-dd
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        Map<String, Object> response = basketballApiService.getGames(date);
        List<Map<String, Object>> games = (List<Map<String, Object>>) response.get("response");
        model.addAttribute("games", games);
        return "home";
    }
}
