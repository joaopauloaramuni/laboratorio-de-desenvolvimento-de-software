package com.example.Captcha.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Captcha.service.RecaptchaV2Service;

@Controller
@RequestMapping("/recaptcha-v2-invisivel")
public class RecaptchaV2InvisivelController {

    private final RecaptchaV2Service recaptchaV2Service;

    @Value("${recaptcha.v2.invisivel.site-key}")
    private String siteKey;

    public RecaptchaV2InvisivelController(RecaptchaV2Service recaptchaV2Service) {
        this.recaptchaV2Service = recaptchaV2Service;
    }

    @GetMapping
    public String pagina(Model model) {
        model.addAttribute("siteKey", siteKey);
        return "recaptcha-v2-invisivel";
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "g-recaptcha-response", required = false) String token,
            Model model) {

        boolean valido = recaptchaV2Service.validarInvisivel(token);

        model.addAttribute("siteKey", siteKey);
        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "O Google não confirmou a verificação. Tente enviar de novo.");

        return "recaptcha-v2-invisivel";
    }
}
