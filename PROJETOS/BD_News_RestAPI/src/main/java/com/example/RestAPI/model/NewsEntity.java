package com.example.RestAPI.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "news")
public class NewsEntity {

    @Id
    private String id;
    private String noticias;

    public NewsEntity(){
    }

    // Método construtor da classe
    public NewsEntity(String id, String noticias) {
        this.id = id;
        this.noticias = noticias;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticias() {
        return noticias;
    }

    public void setNoticias(String noticias) {
        this.noticias = noticias;
    }
}
