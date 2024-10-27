package com.example.TwilioWhatsAppMessenger.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.example.TwilioWhatsAppMessenger.config.TwilioConfig;
import com.example.TwilioWhatsAppMessenger.dto.AppointmentReminderDTO;
import com.example.TwilioWhatsAppMessenger.dto.MessageDTO;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service
public class WhatsAppMessageService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public WhatsAppMessageService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        // Inicializar o cliente Twilio
        com.twilio.Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public String sendMessage(String to, String msg) {
        try {
            Message message = Message.creator(
                            // new PhoneNumber("whatsapp:+" + twilioConfig.getYourwhatsappnumber()),
                            new PhoneNumber("whatsapp:+" + to),
                            new PhoneNumber("whatsapp:+" + twilioConfig.getTwiliowhatsappNumber()),
                            msg)
                    .create();
            return "Mensagem enviada com SID: " + message.getSid();
        }catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar a mensagem: " + e.getMessage();
        }
    }

    public String sendAppointmentReminder(String to, String msg, String contentSid, Map<String, String> contentVariables) {
        try {
            String contentVariablesJson = new JSONObject(contentVariables).toString();
            Message message = Message.creator(
                            // new PhoneNumber("whatsapp:+" + twilioConfig.getYourwhatsappnumber()),
                            new PhoneNumber("whatsapp:+" + to),
                            new PhoneNumber("whatsapp:+" + twilioConfig.getTwiliowhatsappNumber()),
                            msg)
                    .setContentSid(contentSid)
                    .setContentVariables(contentVariablesJson)
                    .create();
            return "Lembrete de compromisso enviado com SID: " + message.getSid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar lembrete de compromisso: " + e.getMessage();
        }
    }
}
