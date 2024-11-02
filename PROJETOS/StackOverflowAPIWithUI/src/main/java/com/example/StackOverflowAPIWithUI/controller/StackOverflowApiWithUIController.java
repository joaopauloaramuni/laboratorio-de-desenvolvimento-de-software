package com.example.StackOverflowAPIWithUI.controller;

import com.example.StackOverflowAPIWithUI.model.Question;
import com.example.StackOverflowAPIWithUI.service.StackOverflowApiWithUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StackOverflowApiWithUIController {

    @Autowired
    private StackOverflowApiWithUIService stackOverflowApiWithUIService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/result")
    public String searchQuestions(@RequestParam String query,
                                    Model model) {
        List<Question> questions;

        questions = stackOverflowApiWithUIService.searchQuestions(query);

        // Adiciona a lista de questões ao modelo
        model.addAttribute("questions", questions);
        model.addAttribute("query", query);

        // Retorna a view result.html
        return "result";
    }
}