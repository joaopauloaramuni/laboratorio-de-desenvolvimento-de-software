package com.example.Captcha.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Captcha.service.RecaptchaV3Service;
import com.example.Captcha.service.RecaptchaV3Service.ResultadoV3;

@Controller
@RequestMapping("/recaptcha-v3")
public class RecaptchaV3Controller {

    // Precisa ser IGUAL à action usada no grecaptcha.execute(...) do HTML
    private static final String ACAO = "contato";

    private final RecaptchaV3Service recaptchaV3Service;

    @Value("${recaptcha.v3.site-key}")
    private String siteKey;

    public RecaptchaV3Controller(RecaptchaV3Service recaptchaV3Service) {
        this.recaptchaV3Service = recaptchaV3Service;
    }

    @GetMapping
    public String pagina(Model model) {
        model.addAttribute("siteKey", siteKey);
        return "recaptcha-v3";
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "g-recaptcha-response", required = false) String token,
            Model model) {

        ResultadoV3 resultado = recaptchaV3Service.validar(token, ACAO);

        model.addAttribute("siteKey", siteKey);
        model.addAttribute("sucesso", resultado.valido());
        model.addAttribute("mensagem", resultado.valido()
                ? "Captcha validado com score " + resultado.score() + ". Mensagem de " + nome + " recebida."
                : "Requisição bloqueada (score " + resultado.score() + "). " + resultado.motivo());

        return "recaptcha-v3";
    }
}
