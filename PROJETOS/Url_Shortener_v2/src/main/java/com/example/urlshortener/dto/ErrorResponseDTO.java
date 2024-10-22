package com.example.urlshortener.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    // Construtor com todos os campos
    public ErrorResponseDTO(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now(); // Inicializa o timestamp com a data/hora atual
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Método toString para facilitar a visualização do objeto
    @Override
    public String toString() {
        return "ErrorResponseDTO{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}