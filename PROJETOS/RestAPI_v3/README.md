# 🚀 RestAPI v3

Projeto desenvolvido com **Spring Boot** demonstrando a evolução de uma API REST básica com **separação em camadas** e introdução ao uso de **anotações do Spring (@Service)**.

👉 Nesta versão, além da separação entre **Controller** e **Service**, foram adicionados:

- múltiplos endpoints
- organização mais próxima de uma arquitetura real
- uso de anotação do Spring na camada de serviço

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

- 🚀 API REST com Spring Boot  
- 🧠 Separação em camadas (Controller + Service)  
- 🔗 Múltiplos endpoints  
- 🏷 Uso da anotação `@Service`  
- 🧪 Retorno de mensagens via HTTP  

---

# 📁 Estrutura do Projeto

```
RestAPI_v3
│
├── src
│   ├── main
│   │   ├── java/com/example/RestAPI
│   │   │   ├── RestApiApplication.java
│   │   │   ├── controller
│   │   │   │   └── Controller.java
│   │   │   └── service
│   │   │       └── Service.java
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
cd RestAPI_v3
```

## 3️⃣ Executar a aplicação

```
mvn spring-boot:run
```

---

# 🌐 Endpoints

## 🔗 Teste principal

### GET /test

```
http://localhost:8080/test
```

📌 Resposta:

```
Essa é minha primeira API REST
```

---

## 🔗 Endpoint de exemplo

### GET /exemplo

```
http://localhost:8080/exemplo
```

📌 Resposta:

```
Mensagem de exemplo
```

---

# 🧠 Arquitetura (v3)

### 🔹 Controller
- Responsável por receber requisições HTTP  
- Define os endpoints da API  
- Delega a lógica para o Service  

### 🔹 Service
- Contém a lógica de negócio  
- Centraliza as regras da aplicação  
- Marcado com `@Service` (bean gerenciado pelo Spring)  

---

# 🔄 Fluxo da aplicação

```
Cliente → Controller → Service → Response
```

---

# 📈 Evolução das versões

| Versão | Evolução |
|--------|--------|
| v1 | Endpoint direto no Controller |
| v2 | Introdução da camada Service |
| v3 | Uso de @Service + múltiplos endpoints |

---

# ⚠️ Observações (v3)

- A classe `Service` já utiliza `@Service` (Spring Bean)  
- A injeção de dependência ainda não está sendo utilizada (`@Autowired`)  
- A instância do Service ainda está sendo criada manualmente (`new Service()`)  
- Este projeto continua sendo uma evolução didática  

👉 Próxima evolução (v4):

- Uso de injeção de dependência com `@Autowired` ou via construtor  
- Remoção da instanciação manual (`new Service()`)  

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

# 🛡 Licença

Este projeto está sob a licença **MIT**.
