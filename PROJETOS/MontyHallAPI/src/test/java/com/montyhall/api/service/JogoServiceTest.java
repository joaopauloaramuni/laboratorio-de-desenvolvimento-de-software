package com.montyhall.api.service;

import com.montyhall.api.dto.DecisaoRequisicao;
import com.montyhall.api.dto.EscolhaRequisicao;
import com.montyhall.api.dto.EstatisticasResposta;
import com.montyhall.api.exception.DadosInvalidosException;
import com.montyhall.api.exception.JogadaInvalidaException;
import com.montyhall.api.exception.JogoNaoEncontradoException;
import com.montyhall.api.model.Jogo;
import com.montyhall.api.model.StatusJogo;
import com.montyhall.api.repository.JogoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários das regras de uma partida.
 * O JogoRepository e o Apresentador são simulados com Mockito:
 * nenhum MongoDB é necessário e nós decidimos onde está o carro.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("JogoService")
class JogoServiceTest {

    @Mock
    private JogoRepository jogoRepository;

    @Mock
    private Apresentador apresentador;

    @InjectMocks
    private JogoService jogoService;

    // ---------------- ajudantes para montar partidas em cada etapa ----------------

    private static Jogo novo(String id, int premiada) {
        Jogo j = new Jogo();
        j.setId(id);
        j.setPortaPremiada(premiada);
        j.setStatus(StatusJogo.AGUARDANDO_ESCOLHA);
        j.setCriadoEm(Instant.parse("2026-11-23T18:00:00Z"));
        return j;
    }

    private static Jogo escolhido(String id, int premiada, int escolhida, int aberta) {
        Jogo j = novo(id, premiada);
        j.setPortaEscolhida(escolhida);
        j.setPortaAberta(aberta);
        j.setStatus(StatusJogo.AGUARDANDO_DECISAO);
        return j;
    }

    private static Jogo finalizado(String id) {
        Jogo j = escolhido(id, 2, 1, 3);
        j.setTrocou(true);
        j.setPortaFinal(2);
        j.setVenceu(true);
        j.setStatus(StatusJogo.FINALIZADO);
        j.setFinalizadoEm(Instant.parse("2026-11-23T18:00:40Z"));
        return j;
    }

