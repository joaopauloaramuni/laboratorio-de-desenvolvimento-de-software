package com.example.SpringCloudClient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClientHelloController {

    @Value("${hello.message}")
    private String helloMessage;

    @GetMapping("/hello-client")
    public String getHelloMessageFromServer() {
        return "Mensagem no lado cliente: " + helloMessage;
    }
}
