package com.montyhall.api.dto;

/**
 * Resultado de uma estratégia (trocar ou manter a porta).
 *
 * @param partidas           quantas partidas usaram a estratégia
 * @param vitorias           quantas ganharam o carro
 * @param derrotas           quantas ficaram com o bode
 * @param percentualVitorias de 0 a 100, com 2 casas decimais
 */
public record EstrategiaResposta(long partidas, long vitorias, long derrotas, double percentualVitorias) {

    public static EstrategiaResposta de(long partidas, long vitorias) {
        return new EstrategiaResposta(partidas, vitorias, partidas - vitorias, percentual(vitorias, partidas));
    }

    /** Ex.: percentual(2, 3) → 66.67. Com 0 partidas devolve 0. */
    public static double percentual(long parte, long total) {
        if (total <= 0) {
            return 0.0;
        }
        return Math.round(parte * 10000.0 / total) / 100.0;
    }
}
