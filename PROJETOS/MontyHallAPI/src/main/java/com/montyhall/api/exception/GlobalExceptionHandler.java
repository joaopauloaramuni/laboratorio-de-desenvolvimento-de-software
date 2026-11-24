package com.montyhall.api.exception;

import com.mongodb.MongoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Captura as exceções lançadas em qualquer controller e devolve um JSON
 * padronizado (ErroResposta) com o código HTTP correto.
 *
 * Assim quem consome a API sempre recebe uma "mensagem" amigável,
 * em vez de uma página de erro HTML ou um stack trace.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - id não existe no MongoDB
    @ExceptionHandler(JogoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleNaoEncontrado(JogoNaoEncontradoException ex, HttpServletRequest request) {
        return montar(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // 400 - porta fora de 1..3, "trocar" ausente, rodadas fora do limite etc.
    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<ErroResposta> handleDadosInvalidos(DadosInvalidosException ex, HttpServletRequest request) {
        return montar(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // 409 - jogada na etapa errada (ex.: decidir antes de escolher a porta)
    @ExceptionHandler(JogadaInvalidaException.class)
    public ResponseEntity<ErroResposta> handleJogadaInvalida(JogadaInvalidaException ex, HttpServletRequest request) {
        return montar(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // 400 - o corpo da requisição não é um JSON válido (ou "porta" veio como texto)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleJsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return montar(HttpStatus.BAD_REQUEST, "O corpo da requisição não é um JSON válido.", request);
    }

    // 400 - parâmetro da URL com tipo errado (ex.: /api/simulacoes?rodadas=muitas)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> handleParametroInvalido(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return montar(HttpStatus.BAD_REQUEST, "O parâmetro '" + ex.getName() + "' tem um valor inválido.", request);
    }

    // 404 - endereço que não existe na API (ex.: /api/jogoz)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResposta> handleRotaInexistente(NoResourceFoundException ex, HttpServletRequest request) {
        return montar(HttpStatus.NOT_FOUND, "Endpoint não encontrado.", request);
    }

    // 405 - método HTTP errado (ex.: PUT em /api/jogos)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResposta> handleMetodo(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return montar(HttpStatus.METHOD_NOT_ALLOWED, "Método " + ex.getMethod() + " não é permitido neste endpoint.", request);
    }

    // 503 - o Spring Boot está no ar, mas não conseguiu falar com o MongoDB Atlas
    @ExceptionHandler({DataAccessResourceFailureException.class, MongoException.class})
    public ResponseEntity<ErroResposta> handleMongoIndisponivel(Exception ex, HttpServletRequest request) {
        return montar(HttpStatus.SERVICE_UNAVAILABLE,
                "Não foi possível conectar ao MongoDB Atlas. Confira a connection string no "
                        + "application.properties e o Network Access do Atlas.", request);
    }

    // 500 - qualquer outro erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenerico(Exception ex, HttpServletRequest request) {
        return montar(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", request);
    }

    private ResponseEntity<ErroResposta> montar(HttpStatus status, String mensagem, HttpServletRequest request) {
        ErroResposta corpo = new ErroResposta(status.value(), status.getReasonPhrase(), mensagem, request.getRequestURI());
        return ResponseEntity.status(status).body(corpo);
    }
}
