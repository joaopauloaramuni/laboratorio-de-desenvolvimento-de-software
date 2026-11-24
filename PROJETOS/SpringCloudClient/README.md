# 💻 SpringCloudClient - Cliente de Configuração

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

Projeto Spring Boot que atua como **cliente do Spring Cloud Config**. Ao iniciar, ele busca suas propriedades no **SpringCloudServer** e as utiliza em um endpoint REST, demonstrando configuração externalizada e centralizada.

---

## 📌 Como funciona?

1. O **SpringCloudServer** (porta `8888`) disponibiliza a propriedade `hello.message`.
2. Ao iniciar, o **SpringCloudClient** se conecta ao servidor através de `spring.config.import`.
3. O valor recebido é injetado no controller com `@Value("${hello.message}")`.
4. O endpoint `/hello-client` devolve essa mensagem.

Repare que o cliente **não define** `hello.message` no seu próprio `application.properties`: o valor vem inteiramente do servidor de configuração.

---

## 📁 Estrutura do Projeto

```
SpringCloudClient/
├── src/
│   ├── main/
│   │   ├── java/com/example/SpringCloudClient/
│   │   │   ├── application/
│   │   │   │   └── SpringCloudClientApplication.java   # Ponto de entrada da aplicação
│   │   │   └── controller/
│   │   │       └── ClientHelloController.java          # Endpoint que usa a configuração remota
│   │   └── resources/
│   │       └── application.properties          # Aponta para o Config Server
│   └── test/
│       └── java/com/example/SpringCloudClient/
│           └── SpringCloudClientApplicationTests.java
└── pom.xml                                     # Dependências Maven
```

---

## ⚙️ Dependências

Definidas no `pom.xml`:

| Dependência | Descrição |
|---|---|
| `spring-boot-starter-web` | Suporte a API REST com Spring MVC |
| `spring-cloud-starter-config` | Transforma a aplicação em um cliente de configuração |
| `spring-boot-starter-test` | Suporte a testes (escopo de teste) |

> 💡 `spring-cloud-starter-config` é usado nos **clientes**; `spring-cloud-config-server` é usado no **servidor**.

---

## 🔧 Configuração

### `src/main/resources/application.properties`

```properties
spring.application.name=SpringCloudClient
server.port=8080
spring.config.import=configserver:http://localhost:8888
```

| Propriedade | Descrição |
|---|---|
| `spring.application.name` | Nome usado pelo Config Server para localizar as propriedades da aplicação |
| `server.port=8080` | Porta do cliente |
| `spring.config.import` | Endereço do Config Server de onde as propriedades são importadas |

> ⚠️ Se o servidor não estiver no ar, o cliente **falha ao iniciar**. Para permitir a inicialização mesmo assim, use `optional:configserver:http://localhost:8888` (nesse caso, `hello.message` precisará de um valor padrão, como `${hello.message:Mensagem padrão}`).

---

## 🌐 Endpoints

### GET `/hello-client`

Retorna a mensagem obtida do servidor de configuração.

**URL:** `http://localhost:8080/hello-client`

```
Mensagem no lado cliente: Oi, sou uma mensagem do "Config Server"
```

---

## ▶️ Como executar

Pré-requisitos: **Java 17** e o **SpringCloudServer** em execução na porta `8888`.

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Depois acesse `http://localhost:8080/hello-client`.

---

## 🔗 Projeto relacionado

- **SpringCloudServer** — servidor de configuração que fornece as propriedades para este cliente (porta `8888`).

---

## 📚 Referências

- [Spring Cloud Config - Client Side Usage](https://docs.spring.io/spring-cloud-config/reference/client.html)
- [Centralized Configuration - Guia Spring](https://spring.io/guides/gs/centralized-configuration/)
