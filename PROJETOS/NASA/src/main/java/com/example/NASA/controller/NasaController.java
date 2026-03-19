package com.example.NASA.controller;

import com.example.NASA.service.NasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nasa")
public class NasaController {

    @Autowired
    private NasaService nasaService;

    @GetMapping("/imagem")
    public String getImagemDoDia() {
        return nasaService.buscarImagemDoDia();
    }
}
