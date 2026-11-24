package com.montyhall.api.model;

/**
 * Em que etapa a partida está.
 *
 * AGUARDANDO_ESCOLHA  → o carro já foi escondido; o jogador ainda não escolheu uma porta
 * AGUARDANDO_DECISAO  → o jogador escolheu e o apresentador abriu uma porta com bode;
 *                       falta decidir se troca ou mantém
 * FINALIZADO          → todas as portas foram reveladas
 */
public enum StatusJogo {
    AGUARDANDO_ESCOLHA,
    AGUARDANDO_DECISAO,
    FINALIZADO
}
