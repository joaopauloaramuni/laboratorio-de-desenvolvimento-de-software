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
@RequestMapping("/recaptcha-v2-checkbox")
public class RecaptchaV2CheckboxController {

    private final RecaptchaV2Service recaptchaV2Service;

    @Value("${recaptcha.v2.checkbox.site-key}")
    private String siteKey;

    public RecaptchaV2CheckboxController(RecaptchaV2Service recaptchaV2Service) {
        this.recaptchaV2Service = recaptchaV2Service;
    }

    @GetMapping
    public String pagina(Model model) {
        // A SITE KEY é pública: vai para o HTML
        model.addAttribute("siteKey", siteKey);
        return "recaptcha-v2-checkbox";
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "g-recaptcha-response", required = false) String token,
            Model model) {

        boolean valido = recaptchaV2Service.validarCheckbox(token);

        model.addAttribute("siteKey", siteKey);
        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "Marque a caixa \"Não sou um robô\" antes de enviar.");

        return "recaptcha-v2-checkbox";
    }
}
