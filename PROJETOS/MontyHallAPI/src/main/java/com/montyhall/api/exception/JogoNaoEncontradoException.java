package com.montyhall.api.exception;

/**
 * Lançada quando o id informado não existe na coleção "jogos".
 * O GlobalExceptionHandler transforma esta exceção em HTTP 404 (Not Found).
 */
public class JogoNaoEncontradoException extends RuntimeException {

    public JogoNaoEncontradoException(String id) {
        super("Jogo com id " + id + " não encontrado.");
    }
}
