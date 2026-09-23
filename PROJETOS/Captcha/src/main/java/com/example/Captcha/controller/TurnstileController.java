package com.example.Captcha.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Captcha.service.TurnstileService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/turnstile")
public class TurnstileController {

    private final TurnstileService turnstileService;

    @Value("${turnstile.site-key}")
    private String siteKey;

    public TurnstileController(TurnstileService turnstileService) {
        this.turnstileService = turnstileService;
    }

    @GetMapping
    public String pagina(Model model) {
        model.addAttribute("siteKey", siteKey);
        return "turnstile";
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "cf-turnstile-response", required = false) String token,
            HttpServletRequest request,
            Model model) {

        boolean valido = turnstileService.validar(token, request.getRemoteAddr());

        model.addAttribute("siteKey", siteKey);
        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "A verificação da Cloudflare não foi concluída. Aguarde o selo ficar verde e envie de novo.");

        return "turnstile";
    }
}
