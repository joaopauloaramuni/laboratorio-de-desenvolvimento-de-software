# 🔐 JWT Rest API

Projeto desenvolvido com **Spring Boot** demonstrando como implementar
**autenticação e autorização utilizando JSON Web Token (JWT)**.

A aplicação expõe endpoints REST que permitem:

-   gerar um **token JWT**
-   **extrair informações do token**
-   acessar rotas protegidas com **Spring Security**

----

# 🛠 Tecnologias

-   Java 17
-   Spring Boot
-   Spring Security
-   Maven
-   JSON Web Token (JWT)
-   Rest API

----

# 📦 Dependências Principais

```xml
    <dependencies>
        <!-- Spring Boot Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- Spring Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- JWT Library -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.3</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.12.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-gson</artifactId>
            <version>0.12.3</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
```

----

# ⚡ Funcionalidades

-   🔑 Gerar token JWT
-   🔍 Extrair username do token
-   👤 Endpoint protegido para usuário
-   👑 Endpoint restrito para administrador

----

# 👥 Usuários de teste

| Usuário | Senha | Role |
|---------|-------|------|
| joao | 4321 | USER |
| admin | 1234 | ADMIN |

----

# 🚀 Endpoints

## Login

### POST /login

```json
{ "username": "joao", "password": "4321" }
```

Retorna um **token JWT**.

----

## Extrair Username

### GET /username/{token}

Exemplo:

```
http://localhost:8080/username/{token}
```

----

## Endpoint de usuário

### GET /user

Requer **Basic Auth**.

Resposta:

```
User: joao
```

----

## Endpoint de administrador

### GET /admin

Apenas usuários com **role ADMIN** podem acessar.

Resposta:

```
Admin: admin
```

----

## 📡 Requests da API

A coleção de requisições utilizada para testar a API foi criada no **Insomnia** e está disponível no arquivo:

`requests/Insomnia_2024-10-22.json`

Esse arquivo pode ser importado diretamente no Insomnia para facilitar os testes dos endpoints.

----

## 🔐 Autenticação

A API utiliza **Basic Authentication** para gerar o token JWT e acessar os endpoints protegidos.

| Endpoint | Método | Usuário | Senha | Descrição |
|--------|--------|--------|--------|--------|
| `/login` | POST | joaopaulo | 4321 | Realiza login e retorna o token JWT |
| `/user` | GET | joao | 4321 | Endpoint acessível por usuários com role `USER` |
| `/admin` | GET | admin | 1234 | Endpoint acessível apenas por usuários com role `ADMIN` |
| `/username/{token}` | GET | — | — | Retorna o username contido no token JWT |

----

## 🧪 Exemplos de Requisições

### Login (gerar token)

### POST
`http://localhost:8080/login`

### Body (JSON)

```json
{
  "username": "joaopaulo",
  "password": "4321"
}
```

----

### Acessar endpoint de usuário

### GET
`http://localhost:8080/user`

Autenticação:

- Username: `joao`
- Password: `4321`

----

### Acessar endpoint de administrador

### GET
`http://localhost:8080/admin`

Autenticação:

- Username: `admin`
- Password: `1234`

----

### Obter username a partir do token

### GET
`http://localhost:8080/username/{jwt_token}`

Exemplo:
`http://localhost:8080/username/eyJhbGciOiJIUzUxMiJ9...`

----

# 📁 Estrutura do Projeto

```
JWT_RestAPI
│
├── requests
│   └── Insomnia_2024-10-22.json
│
├── src
│   ├── main
│   │   ├── java/com/example/JWT_RestAPI
│   │   │   ├── application
│   │   │   │   └── JwtRestApiApplication.java
│   │   │   ├── config
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller
│   │   │   │   └── AuthController.java
│   │   │   ├── model
│   │   │   │   └── LoginRequest.java
│   │   │   ├── security
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── JwtUtil_Old.java
│   │   │   └── service
│   │   │       └── AuthService.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java/com/example/JWT_RestAPI
│           └── JwtRestApiApplicationTests.java
│
├── HELP.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

----

# ▶️ Como Executar

## 1️⃣ Clonar o repositório

```bash
git clone https://github.com/seu-usuario/JWT_RestAPI.git
```

## 2️⃣ Entrar na pasta

```bash
cd JWT_RestAPI
```

## 3️⃣ Executar a aplicação

```bash
mvn spring-boot:run
```

----

# 📚 Links úteis

Spring Boot
- https://spring.io/projects/spring-boot

Spring Security
- https://spring.io/projects/spring-security

JWT
- https://jwt.io/

JJWT Library
- https://github.com/jwtk/jjwt

----

# 🛡 Licença

Este projeto está sob a licença **MIT**.
