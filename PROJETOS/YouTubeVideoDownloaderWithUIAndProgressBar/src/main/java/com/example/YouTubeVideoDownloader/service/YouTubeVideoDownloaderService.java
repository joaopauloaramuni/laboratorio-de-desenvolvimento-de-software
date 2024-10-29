package com.example.YouTubeVideoDownloader.service;

import org.apache.commons.exec.ExecuteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YouTubeVideoDownloaderService {

    @Autowired
    private ProgressService progressService;

    public String downloadVideo(String videoUrl, String outputDirectory, String sessionId) {
        try {
            // Verificação de diretório
            File outputFile = new File(outputDirectory);
            if (!outputFile.exists()) {
                boolean dirCreated = outputFile.mkdirs();
                if (!dirCreated) {
                    System.err.println("Erro ao criar diretório: " + outputFile.getAbsolutePath());
                    return "Erro ao criar diretório: " + outputFile.getAbsolutePath();
                }
            }

            // Caminho do executável yt-dlp
            String ytDlpPath = "/Users/joaopauloaramuni/Downloads/YouTubeVideoDownloaderWithUI/.venv/bin/yt-dlp";
            ProcessBuilder processBuilder = new ProcessBuilder(
                    ytDlpPath,
                    "--format", "bestvideo[height<=1080]+bestaudio/best",
                    "-o", outputDirectory + "/%(title)s.%(ext)s",
                    videoUrl,
                    "--merge-output-format", "mp4"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Captura do progresso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Pattern pattern = Pattern.compile("\\[download\\]\\s+(\\d+\\.\\d+)%");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String progress = matcher.group(1);
                    progressService.sendProgress(sessionId, progress);
                }
                System.out.println(line); // Log no terminal para depuração
            }

            int exitCode = process.waitFor();
            System.out.println("Download concluído com código de saída: " + exitCode);
            progressService.sendProgress(sessionId, "100"); //Se já existe o arquivo de vídeo na pasta
            return "Download concluído com código de saída: " + exitCode;
        } catch (ExecuteException e) {
            System.err.println("Erro ao executar o yt-dlp: " + e.getMessage());
            return "Erro ao executar o yt-dlp: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
            return "Erro: " + e.getMessage();
        }
    }
}