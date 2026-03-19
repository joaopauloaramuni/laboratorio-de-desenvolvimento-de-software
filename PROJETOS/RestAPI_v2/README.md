# 🚀 RestAPI v2

Projeto simples desenvolvido com **Spring Boot** com o objetivo de demonstrar a evolução de uma API REST básica utilizando **separação em camadas (Controller + Service)**.

👉 Nesta versão, a lógica foi desacoplada do controller, introduzindo o conceito de **camada de serviço**.

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
- 🧠 Separação de responsabilidades (Controller + Service)  
- 🔗 Endpoint simples para teste  
- 🧪 Retorno de texto via HTTP  

---

# 📁 Estrutura do Projeto

```
RestAPI_v2
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
cd RestAPI_v2
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

# 🧠 Arquitetura (v2)

Nesta versão foi introduzido o conceito de **camadas**:

### 🔹 Controller
- Responsável por receber requisições HTTP  
- Define os endpoints da API  

### 🔹 Service
- Contém a lógica de negócio  
- Responsável por processar e retornar dados  

---

# 🔄 Fluxo da aplicação

```
Cliente → Controller → Service → Response
```

---

# 💡 Evolução da v1 → v2

| Versão | Característica |
|--------|--------------|
| v1 | Lógica direto no Controller |
| v2 | Separação em Service (melhor prática) |

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

# ⚠️ Observações (v2)

- A classe `Service` ainda não utiliza `@Service` (Spring Bean)  
- A injeção de dependência ainda não está sendo utilizada (`@Autowired`)  
- A instância do Service é criada manualmente (`new Service()`)  
- Este projeto é uma evolução didática  

👉 Próxima evolução (v3):

- Uso de `@Service`  
- Introdução de múltiplos endpoints  

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
