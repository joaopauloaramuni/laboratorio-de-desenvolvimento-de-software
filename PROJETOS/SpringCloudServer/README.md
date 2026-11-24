# ☁️ SpringCloudServer - Servidor de Configuração

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

Projeto Spring Boot que implementa um **Spring Cloud Config Server**, responsável por centralizar e distribuir configurações para outras aplicações. Ele trabalha em conjunto com o projeto **SpringCloudClient**, que busca suas propriedades neste servidor ao iniciar.

---

## 📌 O que é o Spring Cloud Config?

O **Spring Cloud Config** oferece configuração externalizada para sistemas distribuídos. Em vez de cada aplicação manter suas próprias propriedades, elas ficam em um **servidor de configuração** central, e os **clientes** as consultam via HTTP.

Principais benefícios:
- **Configuração centralizada** para vários serviços
- **Separação** entre código e configuração
- **Ambientes diferentes** (dev, homologação, produção) com o mesmo artefato
- **Fonte das propriedades flexível**: sistema de arquivos (perfil `native`), repositório Git, Vault, entre outros

Neste projeto, o servidor usa o perfil **`native`**, lendo as propriedades da pasta local `config/`.

---

## 📁 Estrutura do Projeto

```
SpringCloudServer/
├── config/
│   └── application.properties                  # Propriedades servidas aos clientes
├── src/
│   ├── main/
│   │   ├── java/com/example/SpringCloudServer/
│   │   │   ├── application/
│   │   │   │   └── SpringCloudServerApplication.java   # Ponto de entrada (@EnableConfigServer)
│   │   │   └── controller/
│   │   │       └── ServerHelloController.java          # Endpoint de teste
│   │   └── resources/
│   │       └── application.properties          # Configuração do próprio servidor
│   └── test/
│       └── java/com/example/RestAPI/
│           └── SpringCloudServerApplicationTests.java
└── pom.xml                                     # Dependências Maven
```

---

## ⚙️ Dependências

Definidas no `pom.xml`:

| Dependência | Descrição |
|---|---|
| `spring-boot-starter-web` | Suporte a API REST com Spring MVC |
| `spring-cloud-config-server` | Transforma a aplicação em um servidor de configuração |
| `spring-boot-starter-test` | Suporte a testes (escopo de teste) |

> 💡 `spring-cloud-config-server` é usado no **servidor**; `spring-cloud-starter-config` é usado nos **clientes**.

---

## 🔧 Configuração

### `src/main/resources/application.properties`

```properties
spring.application.name=SpringCloudServer
server.port=8888
spring.profiles.active=native
spring.cloud.config.server.native.search-locations=file:///Users/joaopauloaramuni/Documents/WORKSPACE/PROJETOS_AAW/SpringCloudServer/config
```

| Propriedade | Descrição |
|---|---|
| `server.port=8888` | Porta padrão de um Config Server |
| `spring.profiles.active=native` | Lê as configurações do sistema de arquivos, sem Git |
| `spring.cloud.config.server.native.search-locations` | Pasta onde ficam os arquivos servidos aos clientes |

> ⚠️ **Atenção:** o caminho em `search-locations` é absoluto e aponta para a máquina do autor. Antes de executar, ajuste para o caminho da pasta `config/` no seu computador, ou use um caminho do classpath, por exemplo `classpath:/config` (movendo a pasta para `src/main/resources/config`).

### `config/application.properties`

```properties
hello.message=Oi, sou uma mensagem do "Config Server"
```

É esta propriedade que o servidor entrega aos clientes.

---

## 🌐 Endpoints

### GET `/hello-server`

Retorna a mensagem lida pelo próprio servidor.

**URL:** `http://localhost:8888/hello-server`

```
Mensagem do servidor de configuração: Oi, sou uma mensagem do "Config Server"
```

### GET `/{aplicacao}/{perfil}`

Endpoint fornecido automaticamente pelo Config Server, que mostra as propriedades disponíveis para uma aplicação e perfil.

**URL de exemplo:** `http://localhost:8888/SpringCloudClient/default`

---

## ▶️ Como executar

Pré-requisito: **Java 17**.

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

O servidor sobe em `http://localhost:8888`. Inicie-o **antes** do **SpringCloudClient**, pois o cliente busca suas configurações aqui ao iniciar.

---

## 🔗 Projeto relacionado

- **SpringCloudClient** — cliente que consome as configurações deste servidor (porta `8080`).

---

## 📚 Referências

- [Spring Cloud Config - Documentação oficial](https://docs.spring.io/spring-cloud-config/reference/)
- [Centralized Configuration - Guia Spring](https://spring.io/guides/gs/centralized-configuration/)
