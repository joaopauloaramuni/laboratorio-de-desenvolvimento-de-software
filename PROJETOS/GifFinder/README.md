# Projeto GifFinder

## Descrição do Projeto
O projeto GifFinder é uma aplicação Spring Boot que permite buscar e salvar GIFs a partir de uma consulta de texto utilizando a API do Giphy. O projeto inclui uma estrutura simples e endpoints REST para facilitar a interação com os usuários.

## Configuração
O arquivo `application.properties` deve conter as seguintes propriedades:

```properties
spring.application.name=GifFinder
giphy.api.key=suaapikeyaqui
giphy.api.url=https://api.giphy.com/v1/gifs/search
```

## Estrutura do Projeto
```
application
└── GifFinderApplication.java
config
└── GiphyApiConfig.java
data
└── GifData.java
model
├── Images.java
└── Original.java
response
└── GifResponse.java
service
└── GifService.java
```

## Endpoints
```java
@RestController
@RequestMapping("/api")
public class GifController {

    @Autowired
    private GifService gifService;

    @GetMapping("/gifs")
    public GifResponse getGifs(@RequestParam String query, @RequestParam(defaultValue = "5") int limit) {
        return gifService.searchGif(query, limit);
    }
}
```

### Exemplos de Requisição
- [http://localhost:8080/api/gifs?query=Astronaut](http://localhost:8080/api/gifs?query=Astronaut)
- [http://localhost:8080/api/gifs?query=Developer](http://localhost:8080/api/gifs?query=Developer)
- [http://localhost:8080/api/gifs?query=Developer&limit=10](http://localhost:8080/api/gifs?query=Developer&limit=10)

### Parâmetros
- **query**: A string de busca que será utilizada para encontrar GIFs relacionados.
- **limit**: O número máximo de GIFs que devem ser retornados. O padrão é 5, mas pode ser modificado através da URL.

## Método para Buscar as GIFs
```java
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
```

## Método para Salvar as GIFs
```java
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
```

## Links Úteis
- [Site da Giphy](https://developers.giphy.com/)
- [Documentação Oficial da Giphy](https://developers.giphy.com/docs/)
- [Dashboard para Criação da API Key](https://developers.giphy.com/dashboard/)

## Licença
Este projeto é licenciado sob a Licença MIT.
