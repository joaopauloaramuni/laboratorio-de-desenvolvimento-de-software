package com.example.ZapSender.controller;

import com.example.ZapSender.service.ZapSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zapsender")
public class ZapSenderController {

    @Autowired
    private ZapSenderService zapSenderService;

    /**
     * Envia um template do WhatsApp Cloud API.
     *
     * Exemplo de requisição:
     * POST http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US
     */
    @PostMapping("/enviar-template")
    public ResponseEntity<String> enviarTemplate(
            @RequestParam String numeroDestino,
            @RequestParam String nomeTemplate,
            @RequestParam(defaultValue = "en_US") String codigoIdioma) {

        try {
            String resultado = zapSenderService.enviarTemplate(numeroDestino, nomeTemplate, codigoIdioma);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Erro ao enviar template: " + e.getMessage());
        }
    }
}
