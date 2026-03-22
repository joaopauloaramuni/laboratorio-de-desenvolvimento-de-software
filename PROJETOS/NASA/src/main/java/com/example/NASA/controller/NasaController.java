package com.example.NASA.controller;

import com.example.NASA.dto.NasaApodDTO;
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

        String mediaHtml = "";

        if ("image".equalsIgnoreCase(dto.getMediaType())) {
            mediaHtml = "<img src='" + dto.getUrl() + "' width='600'/>";
        } else if ("video".equalsIgnoreCase(dto.getMediaType())) {

            if (dto.getUrl().endsWith(".mp4")) {
                mediaHtml = "<video width='600' controls autoplay muted loop>" +
                        "<source src='" + dto.getUrl() + "' type='video/mp4'>" +
                        "</video>";
            } else {
                mediaHtml = "<iframe width='600' height='400' src='" +
                        dto.getUrl() +
                        "' frameborder='0' allowfullscreen></iframe>";
            }
        }

        return "<h1>" + dto.getTitle() + "</h1>" +
                mediaHtml +
                "<p>" + dto.getExplanation() + "</p>";
    }
}
