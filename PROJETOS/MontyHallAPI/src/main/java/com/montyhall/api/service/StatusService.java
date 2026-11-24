package com.montyhall.api.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Verifica se a API consegue falar com o MongoDB Atlas.
 * Usado pelo endpoint GET /api/status.
 */
@Service
public class StatusService {

    private final MongoTemplate mongoTemplate;

    public StatusService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /** Envia o comando "ping" ao MongoDB. Se responder, o banco está acessível. */
    public boolean mongoDbOnline() {
        try {
            mongoTemplate.executeCommand("{ ping: 1 }");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
