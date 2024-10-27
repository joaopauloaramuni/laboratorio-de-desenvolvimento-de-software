package com.example.MarvelExplorerRestAPI.model;

public class SeriesPrevious {
    private String resourceURI; // URI do recurso da série anterior
    private String name;        // Nome da série anterior

    // Construtor
    public SeriesPrevious() {
    }

    public SeriesPrevious(String resourceURI, String name) {
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
