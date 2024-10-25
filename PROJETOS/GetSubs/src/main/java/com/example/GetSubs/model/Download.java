package com.example.GetSubs.model;

public class Download {
    private String link;
    private String fileName;
    private int requests;
    private int remaining;
    private String message;
    private String resetTime;
    private String resetTimeUtc;

    // Construtor
    public Download(String link, String fileName, int requests, int remaining, String message, String resetTime, String resetTimeUtc) {
        this.link = link;
        this.fileName = fileName;
        this.requests = requests;
        this.remaining = remaining;
        this.message = message;
        this.resetTime = resetTime;
        this.resetTimeUtc = resetTimeUtc;
    }

    // Getters
    public String getLink() {
        return link;
    }

    public String getFileName() {
        return fileName;
    }

    public int getRequests() {
        return requests;
    }

    public int getRemaining() {
        return remaining;
    }

    public String getMessage() {
        return message;
    }

    public String getResetTime() {
        return resetTime;
    }

    public String getResetTimeUtc() {
        return resetTimeUtc;
    }

    // Setters (se necessário)
    public void setLink(String link) {
        this.link = link;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResetTime(String resetTime) {
        this.resetTime = resetTime;
    }

    public void setResetTimeUtc(String resetTimeUtc) {
        this.resetTimeUtc = resetTimeUtc;
    }
}

