package com.example.NASA.controller;

import com.example.NASA.dtos.NasaApodDTO;
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
    public NasaApodDTO getImagemDoDia() {
        return nasaService.buscarImagemDoDia();
    }

    @GetMapping("/imagem/html")
    public String verImagemHtml() {
        NasaApodDTO dto = nasaService.buscarImagemDoDia();

        return "<h1>" + dto.getTitle() + "</h1>" +
                "<img src='" + dto.getUrl() + "' width='600'/>" +
                "<p>" + dto.getExplanation() + "</p>";
    }
}
