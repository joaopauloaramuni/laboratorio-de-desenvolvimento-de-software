package com.example.ImageCompressor.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageCompressorService {

    public ByteArrayResource compressImage(InputStream imageInputStream, float quality) throws IOException {
        BufferedImage image = ImageIO.read(imageInputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        var imageWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
        var imageOutputStream = ImageIO.createImageOutputStream(outputStream);

        imageWriter.setOutput(imageOutputStream);

        var imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(imageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality);

        imageWriter.write(null, new IIOImage(image, null, null), imageWriteParam);
        imageWriter.dispose();
        imageOutputStream.close();

        // Salvar a imagem comprimida na pasta images na raiz do projeto
        saveCompressedImage(outputStream.toByteArray());

        // Retornar a imagem como ByteArrayResource
        return new ByteArrayResource(outputStream.toByteArray());
    }

    private void saveCompressedImage(byte[] imageData) throws IOException {
        Path outputPath = Paths.get("images/compressed_image.jpg");
        Files.write(outputPath, imageData);
    }
}