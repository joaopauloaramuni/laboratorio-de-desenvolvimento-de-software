# Projeto MoviesWithTMDBApi with UI

Este projeto é uma aplicação que integra com a API do The Movie Database (TMDb) para fornecer informações sobre filmes e séries. Através de endpoints específicos, é possível acessar dados como filmes em exibição, filmes populares, filmes mais bem avaliados e muito mais. Agora, a aplicação também conta com uma interface gráfica com o usuário utilizando Thymeleaf.

## Captura de Tela:

| <img src="https://joaopauloaramuni.github.io/java-imgs/MoviesWithTMDBApiWithUI/imgs/home.png" alt="Home"/> |
| ![Home](https://joaopauloaramuni.github.io/java-imgs/MoviesWithTMDBApiWithUI/imgs/home.png.png) |
|:------------------------:|
|         Home            |

## Dependências

### pom.xml
```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.0</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
[Link para a dependência no Maven Repository](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp/4.12.0)

### application.properties
```properties
spring.application.name=MoviesWithTMDbAPI
tmdb.api.url=https://api.themoviedb.org/3/
tmdb.api.authorization=suaauthorizationkeyaqui
tmdb.api.key=suaapikeyaqui
```

## Estrutura do Projeto

```plaintext
application
└── MoviesWithTMDBApiApplication.java
config
└── TMDBConfig.java
controller
└── MovieController.java
exception
└── GlobalExceptionHandler.java
service
└── MovieService.java
```

## Endpoints

- **/ (Home)**: Exibe filmes populares
- **/now-playing**: Exibe filmes em exibição atualmente
- **/top-rated**: Exibe filmes mais bem avaliados
- **/upcoming**: Exibe filmes em estreia
- **/horror/{year}**: Exibe filmes de terror por ano
- **/tv-shows/{year}**: Exibe séries de TV por ano
- **/tv-shows/top-rated**: Exibe séries de TV mais bem avaliadas

## Método Principal

```java
private String executeRequest(String url) throws IOException {
    Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + tmdbConfig.getAuthorizationHeader())
            .build();

    try (Response response = okHttpClient.newCall(request).execute()) {
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }
}
```

## Paginação

O código foi adaptado para realizar a busca de dados em várias páginas. Para os métodos que buscam filmes ou séries, foram feitas requisições para até 10 páginas diferentes. Assim, o sistema retorna todos os dados das páginas solicitadas.

Exemplo para séries de TV mais bem avaliadas:

```java
public List<Map<String, Object>> getTopRatedTvShows() throws IOException {
    List<Map<String, Object>> allTvShows = new ArrayList<>();

    for (int page = 1; page <= 10; page++) {
        String url = tmdbConfig.getBaseUrl() + "tv/top_rated?language=pt-BR&page=" + page;
        String jsonResponse = executeRequest(url);
        List<Map<String, Object>> tvShows = parseMovies(jsonResponse);

        // Adiciona os filmes da página ao resultado final
        allTvShows.addAll(tvShows);
    }

    return allTvShows;
}
```

## Sobre a TMDB

Welcome to version 3 of The Movie Database (TMDB) API. This is where you will find the definitive list of currently available methods for our movie, tv, actor and image API.

## Documentação Oficial da API
[Documentação oficial da TMDB API](https://developer.themoviedb.org/reference/intro/getting-started)

## API Key e Token
[Obtenha sua chave de API](https://www.themoviedb.org/settings/api)

## Como Executar
1. Certifique-se de ter o Java JDK 17 e Maven instalados.
2. Clone o repositório.
3. Navegue até a pasta do projeto e execute:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse os endpoints usando um navegador ou uma ferramenta como Postman.

## Licença

Este projeto está licenciado sob a MIT License.
