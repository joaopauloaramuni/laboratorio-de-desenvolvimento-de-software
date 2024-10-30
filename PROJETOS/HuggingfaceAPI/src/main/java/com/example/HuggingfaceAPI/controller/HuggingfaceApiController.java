package com.example.HuggingfaceAPI.controller;

import com.example.HuggingfaceAPI.service.HuggingfaceApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class HuggingfaceApiController {

    @Autowired
    private HuggingfaceApiService huggingfaceApiService;

    @PostMapping("/generate")
        public String gerarImagem(@RequestParam String texto){
        try {
            return huggingfaceApiService.gerarImagem(texto);
        } catch (IOException e) {
            // Tratar erro ao gerar ou salvar a imagem
            return "Erro ao gerar a imagem: " + e.getMessage();
        } catch (RuntimeException e) {
            // Tratar erro específico da chamada à API
            return "Erro ao chamar a API: " + e.getMessage();
        }
    }
}
