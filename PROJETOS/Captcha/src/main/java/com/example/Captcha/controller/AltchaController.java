package com.example.Captcha.controller;

import java.util.Map;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Captcha.service.AltchaService;

@Controller
@RequestMapping("/altcha")
public class AltchaController {

    private final AltchaService altchaService;

    public AltchaController(AltchaService altchaService) {
        this.altchaService = altchaService;
    }

    @GetMapping
    public String pagina() {
        return "altcha";
    }

    /** O widget chama este endereço (atributo challengeurl) para pegar um desafio. */
    @GetMapping("/desafio")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> desafio() throws Exception {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(altchaService.criarDesafio());
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "altcha", required = false) String payload,
            Model model) {

        boolean valido = altchaService.validar(payload);

        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "A verificação ALTCHA falhou ou expirou. Marque a caixa de novo.");

        return "altcha";
    }
}
