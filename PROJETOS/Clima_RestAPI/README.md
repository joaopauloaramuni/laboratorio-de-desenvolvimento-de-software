
# ☁️ ClimaTempo Rest API

Projeto desenvolvido com **Spring Boot** que consome a **API do Climatempo** para obter informações meteorológicas.

A aplicação disponibiliza um endpoint REST simples que consulta os **dados meteorológicos atuais** utilizando a API do Climatempo.

---

# 🛠 Tecnologias

* Java 17
* Spring Boot
* Maven
* REST API
* RestTemplate

---

# 🌐 API Utilizada

Este projeto consome a API:

- https://advisor.climatempo.com.br/

Exemplo de endpoint utilizado:

- https://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale/BR

---

# ⚡ Funcionalidades

A API permite realizar:

* ☁️ Consulta de **dados meteorológicos**
* 🌎 Consulta de **informações climáticas do Brasil**
* 🔗 Consumo de **API externa utilizando RestTemplate**

---

# 🚀 Endpoint

## 📌 Consultar clima

GET /clima

Exemplo:

- http://localhost:8080/clima

---

# 📁 Estrutura do Projeto

```
ClimaTempo_RestAPI
│
└── src/main/java/com/example/RestAPI
    │
    ├── application
    │   └── RestApiApplication.java
    │
    ├── controller
    │   └── Controller.java
    │
    └── service
        └── Service.java
│
└── src/main/resources
    │
    └── application.properties
│
└── pom.xml
```

---

# ⚙️ Configuração

No arquivo **application.properties** configure o token da API:

```
climatempo.api.token=seu_token_aqui
climatempo.api.url=https://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale/BR
```

Você pode obter um token criando uma conta no site do Climatempo.

---

# ▶️ Como Executar

### 1️⃣ Clonar o repositório

```
git clone https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software.git
```

---

### 2️⃣ Entrar na pasta do projeto

```
cd laboratorio-de-desenvolvimento-de-software/PROJETOS/ClimaTempo_RestAPI
```

---

### 3️⃣ Executar a aplicação

```
mvn spring-boot:run
```

---

### 4️⃣ Acessar a API

Exemplo no navegador ou Postman:

- http://localhost:8080/clima

---

# 📚 Links úteis

Spring Boot  
- https://spring.io/projects/spring-boot

Maven  
- https://maven.apache.org/

Climatempo API  
- https://advisor.climatempo.com.br/

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
