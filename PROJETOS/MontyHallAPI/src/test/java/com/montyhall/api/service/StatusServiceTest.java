package com.montyhall.api.service;

import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("StatusService")
class StatusServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private StatusService statusService;

    @Test
    @DisplayName("devolve true quando o MongoDB responde ao ping")
    void online() {
        when(mongoTemplate.executeCommand("{ ping: 1 }")).thenReturn(new Document("ok", 1.0));

        assertTrue(statusService.mongoDbOnline());
        verify(mongoTemplate).executeCommand("{ ping: 1 }");
    }

    @Test
    @DisplayName("devolve false quando o MongoDB não responde")
    void offline() {
        when(mongoTemplate.executeCommand("{ ping: 1 }"))
                .thenThrow(new DataAccessResourceFailureException("timeout"));

        assertFalse(statusService.mongoDbOnline());
    }

    @Test
    @DisplayName("devolve false para qualquer outra exceção")
    void erroInesperado() {
        when(mongoTemplate.executeCommand("{ ping: 1 }")).thenThrow(new IllegalStateException("falha"));

        assertFalse(statusService.mongoDbOnline());
    }
}
