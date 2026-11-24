package com.montyhall.api.exception;

/**
 * Lançada quando a jogada é feita na etapa errada da partida
 * (ex.: decidir antes de escolher uma porta, ou jogar numa partida já finalizada).
 * O GlobalExceptionHandler transforma em HTTP 409 (Conflict).
 */
public class JogadaInvalidaException extends RuntimeException {

    public JogadaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
