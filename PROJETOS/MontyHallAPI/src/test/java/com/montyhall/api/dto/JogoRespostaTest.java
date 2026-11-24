package com.montyhall.api.dto;

import com.montyhall.api.model.Jogo;
import com.montyhall.api.model.Premio;
import com.montyhall.api.model.StatusJogo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * O ponto mais importante desta classe: o carro NÃO pode aparecer
 * no JSON antes de a partida terminar.
 */
@DisplayName("JogoResposta (o que o jogador pode ver)")
class JogoRespostaTest {

    private static final Instant CRIADO = Instant.parse("2026-11-23T18:00:00Z");
    private static final Instant FINALIZADO = Instant.parse("2026-11-23T18:00:40Z");

    private static Jogo novo(int premiada) {
        Jogo j = new Jogo();
        j.setId("j1");
        j.setPortaPremiada(premiada);
        j.setStatus(StatusJogo.AGUARDANDO_ESCOLHA);
        j.setCriadoEm(CRIADO);
        return j;
    }

    private static Jogo escolhido(int premiada, int escolhida, int aberta) {
        Jogo j = novo(premiada);
        j.setPortaEscolhida(escolhida);
        j.setPortaAberta(aberta);
        j.setStatus(StatusJogo.AGUARDANDO_DECISAO);
        return j;
    }

    private static Jogo finalizado(int premiada, int escolhida, int aberta, boolean trocou, int portaFinal) {
        Jogo j = escolhido(premiada, escolhida, aberta);
        j.setTrocou(trocou);
        j.setPortaFinal(portaFinal);
        j.setVenceu(premiada == portaFinal);
        j.setStatus(StatusJogo.FINALIZADO);
        j.setFinalizadoEm(FINALIZADO);
        return j;
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("AGUARDANDO_ESCOLHA")
    class AguardandoEscolha {

        @Test
        @DisplayName("todas as portas fechadas e o carro escondido")
        void tudoFechado() {
            JogoResposta r = JogoResposta.de(novo(2));

            assertEquals("j1", r.id());
            assertEquals(StatusJogo.AGUARDANDO_ESCOLHA, r.status());
            assertNull(r.portaPremiada(), "o carro não pode aparecer antes do fim");
            assertEquals(3, r.portas().size());
            assertTrue(r.portas().stream().noneMatch(PortaResposta::aberta));
            assertTrue(r.portas().stream().allMatch(p -> p.conteudo() == null));
            assertTrue(r.portas().stream().noneMatch(PortaResposta::escolhida));
            assertNull(r.portaEscolhida());
            assertNull(r.portaAberta());
            assertNull(r.portaOferecida());
            assertNull(r.trocou());
            assertNull(r.venceu());
            assertEquals("2026-11-23T18:00:00Z", r.criadoEm());
            assertNull(r.finalizadoEm());
        }

        @Test
        @DisplayName("a mensagem explica como escolher a porta")
        void mensagem() {
            String msg = JogoResposta.de(novo(2)).mensagem();

            assertTrue(msg.contains("POST /api/jogos/j1/escolha"));
            assertTrue(msg.contains("\"porta\""));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("AGUARDANDO_DECISAO")
    class AguardandoDecisao {

        @Test
        @DisplayName("só a porta aberta pelo apresentador mostra o conteúdo (bode)")
        void soUmaAberta() {
            // carro na 2, jogador escolheu a 1, apresentador abriu a 3
            JogoResposta r = JogoResposta.de(escolhido(2, 1, 3));

            assertNull(r.portaPremiada());
            assertEquals(List.of(
                    new PortaResposta(1, false, null, true),
                    new PortaResposta(2, false, null, false),
                    new PortaResposta(3, true, Premio.BODE, false)
            ), r.portas());
        }

        @Test
        @DisplayName("portaOferecida é a outra porta fechada")
        void portaOferecida() {
            assertEquals(2, JogoResposta.de(escolhido(2, 1, 3)).portaOferecida());
            assertEquals(3, JogoResposta.de(escolhido(1, 1, 2)).portaOferecida());
        }

        @Test
        @DisplayName("a mensagem cita a porta aberta, a oferecida e como decidir")
        void mensagem() {
            String msg = JogoResposta.de(escolhido(2, 1, 3)).mensagem();

            assertTrue(msg.contains("escolheu a porta 1"));
            assertTrue(msg.contains("abriu a porta 3"));
            assertTrue(msg.contains("trocar para a porta 2"));
            assertTrue(msg.contains("POST /api/jogos/j1/decisao"));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("FINALIZADO")
    class Finalizado {

        @Test
        @DisplayName("revela todas as portas e a porta premiada")
        void revelaTudo() {
            JogoResposta r = JogoResposta.de(finalizado(2, 1, 3, true, 2));

            assertEquals(2, r.portaPremiada());
            assertEquals(List.of(
                    new PortaResposta(1, true, Premio.BODE, false),
                    new PortaResposta(2, true, Premio.CARRO, true),
                    new PortaResposta(3, true, Premio.BODE, false)
            ), r.portas());
            assertTrue(r.trocou());
            assertTrue(r.venceu());
            assertEquals(2, r.portaFinal());
            assertEquals("2026-11-23T18:00:40Z", r.finalizadoEm());
        }

        @Test
        @DisplayName("trocou e ganhou")
        void trocouGanhou() {
            assertEquals("Você trocou para a porta 2 e ganhou o CARRO! 🚗",
                    JogoResposta.de(finalizado(2, 1, 3, true, 2)).mensagem());
        }

        @Test
        @DisplayName("manteve e perdeu: mostra onde estava o carro")
        void mantevePerdeu() {
            assertEquals("Você ficou com a porta 1 e levou um bode. 🐐 O carro estava na porta 2.",
                    JogoResposta.de(finalizado(2, 1, 3, false, 1)).mensagem());
        }

        @Test
        @DisplayName("manteve e ganhou")
        void manteveGanhou() {
            assertEquals("Você ficou com a porta 1 e ganhou o CARRO! 🚗",
                    JogoResposta.de(finalizado(1, 1, 3, false, 1)).mensagem());
        }

        @Test
        @DisplayName("trocou e perdeu")
        void trocouPerdeu() {
            JogoResposta r = JogoResposta.de(finalizado(1, 1, 2, true, 3));

            assertFalse(r.venceu());
            assertEquals("Você trocou para a porta 3 e levou um bode. 🐐 O carro estava na porta 1.", r.mensagem());
        }
    }

    @Test
    @DisplayName("jogo sem status não quebra a conversão")
    void semStatus() {
        Jogo j = new Jogo();
        j.setPortaPremiada(1);

        JogoResposta r = JogoResposta.de(j);

        assertNull(r.mensagem());
        assertNull(r.criadoEm());
        assertNull(r.portaPremiada());
    }
}
