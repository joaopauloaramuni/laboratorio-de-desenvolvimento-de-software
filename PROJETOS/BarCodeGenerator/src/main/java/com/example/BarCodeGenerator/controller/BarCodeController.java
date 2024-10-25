package com.example.BarCodeGenerator.controller;

import com.example.BarCodeGenerator.service.BarCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarCodeController {

    @Autowired
    private BarCodeService barCodeService;

    @GetMapping("/generate-barcode")
    public ResponseEntity<byte[]> generateBarcode(@RequestParam("text") String barcodeText) {
        byte[] barcodeImage = barCodeService.generateBarcode(barcodeText);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=barcode.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(barcodeImage);
    }
}
