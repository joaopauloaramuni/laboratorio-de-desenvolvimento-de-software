package com.example.YouTubeVideoDownloader.service;

import org.springframework.stereotype.Service;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import java.io.File;

@Service
public class YouTubeVideoDownloaderService {

    public String downloadVideo(String videoUrl, String outputDirectory) {
        try {
            // Criar um objeto File para o caminho de saída
            File outputFile = new File(outputDirectory);

            // Verificar se o diretório existe, se não existir, tentar criá-lo
            if (!outputFile.exists()) {
                boolean dirCreated = outputFile.mkdirs(); // Tenta criar o diretório, incluindo qualquer diretório pai necessário

                if (!dirCreated) {
                    System.err.println("Erro ao criar diretório: " + outputFile.getAbsolutePath());
                    return "Erro ao criar diretório: " + outputFile.getAbsolutePath();
                }
            } else {
                System.out.println("Diretório já existe: " + outputFile.getAbsolutePath());
            }

            // Caminho do executável yt-dlp
            String ytDlpPath = "/Users/joaopauloaramuni/Downloads/YouTubeVideoDownloader/.venv/bin/yt-dlp";

            // Comando para executar yt-dlp
            CommandLine cmdLine = new CommandLine(ytDlpPath);

            cmdLine.addArgument("--format");
            cmdLine.addArgument("bestvideo[height<=1080]+bestaudio/best"); // Prioriza 1080p

            // Usando o título do vídeo e a extensão correta para o arquivo
            cmdLine.addArgument("-o");
            cmdLine.addArgument(outputDirectory + "/%(title)s.%(ext)s"); // Salva com título original e extensão correta

            cmdLine.addArgument(videoUrl);
            cmdLine.addArgument("--merge-output-format");
            cmdLine.addArgument("mp4"); // Garante que o vídeo e áudio estejam em MP4

            DefaultExecutor executor = new DefaultExecutor();

            System.out.println("Iniciando download...");
            int exitValue = executor.execute(cmdLine);

            System.out.println("Download concluído com código de saída: " + exitValue);
            return "Download concluído com código de saída: " + exitValue;
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