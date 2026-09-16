package com.example.SecureLoginPUC.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SecureLoginPUC.config.UserConfig;
import com.example.SecureLoginPUC.service.SendEmailService;
import com.example.SecureLoginPUC.service.UserService;

@Controller
public class SecureLoginController {

    private final UserConfig userConfig;
    private final SendEmailService sendEmailService;
    private final UserService userService;

    public SecureLoginController(UserConfig userConfig,
                                 SendEmailService SendEmailService,
                                 UserService userService) {
        this.userConfig = userConfig;
        this.sendEmailService = SendEmailService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        System.out.println("Usuário logado: " + authentication.getName());
        model.addAttribute("usuario", authentication.getName());
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model) {
        System.out.println("Administrador logado: " + authentication.getName());
        model.addAttribute("usuario", authentication.getName());
        return "admin";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("cpf") String cpf,
            @RequestParam("rg") String rg,
            @RequestParam("endereco") String endereco,
            @RequestParam("instituicao") String instituicao,
            @RequestParam("senha") String senha) {

        if (userService.exists(email)) {
            System.out.println("Usuário já cadastrado: " + email);
            return "redirect:/register";
        }

        userService.createUser(email, senha);

        System.out.println("Usuário cadastrado: " + email);

        return "redirect:/login?cadastro=sucesso";
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String handleRecoverPassword(
            @RequestParam("email") String email) {

        sendEmailService.sendEmail(
                email,
                "Recuperação de Senha",
                "Aqui está o link para recuperar sua senha: [link de recuperação]"
        );

        System.out.println("Recuperação de E-mail: Redirecionado para a página de login.");

        return "redirect:/login";
    }
}