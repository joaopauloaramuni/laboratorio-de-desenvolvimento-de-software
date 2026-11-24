package com.montyhall.api.dto;

import com.montyhall.api.model.Jogo;
import com.montyhall.api.model.Portas;
import com.montyhall.api.model.Premio;
import com.montyhall.api.model.StatusJogo;

import java.time.Instant;
import java.util.List;

/**
 * O que a API devolve sobre uma partida.
 *
 * Por que não devolver o Jogo direto, como o Produto no AraCoffee?
 * Porque o Jogo guarda a porta premiada desde o início. Se ele fosse
 * devolvido como está, bastaria olhar o JSON para "colar" no jogo.
 * Esta classe só revela o carro quando a partida está FINALIZADO.
 *
 * @param portaOferecida a porta para a qual o jogador pode trocar (a outra que ficou fechada)
 * @param portaPremiada  null até a partida terminar
 */
public record JogoResposta(
        String id,
        StatusJogo status,
        String mensagem,
        List<PortaResposta> portas,
        Integer portaEscolhida,
        Integer portaAberta,
        Integer portaOferecida,
        Boolean trocou,
        Integer portaFinal,
        Boolean venceu,
        Integer portaPremiada,
        String criadoEm,
        String finalizadoEm
) {

    /** Converte o documento do MongoDB no JSON que o jogador pode ver. */
    public static JogoResposta de(Jogo jogo) {
        boolean finalizado = jogo.getStatus() == StatusJogo.FINALIZADO;

        Integer portaOferecida = null;
        if (jogo.getPortaEscolhida() != null && jogo.getPortaAberta() != null) {
            portaOferecida = Portas.excluindo(jogo.getPortaEscolhida(), jogo.getPortaAberta()).get(0);
        }

        return new JogoResposta(
                jogo.getId(),
                jogo.getStatus(),
                montarMensagem(jogo, portaOferecida),
                montarPortas(jogo, finalizado),
                jogo.getPortaEscolhida(),
                jogo.getPortaAberta(),
                portaOferecida,
                jogo.getTrocou(),
                jogo.getPortaFinal(),
                jogo.getVenceu(),
                finalizado ? jogo.getPortaPremiada() : null,
                texto(jogo.getCriadoEm()),
                texto(jogo.getFinalizadoEm())
        );
    }

    /** Monta as 3 portas, abrindo só as que o jogador já pode ver. */
    private static List<PortaResposta> montarPortas(Jogo jogo, boolean finalizado) {
        Integer portaDoJogador = finalizado ? jogo.getPortaFinal() : jogo.getPortaEscolhida();

        return Portas.TODAS.stream()
                .map(numero -> {
                    boolean aberta = finalizado || numero.equals(jogo.getPortaAberta());
                    Premio conteudo = null;
                    if (aberta) {
                        conteudo = numero.equals(jogo.getPortaPremiada()) ? Premio.CARRO : Premio.BODE;
                    }
                    return new PortaResposta(numero, aberta, conteudo, numero.equals(portaDoJogador));
                })
                .toList();
    }

    /** Fala do apresentador em cada etapa. */
    private static String montarMensagem(Jogo jogo, Integer portaOferecida) {
        if (jogo.getStatus() == null) {
            return null;
        }
        return switch (jogo.getStatus()) {
            case AGUARDANDO_ESCOLHA -> "O carro está atrás de uma das 3 portas. Escolha a sua: "
                    + "POST /api/jogos/" + jogo.getId() + "/escolha com { \"porta\": 1 }, 2 ou 3.";
            case AGUARDANDO_DECISAO -> "Você escolheu a porta " + jogo.getPortaEscolhida()
                    + ". O apresentador abriu a porta " + jogo.getPortaAberta()
                    + " e... é um bode! 🐐 Quer trocar para a porta " + portaOferecida
                    + "? Responda em POST /api/jogos/" + jogo.getId()
                    + "/decisao com { \"trocar\": true } ou { \"trocar\": false }.";
            case FINALIZADO -> {
                String acao = Boolean.TRUE.equals(jogo.getTrocou()) ? "trocou para" : "ficou com";
                if (Boolean.TRUE.equals(jogo.getVenceu())) {
                    yield "Você " + acao + " a porta " + jogo.getPortaFinal() + " e ganhou o CARRO! 🚗";
                }
                yield "Você " + acao + " a porta " + jogo.getPortaFinal() + " e levou um bode. 🐐 "
                        + "O carro estava na porta " + jogo.getPortaPremiada() + ".";
            }
        };
    }

    private static String texto(Instant instante) {
        return instante == null ? null : instante.toString();
    }
}
