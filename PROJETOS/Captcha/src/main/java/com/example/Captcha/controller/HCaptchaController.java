package com.example.Captcha.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Captcha.service.HCaptchaService;

@Controller
@RequestMapping("/hcaptcha")
public class HCaptchaController {

    private final HCaptchaService hCaptchaService;

    @Value("${hcaptcha.site-key}")
    private String siteKey;

    public HCaptchaController(HCaptchaService hCaptchaService) {
        this.hCaptchaService = hCaptchaService;
    }

    @GetMapping
    public String pagina(Model model) {
        model.addAttribute("siteKey", siteKey);
        return "hcaptcha";
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "h-captcha-response", required = false) String token,
            Model model) {

        boolean valido = hCaptchaService.validar(token);

        model.addAttribute("siteKey", siteKey);
        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "Resolva o hCaptcha antes de enviar.");

        return "hcaptcha";
    }
}
