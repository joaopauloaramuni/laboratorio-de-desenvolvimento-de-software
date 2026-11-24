package com.montyhall.api.dto;

/** Texto que resume quem ganhou mais: trocar ou manter. */
final class Conclusao {

    private Conclusao() {
    }

    static String comparar(long total, EstrategiaResposta trocando, EstrategiaResposta mantendo) {
        if (total == 0) {
            return "Nenhuma partida finalizada ainda. Jogue em POST /api/jogos.";
        }
        String placar = "Trocando: " + trocando.percentualVitorias() + "% de vitórias. "
                + "Mantendo: " + mantendo.percentualVitorias() + "%.";

        if (trocando.percentualVitorias() > mantendo.percentualVitorias()) {
            return placar + " Trocar de porta foi a melhor estratégia (a teoria diz 2/3 contra 1/3).";
        }
        if (mantendo.percentualVitorias() > trocando.percentualVitorias()) {
            return placar + " Manter a porta se saiu melhor desta vez, mas com mais partidas "
                    + "a tendência é trocar vencer 2/3 das vezes.";
        }
        return placar + " Empate por enquanto. Com mais partidas, trocar tende a vencer 2/3 das vezes.";
    }
}
