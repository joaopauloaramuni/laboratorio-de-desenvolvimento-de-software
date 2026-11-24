package com.montyhall.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Modelo: Jogo")
class JogoTest {

    @Test
    @DisplayName("construtor vazio: todos os campos começam null")
    void construtorVazio() {
        Jogo j = new Jogo();

        assertNull(j.getId());
        assertNull(j.getPortaPremiada());
        assertNull(j.getPortaEscolhida());
        assertNull(j.getPortaAberta());
        assertNull(j.getPortaFinal());
        assertNull(j.getTrocou());
        assertNull(j.getVenceu());
        assertNull(j.getStatus());
        assertNull(j.getCriadoEm());
        assertNull(j.getFinalizadoEm());
    }

    @Test
    @DisplayName("setters alteram os campos")
    void setters() {
        Instant criado = Instant.parse("2026-11-23T18:00:00Z");
        Instant finalizado = Instant.parse("2026-11-23T18:00:40Z");

        Jogo j = new Jogo();
        j.setId("abc");
        j.setPortaPremiada(2);
        j.setPortaEscolhida(1);
        j.setPortaAberta(3);
        j.setPortaFinal(2);
        j.setTrocou(true);
        j.setVenceu(true);
        j.setStatus(StatusJogo.FINALIZADO);
        j.setCriadoEm(criado);
        j.setFinalizadoEm(finalizado);

        assertEquals("abc", j.getId());
        assertEquals(2, j.getPortaPremiada());
        assertEquals(1, j.getPortaEscolhida());
        assertEquals(3, j.getPortaAberta());
        assertEquals(2, j.getPortaFinal());
        assertTrue(j.getTrocou());
        assertTrue(j.getVenceu());
        assertEquals(StatusJogo.FINALIZADO, j.getStatus());
        assertEquals(criado, j.getCriadoEm());
        assertEquals(finalizado, j.getFinalizadoEm());
    }
}
