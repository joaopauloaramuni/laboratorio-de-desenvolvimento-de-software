# Projeto Encurtador de URLs

Este projeto é um serviço simples de encurtamento de URLs, desenvolvido em Java, Spring Boot e PostgreSQL.

### Documentação Swagger

Acesse a documentação interativa da API através do Swagger:

- **URL**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

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
spring.application.name=urlshortener
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.output.ansi.enabled=ALWAYS
spring.jpa.open-in-view=false

```

## Endpoints da API

### Registro de Usuário

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
- Exemplo de resposta:

  ```json
  {
    "createdAt": "2024-10-18T11:10:50.841085",
    "updatedAt": "2024-10-18T11:10:50.841085",
    "id": 1,
    "originalUrl": "http://exemplo.com",
    "shortUrl": "FtSXgy",
    "expirationDate": "2024-12-31T23:59:59"
  }
  ```

### Redirecionar para a URL Original

- **GET** `/api/{shortUrl}`
- Resposta: Redireciona para a URL original se a URL encurtada for válida e não expirada.
- Exemplo GET: http://localhost:8080/api/FtSXgy

Exemplo de Redirecionamento:

- **GET** /api/aPBBr0
Resposta: Redireciona para a URL original.

## Estrutura do Projeto

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── urlshortener
│   │   │               ├── application
│   │   │               │   └── UrlshortenerApplication.java
│   │   │               ├── controller
│   │   │               │   └── UrlController.java
│   │   │               ├── dto
│   │   │               │   └── UrlRequestDto.java
│   │   │               ├── model
│   │   │               │   ├── BaseEntity.java
│   │   │               │   └── Url.java
│   │   │               ├── repository
│   │   │               │   └── UrlRepository.java
│   │   │               └── service
│   │   │                   └── UrlService.java
│   ├── resources
│   │   └── application.properties
```

## DTO - `UrlRequestDTO`

Esta classe representa o objeto de transferência de dados para requisições de encurtamento de URL:

```java
package com.example.urlshortener.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UrlRequestDTO {

  @NotBlank(message = "URL cannot be blank")
  @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
  private String url;

  private LocalDateTime expirationDate;

  ...
}
```

## Modelo - `Url`

Esta classe representa a entidade da URL no banco de dados:

```java
package com.example.urlshortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Url extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String originalUrl;

  @Column(unique=true)
  private String shortUrl;

  private LocalDateTime expirationDate;

  ...

}
```

## Função `generateRandomString` do UrlService

### Descrição

A função `generateRandomString` cria uma string aleatória composta por letras maiúsculas, letras minúsculas e dígitos numéricos. O comprimento da string gerada é determinado pelo parâmetro `length` passado para a função.

## Executando a Aplicação

### Executando a Aplicação Localmente (Sem Docker)

Para rodar a aplicação localmente, o banco de dados PostgreSQL precisa estar rodando em sua máquina. Certifique-se de que o PostgreSQL esteja configurado com as seguintes credenciais no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=root
```

- Execute a aplicação com o comando Maven:

```bash
mvn spring-boot:run
```

### Executando com Docker e Docker Compose

Ao utilizar o Docker, a comunicação entre a aplicação e o banco de dados ocorre através do nome do serviço Docker, que no caso é `db`.

No arquivo `docker-compose.yml`, o serviço `db` é responsável pelo banco de dados PostgreSQL, e a aplicação se conecta a ele usando o nome `db`:

```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/urlshortener
```

Isso garante que a aplicação use o nome correto dentro da rede interna do Docker para conectar ao banco de dados.

#### Passos para executar com Docker Compose:

1. Construa as imagens e inicie os contêineres:

```bash
docker-compose up --build
```

2. Parar e remover os contêineres:

```bash
docker-compose down
```

Quando rodando via Docker Compose, a URL do banco de dados deve ser `jdbc:postgresql://db:5432/urlshortener` e não `localhost`, já que o Docker usa o nome do serviço para comunicação entre os contêineres.

### Comandos para Executar o Projeto

1. Limpar e construir o projeto com Maven:
   ```bash
   mvn clean install
   ```

2. Construir a imagem Docker:
   ```bash
   docker build -t url-shortener-app .
   ```

3. Executar o contêiner do PostgreSQL:
   ```bash
   docker run --name url-shortener-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=urlshortener -p 5432:5432 -d postgres:17
   ```

4. Executar o contêiner da aplicação com o banco de dados:
   ```bash
   docker run --name url-shortener-container --link url-shortener-db:db -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/urlshortener -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=root -p 8080:8080 -d url-shortener-app
   ```

5. Executar a aplicação e o banco de dados com `docker-compose`:
   ```bash
   docker-compose up
   ```

6. Parar e remover os contêineres com `docker-compose`:
   ```bash
   docker-compose down
   ```

## Licença

Este projeto está licenciado sob a MIT License.