package com.montyhall.api.controller;

import com.montyhall.api.dto.SimulacaoResposta;
import com.montyhall.api.service.SimulacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simula muitas partidas de uma vez, sem gravar nada no MongoDB.
 *
 * GET http://localhost:8080/api/simulacoes                 (10.000 rodadas)
 * GET http://localhost:8080/api/simulacoes?rodadas=100000
 *
 * É um GET porque não altera nada: dá para abrir direto no navegador.
 */
@RestController
@RequestMapping("/api/simulacoes")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @GetMapping
    public SimulacaoResposta simular(@RequestParam(defaultValue = "10000") int rodadas) {
        return simulacaoService.simular(rodadas);
    }
}
