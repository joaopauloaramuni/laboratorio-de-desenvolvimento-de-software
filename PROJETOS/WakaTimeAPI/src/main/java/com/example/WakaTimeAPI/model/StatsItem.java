package com.example.WakaTimeAPI.model;

/** Uma linha do painel: linguagem, editor, sistema, categoria ou projeto. */
public class StatsItem {

    private final String name;
    private final long seconds;
    private final double percent;
    private final String color; // só nas linguagens

    public StatsItem(String name, long seconds, double percent, String color) {
        this.name = name;
        this.seconds = seconds;
        this.percent = percent;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public long getSeconds() {
        return seconds;
    }

    public double getPercent() {
        return percent;
    }

    public String getColor() {
        return color;
    }
}
