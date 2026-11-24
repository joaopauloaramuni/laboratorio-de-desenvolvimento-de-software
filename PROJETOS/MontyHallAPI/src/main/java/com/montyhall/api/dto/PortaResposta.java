package com.montyhall.api.dto;

import com.montyhall.api.model.Premio;

/**
 * Como uma porta aparece para o jogador.
 *
 * conteudo só é preenchido quando a porta está aberta.
 * Enquanto está fechada, vem null: o jogador não pode ver o que tem atrás.
 *
 * @param numero    1, 2 ou 3
 * @param aberta    true se o conteúdo já foi revelado
 * @param conteudo  CARRO, BODE ou null (porta fechada)
 * @param escolhida true se é a porta do jogador neste momento
 */
public record PortaResposta(int numero, boolean aberta, Premio conteudo, boolean escolhida) {
}
