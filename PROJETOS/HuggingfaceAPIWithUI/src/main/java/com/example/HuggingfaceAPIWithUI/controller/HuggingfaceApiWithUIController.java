package com.example.HuggingfaceAPIWithUI.controller;

import com.example.HuggingfaceAPIWithUI.service.HuggingfaceApiWithUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class HuggingfaceApiWithUIController {

    @Autowired
    private HuggingfaceApiWithUIService huggingfaceApiWithUIService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/generate")
    @ResponseBody
    public String gerarImagem(@RequestParam String texto, @RequestParam String diretorio) {
        try {
            return huggingfaceApiWithUIService.gerarImagem(texto, diretorio);
        } catch (IOException e) {
            return "Erro ao gerar a imagem: " + e.getMessage();
        } catch (RuntimeException e) {
            return "Erro ao chamar a API: " + e.getMessage();
        }
    }
}
