# 🚀 RestAPI v1

Projeto simples desenvolvido com **Spring Boot** com o objetivo de demonstrar a criação de uma **API REST básica**.

👉 Ideal para iniciantes que estão dando os primeiros passos com:

- criação de endpoints REST
- estrutura de um projeto Spring Boot
- execução de aplicações Java com Maven

---

# 🛠 Tecnologias

- ☕ Java 17  
- 🌱 Spring Boot  
- 📦 Maven  
- 🔗 REST API  

---

# 📦 Dependências

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

# ⚡ Funcionalidades

- 🚀 API REST básica com Spring Boot  
- 🔗 Endpoint simples para teste  
- 🧪 Retorno de texto via HTTP  

---

# 📁 Estrutura do Projeto

```
RestAPI_v1
│
├── src
│   ├── main
│   │   ├── java/com/example/RestAPI
│   │   │   ├── RestApiApplication.java
│   │   │   └── Controller.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│
├── pom.xml
└── README.md
```

---

# 🚀 Como Executar

## 1️⃣ Clonar o projeto

```
git clone https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software.git
```

## 2️⃣ Entrar na pasta

```
cd RestAPI_v1
```

## 3️⃣ Executar a aplicação

```
mvn spring-boot:run
```

---

# 🌐 Endpoint

## 🔗 Teste da API

### GET /test

```
http://localhost:8080/test
```

📌 Resposta esperada:

```
Essa é minha primeira API REST
```

---

# 🧠 Como funciona

- A classe `RestApiApplication` inicializa a aplicação Spring Boot  
- A anotação `@SpringBootApplication` configura automaticamente o projeto  
- A anotação `@RestController` define um controller REST  
- A anotação `@GetMapping("/test")` expõe um endpoint HTTP GET  
- O método retorna uma String como resposta da API  

---

# 📚 Links úteis

## 🌱 Spring Boot

- https://spring.io/projects/spring-boot  
- https://docs.spring.io/spring-boot/docs/current/reference/html/  

---

## 🔗 REST API

- https://restfulapi.net/  

---

## 📦 Maven

- https://maven.apache.org/  

---

# 💡 Observações

- Projeto **básico e introdutório**  
- Não possui banco de dados  
- Não utiliza arquitetura em camadas (Controller, Service, Repository)  
- Ideal para primeiros testes com Spring Boot  

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
