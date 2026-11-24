package com.montyhall.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Apresentador")
class ApresentadorTest {

    @Mock
    private RandomGenerator random;

    @Test
    @DisplayName("sortearPorta converte o sorteio 0..2 em porta 1..3")
    void sortearPortaComMock() {
        when(random.nextInt(3)).thenReturn(0, 1, 2);
        Apresentador apresentador = new Apresentador(random);

        assertEquals(1, apresentador.sortearPorta());
        assertEquals(2, apresentador.sortearPorta());
        assertEquals(3, apresentador.sortearPorta());
        verify(random, times(3)).nextInt(3);
    }

    @Test
    @DisplayName("sortearPorta com Random de verdade: sempre 1..3 e as 3 aparecem")
    void sortearPortaReal() {
        Apresentador apresentador = new Apresentador(new Random(42));
        Set<Integer> vistas = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            int porta = apresentador.sortearPorta();
            assertTrue(porta >= 1 && porta <= 3);
            vistas.add(porta);
        }
        assertEquals(Set.of(1, 2, 3), vistas);
    }

    @Test
    @DisplayName("construtor sem argumentos (usado pelo Spring) também funciona")
    void construtorPadrao() {
        int porta = new Apresentador().sortearPorta();
        assertTrue(porta >= 1 && porta <= 3);
    }

    @ParameterizedTest(name = "carro na {0}, jogador na {1} → apresentador só pode abrir a {2}")
    @CsvSource({
            "1, 2, 3",
            "1, 3, 2",
            "2, 1, 3",
            "2, 3, 1",
            "3, 1, 2",
            "3, 2, 1"
    })
    void jogadorErrouSoSobraUmBode(int premiada, int escolhida, int esperada) {
        // Só 1 opção: nextInt(1) sempre devolve 0
        when(random.nextInt(1)).thenReturn(0);
        Apresentador apresentador = new Apresentador(random);

        assertEquals(esperada, apresentador.abrirPortaComBode(premiada, escolhida));
        verify(random).nextInt(1);
    }

    @ParameterizedTest(name = "carro e jogador na {0} → sorteia entre {1} e {2}")
    @CsvSource({
            "1, 2, 3",
            "2, 1, 3",
            "3, 1, 2"
    })
    void jogadorAcertouSorteiaEntreDoisBodes(int porta, int primeiroBode, int segundoBode) {
        when(random.nextInt(2)).thenReturn(0, 1);
        Apresentador apresentador = new Apresentador(random);

        assertEquals(primeiroBode, apresentador.abrirPortaComBode(porta, porta));
        assertEquals(segundoBode, apresentador.abrirPortaComBode(porta, porta));
        verify(random, times(2)).nextInt(2);
    }

    @Test
    @DisplayName("com Random de verdade: nunca abre a porta do carro nem a do jogador")
    void nuncaAbrePremiadaNemEscolhida() {
        Apresentador apresentador = new Apresentador(new Random(7));

        for (int premiada = 1; premiada <= 3; premiada++) {
            for (int escolhida = 1; escolhida <= 3; escolhida++) {
                for (int i = 0; i < 200; i++) {
                    int aberta = apresentador.abrirPortaComBode(premiada, escolhida);
                    assertNotEquals(premiada, aberta);
                    assertNotEquals(escolhida, aberta);
                }
            }
        }
    }

    @ParameterizedTest(name = "premiada={0}, escolhida={1} → IllegalArgumentException")
    @CsvSource({"0, 1", "1, 0", "4, 1", "1, 4", "-1, 2"})
    void portasInvalidas(int premiada, int escolhida) {
        Apresentador apresentador = new Apresentador(random);

        assertThrows(IllegalArgumentException.class, () -> apresentador.abrirPortaComBode(premiada, escolhida));
        verifyNoInteractions(random);
    }
}
