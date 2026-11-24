package com.montyhall.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Conecta o Spring Boot ao MongoDB Atlas usando os valores de MongoDBProperties
 * (que vêm do application.properties).
 *
 * Estender AbstractMongoClientConfiguration faz o Spring criar o MongoClient e o
 * MongoTemplate a partir daqui, em vez de usar as propriedades spring.data.mongodb.*.
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final MongoDBProperties mongoDBProperties;

    public MongoConfig(MongoDBProperties mongoDBProperties) {
        this.mongoDBProperties = mongoDBProperties;
    }

    /** Nome do banco (app.mongodb.database). O Atlas cria o banco no primeiro insert. */
    @Override
    protected String getDatabaseName() {
        return mongoDBProperties.getDatabase();
    }

    /** Aplica a connection string (app.mongodb.connection-string) ao cliente do MongoDB. */
    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        String uri = mongoDBProperties.getConnectionString();

        if (uri == null || uri.isBlank()) {
            throw new IllegalStateException(
                    "Configure app.mongodb.connection-string no application.properties "
                            + "(ou a variável de ambiente MONGODB_URI).");
        }

        ConnectionString connectionString;

        try {
            connectionString = new ConnectionString(uri);
        } catch (RuntimeException e) {
            throw new IllegalStateException(
                    "Connection string do MongoDB inválida. Confira app.mongodb.connection-string "
                            + "no application.properties (usuário, senha e endereço do cluster).", e);
        }

        builder.applyConnectionString(connectionString)
                // Se o Atlas não responder em 5 segundos, desiste e devolve erro
                // (o padrão do driver é 30 segundos, longo demais para esperar na tela)
                .applyToClusterSettings(cluster -> cluster.serverSelectionTimeout(5, TimeUnit.SECONDS));
    }

    /** Pacote onde ficam as classes @Document (model). */
    @Override
    protected Collection<String> getMappingBasePackages() {
        return List.of("com.montyhall.api.model");
    }
}
