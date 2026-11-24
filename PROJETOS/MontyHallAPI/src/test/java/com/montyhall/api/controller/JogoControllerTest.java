package com.montyhall.api.controller;

import com.montyhall.api.dto.DecisaoRequisicao;
import com.montyhall.api.dto.EscolhaRequisicao;
import com.montyhall.api.dto.EstatisticasResposta;
import com.montyhall.api.dto.EstrategiaResposta;
import com.montyhall.api.exception.DadosInvalidosException;
import com.montyhall.api.exception.GlobalExceptionHandler;
import com.montyhall.api.exception.JogadaInvalidaException;
import com.montyhall.api.exception.JogoNaoEncontradoException;
import com.montyhall.api.model.Jogo;
import com.montyhall.api.model.StatusJogo;
import com.montyhall.api.service.JogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testa os endpoints REST de /api/jogos com MockMvc em modo "standalone":
 * sem subir o contexto do Spring e sem MongoDB. O JogoService é um mock do Mockito
 * e o GlobalExceptionHandler é registrado para conferir os códigos HTTP de erro.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("JogoController")
class JogoControllerTest {

    private static final String URL = "/api/jogos";

    @Mock
    private JogoService jogoService;

    @InjectMocks
    private JogoController jogoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(jogoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();
    }

    // ---------------- partidas de exemplo ----------------

    private static Jogo novo() {
        Jogo j = new Jogo();
        j.setId("j1");
        j.setPortaPremiada(2);
        j.setStatus(StatusJogo.AGUARDANDO_ESCOLHA);
        j.setCriadoEm(Instant.parse("2026-11-23T18:00:00Z"));
        return j;
    }

    private static Jogo escolhido() {
        Jogo j = novo();
        j.setPortaEscolhida(1);
        j.setPortaAberta(3);
        j.setStatus(StatusJogo.AGUARDANDO_DECISAO);
        return j;
    }

