# Projeto Encurtador de URLs

Este projeto é um serviço simples de encurtamento de URLs, desenvolvido em Java, Spring Boot e PostgreSQL.

## Dockerfile

A aplicação está empacotada utilizando o seguinte Dockerfile:

```Dockerfile
FROM eclipse-temurin:17-jdk-alpine
LABEL authors="joaopauloaramuni"
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Configuração do Ambiente

A aplicação Spring está configurada para se conectar a um banco de dados PostgreSQL usando as seguintes propriedades:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.output.ansi.enabled=ALWAYS
```

## Endpoints da API

### Encurtar URL

- **POST** `/api/shorten`
- Corpo da Requisição:
  ```json
  {
    "url": "http://exemplo.com",
    "expirationDate": "2024-12-31T23:59:59"
  }
  ```
- Resposta: Retorna a URL encurtada.

### Redirecionar para a URL Original

- **GET** `/{shortUrl}`
- Redireciona para a URL original se a URL encurtada for válida e não expirada.

## DTO - `UrlRequestDto`

Esta classe representa o objeto de transferência de dados para requisições de encurtamento de URL:

```java
@Data
public class UrlRequestDto {
  @NotBlank(message = "A URL não pode estar em branco")
  @Pattern(regexp = "^(http|https)://.*$", message = "Formato de URL inválido")
  private String url;

  private LocalDateTime expirationDate;
}
```

## Modelo - `Url`

Esta classe representa a entidade da URL no banco de dados:

```java
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Url {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String originalUrl;

  @Column(unique=true)
  private String shortUrl;

  private LocalDateTime expirationDate;
}
```

## Serviço - `UrlService`

O `UrlService` cuida da lógica de negócio de gerar URLs encurtadas e recuperar as URLs originais:

```java
@Service
public class UrlService {
  public Url generateShortUrl(String originalUrl, LocalDateTime expirationDate) {
    String shortUrl = generateRandomString(6);
    Url url = new Url();
    url.setOriginalUrl((originalUrl));
    url.setShortUrl(shortUrl);
    url.setCreatedAt(LocalDateTime.now());
    url.setExpirationDate(expirationDate);

    return urlRepository.save(url);
  }

  public Optional<Url> getOriginalUrl(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl);
  }
}
```

## Comandos para Executar o Projeto

1. Limpar e construir o projeto com Maven:
   ```bash
   mvn clean install
   ```

2. Construir a imagem Docker:
   ```bash
   docker build -t url-shortener .
   ```

3. Executar o contêiner da aplicação:
   ```bash
   docker run -d -p 8080:8080 url-shortener
   ```

4. Executar o contêiner do PostgreSQL:
   ```bash
   docker run --name teste -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=urlshortener -p 5432:5432 -d postgres:17
   ```

5. Executar a aplicação com o banco de dados:
   ```bash
   docker run --name my-app2 --link teste:db -e SPRING_DATASOURCE_URL=jdbc:postgresql://teste:5432/urlshortener -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=root -p 8080:8080 -d test
   ```

## Licença

Este projeto está licenciado sob a Licença MIT.
