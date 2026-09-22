package com.example.SecureLoginPUC.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SecureLoginPUC.config.UserConfig;
import com.example.SecureLoginPUC.service.PasswordRecoveryService;
import com.example.SecureLoginPUC.service.SendEmailService;
import com.example.SecureLoginPUC.service.UserService;

@Controller
public class SecureLoginController {

        private final UserConfig userConfig;
        private final SendEmailService sendEmailService;
        private final UserService userService;
        private final PasswordRecoveryService passwordRecoveryService;

        public SecureLoginController(
                        UserConfig userConfig,
                        SendEmailService sendEmailService,
                        UserService userService,
                        PasswordRecoveryService passwordRecoveryService) {

                this.userConfig = userConfig;
                this.sendEmailService = sendEmailService;
                this.userService = userService;
                this.passwordRecoveryService = passwordRecoveryService;
        }

        // =========================================================
        // HOME
        // =========================================================

        @GetMapping("/home")
        public String home(
                        Authentication authentication,
                        Model model) {

                System.out.println(
                                "Usuário logado: " + authentication.getName());

                model.addAttribute(
                                "usuario",
                                authentication.getName());

                return "home";
        }

        // =========================================================
        // LOGIN
        // =========================================================

        @GetMapping("/login")
        public String login(Model model) {

                model.addAttribute(
                                "recaptchaSiteKey",
                                userConfig.getRecaptchaSiteKey());

                return "login";
        }

        // =========================================================
        // ERROR
        // =========================================================

        @GetMapping("/error")
        public String error() {
                return "error";
        }

        // =========================================================
        // ADMIN
        // =========================================================

        @GetMapping("/admin")
        public String admin(
                        Authentication authentication,
                        Model model) {

                System.out.println(
                                "Administrador logado: "
                                                + authentication.getName());

                model.addAttribute(
                                "usuario",
                                authentication.getName());

                return "admin";
        }

        // =========================================================
        // CADASTRO
        // =========================================================

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

                        System.out.println(
                                        "Usuário já cadastrado: " + email);

