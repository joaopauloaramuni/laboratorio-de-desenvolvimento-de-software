package com.example.RestAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RestAPI.service.ClimaService;

@RestController
public class Controller {

    private final ClimaService service;

    public Controller(ClimaService service) {
        this.service = service;
    }

    @GetMapping("/clima")
    public String preverTempo(){
        return service.preverTempo();
    }
}
