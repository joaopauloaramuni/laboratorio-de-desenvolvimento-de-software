# Projeto RedditAPI

Este projeto implementa uma API para busca de discussões no Reddit, utilizando a API do Reddit para retornar informações relevantes sobre tópicos discutidos nas comunidades.

## Endpoint

### GET /reddit/discussions/{query}
Comunidade default: brdev

Exemplo: http://localhost:8080/reddit/discussions/engenharia%20de%20software

### GET /reddit/discussions/{comunity}/{query}
Exemplo: http://localhost:8080/reddit/discussions/devops/docker

- **query**: texto da busca, por exemplo: `engenharia%20de%20software`.
- **comunity**: comunidade do reddit, por exemplo: `devops`.

## Classe de Serviço

```java
package com.example.RedditAPI.service;

import com.example.RedditAPI.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RedditApiService {

    @Autowired
    private ApiConfig apiConfig;

    public String searchDiscussions(String query) {
        String searchUrl = apiConfig.getSearchUrl() + "brdev/search.json";
        RestTemplate restTemplate = new RestTemplate();
        String url = searchUrl + "?q=" + query + "&sort=relevance";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String searchDiscussionsByComunity(String community, String query) {
        String searchUrl = apiConfig.getSearchUrl() + community + "/search.json";
        RestTemplate restTemplate = new RestTemplate();
        String url = searchUrl + "?q=" + query + "&sort=relevance";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
```

## Links Úteis

- [Documentação da API do Reddit](https://www.reddit.com/dev/api)
- [API de busca do Reddit](https://www.reddit.com/dev/api#GET_search)
- [Subreddit brdev](https://www.reddit.com/r/brdev/)
- [Busca por 'engenharia%20de%20software'](https://www.reddit.com/r/brdev/search.json?q=engenharia%20de%20software)
- [Configurações de aplicativos Reddit](https://www.reddit.com/prefs/apps/)

## Licença

Este projeto está licenciado sob a MIT License.