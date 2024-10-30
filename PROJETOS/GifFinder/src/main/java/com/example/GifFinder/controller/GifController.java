package com.example.GifFinder.controller;

import com.example.GifFinder.response.GifResponse;
import com.example.GifFinder.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GifController {

    @Autowired
    private GifService gifService;

    @GetMapping("/gifs")
    public GifResponse getGifs(@RequestParam String query, @RequestParam(defaultValue = "5") int limit) {
        return gifService.searchGif(query, limit);
    }
}
