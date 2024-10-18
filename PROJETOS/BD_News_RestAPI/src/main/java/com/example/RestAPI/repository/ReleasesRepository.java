package com.example.RestAPI.repository;

import com.example.RestAPI.model.ReleasesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleasesRepository extends MongoRepository<ReleasesEntity, String> {
    // Métodos de CRUD já estão disponíveis
    //findAll, findById, save, deleteById
}
