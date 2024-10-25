package com.example.GetSubs.dto;

public class SearchRequestDTO {
    private String query;    // Nome do filme
    private String languages; // Códigos de idioma (ex: "pt-br")

    // Construtor padrão
    public SearchRequestDTO() {
    }

    // Construtor com parâmetros
    public SearchRequestDTO(String query, String languages) {
        this.query = query;
        this.languages = languages;
    }

    // Getters e Setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
