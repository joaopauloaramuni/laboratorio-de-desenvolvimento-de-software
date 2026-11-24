package com.montyhall.api.service;

import com.montyhall.api.dto.EstrategiaResposta;
import com.montyhall.api.dto.SimulacaoResposta;
import com.montyhall.api.exception.DadosInvalidosException;
import com.montyhall.api.model.Portas;
import org.springframework.stereotype.Service;

/**
 * Joga milhares de partidas automáticas, em memória (nada é gravado no MongoDB),
 * para mostrar na prática que trocar de porta vence em cerca de 2/3 das vezes.
 *
 * Foi uma simulação assim que convenceu o matemático Paul Erdős (veja o README).
 */
@Service
public class SimulacaoService {

    public static final int MINIMO_RODADAS = 1;
    public static final int MAXIMO_RODADAS = 1_000_000;

    private final Apresentador apresentador;

    public SimulacaoService(Apresentador apresentador) {
        this.apresentador = apresentador;
    }

    public SimulacaoResposta simular(int rodadas) {
        if (rodadas < MINIMO_RODADAS || rodadas > MAXIMO_RODADAS) {
            throw new DadosInvalidosException("A quantidade de rodadas deve ficar entre "
                    + MINIMO_RODADAS + " e " + MAXIMO_RODADAS + ".");
        }

        long vitoriasMantendo = 0;
        long vitoriasTrocando = 0;

        for (int i = 0; i < rodadas; i++) {
            int premiada = apresentador.sortearPorta();   // onde está o carro
            int escolhida = apresentador.sortearPorta();  // escolha do jogador (ao acaso)
            int aberta = apresentador.abrirPortaComBode(premiada, escolhida);
            int trocada = Portas.excluindo(escolhida, aberta).get(0);

            // A mesma rodada é avaliada pelas duas estratégias
            if (escolhida == premiada) {
                vitoriasMantendo++;
            }
            if (trocada == premiada) {
                vitoriasTrocando++;
            }
        }

        return SimulacaoResposta.de(rodadas,
                EstrategiaResposta.de(rodadas, vitoriasTrocando),
                EstrategiaResposta.de(rodadas, vitoriasMantendo));
    }
}
