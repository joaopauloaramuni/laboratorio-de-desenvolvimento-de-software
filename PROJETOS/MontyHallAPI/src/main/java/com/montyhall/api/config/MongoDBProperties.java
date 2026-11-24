package com.montyhall.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Lê do application.properties tudo que começa com "app.mongodb":
 *
 * app.mongodb.connection-string=mongodb+srv://...
 * app.mongodb.database=montyhall
 *
 * O Spring converte "connection-string" para o atributo "connectionString".
 */
@Configuration
@ConfigurationProperties(prefix = "app.mongodb")
public class MongoDBProperties {

    private String connectionString;
    private String database;

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
