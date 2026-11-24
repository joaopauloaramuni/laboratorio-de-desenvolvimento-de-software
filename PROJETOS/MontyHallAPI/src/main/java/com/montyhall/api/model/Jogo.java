package com.montyhall.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Uma partida do jogo, salva na coleção "jogos" do MongoDB.
 *
 * A porta premiada fica gravada desde o início, mas a API só a mostra
 * ao jogador quando a partida termina (veja dto/JogoResposta.java).
 *
 * Exemplo de partida finalizada no Atlas:
 * {
 *   "_id": ObjectId("..."),
 *   "portaPremiada": 2,
 *   "portaEscolhida": 1,
 *   "portaAberta": 3,
 *   "portaFinal": 2,
 *   "trocou": true,
 *   "venceu": true,
 *   "status": "FINALIZADO",
 *   "criadoEm": ISODate("2026-11-23T18:00:00Z"),
 *   "finalizadoEm": ISODate("2026-11-23T18:00:40Z")
 * }
 */
@Document(collection = "jogos")
public class Jogo {

    @Id
    private String id;

    /** Onde está o carro (1, 2 ou 3). Sorteado quando a partida é criada. */
    private Integer portaPremiada;

    /** Primeira escolha do jogador. */
    private Integer portaEscolhida;

    /** Porta com bode que o apresentador abriu. */
    private Integer portaAberta;

    /** Porta com que o jogador terminou (a escolhida ou a outra que sobrou). */
    private Integer portaFinal;

    /** true = trocou de porta; false = manteve. Fica null até a decisão. */
    private Boolean trocou;

    /** true = ganhou o carro. Fica null até a decisão. */
    private Boolean venceu;

    private StatusJogo status;

    private Instant criadoEm;
    private Instant finalizadoEm;

    // Construtor vazio: usado pelo Spring Data para montar o objeto lido do MongoDB
    public Jogo() {
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPortaPremiada() {
        return portaPremiada;
    }

    public void setPortaPremiada(Integer portaPremiada) {
        this.portaPremiada = portaPremiada;
    }

    public Integer getPortaEscolhida() {
        return portaEscolhida;
    }

    public void setPortaEscolhida(Integer portaEscolhida) {
        this.portaEscolhida = portaEscolhida;
    }

    public Integer getPortaAberta() {
        return portaAberta;
    }

    public void setPortaAberta(Integer portaAberta) {
        this.portaAberta = portaAberta;
    }

    public Integer getPortaFinal() {
        return portaFinal;
    }

    public void setPortaFinal(Integer portaFinal) {
        this.portaFinal = portaFinal;
    }

    public Boolean getTrocou() {
        return trocou;
    }

    public void setTrocou(Boolean trocou) {
        this.trocou = trocou;
    }

    public Boolean getVenceu() {
        return venceu;
    }

    public void setVenceu(Boolean venceu) {
        this.venceu = venceu;
    }

    public StatusJogo getStatus() {
        return status;
    }

    public void setStatus(StatusJogo status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getFinalizadoEm() {
        return finalizadoEm;
    }

    public void setFinalizadoEm(Instant finalizadoEm) {
        this.finalizadoEm = finalizadoEm;
    }
}
