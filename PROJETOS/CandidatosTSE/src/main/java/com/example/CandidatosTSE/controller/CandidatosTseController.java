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
     * Tela única com filtros (cargo, partido e busca por texto).
     * Todos os parâmetros são opcionais (required = false).
     */
    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) String partido,
            @RequestParam(required = false) String texto,
            Model model) {

        List<Candidato> candidatos = candidatosTseService.filtrar(cargo, partido, texto);

        model.addAttribute("candidatos", candidatos);
        model.addAttribute("totalEncontrado", candidatos.size());

        // opções para os <select> dos filtros
        model.addAttribute("cargos", candidatosTseService.listarCargos());
        model.addAttribute("partidos", candidatosTseService.listarPartidos());

        // valores atualmente selecionados, para manter o filtro marcado na tela
        model.addAttribute("cargoSelecionado", cargo == null ? "" : cargo);
        model.addAttribute("partidoSelecionado", partido == null ? "" : partido);
        model.addAttribute("textoSelecionado", texto == null ? "" : texto);

        return "index";
    }
}