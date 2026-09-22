package com.example.SendEmail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SendEmail.dto.EmailRequestDTO;
import com.example.SendEmail.service.SendEmailService;

@RestController
@RequestMapping("/api/email")
public class SendEmailController {

    private final SendEmailService sendEmailService;

    public SendEmailController(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("/send-text")
    public String sendTextEmail(@RequestBody EmailRequestDTO emailRequest) {
        sendEmailService.sendTextEmail(
            emailRequest.getTo(),
            emailRequest.getSubject(),
            emailRequest.getBody()
        );

        return "E-mail de texto enviado com sucesso!";
    }

    @PostMapping("/send-html")
    public String sendHtmlEmail(@RequestBody EmailRequestDTO emailRequest) {
        sendEmailService.sendHtmlEmail(
            emailRequest.getTo(),
            emailRequest.getSubject(),
            emailRequest.getBody()
        );

        return "E-mail HTML enviado com sucesso!";
    }
}