package com.example.WakaTimeAPI.model;

public class BestDay {
    private String date;
    private String text;
    private double total_seconds;

    // Getters and Setters

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getTotal_seconds() {
        return total_seconds;
    }

    public void setTotal_seconds(double total_seconds) {
        this.total_seconds = total_seconds;
    }
}