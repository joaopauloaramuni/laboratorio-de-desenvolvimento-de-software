package com.example.MarvelExplorerRestAPI.model;

public class SeriesNext {
    private String resourceURI; // URI do recurso da próxima série
    private String name;         // Nome da próxima série

    // Construtor
    public SeriesNext() {
    }

    public SeriesNext(String resourceURI, String name) {
        this.resourceURI = resourceURI;
        this.name = name;
    }

    // Getters e Setters
    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

