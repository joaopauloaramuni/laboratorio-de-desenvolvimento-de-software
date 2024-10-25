package com.example.GetSubs.service;

import com.example.GetSubs.config.OpenSubtitlesConfig;
import com.example.GetSubs.dto.DownloadByMovieNameRequestDTO;
import com.example.GetSubs.dto.DownloadRequestDTO;
import com.example.GetSubs.dto.LoginRequestDTO;
import com.example.GetSubs.dto.SearchRequestDTO;
import com.example.GetSubs.model.Subtitle;
import com.example.GetSubs.model.File;
import com.example.GetSubs.model.Download;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class GetSubsService {

    @Autowired
    private OpenSubtitlesConfig config;

    @Autowired
    private RestTemplate restTemplate;

    public String authenticateUser(LoginRequestDTO loginRequest) {
        String authUrl = config.getApiUrl() + "/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", config.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authPayload = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", loginRequest.getUsername(), loginRequest.getPassword());
        HttpEntity<String> entity = new HttpEntity<>(authPayload, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, JsonNode.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String jwtToken = response.getBody().get("token").asText();
            System.out.println("Token JWT: " + jwtToken); // Imprime o token no terminal
            return jwtToken; // Retorna o token para uso posterior
        } else {
            return "Erro na autenticação: " + response.getStatusCode();
        }
    }

    public List<Subtitle> searchSubtitles(SearchRequestDTO searchRequestDTO) {
        // Construindo a URL
        String searchUrl = String.format("%s/subtitles?query=%s&languages=%s", config.getApiUrl(), searchRequestDTO.getQuery(), searchRequestDTO.getLanguages());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", config.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Fazendo uma requisição GET
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, JsonNode.class);

        List<Subtitle> subtitles = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode subtitlesNode = response.getBody().get("data");

            // Verificando se subtitlesNode não é nulo
            if (subtitlesNode != null && subtitlesNode.isArray()) {
                for (JsonNode subtitleNode : subtitlesNode) {
                    JsonNode attributes = subtitleNode.get("attributes");
                    List<File> files = new ArrayList<>();

                    // Extraindo os arquivos associados, se existirem
                    if (attributes.has("files")) {
                        for (JsonNode fileNode : attributes.get("files")) {
                            File file = new File(
                                    fileNode.get("file_id").asInt(),
                                    fileNode.get("cd_number").asInt(),
                                    fileNode.get("file_name").asText()
                            );
                            files.add(file);
                        }
                    }

                    Subtitle subtitle = new Subtitle(
                            subtitleNode.get("id").asText(), // ID da legenda
                            attributes.get("language").asText(), // Idioma da legenda
                            attributes.get("subtitle_id").asText(), // ID da legenda
                            attributes.get("download_count").asInt(), // Contagem de downloads
                            attributes.get("hearing_impaired").asBoolean(), // Deficientes auditivos
                            attributes.get("hd").asBoolean(), // HD
                            attributes.get("fps").asDouble(), // FPS
                            attributes.get("release").asText(), // Informações sobre a liberação
                            attributes.get("comments").asText(), // Comentários
                            attributes.get("url").asText(), // URL da legenda
                            files // Lista de arquivos
                    );
                    subtitles.add(subtitle);
                }
            }
        }
        return subtitles;
    }

    public Download downloadSubtitle(DownloadRequestDTO downloadRequest, String token) {
        String downloadUrl = config.getApiUrl() + "/download";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", config.getApiKey());
        headers.set("Authorization", token);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "insomnia/10.1.0");

        String downloadPayload = String.format("{\"file_id\": %d}", downloadRequest.getFileId());
        HttpEntity<String> entity = new HttpEntity<>(downloadPayload, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(downloadUrl, HttpMethod.POST, entity, JsonNode.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode body = response.getBody();

            // Verifique se o corpo não é nulo e se contém todos os campos obrigatórios
            if (body != null && body.has("link")) {
                // Cria e retorna um objeto Download com os dados da resposta
                return new Download(
                        body.get("link").asText(),
                        body.get("file_name").asText(),
                        body.get("requests").asInt(),
                        body.get("remaining").asInt(),
                        body.get("message").asText(),
                        body.get("reset_time").asText(),
                        body.get("reset_time_utc").asText()
                );
            } else {
                throw new IllegalArgumentException("Um ou mais campos obrigatórios não encontrados na resposta");
            }
        } else {
            throw new RuntimeException("Erro no download: " + response.getStatusCode());
        }
    }

    public String downloadSubtitleByMovieName(DownloadByMovieNameRequestDTO downloadByMovieNameRequest) {
        try {
            // 1. Realiza o login e obtém o token
            LoginRequestDTO loginRequest = new LoginRequestDTO();
            loginRequest.setUsername(config.getUsername());
            loginRequest.setPassword(config.getPassword());
            String token = authenticateUser(loginRequest); // Chame o método de autenticação

            // 2. Pesquisa as legendas pelo nome do filme
            SearchRequestDTO searchRequest = new SearchRequestDTO();
            String movieName = downloadByMovieNameRequest.getMovieName();
            searchRequest.setQuery(movieName);
            searchRequest.setLanguages("pt-br");
            List<Subtitle> subtitles = searchSubtitles(searchRequest);

            // Verifica se encontrou alguma legenda
            if (subtitles.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma legenda encontrada para a consulta.");
            }

            // Supondo que você queira pegar o primeiro fileID
            int fileID = subtitles.get(0).getFiles().get(0).getFileId();

            // 3. Chama o método de download usando o fileID
            DownloadRequestDTO downloadRequest = new DownloadRequestDTO();
            downloadRequest.setFileId(fileID);
            Download download = downloadSubtitle(downloadRequest, token);
            String fileUrl = download.getLink();

            // Altere para o caminho desejado
            String destinationPath = "legenda/" + movieName + ".srt";
            downloadFile(fileUrl, destinationPath);

            // Retorna a URL de download
            return fileUrl;
        } catch (Exception e) {
            // Capture qualquer outra exceção e re-lance como uma ResponseStatusException
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a solicitação: " + e.getMessage());
        }
    }

    public void downloadFile(String fileUrl, String destinationPath) {
        try {
            URL url = new URL(fileUrl);
            Path path = Path.of(destinationPath);

            // Cria diretório se não existir
            Files.createDirectories(path.getParent());

            // Faz o download do arquivo
            Files.copy(url.openStream(), path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Arquivo baixado com sucesso: " + destinationPath);
        } catch (IOException e) {
            System.err.println("Erro ao baixar o arquivo: " + e.getMessage());
        }
    }
}
