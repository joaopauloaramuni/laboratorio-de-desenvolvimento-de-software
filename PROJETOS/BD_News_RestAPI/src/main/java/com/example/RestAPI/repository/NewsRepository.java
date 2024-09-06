package com.example.RestAPI.repository;

import com.example.RestAPI.model.NewsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<NewsEntity, String> {
    // Métodos de CRUD já estão disponíveis
    //findAll, findById, save, deleteById
}
