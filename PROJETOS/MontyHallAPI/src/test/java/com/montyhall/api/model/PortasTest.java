package com.montyhall.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Modelo: Portas, Premio e StatusJogo")
class PortasTest {

    @Test
    @DisplayName("são 3 portas: 1, 2 e 3")
    void tresPortas() {
        assertEquals(3, Portas.QUANTIDADE);
        assertEquals(List.of(1, 2, 3), Portas.TODAS);
    }

    @Test
    @DisplayName("Portas.TODAS é imutável")
    void imutavel() {
        assertThrows(UnsupportedOperationException.class, () -> Portas.TODAS.add(4));
    }

    @ParameterizedTest(name = "porta {0} é válida")
    @ValueSource(ints = {1, 2, 3})
    void portasValidas(int porta) {
        assertTrue(Portas.valida(porta));
    }

    @ParameterizedTest(name = "porta {0} é inválida")
    @NullSource
    @ValueSource(ints = {0, 4, -1, 100})
    void portasInvalidas(Integer porta) {
        assertFalse(Portas.valida(porta));
    }

    @Test
    @DisplayName("excluindo duas portas diferentes sobra só uma")
    void excluindoDuas() {
        assertEquals(List.of(2), Portas.excluindo(1, 3));
        assertEquals(List.of(1), Portas.excluindo(2, 3));
        assertEquals(List.of(3), Portas.excluindo(1, 2));
    }

    @Test
    @DisplayName("excluindo a mesma porta duas vezes sobram duas")
    void excluindoRepetida() {
        assertEquals(List.of(1, 3), Portas.excluindo(2, 2));
    }

    @Test
    @DisplayName("excluindo nenhuma porta sobram as três")
    void excluindoNenhuma() {
        assertEquals(Portas.TODAS, Portas.excluindo());
    }

    @Test
    @DisplayName("Premio tem CARRO e BODE")
    void premio() {
        assertArrayEquals(new Premio[]{Premio.CARRO, Premio.BODE}, Premio.values());
    }

    @Test
    @DisplayName("StatusJogo segue a ordem das etapas")
    void status() {
        assertArrayEquals(new StatusJogo[]{
                StatusJogo.AGUARDANDO_ESCOLHA, StatusJogo.AGUARDANDO_DECISAO, StatusJogo.FINALIZADO
        }, StatusJogo.values());
    }
}
