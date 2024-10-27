package com.example.YouTubeVideoDownloader.dto;

public class VideoRequestDTO {
    private String url;
    private String path;

    // Getters e Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

// Melhoria para esse codigo: Usar o metodo record. Ele cria automaticamente todos os getters, setter, construtores, etc, para uma classe DTO !!

// public record VideoRequestDTO(String url, String path) { // Esse é o necessario. Abaixo disso é complemento, nesse caso estou fazendo uma verificação dos campos.
//     public VideoRequestDTO {
//         if (url == null || url.isBlank()) {
//             throw new IllegalArgumentException("URL não pode ser nula ou vazia");
//         }
//         if (path == null || path.isBlank()) {
//             throw new IllegalArgumentException("Path não pode ser nulo ou vazio");
//         }
//     }
// }



