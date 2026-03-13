package com.example.RestAPI.controller;

import com.example.RestAPI.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/clima")
    public String preverTempo(){
        return service.preverTempo();
    }
}
