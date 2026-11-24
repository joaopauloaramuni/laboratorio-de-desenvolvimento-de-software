package com.montyhall.api.exception;

/**
 * Lançada quando a requisição tem algum dado inválido
 * (ex.: porta 4, "trocar" ausente, quantidade de rodadas fora do limite).
 * O GlobalExceptionHandler transforma em HTTP 400.
 */
public class DadosInvalidosException extends RuntimeException {

    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}
