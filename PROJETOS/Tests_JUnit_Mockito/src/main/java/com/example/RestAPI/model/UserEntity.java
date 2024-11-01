package com.example.RestAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "user")
public class UserEntity {

    @Id
    private String id;
    private String nome;
    private String email;
    private Integer idade;
    private Date dataDeCadastro;

    // Construtor da classe
    public UserEntity(String id, String nome, String email, int idade, Date dataDeCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.dataDeCadastro = dataDeCadastro;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(Date dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }
}