    private static Jogo finalizado() {
        Jogo j = escolhido();
        j.setTrocou(true);
        j.setPortaFinal(2);
        j.setVenceu(true);
        j.setStatus(StatusJogo.FINALIZADO);
        j.setFinalizadoEm(Instant.parse("2026-11-23T18:00:40Z"));
        return j;
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("POST /api/jogos")
    class NovoJogo {

        @Test
        @DisplayName("201 com a partida criada, 3 portas fechadas e o carro escondido")
        void criado() throws Exception {
            when(jogoService.novoJogo()).thenReturn(novo());

            mockMvc.perform(post(URL))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value("j1"))
                    .andExpect(jsonPath("$.status").value("AGUARDANDO_ESCOLHA"))
                    .andExpect(jsonPath("$.portas", hasSize(3)))
                    .andExpect(jsonPath("$.portas[0].numero").value(1))
                    .andExpect(jsonPath("$.portas[0].aberta").value(false))
                    .andExpect(jsonPath("$.portas[1].conteudo").doesNotExist())
                    .andExpect(jsonPath("$.portaPremiada").doesNotExist())
                    .andExpect(jsonPath("$.mensagem", notNullValue()))
                    .andExpect(jsonPath("$.criadoEm").value("2026-11-23T18:00:00Z"));

            verify(jogoService).novoJogo();
        }

        @Test
        @DisplayName("503 quando o MongoDB está indisponível")
        void mongoIndisponivel() throws Exception {
            when(jogoService.novoJogo()).thenThrow(new DataAccessResourceFailureException("timeout"));

            mockMvc.perform(post(URL))
                    .andExpect(status().isServiceUnavailable())
                    .andExpect(jsonPath("$.status").value(503))
                    .andExpect(jsonPath("$.caminho").value(URL));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("GET /api/jogos")
    class Listar {

        @Test
        @DisplayName("200 com as partidas (sem filtro)")
        void listarTodas() throws Exception {
            when(jogoService.listar(null)).thenReturn(List.of(novo(), finalizado()));

            mockMvc.perform(get(URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].status").value("AGUARDANDO_ESCOLHA"))
                    .andExpect(jsonPath("$[0].portaPremiada").doesNotExist())
                    .andExpect(jsonPath("$[1].status").value("FINALIZADO"))
                    .andExpect(jsonPath("$[1].portaPremiada").value(2));

            verify(jogoService).listar(null);
        }

        @Test
        @DisplayName("repassa o filtro status ao service")
        void comFiltro() throws Exception {
            when(jogoService.listar("FINALIZADO")).thenReturn(List.of(finalizado()));

            mockMvc.perform(get(URL).param("status", "FINALIZADO"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)));

            verify(jogoService).listar("FINALIZADO");
        }

        @Test
        @DisplayName("400 para status desconhecido")
        void statusInvalido() throws Exception {
            when(jogoService.listar("XYZ")).thenThrow(new DadosInvalidosException(
                    "Status inválido. Use: AGUARDANDO_ESCOLHA, AGUARDANDO_DECISAO, FINALIZADO."));

            mockMvc.perform(get(URL).param("status", "XYZ"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value(
                            "Status inválido. Use: AGUARDANDO_ESCOLHA, AGUARDANDO_DECISAO, FINALIZADO."));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("GET /api/jogos/estatisticas")
    class Estatisticas {

        @Test
        @DisplayName("200 com o placar e não é confundido com /{id}")
        void placar() throws Exception {
            when(jogoService.estatisticas()).thenReturn(EstatisticasResposta.de(
                    EstrategiaResposta.de(30, 20), EstrategiaResposta.de(30, 10)));

            mockMvc.perform(get(URL + "/estatisticas"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.partidasFinalizadas").value(60))
                    .andExpect(jsonPath("$.trocando.vitorias").value(20))
                    .andExpect(jsonPath("$.trocando.percentualVitorias").value(66.67))
                    .andExpect(jsonPath("$.mantendo.percentualVitorias").value(33.33))
                    .andExpect(jsonPath("$.conclusao", notNullValue()));

            verify(jogoService, never()).buscarPorId(anyString());
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("GET /api/jogos/{id}")
    class BuscarPorId {

        @Test
        @DisplayName("200 com a partida")
        void encontrado() throws Exception {
            when(jogoService.buscarPorId("j1")).thenReturn(escolhido());

            mockMvc.perform(get(URL + "/j1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("AGUARDANDO_DECISAO"))
                    .andExpect(jsonPath("$.portaEscolhida").value(1))
                    .andExpect(jsonPath("$.portaAberta").value(3))
                    .andExpect(jsonPath("$.portaOferecida").value(2));
        }

        @Test
        @DisplayName("404 com ErroResposta quando não existe")
        void naoEncontrado() throws Exception {
            when(jogoService.buscarPorId("999")).thenThrow(new JogoNaoEncontradoException("999"));

            mockMvc.perform(get(URL + "/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.erro").value("Not Found"))
                    .andExpect(jsonPath("$.mensagem").value("Jogo com id 999 não encontrado."))
                    .andExpect(jsonPath("$.caminho").value(URL + "/999"))
                    .andExpect(jsonPath("$.timestamp", notNullValue()));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("POST /api/jogos/{id}/escolha")
    class Escolha {

        @Test
        @DisplayName("200: só a porta aberta pelo apresentador mostra o bode")
        void escolhaValida() throws Exception {
            when(jogoService.escolherPorta(eq("j1"), any(EscolhaRequisicao.class))).thenReturn(escolhido());

            mockMvc.perform(post(URL + "/j1/escolha")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"porta\": 1}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("AGUARDANDO_DECISAO"))
                    .andExpect(jsonPath("$.portas[0].escolhida").value(true))
                    .andExpect(jsonPath("$.portas[0].aberta").value(false))
                    .andExpect(jsonPath("$.portas[1].aberta").value(false))
                    .andExpect(jsonPath("$.portas[1].conteudo").doesNotExist())
                    .andExpect(jsonPath("$.portas[2].aberta").value(true))
                    .andExpect(jsonPath("$.portas[2].conteudo").value("BODE"))
                    .andExpect(jsonPath("$.portaOferecida").value(2))
                    .andExpect(jsonPath("$.portaPremiada").doesNotExist());

            ArgumentCaptor<EscolhaRequisicao> captor = ArgumentCaptor.forClass(EscolhaRequisicao.class);
            verify(jogoService).escolherPorta(eq("j1"), captor.capture());
            assertEquals(1, captor.getValue().getPorta());
        }

        @Test
        @DisplayName("400 quando o service rejeita a porta")
        void portaInvalida() throws Exception {
            when(jogoService.escolherPorta(eq("j1"), any(EscolhaRequisicao.class)))
                    .thenThrow(new DadosInvalidosException("Escolha uma porta entre 1 e 3. Ex.: { \"porta\": 1 }"));

            mockMvc.perform(post(URL + "/j1/escolha")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"porta\": 7}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.mensagem").value("Escolha uma porta entre 1 e 3. Ex.: { \"porta\": 1 }"));
        }

        @Test
        @DisplayName("409 quando a porta já foi escolhida")
        void jaEscolhida() throws Exception {
            when(jogoService.escolherPorta(eq("j1"), any(EscolhaRequisicao.class)))
                    .thenThrow(new JogadaInvalidaException("A porta já foi escolhida nesta partida (porta 1)."));

            mockMvc.perform(post(URL + "/j1/escolha")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"porta\": 2}"))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value(409))
                    .andExpect(jsonPath("$.erro").value("Conflict"))
                    .andExpect(jsonPath("$.mensagem").value("A porta já foi escolhida nesta partida (porta 1)."));
        }

        @Test
        @DisplayName("400 quando a porta vem como texto (service não é chamado)")
        void portaComoTexto() throws Exception {
            mockMvc.perform(post(URL + "/j1/escolha")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"porta\": \"a do meio\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("O corpo da requisição não é um JSON válido."));

            verifyNoInteractions(jogoService);
        }

        @Test
        @DisplayName("400 quando o corpo não é JSON")
        void jsonInvalido() throws Exception {
            mockMvc.perform(post(URL + "/j1/escolha")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ porta 1"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("O corpo da requisição não é um JSON válido."));

            verifyNoInteractions(jogoService);
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("POST /api/jogos/{id}/decisao")
    class Decisao {

        @Test
        @DisplayName("200: revela todas as portas e o resultado")
        void decisaoValida() throws Exception {
            when(jogoService.decidir(eq("j1"), any(DecisaoRequisicao.class))).thenReturn(finalizado());

            mockMvc.perform(post(URL + "/j1/decisao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"trocar\": true}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("FINALIZADO"))
                    .andExpect(jsonPath("$.trocou").value(true))
                    .andExpect(jsonPath("$.portaFinal").value(2))
                    .andExpect(jsonPath("$.venceu").value(true))
                    .andExpect(jsonPath("$.portaPremiada").value(2))
                    .andExpect(jsonPath("$.portas[0].conteudo").value("BODE"))
                    .andExpect(jsonPath("$.portas[1].conteudo").value("CARRO"))
                    .andExpect(jsonPath("$.portas[1].escolhida").value(true))
                    .andExpect(jsonPath("$.portas[2].conteudo").value("BODE"))
                    .andExpect(jsonPath("$.mensagem").value("Você trocou para a porta 2 e ganhou o CARRO! 🚗"))
                    .andExpect(jsonPath("$.finalizadoEm").value("2026-11-23T18:00:40Z"));

            ArgumentCaptor<DecisaoRequisicao> captor = ArgumentCaptor.forClass(DecisaoRequisicao.class);
            verify(jogoService).decidir(eq("j1"), captor.capture());
            assertTrue(captor.getValue().getTrocar());
        }

        @Test
        @DisplayName("409 quando decide antes de escolher")
        void antesDaEscolha() throws Exception {
            when(jogoService.decidir(eq("j1"), any(DecisaoRequisicao.class)))
                    .thenThrow(new JogadaInvalidaException("Escolha uma porta antes de decidir se quer trocar."));

            mockMvc.perform(post(URL + "/j1/decisao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"trocar\": false}"))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.mensagem").value("Escolha uma porta antes de decidir se quer trocar."));
        }

        @Test
        @DisplayName("400 quando \"trocar\" não é enviado")
        void semTrocar() throws Exception {
            when(jogoService.decidir(eq("j1"), any(DecisaoRequisicao.class)))
                    .thenThrow(new DadosInvalidosException("Informe se quer trocar de porta. Ex.: { \"trocar\": true }"));

            mockMvc.perform(post(URL + "/j1/decisao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Informe se quer trocar de porta. Ex.: { \"trocar\": true }"));

            ArgumentCaptor<DecisaoRequisicao> captor = ArgumentCaptor.forClass(DecisaoRequisicao.class);
            verify(jogoService).decidir(eq("j1"), captor.capture());
            assertNull(captor.getValue().getTrocar());
        }

        @Test
        @DisplayName("404 quando a partida não existe")
        void naoEncontrado() throws Exception {
            when(jogoService.decidir(eq("999"), any(DecisaoRequisicao.class)))
                    .thenThrow(new JogoNaoEncontradoException("999"));

            mockMvc.perform(post(URL + "/999/decisao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"trocar\": true}"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Jogo com id 999 não encontrado."));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("DELETE /api/jogos/{id}")
    class Excluir {

        @Test
        @DisplayName("204 sem corpo quando exclui")
        void excluido() throws Exception {
            doNothing().when(jogoService).excluir("j1");

            mockMvc.perform(delete(URL + "/j1"))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string(""));

            verify(jogoService).excluir("j1");
        }

        @Test
        @DisplayName("404 quando o id não existe")
        void naoEncontrado() throws Exception {
            doThrow(new JogoNaoEncontradoException("999")).when(jogoService).excluir("999");

            mockMvc.perform(delete(URL + "/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("Erros genéricos")
    class ErrosGenericos {

        @Test
        @DisplayName("405 para método HTTP não suportado")
        void metodoNaoPermitido() throws Exception {
            mockMvc.perform(put(URL))
                    .andExpect(status().isMethodNotAllowed())
                    .andExpect(jsonPath("$.status").value(405))
                    .andExpect(jsonPath("$.mensagem").value("Método PUT não é permitido neste endpoint."));

            verifyNoInteractions(jogoService);
        }

        @Test
        @DisplayName("500 para erro inesperado")
        void erroInesperado() throws Exception {
            when(jogoService.buscarPorId("j1")).thenThrow(new IllegalStateException("bug"));

            mockMvc.perform(get(URL + "/j1"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.mensagem").value("Erro interno no servidor."));
        }
    }

    // ------------------------------------------------------------------
    @Nested
    @DisplayName("Chamada direta (sem MockMvc)")
    class ChamadaDireta {

        @Test
        @DisplayName("novoJogo devolve ResponseEntity 201 com o carro escondido")
        void novoJogo() {
            when(jogoService.novoJogo()).thenReturn(novo());

            var resposta = jogoController.novoJogo();

            assertEquals(201, resposta.getStatusCode().value());
            assertNotNull(resposta.getBody());
            assertEquals("j1", resposta.getBody().id());
            assertNull(resposta.getBody().portaPremiada());
        }

        @Test
        @DisplayName("excluir devolve ResponseEntity 204 sem corpo")
        void excluir() {
            var resposta = jogoController.excluir("j1");

            assertEquals(204, resposta.getStatusCode().value());
            assertNull(resposta.getBody());
            verify(jogoService).excluir("j1");
        }
    }
}
