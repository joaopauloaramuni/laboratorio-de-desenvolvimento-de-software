# 🚀 NASA Images API - Spring Boot (com ApiConfig + @Value)

Projeto desenvolvido com **Spring Boot (Java 17)** para consumir a API pública da NASA (APOD - Astronomy Picture of the Day).

👉 Nesta versão utilizamos uma **classe de configuração (`ApiConfig`) com `@Value`**, seguindo boas práticas do Spring para externalização de configurações.

---

# 🛠 Tecnologias

- ☕ Java 17  
- 🌱 Spring Boot  
- 🔗 REST API  
- 🌌 NASA API  

---

# 📦 Dependências

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

---

# 📁 Estrutura do Projeto

```
NASA
│
├── src
│   ├── main
│   │   ├── java/com/example/NASA
│   │   │   ├── application
│   │   │   │   └── NasaApplication.java
│   │   │   ├── config
│   │   │   │   └── ApiConfig.java
│   │   │   ├── controller
│   │   │   │   └── NasaController.java
│   │   │   └── service
│   │   │       └── NasaService.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│
├── pom.xml
└── README.md
```

---

# ⚙️ application.properties

```
server.port=8080

nasa.api.key=dmpAa1d2DhevQmDPcfRbjOOedTk5TAcYdOL6KdfJ
nasa.api.url=https://api.nasa.gov/planetary/apod
```

---

# ⚙️ ApiConfig (com @Value)

```
package com.example.nasa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${nasa.api.key}")
    private String apiKey;

    @Value("${nasa.api.url}")
    private String apiUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
```

---

# 🚀 Classe Principal

```
package com.example.nasa.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class NasaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NasaApplication.class, args);
    }
}
```

---

# 🎮 Controller

```
package com.example.nasa.controller;

import com.example.nasa.service.NasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nasa")
public class NasaController {

    @Autowired
    private NasaService nasaService;

    @GetMapping("/imagem")
    public String getImagemDoDia() {
        return nasaService.buscarImagemDoDia();
    }
}
```

---

# 🧠 Service (usando ApiConfig)

```
package com.example.nasa.service;

import com.example.nasa.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class NasaService {

    @Autowired
    private ApiConfig apiConfig;

    public String buscarImagemDoDia() {
        String url = apiConfig.getApiUrl() + "?api_key=" + apiConfig.getApiKey();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }
}
```

---

# 🌐 Endpoint

## 🔗 Buscar imagem do dia

```
GET http://localhost:8080/nasa/imagem
```

📌 A API consome dados diretamente da NASA (APOD) e retorna um JSON contendo:

- 📝 **title** → título da imagem
- 📅 **date** → data da publicação
- 📖 **explanation** → descrição detalhada
- 🖼 **url** → URL da imagem
- 🧾 **copyright** → autor (quando disponível)
- 🔍 **hdurl** → imagem em alta resolução

---

# 🧪 Exemplo de resposta

```
{
"copyright": "Michael Seeley",
"date": "2026-03-19",
"explanation": "Even if you live with your head in the clouds, you won’t find a jellyfish like this one very often...",
"hdurl": "https://apod.nasa.gov/apod/image/2603/jellyfish.jpg",
"media_type": "image",
"service_version": "v1",
"title": "Launch Plume: SpaceX Jellyfish",
"url": "https://apod.nasa.gov/apod/image/2603/jellyfish_1024.jpg"
}
```

---

📡 Endpoint original da NASA utilizado:

```
https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY
```

---

# 🧠 Fluxo

```
Cliente → Controller → Service → ApiConfig → NASA API
```

---

# 💡 Observações

- Uso de `@Value` para externalizar configurações  
- `ApiConfig` centraliza acesso às propriedades  
- `@Autowired` injeta dependências automaticamente  
- Melhor prática que hardcode  

👉 Próxima evolução:

- Criar DTO para mapear JSON  
- Usar WebClient ao invés de RestTemplate  
- Tratar erros da API  

---

# 📚 Links úteis

- https://api.nasa.gov/  
- https://github.com/nasa/apod-api
- https://spring.io/projects/spring-boot  

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.