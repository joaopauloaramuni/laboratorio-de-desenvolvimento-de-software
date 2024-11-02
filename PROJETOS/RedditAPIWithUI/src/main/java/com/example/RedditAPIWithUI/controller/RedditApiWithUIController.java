package com.example.RedditAPIWithUI.controller;

import com.example.RedditAPIWithUI.model.Discussion;
import com.example.RedditAPIWithUI.service.RedditApiWithUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RedditApiWithUIController {

    @Autowired
    private RedditApiWithUIService redditApiWithUIService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/result")
    public String searchDiscussions(@RequestParam(required = false) String community,
                                    @RequestParam String query,
                                    Model model) {
        List<Discussion> discussions;

        // Verifica se a comunidade é válida e busca as discussões apropriadas
        if (community != null && !community.isEmpty()) {
            discussions = redditApiWithUIService.searchDiscussionsByCommunity(community, query);
        } else {
            community = "brdev";
            discussions = redditApiWithUIService.searchDiscussions(query);
        }

        // Adiciona a lista de discussões ao modelo
        model.addAttribute("discussions", discussions);
        model.addAttribute("community", community);
        model.addAttribute("query", query);

        // Retorna a view result.html
        return "result";
    }
}
