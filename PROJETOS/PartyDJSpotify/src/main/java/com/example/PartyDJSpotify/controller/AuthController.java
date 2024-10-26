package com.example.PartyDJSpotify.controller;

import com.example.PartyDJSpotify.context.AuthorizationCodeContext;
import com.example.PartyDJSpotify.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/party")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorizationCodeContext authorizationCodeContext;

    @GetMapping("/authInsomnia")
    public String getAuthorizationUrlString() {
        String authorizationUrl = tokenService.getAuthorizationUrl(); // Obtendo a URL de autorização
        System.out.println("Authorization Url: " + authorizationUrl);
        return authorizationUrl;
    }

    @GetMapping("/authRedirect")
    public ResponseEntity<Void> getAuthorizationUrl() {
        String authorizationUrl = tokenService.getAuthorizationUrl(); // Obtendo a URL de autorização
        System.out.println("Authorization Url: " + authorizationUrl);
        return ResponseEntity.status(HttpStatus.FOUND) // 302 - Found
                .location(URI.create(authorizationUrl)) // Redirecionando para a URL de autorização
                .build();
    }

    // Endpoint para o callback do Spotify
    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
        authorizationCodeContext.setAuthorizationCode(code);
        tokenService.renewAccessToken(code); // Chama o serviço para renovar o token
        return ResponseEntity.ok("Authorization successful!" +
                "<br><br>Authorization Url:<br>" + tokenService.getAuthorizationUrl() +
                "<br><br>AccessToken:<br>" + tokenService.ensureAccessTokenIsValid() +
                "<br><br>Code:<br>" + code); // Mensagem de sucesso
    }

}
