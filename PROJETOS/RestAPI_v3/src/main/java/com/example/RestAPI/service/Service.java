package com.example.RestAPI.service;

// A anotação ficará por extenso porque a classe Service.java tem o mesmo nome "Service".
// Quando a classe tiver outro nome, por exemplo UserService.java, basta anotar com @Service.
@org.springframework.stereotype.Service
public class Service {
    public String welcomeMessage() {
        return "Essa é minha primeira API REST";
    }
    public String exampleMessage() {
        return "Mensagem de exemplo";
    }
}
