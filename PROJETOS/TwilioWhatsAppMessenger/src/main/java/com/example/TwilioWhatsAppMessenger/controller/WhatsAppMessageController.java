package com.example.TwilioWhatsAppMessenger.controller;

import com.example.TwilioWhatsAppMessenger.dto.AppointmentReminderDTO;
import com.example.TwilioWhatsAppMessenger.dto.MessageDTO;
import com.example.TwilioWhatsAppMessenger.service.WhatsAppMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppMessageController {

    @Autowired
    private WhatsAppMessageService whatsAppService;

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO request) {
        return ResponseEntity.ok(whatsAppService.sendMessage(request.getTo(), request.getMessage()));
    }

    @PostMapping("/sendAppointmentReminder")
    public ResponseEntity<String> sendAppointmentReminder(@RequestBody AppointmentReminderDTO request) {
        return ResponseEntity.ok(whatsAppService.sendAppointmentReminder(request.getTo(), request.getMessage(), request.getContentSid(), request.getContentVariables()));
    }
}
