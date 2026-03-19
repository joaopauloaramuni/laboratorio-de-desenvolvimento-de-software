# 🌐 View Rest API

Projeto simples desenvolvido com **Spring Boot** com o objetivo de demonstrar como servir um arquivo HTML estático (`index.html`) sem o uso de template engines como **Thymeleaf**.

👉 Este projeto é ideal para iniciantes entenderem:

- como funciona uma **REST API básica**
- como o **Spring Boot serve arquivos estáticos**
- como acessar páginas HTML via `resources/static`

---

# 🛠 Tecnologias

- ☕ Java 17  
- 🌱 Spring Boot  
- 📦 Maven  
- 🌐 HTML, CSS e JavaScript  
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

- 🌐 Servir página HTML estática (`index.html`)
- 🔗 Endpoint REST simples (`/exemplo`)
- 📁 Uso da pasta `resources/static`
- 🚀 Projeto introdutório sem template engine

---

# 📁 Estrutura do Projeto

```
View_RestAPI
│
├── src
│   ├── main
│   │   ├── java/com/example/RestAPI
│   │   │   ├── application
│   │   │   │   └── RestApiApplication.java
│   │   │   ├── controller
│   │   │   │   └── Controller.java
│   │   │   └── service
│   │   │       └── Service.java
│   │   │
│   │   └── resources
│   │       └── static
│   │           ├── index.html
│   │           ├── css/
│   │           ├── js/
│   │           └── images/
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
cd View_RestAPI
```

## 3️⃣ Executar a aplicação

```
mvn spring-boot:run
```

---

# 🌐 Acessos

## 📄 Página HTML

Acesse no navegador:

```
http://localhost:8080/
```

👉 O Spring Boot automaticamente serve arquivos dentro de:

```
src/main/resources/static
```

---

## 🔗 Endpoint REST

### GET /exemplo

```
http://localhost:8080/exemplo
```

📌 Retorna um HTML com link para a página principal.

---

# 🧠 Como funciona

- O Spring Boot detecta automaticamente arquivos em `resources/static`
- O arquivo `index.html` é carregado automaticamente ao acessar `/`
- Não é necessário usar **Thymeleaf** ou qualquer template engine
- O controller expõe um endpoint REST simples retornando HTML como String

---

# 📚 Links úteis

## 🌱 Spring Boot

- https://spring.io/projects/spring-boot  
- https://docs.spring.io/spring-boot/docs/current/reference/html/  

---

## 📁 Arquivos estáticos no Spring

- https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.static-content  

---

## 📦 Maven

- https://maven.apache.org/  

---

# 💡 Observações

- Este projeto é **introdutório**  
- Não utiliza template engine (como Thymeleaf)  
- Ideal para entender o funcionamento básico do Spring Boot  

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
