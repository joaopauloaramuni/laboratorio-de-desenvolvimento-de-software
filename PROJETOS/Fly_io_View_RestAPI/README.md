# ☁️ Fly.io View Rest API

Projeto desenvolvido com **Spring Boot** para demonstrar, de forma simples, como realizar o **deploy de uma aplicação Java na nuvem utilizando o Fly.io**.

A aplicação disponibiliza:

- 🌐 uma página HTML estática
- 🔗 um endpoint REST de exemplo
- ☁️ deploy em ambiente cloud (Fly.io)

----

# 💡 Objetivo do Projeto

Este projeto tem como objetivo:

- demonstrar deploy de aplicações Java na nuvem  
- apresentar integração com Docker  
- servir como exemplo didático para alunos  
- facilitar o entendimento de APIs simples com Spring Boot  

----

# 🚀 Funcionalidades

- 📄 Servir página estática (`index.html`)
- 🔗 Endpoint REST `/exemplo`
- ☁️ Deploy na nuvem com Fly.io
- 🐳 Containerização com Docker

----

# 🛠 Tecnologias

- Java 17  
- Spring Boot  
- Maven  
- Docker  
- Fly.io  
- Rest API  

----

# 📁 Estrutura do Projeto

```
Fly_io_View_RestAPI
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
│   │       ├── static
│   │       │   ├── css/
│   │       │   ├── images/
│   │       │   ├── js/
│   │       │   └── index.html
│   │       └── application.properties
│
├── Dockerfile
├── fly.toml
└── pom.xml
```

----

# ▶️ Como Executar

## 1️⃣ Build do projeto

```bash
mvn clean package
```

## 2️⃣ Executar localmente

```bash
java -jar target/*.jar
```

----

# 🌐 Acessando a Aplicação

## 🖥 Ambiente local

Após executar o projeto:

👉 http://localhost:8080/

## 🔗 Endpoint REST

👉 http://localhost:8080/exemplo

----

## ☁️ Ambiente em produção (Fly.io)

👉 https://fly-io-view-restapi.fly.dev/

Endpoint:

👉 https://fly-io-view-restapi.fly.dev/exemplo

----

# 🧪 Exemplo de Resposta

Ao acessar `/exemplo`, a API retorna:

```
Quando estiver rodando o projeto localmente, acesse <a href='http://localhost:8080/'>localhost:8080</a> para visualizar o contéudo do arquivo resources/static/index.html. 
Caso já tenha feito o deploy no Fly.io, acesse <a href='https://fly-io-view-restapi.fly.dev/'>fly-io-view-restapi.fly.dev</a> para visualizar o conteúdo do arquivo resources/static/index.html ou 
<a href='https://fly-io-view-restapi.fly.dev/exemplo'>fly-io-view-restapi.fly.dev/exemplo</a> para acessar o endpoint /exemplo.
```

----

# 🐳 Docker

O projeto utiliza um **Dockerfile** para empacotar a aplicação Spring Boot em um container.

Isso é essencial porque o **Fly.io não executa aplicações Java/Spring Boot diretamente**, ele executa **containers**.

Ou seja: antes de subir para a nuvem, precisamos "empacotar" a aplicação em um formato que o Fly.io entenda — e esse formato é o **container Docker**.

----

## 📄 Dockerfile utilizado