    private void saveDevolveOProprioJogo() {
        when(jogoRepository.save(any(Jogo.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("novoJogo()")
    class NovoJogo {

        @Test
        @DisplayName("o apresentador esconde o carro e a partida é gravada aguardando a escolha")
        void criaPartida() {
            when(apresentador.sortearPorta()).thenReturn(2);
            saveDevolveOProprioJogo();

            Jogo jogo = jogoService.novoJogo();

            assertNull(jogo.getId(), "o id deve ser gerado pelo MongoDB");
            assertEquals(2, jogo.getPortaPremiada());
            assertEquals(StatusJogo.AGUARDANDO_ESCOLHA, jogo.getStatus());
            assertNotNull(jogo.getCriadoEm());
            assertNull(jogo.getPortaEscolhida());
            assertNull(jogo.getPortaAberta());
            assertNull(jogo.getTrocou());
            assertNull(jogo.getVenceu());
            verify(apresentador).sortearPorta();
            verify(jogoRepository).save(jogo);
        }

        @Test
        @DisplayName("devolve o que o repositório retornou (com o id do MongoDB)")
        void devolveSalvo() {
            Jogo salvo = novo("abc", 3);
            when(apresentador.sortearPorta()).thenReturn(3);
            when(jogoRepository.save(any(Jogo.class))).thenReturn(salvo);

            assertSame(salvo, jogoService.novoJogo());
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("listar(status)")
    class Listar {

        @ParameterizedTest(name = "status = \"{0}\" → lista todas")
        @NullSource
        @ValueSource(strings = {"", "   "})
        void semFiltro(String status) {
            List<Jogo> todos = List.of(novo("1", 1), novo("2", 2));
            when(jogoRepository.findAllByOrderByCriadoEmDesc()).thenReturn(todos);

            assertEquals(todos, jogoService.listar(status));
            verify(jogoRepository).findAllByOrderByCriadoEmDesc();
            verifyNoMoreInteractions(jogoRepository);
        }

        @ParameterizedTest(name = "status = \"{0}\" → FINALIZADO")
        @ValueSource(strings = {"FINALIZADO", "finalizado", "  Finalizado  "})
        void comFiltro(String status) {
            List<Jogo> esperado = List.of(finalizado("1"));
            when(jogoRepository.findByStatusOrderByCriadoEmDesc(StatusJogo.FINALIZADO)).thenReturn(esperado);

            assertEquals(esperado, jogoService.listar(status));
            verify(jogoRepository).findByStatusOrderByCriadoEmDesc(StatusJogo.FINALIZADO);
            verifyNoMoreInteractions(jogoRepository);
        }

        @Test
        @DisplayName("status desconhecido → 400 com os valores aceitos, sem consultar o banco")
        void statusInvalido() {
            DadosInvalidosException ex = assertThrows(DadosInvalidosException.class,
                    () -> jogoService.listar("TERMINADO"));

            assertEquals("Status inválido. Use: AGUARDANDO_ESCOLHA, AGUARDANDO_DECISAO, FINALIZADO.", ex.getMessage());
            verifyNoInteractions(jogoRepository);
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("buscarPorId(id)")
    class BuscarPorId {

        @Test
        @DisplayName("devolve a partida quando o id existe")
        void encontrado() {
            Jogo j = novo("abc", 1);
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(j));

            assertSame(j, jogoService.buscarPorId("abc"));
        }

        @Test
        @DisplayName("lança JogoNaoEncontradoException quando o id não existe")
        void naoEncontrado() {
            when(jogoRepository.findById("xyz")).thenReturn(Optional.empty());

            JogoNaoEncontradoException ex = assertThrows(JogoNaoEncontradoException.class,
                    () -> jogoService.buscarPorId("xyz"));
            assertEquals("Jogo com id xyz não encontrado.", ex.getMessage());
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("escolherPorta(id, requisicao)")
    class EscolherPorta {

        @Test
        @DisplayName("grava a escolha, a porta aberta pelo apresentador e muda a etapa")
        void escolhaValida() {
            Jogo jogo = novo("abc", 2);
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(jogo));
            when(apresentador.abrirPortaComBode(2, 1)).thenReturn(3);
            saveDevolveOProprioJogo();

            Jogo resultado = jogoService.escolherPorta("abc", EscolhaRequisicao.daPorta(1));

            assertSame(jogo, resultado);
            assertEquals(1, resultado.getPortaEscolhida());
            assertEquals(3, resultado.getPortaAberta());
            assertEquals(StatusJogo.AGUARDANDO_DECISAO, resultado.getStatus());
            assertEquals(2, resultado.getPortaPremiada(), "o carro não muda de lugar");
            assertNull(resultado.getVenceu());
            verify(apresentador).abrirPortaComBode(2, 1);
            verify(jogoRepository).save(jogo);
        }

        @Test
        @DisplayName("o apresentador recebe a porta do carro e a do jogador, nessa ordem")
        void ordemDosArgumentos() {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(novo("abc", 3)));
            when(apresentador.abrirPortaComBode(3, 3)).thenReturn(1);
            saveDevolveOProprioJogo();

            jogoService.escolherPorta("abc", EscolhaRequisicao.daPorta(3));

            ArgumentCaptor<Jogo> captor = ArgumentCaptor.forClass(Jogo.class);
            verify(jogoRepository).save(captor.capture());
            assertEquals(3, captor.getValue().getPortaEscolhida());
            assertEquals(1, captor.getValue().getPortaAberta());
        }

        @ParameterizedTest(name = "porta = {0} → 400")
        @NullSource
        @ValueSource(ints = {0, 4, -1, 99})
        void portaInvalida(Integer porta) {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(novo("abc", 1)));

            DadosInvalidosException ex = assertThrows(DadosInvalidosException.class,
                    () -> jogoService.escolherPorta("abc", EscolhaRequisicao.daPorta(porta)));

            assertEquals("Escolha uma porta entre 1 e 3. Ex.: { \"porta\": 1 }", ex.getMessage());
            verifyNoInteractions(apresentador);
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("corpo nulo → 400")
        void corpoNulo() {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(novo("abc", 1)));

            assertThrows(DadosInvalidosException.class, () -> jogoService.escolherPorta("abc", null));
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("porta já escolhida → 409 e nada muda")
        void jaEscolhida() {
            Jogo jogo = escolhido("abc", 2, 1, 3);
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(jogo));

            JogadaInvalidaException ex = assertThrows(JogadaInvalidaException.class,
                    () -> jogoService.escolherPorta("abc", EscolhaRequisicao.daPorta(2)));

            assertEquals("A porta já foi escolhida nesta partida (porta 1).", ex.getMessage());
            assertEquals(1, jogo.getPortaEscolhida());
            verifyNoInteractions(apresentador);
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("partida finalizada → 409")
        void partidaFinalizada() {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(finalizado("abc")));

            assertThrows(JogadaInvalidaException.class,
                    () -> jogoService.escolherPorta("abc", EscolhaRequisicao.daPorta(2)));
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("id inexistente → 404 (antes de validar o corpo)")
        void naoEncontrado() {
            when(jogoRepository.findById("xyz")).thenReturn(Optional.empty());

            assertThrows(JogoNaoEncontradoException.class,
                    () -> jogoService.escolherPorta("xyz", EscolhaRequisicao.daPorta(1)));
            verifyNoInteractions(apresentador);
            verify(jogoRepository, never()).save(any());
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("decidir(id, requisicao)")
    class Decidir {

        /**
         * Os 4 resultados possíveis.
         * Colunas: carro, escolha inicial, porta aberta, trocar?, porta final, venceu?
         */
        @ParameterizedTest(name = "carro na {0}, escolheu {1}, abriu {2}, trocar={3} → termina na {4}, venceu={5}")
        @CsvSource({
                "2, 1, 3, true,  2, true",   // errou de início e trocou: GANHA
                "2, 1, 3, false, 1, false",  // errou de início e manteve: perde
                "1, 1, 3, false, 1, true",   // acertou de início e manteve: GANHA
                "1, 1, 2, true,  3, false",  // acertou de início e trocou: perde
                "3, 2, 1, true,  3, true",
                "3, 3, 1, true,  2, false"
        })
        void resultados(int premiada, int escolhida, int aberta, boolean trocar, int portaFinal, boolean venceu) {
            Jogo jogo = escolhido("abc", premiada, escolhida, aberta);
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(jogo));
            saveDevolveOProprioJogo();

            Jogo resultado = jogoService.decidir("abc", DecisaoRequisicao.comTrocar(trocar));

            assertEquals(trocar, resultado.getTrocou());
            assertEquals(portaFinal, resultado.getPortaFinal());
            assertEquals(venceu, resultado.getVenceu());
            assertEquals(StatusJogo.FINALIZADO, resultado.getStatus());
            assertNotNull(resultado.getFinalizadoEm());
            verify(jogoRepository).save(jogo);
            verifyNoInteractions(apresentador);
        }

        @Test
        @DisplayName("trocar nunca termina na porta aberta pelo apresentador")
        void trocarNaoVaiParaPortaAberta() {
            Jogo jogo = escolhido("abc", 1, 1, 2);
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(jogo));
            saveDevolveOProprioJogo();

            Jogo resultado = jogoService.decidir("abc", DecisaoRequisicao.comTrocar(true));

            assertNotEquals(resultado.getPortaAberta(), resultado.getPortaFinal());
            assertNotEquals(resultado.getPortaEscolhida(), resultado.getPortaFinal());
        }

        @Test
        @DisplayName("\"trocar\" ausente → 400")
        void trocarNulo() {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(escolhido("abc", 2, 1, 3)));

            DadosInvalidosException ex = assertThrows(DadosInvalidosException.class,
                    () -> jogoService.decidir("abc", DecisaoRequisicao.comTrocar(null)));

            assertEquals("Informe se quer trocar de porta. Ex.: { \"trocar\": true }", ex.getMessage());
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("corpo nulo → 400")
        void corpoNulo() {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(escolhido("abc", 2, 1, 3)));

            assertThrows(DadosInvalidosException.class, () -> jogoService.decidir("abc", null));
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("decidir antes de escolher a porta → 409")
        void antesDaEscolha() {
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(novo("abc", 2)));

            JogadaInvalidaException ex = assertThrows(JogadaInvalidaException.class,
                    () -> jogoService.decidir("abc", DecisaoRequisicao.comTrocar(true)));

            assertEquals("Escolha uma porta antes de decidir se quer trocar.", ex.getMessage());
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("decidir duas vezes → 409 e o resultado não muda")
        void jaFinalizado() {
            Jogo jogo = finalizado("abc");
            when(jogoRepository.findById("abc")).thenReturn(Optional.of(jogo));

            JogadaInvalidaException ex = assertThrows(JogadaInvalidaException.class,
                    () -> jogoService.decidir("abc", DecisaoRequisicao.comTrocar(false)));

            assertEquals("Esta partida já terminou. Comece uma nova em POST /api/jogos.", ex.getMessage());
            assertTrue(jogo.getTrocou());
            assertEquals(2, jogo.getPortaFinal());
            verify(jogoRepository, never()).save(any());
        }

        @Test
        @DisplayName("id inexistente → 404")
        void naoEncontrado() {
            when(jogoRepository.findById("xyz")).thenReturn(Optional.empty());

            assertThrows(JogoNaoEncontradoException.class,
                    () -> jogoService.decidir("xyz", DecisaoRequisicao.comTrocar(true)));
            verify(jogoRepository, never()).save(any());
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("excluir(id)")
    class Excluir {

        @Test
        @DisplayName("exclui quando o id existe")
        void excluiExistente() {
            when(jogoRepository.existsById("abc")).thenReturn(true);

            jogoService.excluir("abc");

            verify(jogoRepository).deleteById("abc");
        }

        @Test
        @DisplayName("lança 404 quando o id não existe e não chama deleteById")
        void naoEncontrado() {
            when(jogoRepository.existsById("xyz")).thenReturn(false);

            assertThrows(JogoNaoEncontradoException.class, () -> jogoService.excluir("xyz"));
            verify(jogoRepository, never()).deleteById(anyString());
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("estatisticas()")
    class Estatisticas {

        @Test
        @DisplayName("separa quem trocou de quem manteve e calcula os percentuais")
        void placar() {
            when(jogoRepository.countByTrocou(true)).thenReturn(30L);
            when(jogoRepository.countByTrocouAndVenceu(true, true)).thenReturn(20L);
            when(jogoRepository.countByTrocou(false)).thenReturn(30L);
            when(jogoRepository.countByTrocouAndVenceu(false, true)).thenReturn(10L);

            EstatisticasResposta r = jogoService.estatisticas();

            assertEquals(60, r.partidasFinalizadas());
            assertEquals(30, r.trocando().partidas());
            assertEquals(20, r.trocando().vitorias());
            assertEquals(10, r.trocando().derrotas());
            assertEquals(66.67, r.trocando().percentualVitorias());
            assertEquals(30, r.mantendo().partidas());
            assertEquals(10, r.mantendo().vitorias());
            assertEquals(33.33, r.mantendo().percentualVitorias());
            assertTrue(r.conclusao().contains("Trocar de porta foi a melhor estratégia"));
        }

        @Test
        @DisplayName("sem partidas finalizadas: tudo zero, sem divisão por zero")
        void semPartidas() {
            when(jogoRepository.countByTrocou(anyBoolean())).thenReturn(0L);
            when(jogoRepository.countByTrocouAndVenceu(anyBoolean(), anyBoolean())).thenReturn(0L);

            EstatisticasResposta r = jogoService.estatisticas();

            assertEquals(0, r.partidasFinalizadas());
            assertEquals(0.0, r.trocando().percentualVitorias());
            assertEquals(0.0, r.mantendo().percentualVitorias());
            assertEquals("Nenhuma partida finalizada ainda. Jogue em POST /api/jogos.", r.conclusao());
        }
    }

    @Test
    @DisplayName("o service nunca pede ao apresentador para abrir porta fora das etapas certas")
    void apresentadorSoNaEscolha() {
        when(jogoRepository.findById("abc")).thenReturn(Optional.of(escolhido("abc", 2, 1, 3)));
        saveDevolveOProprioJogo();

        jogoService.decidir("abc", DecisaoRequisicao.comTrocar(true));

        verify(apresentador, never()).abrirPortaComBode(anyInt(), anyInt());
    }
}
