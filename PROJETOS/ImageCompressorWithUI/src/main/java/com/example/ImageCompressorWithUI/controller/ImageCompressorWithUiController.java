package com.example.ImageCompressorWithUI.controller;

import com.example.ImageCompressorWithUI.service.ImageCompressorWithUiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ImageCompressorWithUiController {

    @Autowired
    private ImageCompressorWithUiService imageCompressorWithUiService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("quality", 0.7f); // Qualidade padrão
        return "home";
    }

    @PostMapping("/images/compress")
    public ResponseEntity<ByteArrayResource> compressImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "quality", defaultValue = "0.7") float quality) throws IOException {

        ByteArrayResource compressedImage = imageCompressorWithUiService.compressImage(file.getInputStream(), quality);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"compressed_image.jpg\"")
                .body(compressedImage);
    }
}
