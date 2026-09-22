package com.example.SecureLoginPUC.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    // =========================================================
    // USUÁRIO
    // =========================================================

    @Value("${app.user.username}")
    private String userUsername;

    @Value("${app.user.password}")
    private String userPassword;

    @Value("${app.user.name}")
    private String userName;


    // =========================================================
    // ADMINISTRADOR
    // =========================================================

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.name}")
    private String adminName;


    // =========================================================
    // GOOGLE reCAPTCHA
    // =========================================================

    @Value("${recaptcha.site-key}")
    private String recaptchaSiteKey;

    @Value("${recaptcha.secret-key}")
    private String recaptchaSecretKey;


    // =========================================================
    // GETTERS - USUÁRIO
    // =========================================================

    public String getUserUsername() {
        return userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }


    // =========================================================
    // GETTERS - ADMINISTRADOR
    // =========================================================

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminName() {
        return adminName;
    }


    // =========================================================
    // GETTERS - reCAPTCHA
    // =========================================================

    public String getRecaptchaSiteKey() {
        return recaptchaSiteKey;
    }

    public String getRecaptchaSecretKey() {
        return recaptchaSecretKey;
    }
}