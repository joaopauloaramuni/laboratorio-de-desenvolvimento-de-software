package com.montyhall.api.model;

import java.util.Arrays;
import java.util.List;

/**
 * As 3 portas do palco e uma ajuda para saber "quais portas sobram".
 *
 * Exemplo: Portas.excluindo(1, 3) → [2]
 *          Portas.excluindo(2, 2) → [1, 3]
 */
public final class Portas {

    public static final int QUANTIDADE = 3;

    public static final List<Integer> TODAS = List.of(1, 2, 3);

    private Portas() {
    }

    /** true quando o número é 1, 2 ou 3. */
    public static boolean valida(Integer porta) {
        return porta != null && TODAS.contains(porta);
    }

    /** Portas que sobram depois de tirar as informadas (na ordem 1, 2, 3). */
    public static List<Integer> excluindo(Integer... portas) {
        List<Integer> remover = Arrays.asList(portas);
        return TODAS.stream()
                .filter(porta -> !remover.contains(porta))
                .toList();
    }
}
