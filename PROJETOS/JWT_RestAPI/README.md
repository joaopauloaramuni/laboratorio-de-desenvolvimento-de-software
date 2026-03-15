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
| `/login` | POST | joao | 4321 | Realiza login e retorna o token JWT |
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
  "username": "joao",
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

# 🔑 Classe `JwtUtil`

A classe `JwtUtil` é responsável por **gerar e validar tokens JWT (JSON
Web Token)** utilizados na autenticação da API. Ela utiliza a biblioteca
**JJWT (Java JWT)** para criar, assinar e interpretar os tokens.

----

## 📦 Importações utilizadas

``` java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
```

Essas classes permitem:

-   Criar tokens JWT
-   Assinar tokens com uma chave secreta
-   Decodificar tokens recebidos
-   Extrair informações do token

----

## 🔐 Chave secreta (SECRET_KEY)

``` java
private static final SecretKey SECRET_KEY = generateSecretKey();
```

A chave secreta é usada para **assinar digitalmente o token**,
garantindo que o token não foi alterado e que apenas o servidor consiga
gerar tokens válidos.

A chave é criada pelo método:

``` java
private static SecretKey generateSecretKey() {
    SecretKey key = Jwts.SIG.HS512.key().build();
    return key;
}
```

Nesse caso, a biblioteca gera automaticamente uma chave segura para o
algoritmo **HS512**.

----

## 🔑 Representação da chave em Base64

``` java
private static final String SECRET_STRING = getSecretString();
```

Esse método converte a chave secreta para **Base64**, permitindo
armazenar ou reutilizar a chave posteriormente.

``` java
private static String getSecretString() {
    String secretString = Encoders.BASE64.encode(SECRET_KEY.getEncoded());
    System.out.println("Secret Key: " + secretString);
    return secretString;
}
```

A chave é exibida no console apenas para **fins de teste**.

Em aplicações reais, a chave normalmente é armazenada em:

-   variáveis de ambiente
-   arquivos `.properties`
-   serviços de gerenciamento de segredos

----

## ⏳ Tempo de expiração do token

``` java
private static final long EXPIRATION_TIME = 864_000_000;
```

Define o tempo de validade do token.

    864000000 ms = 10 dias

Após esse período o token **expira** e o usuário precisa realizar login
novamente.

----

## 🪪 Geração do Token

``` java
public static String generateToken(String username) {
    String token = Jwts.builder()
            .subject(username)
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact();

    System.out.println("Token: " + token);
    return token;
}
```

Esse método:

1.  Recebe o **username**
2.  Cria um token JWT
3.  Define o usuário como **subject**
4.  Define a **data de expiração**
5.  Assina o token com a chave secreta
6.  Retorna o token gerado

Um token JWT possui três partes:

    HEADER.PAYLOAD.SIGNATURE

----

## 🔎 Extração do Username do Token

``` java
public static String extractUsername(String token) {
    SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));

    return Jwts.parser()
            .verifyWith(secret)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}
```

Esse método:

1.  Decodifica a chave secreta
2.  Verifica se o token é válido
3.  Lê o conteúdo do token
4.  Retorna o **username** armazenado no payload

----

## 🧠 Fluxo de funcionamento do JWT

    Usuário faz login
          ↓
    Servidor valida username e senha
          ↓
    Servidor gera token JWT
          ↓
    Cliente recebe o token
          ↓
    Cliente envia o token nas próximas requisições
          ↓
    Servidor valida o token
          ↓
    Servidor libera ou bloqueia o acesso

----

## ⚠️ Observação importante

Nesta implementação a **chave secreta é gerada sempre que a aplicação
inicia**.

Isso significa que tokens gerados antes de reiniciar o servidor deixam
de funcionar.

Em aplicações reais, a chave normalmente é **fixa** e armazenada em:

-   `application.properties`
-   variáveis de ambiente
-   serviços de secret management

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
