package com.example.TextToGif.service;

import com.example.TextToGif.config.GiphyApiConfig;
import com.example.TextToGif.data.GifData;
import com.example.TextToGif.response.GifResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.List;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

@Service
public class GifService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GiphyApiConfig apiConfig;

    public GifResponse searchGif(String query, int limit) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiConfig.getApiUrl())
                .queryParam("api_key", apiConfig.getApiKey())
                .queryParam("q", query)
                .queryParam("limit", limit)
                .queryParam("lang", "en")
                .build()
                .toUri();

        GifResponse response = restTemplate.getForObject(uri, GifResponse.class);

        // Salvar os GIFs em uma pasta
        if (response != null && response.getData() != null) {
            System.out.println("\nChamando o método saveGifs...");
            saveGifs(response.getData());
        }

        return response;
    }

    private void saveGifs(List<GifData> gifs) {
        for (GifData gif : gifs) {
            String gifUrl = "https://i.giphy.com/" + gif.getId() + ".webp";
            System.out.println("\nSalvando GIF: " + gifUrl);
            System.out.println("URL Original: " + gif.getImages().getOriginal().getUrl());
            System.out.println("Gif ID: " + gif.getId());
            String fileName = gif.getId() + ".gif"; // Nome do arquivo baseado no ID do GIF
            Path outputPath = Path.of("gifs", fileName); // Caminho para salvar o arquivo na pasta gifs

            try {
                // Abrindo a conexão HTTP
                HttpURLConnection connection = (HttpURLConnection) new URL(gifUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
                connection.setDoInput(true);
                connection.connect();

                // Verifica se a resposta foi bem-sucedida
                int responseCode = connection.getResponseCode();
                System.out.println("Código de resposta: " + responseCode);
                System.out.println("Tipo de conteúdo: " + connection.getContentType());
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Obtém o InputStream da conexão
                    try (InputStream in = connection.getInputStream()) {
                        // Salva o GIF na pasta gifs
                        Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("GIF salvo: " + outputPath);
                    }
                } else {
                    // Lê e imprime a resposta do erro
                    String errorMessage = new BufferedReader(new InputStreamReader(connection.getErrorStream()))
                            .lines()
                            .collect(Collectors.joining("\n"));
                    System.err.println("Erro ao baixar o GIF: " + gifUrl + ". Código de resposta: " + responseCode);
                    System.err.println("Mensagem de erro: " + errorMessage);
                }
            } catch (IOException e) {
                System.err.println("Erro ao salvar o GIF: " + gifUrl);
                e.printStackTrace();
            }
        }
    }
}
