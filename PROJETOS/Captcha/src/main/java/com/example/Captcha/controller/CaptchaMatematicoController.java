package com.example.Captcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Captcha.service.CaptchaMatematicoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/captcha-matematico")
public class CaptchaMatematicoController {

    private final CaptchaMatematicoService captchaMatematicoService;

    public CaptchaMatematicoController(CaptchaMatematicoService captchaMatematicoService) {
        this.captchaMatematicoService = captchaMatematicoService;
    }

    @GetMapping
    public String pagina(HttpSession session, Model model) {
        model.addAttribute("pergunta", captchaMatematicoService.gerarPergunta(session));
        return "captcha-matematico";
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "resposta", required = false) String resposta,
            HttpSession session,
            Model model) {

        boolean valido = captchaMatematicoService.validar(session, resposta);

        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "Resposta errada. Uma nova conta foi gerada.");

        // Toda tentativa gera uma pergunta nova
        model.addAttribute("pergunta", captchaMatematicoService.gerarPergunta(session));

        return "captcha-matematico";
    }
}