```
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

----

## 🔍 Explicação linha a linha

### 🧱 `FROM eclipse-temurin:17-jdk-alpine`

Define a imagem base do container:

- Java 17 (compatível com o projeto)
- Alpine Linux (leve e otimizado)

💡 Resultado: container mais rápido e menor

----

### 📦 `VOLUME /tmp`

Cria um volume temporário dentro do container.

- usado pelo Spring Boot para arquivos temporários
- melhora compatibilidade e desempenho

----

### 📥 `COPY target/*.jar app.jar`

Copia o `.jar` gerado pelo Maven para dentro do container:

- origem: `target/`
- destino: `app.jar`

----

### ▶️ `ENTRYPOINT ["java","-jar","/app.jar"]`

Define o comando que será executado ao iniciar o container:

- executa a aplicação Spring Boot
- sobe o servidor embutido (Tomcat)

----

## ☁️ Por que isso é importante para o Fly.io?

O Fly.io funciona baseado em **containers (Docker)**.

Isso significa que:

❌ Ele não entende diretamente:
- projetos Maven
- código Java
- aplicações Spring Boot

✅ Ele entende:
- imagens Docker

----

## 🔄 Fluxo completo do deploy

1. Você escreve a aplicação em Spring Boot ☕  
2. Gera o `.jar` com Maven 📦  
3. O Docker empacota a aplicação 🐳  
4. O Fly.io executa o container ☁️  

----

## 💡 Vantagens de usar Docker

- 🔁 Portabilidade (roda em qualquer ambiente)
- ⚙️ Ambiente padronizado
- 🚀 Deploy mais simples
- 🧪 Evita problema de "na minha máquina funciona"

O Docker atua como uma **camada intermediária** entre sua aplicação e o Fly.io, permitindo que uma aplicação Java seja executada em qualquer ambiente que suporte containers.

Sem o Docker, o Fly.io não conseguiria rodar diretamente uma aplicação Spring Boot.

----

# ⚙️ Configuração do Fly.io (`fly.toml`)

O arquivo `fly.toml` é o **arquivo de configuração principal do deploy no Fly.io**.

Ele define como a aplicação será executada na nuvem, incluindo:

- 🌎 região do servidor
- 🔌 porta da aplicação
- ⚡ comportamento de inicialização/parada
- 🖥 recursos da máquina (CPU e memória)

----

## 📄 Exemplo utilizado no projeto

```
app = 'fly-io-view-restapi'
primary_region = 'gru'

[build]

[http_service]
  internal_port = 8080
  force_https = true
  auto_stop_machines = true
  auto_start_machines = true
  min_machines_running = 0
  processes = ['app']

[[vm]]
  cpu_kind = 'shared'
  cpus = 1
  memory_mb = 1024
```

----

## 🔍 Explicação dos principais campos

### 🏷 `app`
Nome da aplicação no Fly.io

```
app = 'fly-io-view-restapi'
```

----

### 🌎 `primary_region`
Define a região onde a aplicação será hospedada

```
primary_region = 'gru'
```

- `gru` = São Paulo (Brasil)
- reduz latência para usuários brasileiros 🇧🇷

----

### 🔨 `[build]`
Seção responsável pelo processo de build

```
[build]
```

- pode ser usada para customizar builds
- neste projeto, está usando o **Dockerfile automaticamente**

----

### 🌐 `[http_service]`
Configura o serviço HTTP da aplicação

```
internal_port = 8080
```

- porta interna do container (Spring Boot roda em 8080)

----

```
force_https = true
```

- força acesso via HTTPS 🔒

----

```
auto_stop_machines = true
auto_start_machines = true
```

- desliga a aplicação quando não há uso ⚡
- liga automaticamente quando recebe requisição

💡 **economia de recursos (e custo)**

----

```
min_machines_running = 0
```

- permite zero instâncias rodando (modo serverless-like)

----

```
processes = ['app']
```

- define o processo principal da aplicação

----

### 🖥 `[[vm]]`
Configuração da máquina virtual

```
[[vm]]
  cpu_kind = 'shared'
  cpus = 1
  memory_mb = 1024
```

- `cpu_kind = shared` → CPU compartilhada
- `cpus = 1` → 1 CPU
- `memory_mb = 1024` → 1GB de RAM

----

## 💡 Resumo

O `fly.toml` funciona como um **"application.yml da infraestrutura"**, definindo:

- onde a aplicação roda 🌎  
- como ela roda ⚙️  
- quanto recurso ela usa 🖥  
- quando ela liga/desliga ⚡  

----

## 🎯 Por que isso é importante?

- facilita deploy automatizado 🚀  
- padroniza ambientes  
- permite escalar aplicações facilmente  
- reduz custos com auto start/stop  

----

# ☁️ Deploy no Fly.io (passo a passo)

1. Instale o CLI do Fly.io  
2. Faça login:

```bash
fly auth login
```

3. Deploy da aplicação:

```bash
fly deploy
```

----

# 📚 Links úteis

## ☁️ Fly.io

Fly.io (Site oficial)  
- https://fly.io/

Documentação  
- https://fly.io/docs/

----

## 🌱 Spring

Spring Boot  
- https://spring.io/projects/spring-boot

Spring Boot Documentation  
- https://docs.spring.io/spring-boot/docs/current/reference/html/

----

## 🐳 Docker

Docker  
- https://www.docker.com/

----

# 🛡 Licença

Este projeto está sob a licença **MIT**.
