package com.example.WakaTimeAPI.model;

public class Dependency {
    private String decimal;
    private String digital;
    private int hours;
    private int minutes;
    private String name;
    private double percent;
    private String text;
    private double total_seconds;

    // Getters and Setters

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getDigital() {
        return digital;
    }

    public void setDigital(String digital) {
        this.digital = digital;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
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