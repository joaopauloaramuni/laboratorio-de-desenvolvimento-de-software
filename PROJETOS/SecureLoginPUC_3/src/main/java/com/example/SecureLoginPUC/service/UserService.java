package com.example.SecureLoginPUC.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.SecureLoginPUC.config.UserConfig;

@Service
public class UserService {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    /*
     * ============================================================
     * NOMES DOS USUÁRIOS
     * ============================================================
     *
     * Guarda o nome associado ao e-mail/usuário.
     *
     * Exemplo:
     *
     * joao -> João Paulo
     * admin -> Administrador
     * joao@gmail.com -> João Paulo
     *
     */

    private final Map<String, String> userNames = new HashMap<>();

    /*
     * ============================================================
     * CONSTRUTOR
     * ============================================================
     */

    public UserService(
            InMemoryUserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder,
            UserConfig userConfig) {

        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;

        /*
         * Registra os nomes dos usuários pré-configurados.
         */
        userNames.put(
                userConfig.getUserUsername(),
                userConfig.getUserName());

        userNames.put(
                userConfig.getAdminUsername(),
                userConfig.getAdminName());
    }

    /*
     * ============================================================
     * CRIAR USUÁRIO
     * ============================================================
     */

    public void createUser(
            String email,
            String senha,
            String nome) {

        /*
         * Cria o usuário do Spring Security.
         */
        UserDetails user = User.builder()
                .username(email)
                .password(
                        passwordEncoder.encode(senha))
                .roles("USER")
                .build();

        /*
         * Salva o usuário em memória.
         */
        userDetailsManager.createUser(user);

        /*
         * Salva o nome associado ao e-mail.
         */
        userNames.put(
                email,
                nome);
    }

    /*
     * ============================================================
     * VERIFICAR SE USUÁRIO EXISTE
     * ============================================================
     */

    public boolean exists(String email) {

        return userDetailsManager.userExists(email);
    }

    /*
     * ============================================================
     * BUSCAR NOME DO USUÁRIO
     * ============================================================
     */

    public String getName(String email) {

        return userNames.get(email);
    }

    /*
     * ============================================================
     * ATUALIZAR SENHA
     * ============================================================
     */

    public void updatePassword(
            String email,
            String novaSenha) {

        /*
         * Busca o usuário atual.
         */
        UserDetails usuarioAtual = userDetailsManager.loadUserByUsername(email);

        /*
         * Cria uma nova versão do usuário
         * mantendo as permissões atuais.
         */
        UserDetails usuarioAtualizado = User.builder()
                .username(
                        usuarioAtual.getUsername())
                .password(
                        passwordEncoder.encode(novaSenha))
                .authorities(
                        usuarioAtual.getAuthorities())
                .build();

        /*
         * Atualiza o usuário no armazenamento em memória.
         */
        userDetailsManager.updateUser(
                usuarioAtualizado);
    }
}