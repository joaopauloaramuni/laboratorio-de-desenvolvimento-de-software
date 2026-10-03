package com.taskflow.exception;

/**
 * Excecao lancada quando um recurso (ex.: uma tarefa) nao e encontrado.
 * Mapeada para HTTP 404 pelo GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
