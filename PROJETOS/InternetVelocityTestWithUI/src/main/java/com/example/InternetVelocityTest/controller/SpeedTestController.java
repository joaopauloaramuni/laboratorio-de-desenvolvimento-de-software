package com.example.InternetVelocityTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.InternetVelocityTest.service.SpeedTestService;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/speed-test")
public class SpeedTestController {

    @Autowired
    private SpeedTestService speedTestService;

    @GetMapping("/download")
    public String performDownloadSpeedTest() {
        speedTestService.startDownloadSpeedTest();
        System.out.println("O teste de velocidade de download foi iniciado. Verifique os logs e a página download.html para progresso e conclusão.");
        return "download"; // Retorna a página download.html
    }

    @GetMapping("/upload")
    public String performUploadSpeedTest() {
        speedTestService.startUploadSpeedTest();
        System.out.println("O teste de velocidade de upload foi iniciado. Verifique os logs e a página download.html para progresso e conclusão.");
        return "upload"; // Retorna a página upload.html
    }
}
