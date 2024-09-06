package com.example.RestAPI.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "releases")
public class ReleasesEntity {

    @Id
    private String id;
    private String releases;

    public ReleasesEntity(){
    }

    // Método construtor da classe
    public ReleasesEntity(String id, String releases) {
        this.id = id;
        this.releases = releases;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleases() {
        return releases;
    }

    public void setReleases(String releases) {
        this.releases = releases;
    }
}
