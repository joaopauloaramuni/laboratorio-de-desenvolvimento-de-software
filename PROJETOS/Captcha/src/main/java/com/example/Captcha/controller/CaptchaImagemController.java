package com.example.Captcha.controller;

import java.io.IOException;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Captcha.service.CaptchaImagemService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/captcha-imagem")
public class CaptchaImagemController {

    private final CaptchaImagemService captchaImagemService;

    public CaptchaImagemController(CaptchaImagemService captchaImagemService) {
        this.captchaImagemService = captchaImagemService;
    }

    @GetMapping
    public String pagina() {
        return "captcha-imagem";
    }

    /** Endereço usado no src da tag <img>. Cada chamada gera um texto novo. */
    @GetMapping(value = "/imagem", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> imagem(HttpSession session) throws IOException {
        byte[] png = captchaImagemService.gerarImagem(session);

        // Impede o navegador de reaproveitar uma imagem antiga do cache
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .contentType(MediaType.IMAGE_PNG)
                .body(png);
    }

    @PostMapping
    public String enviar(
            @RequestParam String nome,
            @RequestParam(name = "captcha", required = false) String resposta,
            HttpSession session,
            Model model) {

        boolean valido = captchaImagemService.validar(session, resposta);

        model.addAttribute("sucesso", valido);
        model.addAttribute("mensagem", valido
                ? "Captcha validado. Mensagem de " + nome + " recebida."
                : "O texto não confere com a imagem. Uma nova imagem foi gerada.");

        return "captcha-imagem";
    }
}
