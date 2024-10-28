package com.example.TwilioSMSMessenger.service;

import org.springframework.stereotype.Service;
import com.example.TwilioSMSMessenger.config.TwilioConfig;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SMSMessageService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public SMSMessageService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        // Inicializar o cliente Twilio
        com.twilio.Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public String sendSMS(String to, String msg) {
        try {
            Message message = Message.creator(
                    new PhoneNumber("+" + to),
                    new PhoneNumber("+" + twilioConfig.getTwilioSMSNumber()),
                    msg)
                            .create();
            return "Mensagem enviada com SID: " + message.getSid();
        }catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar a mensagem: " + e.getMessage();
        }
    }
}
