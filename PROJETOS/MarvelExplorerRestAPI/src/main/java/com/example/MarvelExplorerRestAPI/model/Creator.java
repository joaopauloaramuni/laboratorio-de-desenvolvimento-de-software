package com.example.MarvelExplorerRestAPI.model;

public class Creator {
    private String resourceURI; // URI do recurso do criador
    private String name;        // Nome do criador
    private String role;        // Papel do criador (ex: "writer", "artist", etc.)

    // Construtor
    public Creator() {
    }

    public Creator(String resourceURI, String name, String role) {
        this.resourceURI = resourceURI;
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

