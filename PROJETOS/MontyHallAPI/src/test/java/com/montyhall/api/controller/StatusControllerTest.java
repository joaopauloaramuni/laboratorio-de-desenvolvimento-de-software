package com.montyhall.api.controller;

import com.montyhall.api.exception.GlobalExceptionHandler;
import com.montyhall.api.service.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("StatusController")
class StatusControllerTest {

    @Mock
    private StatusService statusService;

    @InjectMocks
    private StatusController statusController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(statusController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest(name = "GET {0} com MongoDB online")
    @ValueSource(strings = {"/", "/api/status"})
    void mongoOnline(String url) throws Exception {
        when(statusService.mongoDbOnline()).thenReturn(true);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.api").value("online"))
                .andExpect(jsonPath("$.mongodb").value("online"));
    }

    @ParameterizedTest(name = "GET {0} com MongoDB offline")
    @ValueSource(strings = {"/", "/api/status"})
    void mongoOffline(String url) throws Exception {
        when(statusService.mongoDbOnline()).thenReturn(false);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.api").value("online"))
                .andExpect(jsonPath("$.mongodb").value("offline"));
    }

    @Test
    @DisplayName("o mapa de resposta mantém a ordem: api, mongodb")
    void ordemDasChaves() {
        when(statusService.mongoDbOnline()).thenReturn(true);

        Map<String, String> resposta = statusController.status();

        assertEquals(List.of("api", "mongodb"), List.copyOf(resposta.keySet()));
        assertEquals("online", resposta.get("api"));
        assertEquals("online", resposta.get("mongodb"));
    }
}
