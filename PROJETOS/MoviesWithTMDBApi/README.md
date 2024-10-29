# MoviesWithTMDBApi

Este projeto é uma aplicação que integra com a API do The Movie Database (TMDb) para fornecer informações sobre filmes e séries. Através de endpoints específicos, é possível acessar dados como filmes em exibição, filmes populares, filmes mais bem avaliados e muito mais.

## Dependências

### pom.xml
```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.0</version>
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

- **GET /movies/now-playing**  
  Exemplo: [http://localhost:8080/movies/now-playing](http://localhost:8080/movies/now-playing)

- **GET /movies/popular**  
  Exemplo: [http://localhost:8080/movies/popular](http://localhost:8080/movies/popular)

- **GET /movies/top-rated-movies**  
  Exemplo: [http://localhost:8080/movies/top-rated-movies](http://localhost:8080/movies/top-rated-movies)

- **GET /movies/upcoming**  
  Exemplo: [http://localhost:8080/movies/upcoming](http://localhost:8080/movies/upcoming)

- **GET /movies/horrorMoviesByYear**  
  Exemplo: [http://localhost:8080/movies/horrorMoviesByYear?year=2024](http://localhost:8080/movies/horrorMoviesByYear?year=2024)  
  `@RequestParam String year`

- **GET /movies/tvShowsByYear**  
  Exemplo: [http://localhost:8080/movies/tvShowsByYear?year=2024](http://localhost:8080/movies/tvShowsByYear?year=2024)  
  `@RequestParam String year`

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
