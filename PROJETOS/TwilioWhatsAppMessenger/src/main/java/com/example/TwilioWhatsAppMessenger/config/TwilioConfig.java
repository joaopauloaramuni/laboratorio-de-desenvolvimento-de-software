package com.example.TwilioWhatsAppMessenger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio_account_ssid}")
    private String accountSid;

    @Value("${twilio_auth_token}")
    private String authToken;

    @Value("${twilio_whatsapp_number}")
    private String twiliowhatsappNumber;

    @Value("${your_whatsapp_number}")
    private String yourwhatsappnumber;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getTwiliowhatsappNumber() {
        return twiliowhatsappNumber;
    }

    public String getYourwhatsappnumber() {
        return yourwhatsappnumber;
    }
}
