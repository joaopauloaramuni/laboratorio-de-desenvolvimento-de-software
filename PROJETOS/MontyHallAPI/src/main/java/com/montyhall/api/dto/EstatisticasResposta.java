package com.montyhall.api.dto;

/**
 * Placar de todas as partidas finalizadas, gravadas no MongoDB.
 *
 * GET /api/jogos/estatisticas
 */
public record EstatisticasResposta(
        long partidasFinalizadas,
        EstrategiaResposta trocando,
        EstrategiaResposta mantendo,
        String conclusao
) {

    public static EstatisticasResposta de(EstrategiaResposta trocando, EstrategiaResposta mantendo) {
        long total = trocando.partidas() + mantendo.partidas();
        return new EstatisticasResposta(total, trocando, mantendo, Conclusao.comparar(total, trocando, mantendo));
    }
}
