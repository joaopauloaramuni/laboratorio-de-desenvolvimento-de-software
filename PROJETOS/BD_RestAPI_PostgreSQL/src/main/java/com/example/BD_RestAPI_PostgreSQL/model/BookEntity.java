package com.example.BD_RestAPI_PostgreSQL.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_entity")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    // Construtor padrão
    public BookEntity() {
    }

    // Construtor com parâmetros
    public BookEntity(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
