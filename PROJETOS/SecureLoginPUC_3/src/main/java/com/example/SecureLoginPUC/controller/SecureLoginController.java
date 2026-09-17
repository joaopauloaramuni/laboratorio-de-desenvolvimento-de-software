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

    /*
     * ============================================================
     * HOME
     * ============================================================
     */

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

    /*
     * ============================================================
     * LOGIN
     * ============================================================
     */

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /*
     * ============================================================
     * ERROR
     * ============================================================
     */

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    /*
     * ============================================================
     * ADMIN
     * ============================================================
     */

    @GetMapping("/admin")
    public String admin(
            Authentication authentication,
            Model model) {

        System.out.println(
                "Administrador logado: " + authentication.getName());

        model.addAttribute(
                "usuario",
                authentication.getName());

        return "admin";
    }

    /*
     * ============================================================
     * CADASTRO
     * ============================================================
     */

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

        /*
         * Verifica se o usuário já existe.
         */
        if (userService.exists(email)) {

            System.out.println(
                    "Usuário já cadastrado: " + email);

            return "redirect:/register";
        }

        /*
         * Cria o usuário e salva também o nome.
         */
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

    /*
     * ============================================================
     * RECUPERAÇÃO DE SENHA
     * ============================================================
     */

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String handleRecoverPassword(
            @RequestParam("email") String email) {

        /*
         * Verifica se o usuário existe.
         */
        if (!userService.exists(email)) {

            System.out.println(
                    "E-mail não encontrado: " + email);

            return "redirect:/recoverpassword?erro=email";
        }

        /*
         * Recupera o nome do usuário.
         */
        String nome = userService.getName(email);

        /*
         * Caso o nome não esteja cadastrado,
         * utiliza o próprio e-mail como identificação.
         */
        if (nome == null || nome.isBlank()) {
            nome = email;
        }

        /*
         * Gera um token único para recuperação.
         */
        String token = passwordRecoveryService.generateToken(email);

        /*
         * Monta o link de recuperação.
         */
        String link = "http://localhost:8080/resetpassword?token="
                + token;

        /*
         * Envia o e-mail personalizado.
         */
        sendEmailService.sendEmail(
                email,
                "Recuperação de Senha - PUC Minas",

                "Olá, " + nome + "!\n\n"
                        + "Recebemos uma solicitação para "
                        + "redefinir sua senha.\n\n"

                        + "Clique no link abaixo para criar "
                        + "uma nova senha:\n\n"

                        + link + "\n\n"

                        + "Este link é válido por 15 minutos.\n\n"

                        + "Se você não solicitou a recuperação "
                        + "da senha, ignore este e-mail.");

        System.out.println("Link de recuperação enviado para: " + email);

        return "redirect:/recoverpassword?sucesso=email";
    }

    /*
     * ============================================================
     * TELA PARA DEFINIR A NOVA SENHA
     * ============================================================
     */

    @GetMapping("/resetpassword")
    public String resetPassword(
            @RequestParam("token") String token,
            Model model) {

        /*
         * Verifica o token.
         */
        String email = passwordRecoveryService.getEmailFromToken(token);

        /*
         * Token inválido ou expirado.
         */
        if (email == null) {

            model.addAttribute(
                    "erro",
                    "O link de recuperação é inválido ou expirou.");

            return "error";
        }

        /*
         * Envia o token para o formulário.
         */
        model.addAttribute(
                "token",
                token);

        return "resetpassword";
    }

    /*
     * ============================================================
     * SALVAR NOVA SENHA
     * ============================================================
     */

    @PostMapping("/resetpassword")
    public String handleResetPassword(
            @RequestParam("token") String token,
            @RequestParam("senha") String senha,
            @RequestParam("confirmarSenha") String confirmarSenha) {

        /*
         * Verifica se as senhas são iguais.
         */
        if (!senha.equals(confirmarSenha)) {

            return "redirect:/resetpassword?token="
                    + token
                    + "&erro=senhas";
        }

        /*
         * Recupera o e-mail associado ao token.
         */
        String email = passwordRecoveryService.getEmailFromToken(token);

        /*
         * Verifica se o token ainda é válido.
         */
        if (email == null) {

            return "redirect:/login?erro=token";
        }

        /*
         * Atualiza a senha.
         */
        userService.updatePassword(
                email,
                senha);

        /*
         * Invalida o token para impedir
         * que ele seja utilizado novamente.
         */
        passwordRecoveryService.invalidateToken(token);

        System.out.println(
                "Senha alterada com sucesso para: " + email);

        return "redirect:/login?senha=alterada";
    }
}