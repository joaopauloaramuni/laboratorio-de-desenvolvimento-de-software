package com.example.PdfTranslator.controller;

import com.example.PdfTranslator.service.PdfTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PdfTranslatorController {

    @Autowired
    private PdfTranslatorService pdfTranslatorService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/translate")
    public ResponseEntity<byte[]> translatePdf(@RequestParam("file") MultipartFile file)
            throws IOException, InterruptedException {

        byte[] translatedContent = pdfTranslatorService.translatePdfToTxt(file);

        String filename = "traducao_" +
                file.getOriginalFilename().replace(".pdf", "") + ".txt";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .body(translatedContent);
    }
}