package com.example.TwilioSMSMessenger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio_account_ssid}")
    private String accountSid;

    @Value("${twilio_auth_token}")
    private String authToken;

    @Value("${twilio_sms_number}")
    private String twilioSMSNumber;

    @Value("${your_sms_number}")
    private String yourSMSnumber;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getTwilioSMSNumber() {
        return twilioSMSNumber;
    }

    public String getYourSMSnumber() {
        return yourSMSnumber;
    }
}