                        return "redirect:/register?erro=true";
                }

                userService.createUser(
                                email,
                                senha,
                                nome);

                System.out.println(
                                "Usuário cadastrado: " + email);

                System.out.println(
                                "Nome cadastrado: " + nome);

                /*
                 * CPF, RG, endereço e instituição ainda não
                 * estão sendo armazenados, pois o projeto
                 * atualmente utiliza usuários em memória.
                 */

                return "redirect:/login?cadastro=sucesso";
        }

        // =========================================================
        // RECUPERAÇÃO DE SENHA
        // =========================================================

        @GetMapping("/recoverpassword")
        public String recoverpassword() {
                return "recoverpassword";
        }

        @PostMapping("/recoverpassword")
        public String handleRecoverPassword(
                        @RequestParam("email") String email) {

                if (!userService.exists(email)) {

                        System.out.println(
                                        "E-mail não encontrado: " + email);

                        return "redirect:/recoverpassword?erro=email";
                }

                String nome = userService.getName(email);

                if (nome == null || nome.isBlank()) {
                        nome = email;
                }

                String token = passwordRecoveryService.generateToken(email);

                String link = "http://localhost:8080/resetpassword?token="
                                + token;

                sendEmailService.sendEmail(
                                email,
                                "Recuperação de Senha - PUC Minas",
                                "<!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'></head><body style='margin:0;padding:0;background-color:#000000;font-family:\"Courier New\",Courier,monospace;color:#00ff00;'><div style='width:100%;padding:40px 20px;box-sizing:border-box;background-color:#000000;'><div style='max-width:600px;margin:0 auto;background:#000000;border:1px solid #00ff00;border-radius:10px;overflow:hidden;box-shadow:0 0 20px rgba(0,255,0,0.15);'><div style='padding:40px 30px;text-align:center;background-color:#000000;border-bottom:1px dashed #00ff00;'><div style='color:#00ff00;font-size:12px;font-weight:500;letter-spacing:2px;margin-bottom:12px;font-family:\"Courier New\",Courier,monospace;'>PUC MINAS</div><div style='color:#00ff00;font-size:26px;font-weight:bold;line-height:1.2;font-family:\"Courier New\",Courier,monospace;'>Recuperação de Senha</div></div><div style='padding:40px 45px;text-align:center;background-color:#000000;'><p style='margin:0 0 18px 0;color:#00ff00;font-size:17px;font-weight:bold;font-family:\"Courier New\",Courier,monospace;'>Olá, "
                                                + nome
                                                + "!</p><div style='width:60px;height:3px;margin:0 auto 25px auto;background-color:#00ff00;border-radius:999px;'></div><p style='margin:0 0 18px 0;color:#00ff00;opacity:0.85;font-size:14px;line-height:1.7;font-family:\"Courier New\",Courier,monospace;'>Recebemos uma solicitação para redefinir a senha da sua conta no sistema.</p><p style='margin:0 0 30px 0;color:#00ff00;opacity:0.85;font-size:14px;line-height:1.7;font-family:\"Courier New\",Courier,monospace;'>Clique no botão abaixo para criar uma nova senha.</p><a href='"
                                                + link
                                                + "' style='display:inline-block;padding:14px 28px;background-color:#000000;color:#00ff00;text-decoration:none;border:1px solid #00ff00;border-radius:5px;font-size:14px;font-weight:bold;font-family:\"Courier New\",Courier,monospace;'>&gt; Redefinir minha senha</a><p style='margin:30px 0 0 0;padding:12px;background-color:#000000;border:1px solid #00ff00;border-radius:5px;color:#00ff00;font-size:13px;line-height:1.6;font-family:\"Courier New\",Courier,monospace;'>Este link é válido por <strong style='color:#00ff00;'>15 minutos</strong>.</p><p style='margin:25px 0 0 0;color:#00ff00;opacity:0.6;font-size:12px;line-height:1.6;font-family:\"Courier New\",Courier,monospace;'>Se você não solicitou a recuperação da senha, ignore este e-mail.</p></div><div style='padding:20px 30px;background-color:#000000;border-top:1px dashed #00ff00;text-align:center;'><p style='margin:0;color:#00ff00;opacity:0.7;font-size:11px;line-height:1.5;font-family:\"Courier New\",Courier,monospace;'>PUC Minas<br>Sistema de Autenticação</p></div></div></div></body></html>");

                System.out.println(
                                "Link de recuperação enviado para: " + email);

                return "redirect:/recoverpassword?sucesso=email";
        }

        // =========================================================
        // RESET DE SENHA
        // =========================================================

        @GetMapping("/resetpassword")
        public String resetPassword(
                        @RequestParam("token") String token,
                        Model model) {

                String email = passwordRecoveryService
                                .getEmailFromToken(token);

                if (email == null) {

                        model.addAttribute(
                                        "erro",
                                        "O link de recuperação é inválido ou expirou.");

                        return "error";
                }

                model.addAttribute(
                                "token",
                                token);

                return "resetpassword";
        }

        @PostMapping("/resetpassword")
        public String handleResetPassword(
                        @RequestParam("token") String token,
                        @RequestParam("senha") String senha,
                        @RequestParam("confirmarSenha") String confirmarSenha) {

                if (!senha.equals(confirmarSenha)) {

                        return "redirect:/resetpassword?token="
                                        + token
                                        + "&erro=senhas";
                }

                String email = passwordRecoveryService
                                .getEmailFromToken(token);

                if (email == null) {

                        return "redirect:/login?erro=token";
                }

                userService.updatePassword(
                                email,
                                senha);

                passwordRecoveryService.invalidateToken(token);

                System.out.println(
                                "Senha alterada com sucesso para: "
                                                + email);

                return "redirect:/login?senha=alterada";
        }
}