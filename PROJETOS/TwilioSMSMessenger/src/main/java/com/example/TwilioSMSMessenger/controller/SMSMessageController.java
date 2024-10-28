package com.example.TwilioSMSMessenger.controller;

import com.example.TwilioSMSMessenger.dto.MessageDTO;
import com.example.TwilioSMSMessenger.service.SMSMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SMSMessageController {

    @Autowired
    private SMSMessageService smsMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO request) {
        return ResponseEntity.ok(smsMessageService.sendSMS(request.getTo(), request.getMessage()));
    }
}
