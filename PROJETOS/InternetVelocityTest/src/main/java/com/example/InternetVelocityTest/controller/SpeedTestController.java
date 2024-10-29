package com.example.InternetVelocityTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.InternetVelocityTest.service.SpeedTestService;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/speed-test")
public class SpeedTestController {

    @Autowired
    private SpeedTestService speedTestService;

    @GetMapping("/download")
    public String performDownloadSpeedTest() {
        speedTestService.startDownloadSpeedTest();
        return "O teste de velocidade de download foi iniciado. Verifique os logs para progresso e conclusão.";
    }

    @GetMapping("/upload")
    public String performUploadSpeedTest() {
        speedTestService.startUploadSpeedTest();
        return "O teste de velocidade de upload foi iniciado. Verifique os logs para progresso e conclusão.";
    }
}
