package com.montyhall.api.service;

import com.montyhall.api.dto.DecisaoRequisicao;
import com.montyhall.api.dto.EscolhaRequisicao;
import com.montyhall.api.dto.EstatisticasResposta;
import com.montyhall.api.dto.EstrategiaResposta;
import com.montyhall.api.exception.DadosInvalidosException;
import com.montyhall.api.exception.JogadaInvalidaException;
import com.montyhall.api.exception.JogoNaoEncontradoException;
import com.montyhall.api.model.Jogo;
import com.montyhall.api.model.Portas;
import com.montyhall.api.model.StatusJogo;
import com.montyhall.api.repository.JogoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Regras de uma partida do Monty Hall.
 * O controller recebe a requisição HTTP e o service decide o que fazer com o MongoDB.
 *
 *  1. novoJogo()     → o apresentador esconde o carro           (AGUARDANDO_ESCOLHA)
 *  2. escolherPorta() → o jogador escolhe; o apresentador abre um bode (AGUARDANDO_DECISAO)
 *  3. decidir()       → o jogador troca ou mantém; tudo é revelado     (FINALIZADO)
 */
@Service
public class JogoService {

    private final JogoRepository jogoRepository;
    private final Apresentador apresentador;

    // Injeção de dependência pelo construtor (equivale ao @Autowired no atributo)
    public JogoService(JogoRepository jogoRepository, Apresentador apresentador) {
        this.jogoRepository = jogoRepository;
        this.apresentador = apresentador;
    }

    /** Começa uma partida: sorteia a porta do carro e grava no MongoDB. */
    public Jogo novoJogo() {
        Jogo jogo = new Jogo();
        jogo.setPortaPremiada(apresentador.sortearPorta());
        jogo.setStatus(StatusJogo.AGUARDANDO_ESCOLHA);
        jogo.setCriadoEm(Instant.now());
        return jogoRepository.save(jogo);
    }

    /** Lista as partidas (mais novas primeiro). O filtro de status é opcional. */
    public List<Jogo> listar(String status) {
        if (status == null || status.isBlank()) {
            return jogoRepository.findAllByOrderByCriadoEmDesc();
        }
        return jogoRepository.findByStatusOrderByCriadoEmDesc(converterStatus(status));
    }

    public Jogo buscarPorId(String id) {
        return jogoRepository.findById(id)
                .orElseThrow(() -> new JogoNaoEncontradoException(id));
    }

    /** Etapa 2: o jogador escolhe uma porta e o apresentador abre outra, com bode. */
    public Jogo escolherPorta(String id, EscolhaRequisicao requisicao) {
        Jogo jogo = buscarPorId(id); // lança 404 se não existir

        if (requisicao == null || !Portas.valida(requisicao.getPorta())) {
            throw new DadosInvalidosException("Escolha uma porta entre 1 e " + Portas.QUANTIDADE
                    + ". Ex.: { \"porta\": 1 }");
        }
        if (jogo.getStatus() != StatusJogo.AGUARDANDO_ESCOLHA) {
            throw new JogadaInvalidaException("A porta já foi escolhida nesta partida (porta "
                    + jogo.getPortaEscolhida() + ").");
        }

        int escolhida = requisicao.getPorta();
        jogo.setPortaEscolhida(escolhida);
        jogo.setPortaAberta(apresentador.abrirPortaComBode(jogo.getPortaPremiada(), escolhida));
        jogo.setStatus(StatusJogo.AGUARDANDO_DECISAO);

        return jogoRepository.save(jogo);
    }

    /** Etapa 3: o jogador troca (ou não) de porta e o resultado é revelado. */
    public Jogo decidir(String id, DecisaoRequisicao requisicao) {
        Jogo jogo = buscarPorId(id); // lança 404 se não existir

        if (requisicao == null || requisicao.getTrocar() == null) {
            throw new DadosInvalidosException("Informe se quer trocar de porta. Ex.: { \"trocar\": true }");
        }
        if (jogo.getStatus() == StatusJogo.AGUARDANDO_ESCOLHA) {
            throw new JogadaInvalidaException("Escolha uma porta antes de decidir se quer trocar.");
        }
        if (jogo.getStatus() == StatusJogo.FINALIZADO) {
            throw new JogadaInvalidaException("Esta partida já terminou. Comece uma nova em POST /api/jogos.");
        }

        boolean trocar = requisicao.getTrocar();

        // Trocar = ficar com a única porta que não foi escolhida nem aberta
        int portaFinal = trocar
                ? Portas.excluindo(jogo.getPortaEscolhida(), jogo.getPortaAberta()).get(0)
                : jogo.getPortaEscolhida();

        jogo.setTrocou(trocar);
        jogo.setPortaFinal(portaFinal);
        jogo.setVenceu(jogo.getPortaPremiada().equals(portaFinal));
        jogo.setStatus(StatusJogo.FINALIZADO);
        jogo.setFinalizadoEm(Instant.now());

        return jogoRepository.save(jogo);
    }

    public void excluir(String id) {
        if (!jogoRepository.existsById(id)) {
            throw new JogoNaoEncontradoException(id);
        }
        jogoRepository.deleteById(id);
    }

    /** Placar das partidas finalizadas: quem trocou x quem manteve. */
    public EstatisticasResposta estatisticas() {
        EstrategiaResposta trocando = EstrategiaResposta.de(
                jogoRepository.countByTrocou(true),
                jogoRepository.countByTrocouAndVenceu(true, true));

        EstrategiaResposta mantendo = EstrategiaResposta.de(
                jogoRepository.countByTrocou(false),
                jogoRepository.countByTrocouAndVenceu(false, true));

        return EstatisticasResposta.de(trocando, mantendo);
    }

    /** "finalizado", " FINALIZADO " → StatusJogo.FINALIZADO. Valor desconhecido → 400. */
    private StatusJogo converterStatus(String status) {
        try {
            return StatusJogo.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            String aceitos = Arrays.stream(StatusJogo.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new DadosInvalidosException("Status inválido. Use: " + aceitos + ".");
        }
    }
}
