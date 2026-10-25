# 🌤️ Clima Rest API 2 (Open-Meteo)

Projeto desenvolvido com **Spring Boot** que consome as APIs gratuitas do **[Open-Meteo](https://open-meteo.com/)** para obter informações meteorológicas.

É a evolução do projeto **Clima_RestAPI**: troca a API do Climatempo pela Open-Meteo, que **não precisa de cadastro nem de token**, e passa a ler as URLs das APIs do `application.properties` com `@Value`.

A aplicação disponibiliza dois endpoints REST:

* a **temperatura atual de Belo Horizonte**;
* os **dados de localização** (latitude, longitude, estado, país...) de qualquer cidade.

---

# 🛠 Tecnologias

* Java 25
* Spring Boot 4
* Maven (com Maven Wrapper)
* REST API
* RestTemplate
* `@Value` (leitura do `application.properties`)

---

# 🌐 APIs Utilizadas

Este projeto consome duas APIs do Open-Meteo:

| API | Para que serve | Endereço |
| --- | --- | --- |
| **Forecast API** | Previsão do tempo a partir de latitude e longitude | https://open-meteo.com/en/docs |
| **Geocoding API** | Busca uma cidade pelo nome e devolve a localização | https://open-meteo.com/en/docs/geocoding-api |

URLs usadas no projeto:

- https://api.open-meteo.com/v1/forecast?latitude=-19.9167&longitude=-43.9345&current=temperature_2m
- https://geocoding-api.open-meteo.com/v1/search?name=curitiba&count=1&language=pt&format=json

> 🔓 As duas APIs são **abertas**: não é preciso criar conta nem configurar token para rodar o projeto.

---

# ⚡ Funcionalidades

A API permite realizar:

* 🌡️ Consulta da **temperatura atual de Belo Horizonte**
* 📍 Consulta dos **dados de localização de uma cidade** pelo nome
* 🔗 Consumo de **API externa utilizando RestTemplate**
* ⚙️ Leitura de configurações do **application.properties** com `@Value`

---

# 🚀 Endpoints

## 📌 Temperatura atual em Belo Horizonte

```
GET /climaBH
```

Exemplo:

- http://localhost:8080/climaBH

Exemplo de resposta (os valores mudam a cada consulta):

```json
{
  "latitude": -19.92,
  "longitude": -43.94,
  "timezone": "GMT",
  "elevation": 858.0,
  "current_units": {
    "time": "iso8601",
    "interval": "seconds",
    "temperature_2m": "°C"
  },
  "current": {
    "time": "2026-10-25T14:45",
    "interval": 900,
    "temperature_2m": 27.3
  }
}
```

O campo `current.temperature_2m` é a temperatura do ar a 2 metros do chão, em °C.

---

## 📌 Dados de localização de uma cidade

```
GET /clima/{cidade}
```

Exemplos:

- http://localhost:8080/clima/curitiba
- http://localhost:8080/clima/sao-paulo
- http://localhost:8080/clima/rio-de-janeiro

> 💡 Para cidades com nome composto, use **hífen** no lugar do espaço (`sao-paulo`). O `ClimaService` troca o `-` por espaço antes de chamar a API.

Exemplo de resposta (resumida):

```json
{
  "results": [
    {
      "name": "Curitiba",
      "latitude": -25.42778,
      "longitude": -49.27306,
      "country_code": "BR",
      "country": "Brasil",
      "admin1": "Paraná",
      "timezone": "America/Sao_Paulo",
      "population": 1718421
    }
  ]
}
```

> ⚠️ **Atenção:** este endpoint devolve a **localização** da cidade (vinda da Geocoding API), e **não** a temperatura. Para ter a temperatura de qualquer cidade, veja o [desafio](#-desafio-para-evoluir-o-projeto) no fim deste README.

---

# 📁 Estrutura do Projeto

```
📁 Clima_RestAPI_2
├── 📄 pom.xml                       ← dependências Maven (Spring Web MVC e testes)
├── ⚙️ mvnw / mvnw.cmd               ← Maven Wrapper: roda o Maven sem instalar (Linux-macOS / Windows)
├── 📁 .mvn/wrapper                  ← versão do Maven usada pelo wrapper
├── 📄 HELP.md                       ← links gerados pelo Spring Initializr
│
└── 📁 src
    ├── 📁 main
    │   ├── 📁 java/com/example/ClimaAPI
    │   │   ├── 🚀 ClimaApiApplication.java    ← classe main: sobe a aplicação
    │   │   ├── 📁 controller
    │   │   │   └── 🎮 Controller.java        ← endpoints /climaBH e /clima/{cidade}
    │   │   └── 📁 service
    │   │       └── 🧠 ClimaService.java      ← chama as APIs do Open-Meteo com RestTemplate
    │   │
    │   └── 📁 resources
    │       └── ⚙️ application.properties     ← URLs das APIs (lidas com @Value)
    │
    └── 📁 test/java/com/example/ClimaAPI
        └── 🧪 ClimaApiApplicationTests.java  ← verifica se o contexto do Spring sobe
```

---

# ⚙️ Configuração

No arquivo **application.properties** ficam as URLs das APIs:

```properties
spring.application.name=ClimaAPI
api.url.bh=https://api.open-meteo.com/v1/forecast?latitude=-19.9167&longitude=-43.9345&current=temperature_2m
api.url.cidade=https://geocoding-api.open-meteo.com/v1/search?name=
```

O `ClimaService` lê esses valores com `@Value`:

```java
@Value("${api.url.bh}")
private String apiUrlBH;

@Value("${api.url.cidade}")
private String apiUrlCidade;
```

> 💡 **Por que usar o `application.properties` em vez de constantes no código?** Para trocar a URL (outra cidade, outra API, ambiente de testes) sem recompilar o projeto. O jeito antigo, com constantes, ficou comentado no `ClimaService` para comparação.

Para consultar **outra cidade** no `/climaBH`, troque a latitude e a longitude em `api.url.bh`. Para pedir mais informações, acrescente variáveis em `current`, separadas por vírgula:

```properties
api.url.bh=https://api.open-meteo.com/v1/forecast?latitude=-19.9167&longitude=-43.9345&current=temperature_2m,relative_humidity_2m,wind_speed_10m
```

---

# ▶️ Como Executar

### ✅ Pré-requisitos

* **JDK 25** instalado (confira com `java -version`).
* O Maven **não** precisa estar instalado: o projeto usa o **Maven Wrapper** (`mvnw`).

---

### 1️⃣ Clonar o repositório

```
git clone https://github.com/joaopauloaramuni/desenvolvimento-e-integracao-de-aplicacoes-web.git
```

---

### 2️⃣ Entrar na pasta do projeto

```
cd desenvolvimento-e-integracao-de-aplicacoes-web/PROJETOS/SpringBoot/Clima_RestAPI_2
```

> O projeto também está nos repositórios `desenvolvimento-de-interfaces-web` (em `PROJETOS/SpringBoot/Clima_RestAPI_2`), `laboratorio-de-desenvolvimento-de-software` e `arquitetura-de-aplicacoes-web` (nesses dois, em `PROJETOS/Clima_RestAPI_2`).

---

### 3️⃣ Executar a aplicação

```
./mvnw spring-boot:run
```

No Windows:

```
mvnw.cmd spring-boot:run
```

Ou abra o projeto no IntelliJ / VS Code e execute a classe `ClimaApiApplication`.

Espere aparecer no terminal `Tomcat started on port 8080`.

---

### 4️⃣ Acessar a API

Exemplo no navegador, Postman ou Insomnia:

- http://localhost:8080/climaBH
- http://localhost:8080/clima/curitiba

Ou pelo terminal:

```
curl http://localhost:8080/climaBH
```

---

# 🔍 Como funciona

```
Navegador ──GET /climaBH──▶ Controller ──▶ ClimaService ──RestTemplate──▶ Open-Meteo
          ◀──── JSON ─────            ◀──────────────── JSON ──────────────
```

1. O **Controller** recebe a requisição e chama o **ClimaService**. O service chega pelo construtor (injeção de dependência).
2. O **ClimaService** monta a URL e usa o **RestTemplate** para fazer um `GET` na API externa.
3. A resposta da Open-Meteo volta como **texto JSON** (`String`) e é devolvida sem alterações para quem chamou.

---

# 🎯 Desafio para evoluir o projeto

Hoje o `/clima/{cidade}` só devolve a localização. Para devolver a **temperatura de qualquer cidade**, combine as duas APIs:

1. Chame a **Geocoding API** com o nome da cidade.
2. Pegue a `latitude` e a `longitude` do primeiro item de `results`.
3. Chame a **Forecast API** com essas coordenadas.
4. Devolva um JSON enxuto, por exemplo:

```json
{ "cidade": "Curitiba", "estado": "Paraná", "temperatura": 18.4, "unidade": "°C" }
```

Ideias extras:

* Criar **records/DTOs** para a resposta, em vez de devolver `String`.
* Devolver **404** quando a cidade não existir (`results` vazio).
* Trocar o `RestTemplate` pelo **RestClient**, a API mais nova do Spring para chamadas HTTP.

---

# 📚 Links úteis

Spring Boot  
- https://spring.io/projects/spring-boot

Maven Wrapper  
- https://maven.apache.org/wrapper/

Open-Meteo: Forecast API  
- https://open-meteo.com/en/docs

Open-Meteo: Geocoding API  
- https://open-meteo.com/en/docs/geocoding-api

RestTemplate / RestClient  
- https://docs.spring.io/spring-framework/reference/integration/rest-clients.html

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
