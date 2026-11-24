package com.montyhall.api.dto;

/**
 * Resultado de GET /api/simulacoes?rodadas=10000
 *
 * Cada rodada é jogada uma vez e avaliada pelas duas estratégias:
 * em toda rodada, exatamente uma delas ganha o carro.
 *
 * @param teoricoTrocando valor exato da teoria (2/3), em %
 * @param teoricoMantendo valor exato da teoria (1/3), em %
 */
public record SimulacaoResposta(
        int rodadas,
        EstrategiaResposta trocando,
        EstrategiaResposta mantendo,
        double teoricoTrocando,
        double teoricoMantendo,
        String conclusao
) {

    public static final double TEORICO_TROCANDO = EstrategiaResposta.percentual(2, 3); // 66.67
    public static final double TEORICO_MANTENDO = EstrategiaResposta.percentual(1, 3); // 33.33

    public static SimulacaoResposta de(int rodadas, EstrategiaResposta trocando, EstrategiaResposta mantendo) {
        return new SimulacaoResposta(rodadas, trocando, mantendo, TEORICO_TROCANDO, TEORICO_MANTENDO,
                Conclusao.comparar(rodadas, trocando, mantendo));
    }
}
