package com.example.CandidatosTSE.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CandidatosTSE.model.Candidato;
import com.example.CandidatosTSE.service.CandidatosTseService;

@Controller
public class CandidatosTseController {

    private final CandidatosTseService candidatosTseService;

    public CandidatosTseController(CandidatosTseService candidatosTseService) {
        this.candidatosTseService = candidatosTseService;
    }

    /**
     * Tela única com filtros por perfil (gênero, escolaridade e faixa etária).
     * Todos os parâmetros são opcionais (required = false).
     */
    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String escolaridade,
            @RequestParam(required = false) Integer idadeMin,
            @RequestParam(required = false) Integer idadeMax,
            Model model) {

        List<Candidato> candidatos = candidatosTseService.filtrarPerfil(genero, escolaridade, idadeMin, idadeMax);

        model.addAttribute("candidatos", candidatos);
        model.addAttribute("totalEncontrado", candidatos.size());

        // opções para os filtros (botões de opção de gênero e <select> de escolaridade)
        model.addAttribute("generos", candidatosTseService.listarGeneros());
        model.addAttribute("escolaridades", candidatosTseService.listarEscolaridades());

        // valores atualmente selecionados, para manter o filtro marcado na tela
        model.addAttribute("generoSelecionado", genero == null ? "" : genero);
        model.addAttribute("escolaridadeSelecionada", escolaridade == null ? "" : escolaridade);
        model.addAttribute("idadeMin", idadeMin);
        model.addAttribute("idadeMax", idadeMax);

        return "index";
    }
}