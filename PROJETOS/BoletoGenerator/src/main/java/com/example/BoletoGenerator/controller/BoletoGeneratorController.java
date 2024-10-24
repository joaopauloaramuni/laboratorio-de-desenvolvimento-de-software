package com.example.BoletoGenerator.controller;

import com.example.BoletoGenerator.service.BoletoGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoletoGeneratorController {

    @Autowired
    private BoletoGeneratorService boletoGeneratorService; // Injeção de dependência diretamente no campo

    @GetMapping("/gerar-boleto")
    public ResponseEntity<String> gerarBoleto() {
        try {
            boletoGeneratorService.gerarBoleto();
            return ResponseEntity.ok("Boleto gerado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar o boleto: " + e.getMessage());
        }
    }
}
