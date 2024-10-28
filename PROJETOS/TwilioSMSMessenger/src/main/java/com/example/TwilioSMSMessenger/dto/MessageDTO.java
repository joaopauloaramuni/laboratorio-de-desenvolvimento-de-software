package com.example.TwilioSMSMessenger.dto;

public class MessageDTO {
    private String to;
    private String message;

    // Getters e Setters
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
}
