package com.example.ClimaAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClimaAPI.service.ClimaService;

@RestController
public class Controller {

    private final ClimaService service;

    public Controller(ClimaService service) {
        this.service = service;
    }

    // http://localhost:8080/climaBH
    @GetMapping("/climaBH")
    public String preverTempoBH() {
        return service.preverTempoBH();
    }

    // http://localhost:8080/clima/curitiba
    @GetMapping("/clima/{cidade}") 
    public String preverTempo(@PathVariable String cidade) {
        return service.preverTempo(cidade);
    } 
    
}
