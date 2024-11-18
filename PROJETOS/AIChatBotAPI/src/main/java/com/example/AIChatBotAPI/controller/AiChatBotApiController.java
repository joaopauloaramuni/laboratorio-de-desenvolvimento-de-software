package com.example.AIChatBotAPI.controller;

import com.example.AIChatBotAPI.model.ApiResponse;
import com.example.AIChatBotAPI.service.AiChatBotApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AiChatBotApiController {

    @Autowired
    private AiChatBotApiService aiChatBotApiService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/ask")
    public String askAI(@RequestParam("question") String question, Model model) {
        ApiResponse apiResponse = aiChatBotApiService.getResponseFromAI(question);

        model.addAttribute("question", question);
        model.addAttribute("response", apiResponse.getMessage());
        model.addAttribute("isSuccess", apiResponse.isSuccess());
        model.addAttribute("errorMessage", apiResponse.getErrorMessage());
        model.addAttribute("errorCode", apiResponse.getErrorCode());

        return "home";
    }

}

