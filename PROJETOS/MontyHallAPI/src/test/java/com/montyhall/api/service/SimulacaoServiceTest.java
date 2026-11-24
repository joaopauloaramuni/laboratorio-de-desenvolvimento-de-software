package com.montyhall.api.service;

import com.montyhall.api.dto.SimulacaoResposta;
import com.montyhall.api.exception.DadosInvalidosException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimulacaoService")
class SimulacaoServiceTest {

    @Mock
    private Apresentador apresentador;

    @InjectMocks
    private SimulacaoService simulacaoService;

    @Test
    @DisplayName("com o Apresentador mockado: conta certo quem ganhou em cada rodada")
    void contagemControlada() {
        // Rodada 1: carro na 1, jogador escolhe 1 → manter ganha
        // Rodada 2: carro na 1, jogador escolhe 2 → trocar ganha
        // Rodada 3: carro na 3, jogador escolhe 1 → trocar ganha
        when(apresentador.sortearPorta()).thenReturn(1, 1, 1, 2, 3, 1);
        when(apresentador.abrirPortaComBode(1, 1)).thenReturn(2);
        when(apresentador.abrirPortaComBode(1, 2)).thenReturn(3);
        when(apresentador.abrirPortaComBode(3, 1)).thenReturn(2);

        SimulacaoResposta r = simulacaoService.simular(3);

        assertEquals(3, r.rodadas());
        assertEquals(1, r.mantendo().vitorias());
        assertEquals(2, r.trocando().vitorias());
        assertEquals(33.33, r.mantendo().percentualVitorias());
        assertEquals(66.67, r.trocando().percentualVitorias());
        verify(apresentador, times(6)).sortearPorta();
        verify(apresentador, times(3)).abrirPortaComBode(anyInt(), anyInt());
    }

    @Test
    @DisplayName("com sorteio de verdade: trocar fica perto de 66,67% e manter perto de 33,33%")
    void leiDosGrandesNumeros() {
        SimulacaoService real = new SimulacaoService(new Apresentador(new Random(2026)));

        SimulacaoResposta r = real.simular(100_000);

        assertEquals(100_000, r.trocando().partidas());
        assertEquals(100_000, r.mantendo().partidas());
        assertEquals(66.67, r.trocando().percentualVitorias(), 1.0);
        assertEquals(33.33, r.mantendo().percentualVitorias(), 1.0);
        assertTrue(r.conclusao().contains("Trocar de porta foi a melhor estratégia"));
    }

    @Test
    @DisplayName("em toda rodada exatamente uma estratégia ganha")
    void umaEstrategiaGanhaPorRodada() {
        SimulacaoService real = new SimulacaoService(new Apresentador(new Random(1)));

        SimulacaoResposta r = real.simular(5_000);

        assertEquals(5_000, r.trocando().vitorias() + r.mantendo().vitorias());
        assertEquals(r.trocando().vitorias(), r.mantendo().derrotas());
    }

    @Test
    @DisplayName("aceita os limites: 1 rodada")
    void minimo() {
        when(apresentador.sortearPorta()).thenReturn(2, 2);
        when(apresentador.abrirPortaComBode(2, 2)).thenReturn(1);

        assertEquals(1, simulacaoService.simular(SimulacaoService.MINIMO_RODADAS).rodadas());
    }

    @ParameterizedTest(name = "rodadas = {0} → 400")
    @ValueSource(ints = {0, -1, -1000, 1_000_001, Integer.MAX_VALUE})
    void foraDoLimite(int rodadas) {
        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class,
                () -> simulacaoService.simular(rodadas));

        assertEquals("A quantidade de rodadas deve ficar entre 1 e 1000000.", ex.getMessage());
        verifyNoInteractions(apresentador);
    }
}
