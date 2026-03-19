# 🚀 NASA Images API - Spring Boot

Projeto desenvolvido com **Spring Boot (Java 17)** para consumir a API pública da NASA (**APOD - Astronomy Picture of the Day**).

A aplicação permite:

- 🌌 Buscar a imagem do dia da NASA
- 📄 Retornar os dados em formato JSON (DTO)
- 🖼 Exibir a imagem diretamente no navegador (HTML)

---

# 🛠 Tecnologias

- ☕ Java 17  
- 🌱 Spring Boot  
- 🔗 REST API  
- 🌌 NASA API (APOD)  
- 🔄 RestTemplate  
- 📦 DTO (Data Transfer Object)  

---

# 🖼️ Capturas de Tela

| ![Imagem do dia](https://joaopauloaramuni.github.io/java-imgs/NASA/imgs/jellyfish_1024.jpg) |
|:-------------------------------------------------------------------------------------------:|
|                                        Imagem do dia                                        |

---

# 📦 Dependências

```
spring-boot-starter-web
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
│   │   │   ├── service
│   │   │   │   └── NasaService.java
│   │   │   └── dtos
│   │   │       └── NasaApodDTO.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│
├── pom.xml
└── README.md
```

---

# 🚀 Endpoints

## 📡 Buscar imagem (JSON)

```
GET http://localhost:8080/nasa/imagem
```

📌 Retorna um JSON com:

- 📝 título  
- 📅 data  
- 📖 descrição  
- 🖼 URL da imagem  
- 🔍 URL em alta resolução  
- 🎥 tipo de mídia (image ou video)  

---

## 🖼 Visualizar imagem no navegador (HTML)

```
GET http://localhost:8080/nasa/imagem/html
```

📌 Retorna uma página HTML contendo:

- Título da imagem  
- Imagem renderizada  
- Descrição  

---

# 🌌 Exemplo de uso da API da NASA

## 🔗 Endpoint original

```
https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY
```

---

## 🧪 Exemplo de resposta

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

# 🔄 Fluxo da Aplicação

```
Cliente → Controller → Service → NASA API
                      ↓
                   DTO (Java)
                      ↓
                 Retorno JSON/HTML
```

---

# 🧠 Sobre o uso de DTO

A aplicação utiliza um **DTO (Data Transfer Object)** para mapear o JSON da NASA para um objeto Java.

✔ Vantagens:

- Tipagem forte  
- Código mais limpo  
- Melhor manutenção  
- Evita parsing manual  

---

# ⚠️ Observações importantes

- A API da NASA pode retornar:
  - `"image"` → imagem  
  - `"video"` → vídeo (ex: YouTube)  

- Nem todos os campos são obrigatórios:
  - `copyright` pode ser `null`
  - `hdurl` pode não existir  

---

# ▶️ Como executar

## 1️⃣ Clonar o projeto

```
git clone https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software.git
```

## 2️⃣ Entrar na pasta

```
cd NASA
```

## 3️⃣ Executar

```
mvn spring-boot:run
```

---

# 📚 Links úteis

- https://api.nasa.gov/
- https://api.nasa.gov/planetary/apod
- https://spring.io/projects/spring-boot
- https://docs.spring.io/spring-boot/docs/current/reference/html/
- https://github.com/nasa/apod-api

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.