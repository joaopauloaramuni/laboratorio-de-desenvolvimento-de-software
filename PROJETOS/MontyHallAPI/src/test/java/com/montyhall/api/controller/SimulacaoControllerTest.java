package com.montyhall.api.controller;

import com.montyhall.api.dto.EstrategiaResposta;
import com.montyhall.api.dto.SimulacaoResposta;
import com.montyhall.api.exception.DadosInvalidosException;
import com.montyhall.api.exception.GlobalExceptionHandler;
import com.montyhall.api.service.SimulacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimulacaoController")
class SimulacaoControllerTest {

    private static final String URL = "/api/simulacoes";

    @Mock
    private SimulacaoService simulacaoService;

    @InjectMocks
    private SimulacaoController simulacaoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(simulacaoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();
    }

    private static SimulacaoResposta resultado(int rodadas, long vitoriasTrocando) {
        return SimulacaoResposta.de(rodadas,
                EstrategiaResposta.de(rodadas, vitoriasTrocando),
                EstrategiaResposta.de(rodadas, rodadas - vitoriasTrocando));
    }

    @Test
    @DisplayName("sem parâmetro usa 10.000 rodadas")
    void padrao() throws Exception {
        when(simulacaoService.simular(10_000)).thenReturn(resultado(10_000, 6_670));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rodadas").value(10_000))
                .andExpect(jsonPath("$.trocando.vitorias").value(6_670))
                .andExpect(jsonPath("$.trocando.percentualVitorias").value(66.7))
                .andExpect(jsonPath("$.mantendo.percentualVitorias").value(33.3))
                .andExpect(jsonPath("$.teoricoTrocando").value(66.67))
                .andExpect(jsonPath("$.teoricoMantendo").value(33.33));

        verify(simulacaoService).simular(10_000);
    }

    @Test
    @DisplayName("repassa o parâmetro rodadas")
    void comParametro() throws Exception {
        when(simulacaoService.simular(300)).thenReturn(resultado(300, 200));

        mockMvc.perform(get(URL).param("rodadas", "300"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rodadas").value(300))
                .andExpect(jsonPath("$.trocando.percentualVitorias").value(66.67));

        verify(simulacaoService).simular(300);
    }

    @Test
    @DisplayName("400 quando rodadas está fora do limite")
    void foraDoLimite() throws Exception {
        when(simulacaoService.simular(0))
                .thenThrow(new DadosInvalidosException("A quantidade de rodadas deve ficar entre 1 e 1000000."));

        mockMvc.perform(get(URL).param("rodadas", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("A quantidade de rodadas deve ficar entre 1 e 1000000."));
    }

    @Test
    @DisplayName("400 quando rodadas não é número (service não é chamado)")
    void naoNumerico() throws Exception {
        mockMvc.perform(get(URL).param("rodadas", "muitas"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.mensagem").value("O parâmetro 'rodadas' tem um valor inválido."));

        verify(simulacaoService, never()).simular(anyInt());
    }
}
