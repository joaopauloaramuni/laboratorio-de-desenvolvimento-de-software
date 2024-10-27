# MarvelExplorerRestAPI

## Propósito do Projeto
O projeto MarvelExplorerRestAPI é uma API REST que permite explorar dados sobre personagens, quadrinhos, criadores, eventos, séries e histórias do universo Marvel. A API integra-se à API oficial da Marvel, oferecendo endpoints que facilitam a recuperação de informações relevantes para desenvolvedores e entusiastas.

## Estrutura do Projeto
```
Pasta MarvelExplorerRestAPI
├── application
│   └── MarvelExplorerRestApiApplication.java
├── config
│   └── MarvelApiConfig.java
├── controller
│   └── MarvelController.java
├── data
│   └── Data.java
├── model
│   ├── Character.java
│   ├── Comic.java
│   ├── Creator.java
│   ├── Event.java
│   ├── OriginalIssue.java
│   ├── Serie.java
│   ├── SeriesNext.java
│   ├── SeriesPrevious.java
│   ├── Storie.java
│   ├── Thumbnail.java
│   └── Url.java
├── response
│   ├── CharactersResponse.java
│   ├── ComicsResponse.java
│   ├── EventsResponse.java
│   ├── SeriesResponse.java
│   └── StoriesResponse.java
└── service
    ├── MarvelApiService.java
    └── MarvelService.java
```

## Endpoints
- **GET** /marvel/characters
  - URL: http://localhost:8080/marvel/characters

- **GET** /marvel/comics
  - URL: http://localhost:8080/marvel/comics

- **GET** /marvel/events
  - URL: http://localhost:8080/marvel/events

- **GET** /marvel/series
  - URL: http://localhost:8080/marvel/series

- **GET** /marvel/stories
  - URL: http://localhost:8080/marvel/stories

- **GET** /marvel/characters/{id}
  - URL: http://localhost:8080/marvel/characters/1011334

- **GET** /marvel/comics/{id}
  - URL: http://localhost:8080/marvel/comics/82967

- **GET** /marvel/events/{id}
  - URL: http://localhost:8080/marvel/events/116

- **GET** /marvel/series/{id}
  - URL: http://localhost:8080/marvel/series/31445

- **GET** /marvel/stories/{id}
  - URL: http://localhost:8080/marvel/stories/7

## Configuração
O arquivo `application.properties` deve conter as seguintes configurações:
```
spring.application.name=MarvelExplorerRestAPI
marvel.api.public-key=suapublickeyaqui
marvel.api.private-key=suaprivatekeyaqui
marvel.api.base-url=https://gateway.marvel.com/v1/public/
```

Para criar sua public key e sua private key, acesse: [Marvel Developer Account](https://developer.marvel.com/account)

## Documentação
A documentação oficial da API da Marvel pode ser encontrada em: [Documentação da API da Marvel](https://developer.marvel.com/docs)

## Métodos do MarvelApiService
### `public String buildUrl(String endpoint)`
Este método constrói a URL completa para a chamada à API Marvel. Ele gera um timestamp e um hash baseado nas chaves pública e privada, conforme o seguinte processo:

1. Obtém o timestamp atual.
2. Gera um hash MD5 usando o timestamp, a chave privada e a chave pública.
3. Retorna a URL completa para a chamada à API com os parâmetros necessários.

### `private String generateHash(long timestamp)`
Este método gera um hash MD5 a partir do timestamp, da chave privada e da chave pública. O processo é realizado da seguinte forma:

1. Combina o timestamp, a chave privada e a chave pública em uma única string.
2. Calcula o hash MD5 da string combinada.
3. Retorna o hash como uma string hexadecimal.

## Licença

Este projeto está licenciado sob a MIT License.
