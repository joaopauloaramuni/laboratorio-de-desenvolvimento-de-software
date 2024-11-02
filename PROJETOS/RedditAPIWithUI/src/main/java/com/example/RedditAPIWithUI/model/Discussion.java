package com.example.RedditAPIWithUI.model;

public class Discussion {
    private String title;
    private String url;
    private String author;
    private String selftext;
    private String createdDate;

    // Construtores, getters e setters
    public Discussion(String title, String url, String author, String selftext, String createdDate) {
        this.title = title;
        this.url = url;
        this.author = author;
        this.selftext = selftext;
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
