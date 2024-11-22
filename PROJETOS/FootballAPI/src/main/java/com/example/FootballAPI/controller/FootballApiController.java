package com.example.FootballAPI.controller;

import com.example.FootballAPI.service.FootballApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class FootballApiController {

    @Autowired
    private FootballApiService footballApiService;

    @GetMapping("/")
    public String home(Model model) {
        // Obtém os próximos 10 jogos das ligas brasileiras desejadas
        List<Map<String, Object>> upcomingGames = footballApiService.getUpcomingGames();

        // Adiciona os jogos no modelo para exibição na página
        model.addAttribute("upcomingGames", upcomingGames);

        // Retorna a página 'home' com a lista de jogos
        return "home";
    }
}
