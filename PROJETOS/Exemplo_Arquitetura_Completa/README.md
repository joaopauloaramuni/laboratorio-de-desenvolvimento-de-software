# 🔐 JavaDoc JWT Rest API

[![JavaDoc](https://img.shields.io/badge/JavaDoc-Documentação-blue?style=for-the-badge&logo=java)](#-geração-de-javadoc)

Projeto desenvolvido com **Spring Boot** com o objetivo de demonstrar, de forma prática, a implementação de **autenticação e autorização utilizando JSON Web Token (JWT)**, além da geração de documentação automática com **JavaDoc**.

A aplicação expõe endpoints REST que permitem:

- 🔑 gerar um **token JWT** a partir de credenciais válidas  
- 🔍 extrair informações contidas no token  
- 🔐 acessar rotas protegidas utilizando **Spring Security**  
- 📘 visualizar a documentação do código gerada automaticamente via **JavaDoc**

O projeto serve como material didático para compreensão dos conceitos de segurança em APIs e documentação de código em aplicações Java.

----

> [!NOTE]
> Além disso, este projeto também foi utilizado como base para demonstrar, de forma didática, a aplicação de uma **arquitetura em camadas**. Diferente de uma versão anterior mais simplificada, aqui a estrutura foi reorganizada em múltiplos pacotes (como `controller`, `service`, `repository`, `security`, `dto`, entre outros), evidenciando claramente a separação de responsabilidades entre as partes do sistema. Essa abordagem permite aos alunos compreenderem não apenas o funcionamento do JWT e do Spring Security, mas também como estruturar projetos reais de forma organizada, escalável e alinhada com boas práticas de engenharia de software.

----

# 📦 Estrutura de Pacotes e Responsabilidades

Este projeto segue uma arquitetura em camadas com foco em **separação de responsabilidades**, **baixo acoplamento** e **alta coesão**.  
Além disso, diferencia claramente **camadas de fluxo principal** e **camadas de suporte (cross-cutting)**.

----

## 🏗️ Visão Geral da Arquitetura

**Fluxo principal:**

`Cliente → Controller → Service → Repository → Banco de Dados`

**Camadas transversais (atuam em todo o fluxo):**

`Security | DTO | Exception`

----

### 📁 application

Camada responsável por inicializar a aplicação.

- Ponto de entrada do sistema
- Inicialização do contexto do Spring

📌 **Responsabilidade:** bootstrap da aplicação  

✅ Deve inicializar a aplicação  
✅ Deve configurar o contexto base  
✅ Deve manter simplicidade  

🚫 Não deve conter regra de negócio  
🚫 Não deve conter lógica de infraestrutura complexa  
🚫 Não deve depender de outras camadas  

----

### ⚙️ config

Camada de configuração da aplicação.

- Centraliza configurações técnicas
- Define comportamento de frameworks

📌 **Responsabilidade:** infraestrutura e configuração do sistema  

✅ Deve centralizar configurações  
✅ Deve definir beans e integrações  
✅ Deve configurar frameworks  

🚫 Não deve conter regra de negócio  
🚫 Não deve conter lógica de domínio  
🚫 Não deve acessar diretamente controller/service  

----

### 🎮 controller

Camada de interface com o cliente.

- Recebe requisições externas (HTTP)
- Converte entrada em objetos da aplicação
- Delega processamento para a camada de serviço

📌 **Responsabilidade:** orquestração da entrada e saída  

✅ Deve receber requisições HTTP  
✅ Deve delegar para o service  
✅ Deve retornar respostas ao cliente  

🚫 Não deve conter regra de negócio  
🚫 Não deve acessar diretamente o repository  
🚫 Não deve implementar validações complexas  

----

### 🧠 service

Camada de regras de negócio.

- Implementa a lógica do sistema
- Orquestra chamadas entre diferentes componentes
- Garante consistência das operações

📌 **Responsabilidade:** processamento e decisões do domínio  

✅ Deve implementar regras de negócio  
✅ Deve orquestrar operações  
✅ Deve garantir consistência  

🚫 Não deve conhecer detalhes de HTTP  
🚫 Não deve depender de controller  
🚫 Não deve expor entidades diretamente  

----

### 🗄️ repository

Camada de acesso a dados.

- Abstrai a comunicação com o banco
- Executa operações de persistência

📌 **Responsabilidade:** isolamento da camada de dados  

✅ Deve acessar o banco de dados  
✅ Deve executar CRUD  
✅ Deve abstrair persistência  

🚫 Não deve conter regra de negócio  
🚫 Não deve implementar lógica de aplicação  
🚫 Não deve ser acessado pelo controller  

----

### 🧩 model

Representação do domínio da aplicação.

- Define entidades e estruturas persistidas
- Representa o estado do sistema

📌 **Responsabilidade:** modelagem dos dados do domínio  

✅ Deve representar entidades  
✅ Deve definir atributos do domínio  
✅ Pode conter validações simples  

🚫 Não deve conter lógica de infraestrutura  
🚫 Não deve depender de controller/service  
🚫 Evitar lógica complexa  

----

### 📦 dto

Objetos de transferência de dados (**Data Transfer Objects**).

- Transportam dados entre camadas
- Definem contratos de entrada e saída da API

📌 **Responsabilidade:** desacoplamento entre API e domínio  

📌 **Observação:** não fazem parte do fluxo, atuam entre as camadas  

✅ Deve transportar dados  
✅ Deve definir contratos da API  
✅ Deve proteger o model  

🚫 Não deve conter regra de negócio  
🚫 Não deve acessar banco  
🚫 Não deve conter lógica complexa  

----

### ⚠️ exception

Camada de tratamento de erros.

- Centraliza captura e tratamento de exceções
- Padroniza respostas de erro

📌 **Responsabilidade:** gerenciamento de falhas  

📌 **Observação:** atua de forma transversal  

✅ Deve tratar exceções  
✅ Deve padronizar erros  
✅ Deve centralizar tratamento  

🚫 Não deve conter regra de negócio  
🚫 Não deve depender de camadas específicas  
🚫 Não deve conter lógica de domínio  

----

### 🔐 security

Camada de segurança da aplicação.

- Controla autenticação e autorização
- Protege endpoints
- Gerencia identidade do usuário

📌 **Responsabilidade:** controle de acesso e proteção  

📌 **Observação:** atua de forma transversal  

✅ Deve autenticar usuários  
✅ Deve autorizar acessos  
✅ Deve proteger endpoints  

🚫 Não deve conter regra de negócio da aplicação  
🚫 Não deve depender de controller/service  
🚫 Não deve conter lógica de domínio  

----

### 🧠 Tipos de Camadas na Arquitetura

**Camadas de fluxo (core):**
- Controller
- Service
- Repository

**Camadas de suporte (cross-cutting):**
- Security
- DTO
- Exception
- Config

----

### ✅ Benefícios da Arquitetura

- Separação clara de responsabilidades  
- Baixo acoplamento entre camadas  
- Facilidade de manutenção  
- Melhor testabilidade  
- Escalabilidade  
- Código mais organizado e previsível

----

# 📘 Geração de JavaDoc

Este projeto também demonstra como gerar documentação automática do código utilizando **JavaDoc**.

A documentação gerada descreve classes, métodos e parâmetros da aplicação, facilitando o entendimento e manutenção do código.

📘 Acesse um exemplo da documentação gerada:

[📘 Acessar JavaDoc - AuthService](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/blob/main/PROJETOS/JavaDoc_JWT_RestAPI/docs/com/example/JWT_RestAPI/service/AuthService.html)

---

## ⚙️ Como gerar o JavaDoc

### 🔹 Usando Maven

```bash
mvn javadoc:javadoc
```

Após a execução, a documentação será gerada em:

```
target/site/apidocs/index.html
```

---

### 🔹 Usando linha de comando (Java puro)

```bash
javadoc -d docs -sourcepath src/main/java -subpackages com.example
```

Isso criará a documentação na pasta:

```
/docs
```

---

## 📂 Estrutura da documentação gerada

Após gerar o JavaDoc, você verá arquivos como:

```
docs/
├── index.html
├── allclasses-index.html
├── allpackages-index.html
├── overview-summary.html
├── index-all.html
└── com/example/...
```

---

## 🧾 Exemplo de HTML gerado pelo JavaDoc

```
<!DOCTYPE html>
<html>
<head>
    <title>AuthService</title>
</head>
<body>

<h1>Class AuthService</h1>

<p>
Serviço responsável pela autenticação de usuários e geração de tokens JWT.
</p>

<h2>Method Summary</h2>

<ul>
    <li>
        <b>generateToken(String username)</b><br>
        Gera um token JWT para o usuário informado.
    </li>
    <li>
        <b>validateToken(String token)</b><br>
        Valida o token JWT recebido.
    </li>
</ul>

<h2>Method Detail</h2>

<h3>generateToken</h3>
<p>
Recebe um username e retorna um token JWT válido.
</p>

</body>
</html>
```

---

## 🧠 O que o JavaDoc documenta

- 📦 Pacotes  
- 🏗 Classes  
- 🔧 Métodos  
- 📥 Parâmetros  
- 📤 Retornos  
- 💬 Comentários do código  

---

## 💡 Exemplo de comentário JavaDoc

```
/**
 * Gera um token JWT para um usuário.
 *
 * @param username Nome do usuário
 * @return Token JWT gerado
 */
```

---

## ⚠️ Observação importante

Nesta implementação, a qualidade da documentação depende diretamente dos comentários no código.

Sem comentários JavaDoc, a documentação gerada será limitada.

---

## 🚀 Objetivo no projeto

- Demonstrar geração automática de documentação  
- Facilitar o entendimento do código por outros desenvolvedores  
- Servir como material de apoio didático

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
		<!-- Spring doc -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.3.0</version>
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
JavaDoc_JWT_RestAPI
│
├── docs
│   ├── index.html
│   ├── allclasses-index.html
│   ├── allpackages-index.html
│   └── com/example/JWT_RestAPI/...
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

## Spring

Spring Boot  
- https://spring.io/projects/spring-boot

Spring Boot Documentation  
- https://docs.spring.io/spring-boot/docs/current/reference/html/

Spring Security  
- https://spring.io/projects/spring-security

Spring Security JWT API  
- https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html

----

## JWT

JSON Web Token (Introdução e Debugger)  
- https://jwt.io/

RFC 7519 — JSON Web Token Specification  
- https://datatracker.ietf.org/doc/html/rfc7519

----

## Biblioteca usada no projeto

JJWT (Java JWT Library)  
- https://github.com/jwtk/jjwt

----

## JavaDoc

JavaDoc Tool (Documentação Oficial)  
- https://docs.oracle.com/en/java/javase/17/docs/specs/man/javadoc.html  

JavaDoc Guide (Oracle)  
- https://www.oracle.com/java/technologies/javase/javadoc-tool.html  

----

# 🛡 Licença

Este projeto está sob a licença **MIT**.
