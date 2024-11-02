# Projeto StackOverflowAPI

Este projeto implementa uma API para busca de perguntas no Stack Overflow utilizando a API pública do Stack Exchange. A aplicação é construída com Spring Boot, permitindo que os usuários realizem buscas por palavras-chave e filtrem os resultados conforme suas necessidades.

## Funcionalidades

- **Buscar perguntas**: Realiza buscas no Stack Overflow com base em palavras-chave fornecidas.
- **Filtrar por tags**: Permite filtrar os resultados utilizando tags específicas.
- **Ordenar resultados**: Ordena as perguntas por relevância ou data de criação.
- **Consultar detalhes**: Exibe informações detalhadas, como título, link, número de respostas e pontuação das perguntas.
- **Controle de Cota**: Monitora e gerencia o uso da cota diária da API para evitar excedentes.

## Estrutura do Projeto

/StackOverflowAPI
```
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── exemplo
│   │   │           └── StackOverflowAPI
│   │   │               ├── application
│   │   │               │   └── StackOverflowApiApplication.java
│   │   │               ├── config
│   │   │               │   └── ApiConfig.java
│   │   │               ├── controller
│   │   │               │   └── StackOverflowApiController.java
│   │   │               └── service
│   │   │                   └── StackOverflowApiService.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── static
│   └── test
│       └── java
│           └── com
│               └── exemplo
│                   └── StackOverflowAPI
│                       └── StackOverflowApiApplicationTests.java
│
├── pom.xml
├── README.md
```

## Endpoint

### GET /stackoverflow/search/{query}

Exemplo local: http://localhost:8080/stackoverflow/search/nullpointerexception%20java

Exemplo na API: https://api.stackexchange.com/2.3/search?order=desc&sort=activity&site=stackoverflow&intitle=nullpointerexception%20java

- **query**: texto da busca, por exemplo: `nullpointerexception%20java`.

## Método principal de serviço
```java
public String searchQuestions(String query) {
    String searchUrl = apiConfig.getSearchUrl() + "?order=desc&sort=activity&site=stackoverflow&intitle=" + query;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);
    return response.getBody();
}
```

## Links Úteis

- [Documentação da API do Stack Exchange](https://api.stackexchange.com/)
- [API de busca do Stack Overflow](https://api.stackexchange.com/docs/search)
- [Busca por 'nullpointerexception%20java'](https://api.stackexchange.com/2.3/search?order=desc&sort=activity&site=stackoverflow&intitle=nullpointerexception%20java)
- [Stack apps](https://stackapps.com/users/login)

## Licença

Este projeto está licenciado sob a MIT License.
