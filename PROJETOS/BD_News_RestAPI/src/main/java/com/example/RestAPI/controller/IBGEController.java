package com.example.RestAPI.controller;
import com.example.RestAPI.service.IBGEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IBGEController {
    @Autowired
    private IBGEService ibgeService;

    @GetMapping("/noticias")
    public String consultarNoticias(){
        return ibgeService.consultarNoticias();
    }

    @GetMapping("/releases")
    public String consultarReleases(){
        return ibgeService.consultarReleases();
    }

    @GetMapping("/noticiasereleases")
    public String consultarNoticiasEReleases() {
        return ibgeService.consultarNoticiasEReleases();
    }

}
