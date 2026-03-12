# 🚗 FIPE Rest API

Projeto desenvolvido com **Spring Boot** que consome a **API pública da Tabela FIPE** para consultar informações de veículos.

A aplicação disponibiliza endpoints REST que permitem consultar **marcas, modelos, anos e valores de veículos** utilizando dados da Tabela FIPE.

---

# 🛠 Tecnologias

* Java 17
* Spring Boot
* Maven
* REST API
* RestTemplate

---

# 🌐 API Utilizada

Este projeto consome a API pública:

https://parallelum.com.br/fipe/api/v1/carros

Documentação oficial:

https://deividfortuna.github.io/fipe/

---

# ⚡ Funcionalidades

A API permite realizar as seguintes consultas:

* 🚗 Listar **marcas de veículos**
* 🚙 Listar **modelos de uma marca**
* 📅 Listar **anos disponíveis de um modelo**
* 💰 Consultar **valor FIPE de um veículo**

Fluxo de consulta da aplicação:

Marcas → Modelos → Anos → Valor

---

# 🚀 Endpoints

## 📌 Listar marcas

GET /marcas

Exemplo:

http://localhost:8080/marcas

---

## 📌 Listar modelos de uma marca

GET /modelos/{marca}

Exemplo:

http://localhost:8080/modelos/59

---

## 📌 Listar anos de um modelo

GET /anos/{marca}/{modelo}

Exemplo:

http://localhost:8080/anos/59/5940

---

## 📌 Consultar valor FIPE

GET /valor/{marca}/{modelo}/{ano}

Exemplo:

http://localhost:8080/valor/59/5940/2014-3

---

# 📁 Estrutura do Projeto

```
Fipe_RestAPI
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
└── pom.xml
```

---

# ▶️ Como Executar

### 1️⃣ Clonar o repositório

```
git clone https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software.git
```

---

### 2️⃣ Entrar na pasta do projeto

```
cd laboratorio-de-desenvolvimento-de-software/PROJETOS/Fipe_RestAPI
```

---

### 3️⃣ Executar a aplicação

```
mvn spring-boot:run
```

---

### 4️⃣ Acessar a API

Exemplo no navegador ou Postman:

http://localhost:8080/marcas

---

# 📚 Links úteis

Spring Boot
- https://spring.io/projects/spring-boot

Maven
- https://maven.apache.org/

API FIPE
- https://deividfortuna.github.io/fipe/

GitHub FIPE
- https://github.com/deividfortuna/fipe

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
