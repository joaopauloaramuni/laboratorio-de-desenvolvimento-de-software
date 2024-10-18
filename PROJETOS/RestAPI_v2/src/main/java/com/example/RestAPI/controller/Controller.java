package com.example.RestAPI.controller;

import com.example.RestAPI.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    //Classe de serviços
    Service service = new Service();

    // Exemplo de endpoint usando Spring Boot
    @GetMapping("/test")
    public String helloWorld(){
        return service.welcomeMessage();
    }
}
