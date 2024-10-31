package com.example.ImageCompressor.controller;

import com.example.ImageCompressor.service.ImageCompressorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageCompressorController {

    @Autowired
    private ImageCompressorService imageCompressorService;

    @PostMapping("/compress")
    public ResponseEntity<ByteArrayResource> compressImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "quality", defaultValue = "0.7") float quality) throws IOException {

        ByteArrayResource compressedImage = imageCompressorService.compressImage(file.getInputStream(), quality);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"compressed_image.jpg\"")
                .body(compressedImage);
    }
}
