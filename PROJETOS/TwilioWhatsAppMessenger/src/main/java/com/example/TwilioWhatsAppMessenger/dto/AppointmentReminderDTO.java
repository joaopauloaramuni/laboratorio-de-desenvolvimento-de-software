package com.example.TwilioWhatsAppMessenger.dto;

import java.util.Map;

public class AppointmentReminderDTO {

    private String to;
    private String message;
    private String contentSid;
    private Map<String, String> contentVariables;

    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentSid() {
        return contentSid;
    }

    public void setContentSid(String contentSid) {
        this.contentSid = contentSid;
    }

    public Map<String, String> getContentVariables() {
        return contentVariables;
    }

    public void setContentVariables(Map<String, String> contentVariables) {
        this.contentVariables = contentVariables;
    }
}
