package com.example.ZapSender.controller;

import com.example.ZapSender.config.ApiConfig;
import com.example.ZapSender.service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebHookController {

    @Autowired
    private WebHookService webHookService;

    @Autowired
    private ApiConfig apiConfig;

    // ================================
    // Envia template via WhatsApp
    // ================================
    @PostMapping("/enviar-template")
    public ResponseEntity<String> enviarTemplate(
            @RequestParam String numeroDestino,
            @RequestParam String nomeTemplate,
            @RequestParam(defaultValue = "en_US") String codigoIdioma) {
        try {
            String resultado = webHookService.enviarTemplate(numeroDestino, nomeTemplate, codigoIdioma);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Erro ao enviar template: " + e.getMessage());
        }
    }

    // ================================
    // Envia mensagem de texto simples
    // ================================
    @PostMapping("/enviar-texto")
    public ResponseEntity<String> enviarMensagemTexto(
            @RequestParam String numeroDestino,
            @RequestParam String texto) {
        try {
            String resultado = webHookService.enviarMensagemTexto(numeroDestino, texto);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Erro ao enviar mensagem de texto: " + e.getMessage());
        }
    }

    // ================================
    // Envia mensagem com botões de opinião
    // ================================
    @PostMapping("/enviar-botoes")
    public ResponseEntity<String> enviarPerguntaComBotoes(
            @RequestParam String numeroDestino) {
        try {
            String resultado = webHookService.enviarPerguntaComBotoes(numeroDestino);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Erro ao enviar pergunta com botões: " + e.getMessage());
        }
    }

    // ================================
    // Validação do Webhook do WhatsApp
    // ================================
    @GetMapping
    public ResponseEntity<String> validarWebhook(
            @RequestParam(name = "hub.mode", required = false) String mode,
            @RequestParam(name = "hub.verify_token", required = false) String token,
            @RequestParam(name = "hub.challenge", required = false) String challenge) {

        if ("subscribe".equals(mode) && apiConfig.getVerifyToken().equals(token)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(403).body("❌ Token inválido");
        }
    }

    // ======================================================
    // Recebe mensagens e interações do WhatsApp via webhook
    // ======================================================
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receberWebhook(@RequestBody Map<String, Object> payload) {
        try {
            System.out.println("📩 Payload recebido: " + payload);

            // Processa o webhook (respostas de texto e botões)
            webHookService.processarWebhook(payload);

            return ResponseEntity.ok("{\"status\": \"ok\"}");
        } catch (Exception e) {
            System.out.println("❌ Erro processando webhook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\": \"erro\", \"mensagem\": \"" + e.getMessage() + "\"}");
        }
    }

}
