package com.montyhall.api.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a configuração do cliente do MongoDB sem abrir nenhuma conexão:
 * o MongoClientSettings só é montado, nunca usado.
 */
@DisplayName("MongoConfig e MongoDBProperties")
class MongoConfigTest {

    private MongoDBProperties props;
    private MongoConfig mongoConfig;

    @BeforeEach
    void setUp() {
        props = new MongoDBProperties();
        props.setDatabase("montyhall");
        mongoConfig = new MongoConfig(props);
    }

    @Test
    @DisplayName("usa o nome do banco vindo das propriedades")
    void nomeDoBanco() {
        assertEquals("montyhall", mongoConfig.getDatabaseName());

        props.setDatabase("outro-banco");
        assertEquals("outro-banco", mongoConfig.getDatabaseName());
    }

    @Test
    @DisplayName("mapeia o pacote model")
    void pacotesMapeados() {
        assertEquals(List.of("com.montyhall.api.model"), List.copyOf(mongoConfig.getMappingBasePackages()));
    }

    @Test
    @DisplayName("aplica a connection string e o timeout de 5 segundos")
    void connectionStringValida() {
        props.setConnectionString("mongodb://localhost:27017/montyhall");
        MongoClientSettings.Builder builder = MongoClientSettings.builder();

        mongoConfig.configureClientSettings(builder);
        MongoClientSettings settings = builder.build();

        assertEquals(5, settings.getClusterSettings().getServerSelectionTimeout(TimeUnit.SECONDS));
        assertEquals(List.of(new ServerAddress("localhost", 27017)), settings.getClusterSettings().getHosts());
    }

    @ParameterizedTest(name = "connection string = \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void connectionStringAusente(String uri) {
        props.setConnectionString(uri);
        MongoClientSettings.Builder builder = MongoClientSettings.builder();

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> mongoConfig.configureClientSettings(builder));
        assertTrue(ex.getMessage().contains("app.mongodb.connection-string"));
        assertTrue(ex.getMessage().contains("MONGODB_URI"));
    }

    @ParameterizedTest(name = "connection string inválida = \"{0}\"")
    @ValueSource(strings = {"isso-nao-e-uma-uri", "http://localhost:27017"})
    void connectionStringInvalida(String uri) {
        props.setConnectionString(uri);
        MongoClientSettings.Builder builder = MongoClientSettings.builder();

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> mongoConfig.configureClientSettings(builder));
        assertTrue(ex.getMessage().startsWith("Connection string do MongoDB inválida."));
        assertNotNull(ex.getCause(), "a exceção original do driver deve ser preservada");
    }

    @Test
    @DisplayName("MongoDBProperties: getters e setters")
    void propriedades() {
        MongoDBProperties p = new MongoDBProperties();
        assertNull(p.getConnectionString());
        assertNull(p.getDatabase());

        p.setConnectionString("mongodb://localhost");
        p.setDatabase("montyhall");

        assertEquals("mongodb://localhost", p.getConnectionString());
        assertEquals("montyhall", p.getDatabase());
    }
}
