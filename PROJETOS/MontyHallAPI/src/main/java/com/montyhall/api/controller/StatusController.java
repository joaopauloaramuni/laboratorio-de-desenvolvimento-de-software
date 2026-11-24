package com.montyhall.api.controller;

import com.montyhall.api.service.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Diz se a API está no ar e se o MongoDB Atlas responde.
 *
 * GET http://localhost:8080/            (útil para abrir a URL do Render no navegador)
 * GET http://localhost:8080/api/status
 *
 * { "api": "online", "mongodb": "online" }
 */
@RestController
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping({"/", "/api/status"})
    public Map<String, String> status() {
        Map<String, String> resposta = new LinkedHashMap<>();
        resposta.put("api", "online");
        resposta.put("mongodb", statusService.mongoDbOnline() ? "online" : "offline");
        return resposta;
    }
}
