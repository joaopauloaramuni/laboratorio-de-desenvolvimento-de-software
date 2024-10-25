package com.example.QrCodeGenerator.controller;

import com.example.QrCodeGenerator.service.QrCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    // Endpoint para gerar o QR Code como imagem PNG
    @GetMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String text,
                                                 @RequestParam(defaultValue = "300") int width,
                                                 @RequestParam(defaultValue = "300") int height) throws WriterException, IOException {

        byte[] qrCodeImage = qrCodeService.generateQRCodeImage(text, width, height);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(qrCodeImage);
    }

    // Endpoint para salvar o QR Code como imagem PNG em um arquivo local
    @GetMapping("/saveQRCode")
    public String saveQRCode(@RequestParam String text,
                             @RequestParam(defaultValue = "300") int width,
                             @RequestParam(defaultValue = "300") int height) throws WriterException, IOException {

        qrCodeService.saveQRCodeImage(text, width, height);
        return "QR Code salvo com sucesso em: " + qrCodeService.getQRCodeImagePath();
    }
}
