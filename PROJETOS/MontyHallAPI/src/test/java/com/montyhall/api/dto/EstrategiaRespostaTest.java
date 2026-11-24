package com.montyhall.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EstrategiaResposta, EstatisticasResposta e SimulacaoResposta")
class EstrategiaRespostaTest {

    @ParameterizedTest(name = "{0} de {1} = {2}%")
    @CsvSource({
            "2, 3, 66.67",
            "1, 3, 33.33",
            "0, 10, 0.0",
            "10, 10, 100.0",
            "1, 8, 12.5",
            "5, 0, 0.0"
    })
    void percentual(long parte, long total, double esperado) {
        assertEquals(esperado, EstrategiaResposta.percentual(parte, total));
    }

    @Test
    @DisplayName("de() calcula derrotas e percentual")
    void de() {
        EstrategiaResposta e = EstrategiaResposta.de(30, 20);

        assertEquals(30, e.partidas());
        assertEquals(20, e.vitorias());
        assertEquals(10, e.derrotas());
        assertEquals(66.67, e.percentualVitorias());
    }

    @Test
    @DisplayName("estatísticas sem partidas pedem para jogar")
    void semPartidas() {
        EstatisticasResposta r = EstatisticasResposta.de(EstrategiaResposta.de(0, 0), EstrategiaResposta.de(0, 0));

        assertEquals(0, r.partidasFinalizadas());
        assertEquals("Nenhuma partida finalizada ainda. Jogue em POST /api/jogos.", r.conclusao());
    }

    @Test
    @DisplayName("estatísticas: trocar venceu")
    void trocarVenceu() {
        EstatisticasResposta r = EstatisticasResposta.de(EstrategiaResposta.de(30, 20), EstrategiaResposta.de(30, 10));

        assertEquals(60, r.partidasFinalizadas());
        assertTrue(r.conclusao().startsWith("Trocando: 66.67% de vitórias. Mantendo: 33.33%."));
        assertTrue(r.conclusao().contains("Trocar de porta foi a melhor estratégia"));
    }

    @Test
    @DisplayName("estatísticas: manter venceu (amostra pequena)")
    void manterVenceu() {
        EstatisticasResposta r = EstatisticasResposta.de(EstrategiaResposta.de(2, 0), EstrategiaResposta.de(2, 2));

        assertTrue(r.conclusao().contains("Manter a porta se saiu melhor desta vez"));
    }

    @Test
    @DisplayName("estatísticas: empate")
    void empate() {
        EstatisticasResposta r = EstatisticasResposta.de(EstrategiaResposta.de(2, 1), EstrategiaResposta.de(2, 1));

        assertTrue(r.conclusao().contains("Empate por enquanto"));
    }

    @Test
    @DisplayName("simulação traz os valores teóricos 66.67% e 33.33%")
    void simulacao() {
        SimulacaoResposta r = SimulacaoResposta.de(3, EstrategiaResposta.de(3, 2), EstrategiaResposta.de(3, 1));

        assertEquals(3, r.rodadas());
        assertEquals(66.67, r.teoricoTrocando());
        assertEquals(33.33, r.teoricoMantendo());
        assertEquals(66.67, r.trocando().percentualVitorias());
        assertEquals(33.33, r.mantendo().percentualVitorias());
        assertNotNull(r.conclusao());
    }
}
