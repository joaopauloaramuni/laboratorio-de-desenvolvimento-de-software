package com.example.SecureLoginPUC.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryService {

    private final Map<String, RecoveryToken> tokens = new HashMap<>();

    /**
     * Gera um token de recuperação para o e-mail informado.
     */
    public String generateToken(String email) {

        String token = UUID.randomUUID().toString();

        // Token válido por 15 minutos
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(15);

        tokens.put(token, new RecoveryToken(email, expiration));

        return token;
    }

    /**
     * Retorna o e-mail associado ao token.
     * Retorna null caso o token seja inválido ou expirado.
     */
    public String getEmailFromToken(String token) {

        RecoveryToken recoveryToken = tokens.get(token);

        if (recoveryToken == null) {
            return null;
        }

        // Verifica se o token expirou
        if (LocalDateTime.now().isAfter(recoveryToken.expiration())) {
            tokens.remove(token);
            return null;
        }

        return recoveryToken.email();
    }

    /**
     * Remove o token depois que ele for utilizado.
     */
    public void invalidateToken(String token) {
        tokens.remove(token);
    }

    /**
     * Guarda as informações do token.
     */
    private record RecoveryToken(
            String email,
            LocalDateTime expiration) {
    }
}