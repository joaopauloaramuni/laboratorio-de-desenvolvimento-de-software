package com.example.StackOverflowAPI.controller;

import com.example.StackOverflowAPI.service.StackOverflowApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stackoverflow")
public class StackOverflowApiController {

    @Autowired
    private StackOverflowApiService stackOverflowApiService;

    @GetMapping("/search/{query}")
    public String search(@PathVariable String query) {
        return stackOverflowApiService.searchQuestions(query);
    }
}