package com.example.BarCodeGenerator.service;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class BarCodeService {

    public byte[] generateBarcode(String barcodeText) {
        try {
            Code39Bean barcodeGenerator = new Code39Bean();
            barcodeGenerator.setModuleWidth(0.2);  // Largura do módulo
            barcodeGenerator.setHeight(15f);       // Altura do código
            barcodeGenerator.doQuietZone(false);   // Desativa a zona silenciosa

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(outputStream, "image/png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            barcodeGenerator.generateBarcode(canvasProvider, barcodeText);
            canvasProvider.finish();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o código de barras", e);
        }
    }
}
