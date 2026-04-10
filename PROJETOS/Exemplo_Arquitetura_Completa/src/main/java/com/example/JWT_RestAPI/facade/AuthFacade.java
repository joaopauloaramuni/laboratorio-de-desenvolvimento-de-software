package com.example.JWT_RestAPI.facade;

import com.example.JWT_RestAPI.dto.LoginRequestDTO;
import com.example.JWT_RestAPI.dto.LoginResponseDTO;
import com.example.JWT_RestAPI.mapper.AuthMapper;
import com.example.JWT_RestAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Realiza o processo de autenticação do usuário e gera um token JWT.
     *
     * <p>O fluxo executado por este método é:</p>
     * <ul>
     *     <li>Autentica o usuário utilizando {@link AuthenticationManager}</li>
     *     <li>Gera um token JWT a partir do username autenticado</li>
     *     <li>Converte os dados de autenticação em um {@link LoginResponseDTO}</li>
     * </ul>
     *
     * @param request objeto contendo username e password informados pelo usuário
     * @return {@link LoginResponseDTO} contendo o token JWT e as roles do usuário autenticado
     * @throws org.springframework.security.core.AuthenticationException
     * caso as credenciais sejam inválidas
     */
    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String token = authService.generateToken(authentication.getName());
        return AuthMapper.toLoginResponse(token, authentication.getAuthorities());
    }

    /**
     * Gera um token JWT para o username fornecido.
     *
     * @param username nome de usuário
     * @return token JWT
     */
     /*public String generateToken(String username) {
        return authService.generateToken(username);
     }*/

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