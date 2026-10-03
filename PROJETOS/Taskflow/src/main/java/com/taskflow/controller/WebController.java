package com.taskflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsavel apenas por renderizar a pagina HTML (Thymeleaf).
 *
 * Repare que essa pagina NAO recebe os dados das tarefas diretamente do
 * Model do Spring: o front-end (JavaScript no navegador) consome a API REST
 * em /api/tasks via fetch(), exatamente como faria um front-end separado
 * (React, Next.js, etc). Isso demonstra o desacoplamento entre interface e API.
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
