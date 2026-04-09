package com.example.JWT_RestAPI.facade;

import com.example.JWT_RestAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade responsável por intermediar a comunicação entre o Controller
 * e o serviço de autenticação.
 *
 * <p>Essa implementação apenas delega chamadas ao {@link AuthService},
 * sem adicionar novas regras ou comportamentos.</p>
 */
@Component
public class AuthFacade {

    @Autowired
    private AuthService authService;

    /**
     * Gera um token JWT para o username fornecido.
     *
     * @param username nome de usuário
     * @return token JWT
     */
    public String generateToken(String username) {
        return authService.generateToken(username);
    }

    /**
     * Extrai o username de um token JWT.
     *
     * @param token token JWT
     * @return username extraído
     */
    public String extractUsername(String token) {
        return authService.extractUsername(token);
    }
}