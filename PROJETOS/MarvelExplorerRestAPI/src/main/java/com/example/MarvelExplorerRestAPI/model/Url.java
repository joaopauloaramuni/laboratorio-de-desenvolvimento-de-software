package com.example.MarvelExplorerRestAPI.model;

public class Url {
    private String type; // Tipo da URL, ex: "detail", "wiki", etc.
    private String url;  // URL em si

    // Construtor
    public Url() {
    }

    public Url(String type, String url) {
        this.type = type;
        this.url = url;
    }

    // Getters e Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

