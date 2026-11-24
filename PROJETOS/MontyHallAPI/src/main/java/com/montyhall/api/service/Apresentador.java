package com.montyhall.api.service;

import com.montyhall.api.model.Portas;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * O apresentador do programa: esconde o carro e, depois da escolha do
 * jogador, abre uma porta que ele SABE que tem bode.
 *
 * Esse "saber" é o coração do problema de Monty Hall:
 *  - se o jogador escolheu o carro, sobram 2 bodes e o apresentador sorteia qual abrir;
 *  - se o jogador escolheu um bode, só sobra 1 bode e o apresentador é OBRIGADO a abri-lo.
 *
 * Ficou em uma classe separada (e não dentro do JogoService) para que os
 * testes possam trocar o sorteio por um mock do Mockito e controlar
 * exatamente onde o carro está.
 */
@Component
public class Apresentador {

    /**
     * RandomGenerator é a interface de sorteio do Java 17 (java.util.Random a implementa).
     * Depender da interface deixa o Mockito simular o sorteio nos testes.
     */
    private final RandomGenerator random;

    // Usado pelo Spring
    public Apresentador() {
        this(new Random());
    }

    // Usado nos testes: um mock do Mockito ou um Random de semente fixa (resultado repetível)
    public Apresentador(RandomGenerator random) {
        this.random = random;
    }

    /** Sorteia uma porta de 1 a 3, cada uma com 1/3 de chance. */
    public int sortearPorta() {
        return Portas.TODAS.get(random.nextInt(Portas.QUANTIDADE));
    }

    /**
     * Escolhe a porta que o apresentador vai abrir: nunca a premiada
     * e nunca a escolhida pelo jogador.
     */
    public int abrirPortaComBode(int portaPremiada, int portaEscolhida) {
        if (!Portas.valida(portaPremiada) || !Portas.valida(portaEscolhida)) {
            throw new IllegalArgumentException("As portas precisam estar entre 1 e " + Portas.QUANTIDADE + ".");
        }

        List<Integer> portasComBode = Portas.excluindo(portaPremiada, portaEscolhida);

        // 2 opções quando o jogador acertou o carro; 1 opção quando errou
        return portasComBode.get(random.nextInt(portasComBode.size()));
    }
}
