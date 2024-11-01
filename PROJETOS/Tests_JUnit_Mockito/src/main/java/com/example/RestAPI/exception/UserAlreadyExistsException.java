package com.example.RestAPI.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
}