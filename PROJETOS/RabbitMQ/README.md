# 🐇 RabbitMQ - Sistema de Compra de Ingressos

Um projeto Spring Boot que demonstra mensageria assíncrona com **RabbitMQ**, simulando um fluxo de compra de ingressos onde os pedidos são enviados para uma fila e processados por um consumidor.

---

## 📌 O que é RabbitMQ?

**RabbitMQ** é um message broker de código aberto que implementa o protocolo **AMQP** (Advanced Message Queuing Protocol). Ele atua como intermediário entre aplicações, permitindo que **produtores** enviem mensagens para **filas**, que são então consumidas por **consumidores** — tudo de forma assíncrona e desacoplada.

Principais benefícios:
- **Desacoplamento** entre serviços
- **Resiliência** — mensagens são persistidas mesmo que o consumidor esteja temporariamente indisponível
- **Escalabilidade** — múltiplos consumidores podem processar mensagens em paralelo
- **Balanceamento de carga** entre consumidores

Neste projeto, o RabbitMQ é utilizado para processar pedidos de compra de ingressos: o endpoint HTTP recebe a requisição e coloca na fila, enquanto o listener processa de forma assíncrona.

---

## 📁 Estrutura do Projeto

```
RabbitMQ/
├── src/
│   └── main/
│       ├── java/com/example/RabbitMQ/
│       │   ├── RabbitMqApplication.java        # Ponto de entrada da aplicação
│       │   ├── controller/
│       │   │   └── RabbitMqController.java      # Endpoint REST
│       │   ├── service/
│       │   │   └── RabbitMqService.java         # Lógica de Producer e Consumer
│       │   ├── config/
│       │   │   ├── RabbitConfig.java            # Configuração da fila e template
│       │   │   └── GsonMessageConverter.java    # Conversor de mensagens JSON customizado
│       │   └── dto/
│       │       └── PedidoIngressoDTO.java       # Objeto de Transferência de Dados
│       └── resources/
│           └── application.properties           # Configurações da aplicação
└── pom.xml                                      # Dependências Maven
```

---

## ⚙️ Dependências

Definidas no `pom.xml`:

| Dependência | Descrição |
|---|---|
| `spring-boot-starter-web` | Suporte a API REST com Spring MVC |
| `spring-boot-starter-amqp` | Integração com RabbitMQ via Spring AMQP |
| `spring-rabbit-test` | Suporte a testes para RabbitMQ (escopo de teste) |
| `spring-boot-starter-test` | Suporte geral a testes (escopo de teste) |
| `com.google.code.gson` | Serialização/desserialização JSON com Gson |

---

## 🌐 Endpoints

### POST `/ingressos/comprar`

Envia um pedido de compra de ingresso para a fila do RabbitMQ.

**URL:** `http://localhost:8080/ingressos/comprar`  
**Método:** `POST`  
**Content-Type:** `application/json`

#### Corpo da Requisição

```json
{
  "nomeCliente": "João Silva",
  "evento": "Rock in Rio",
  "quantidade": 2
}
```

#### Resposta

```
Pedido enviado para processamento!
```

#### Exemplo de saída no Console

```
🟡 Pedido enviado: João Silva
🟢 Processando pedido:
Cliente: João Silva
Evento: Rock in Rio
Quantidade: 2
Ingresso emitido!
```

---

## 🚀 Instalando e Executando o RabbitMQ

### 🪟 Windows

**Opção 1 — Usando o instalador oficial:**

1. Instale o **Erlang**: https://www.erlang.org/downloads  
2. Instale o **RabbitMQ**: https://www.rabbitmq.com/install-windows.html  
3. Abra o **Prompt de Comando como Administrador** e execute:

```bash
rabbitmq-service start
rabbitmq-plugins enable rabbitmq_management
```

4. Acesse o painel de gerenciamento: http://localhost:15672  
   Credenciais padrão: `guest` / `guest`

**Opção 2 — Usando Docker (recomendado):**

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
```

---

### 🐧 Linux (Ubuntu/Debian)

```bash
# Instalar dependências
sudo apt-get install -y curl gnupg apt-transport-https

# Adicionar repositório oficial do RabbitMQ
curl -1sLf "https://keys.openpgp.org/vks/v1/by-fingerprint/0A9AF2115F4687BD29803A206B73A36E6026DFCA" | sudo gpg --dearmor -o /usr/share/keyrings/com.rabbitmq.team.gpg

sudo tee /etc/apt/sources.list.d/rabbitmq.list <<EOF
deb [signed-by=/usr/share/keyrings/com.rabbitmq.team.gpg] https://ppa1.rabbitmq.com/rabbitmq/rabbitmq-server/deb/ubuntu focal main
EOF

# Instalar Erlang e RabbitMQ
sudo apt-get update
sudo apt-get install -y erlang rabbitmq-server

# Iniciar o serviço
sudo systemctl start rabbitmq-server
sudo systemctl enable rabbitmq-server

# Habilitar o plugin de gerenciamento
sudo rabbitmq-plugins enable rabbitmq_management
```

Acesse: http://localhost:15672 — credenciais: `guest` / `guest`

**Ou com Docker:**

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
```

---

### 🍎 macOS

**Opção 1 — Usando Homebrew:**

```bash
# Instalar o RabbitMQ
brew install rabbitmq

# Iniciar o serviço
brew services start rabbitmq

# Habilitar o plugin de gerenciamento
rabbitmq-plugins enable rabbitmq_management
```

Acesse: http://localhost:15672 — credenciais: `guest` / `guest`

**Opção 2 — Usando Docker:**

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
```

---

## ▶️ Executando o Projeto

Certifique-se de que o RabbitMQ está em execução antes de iniciar a aplicação.

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/rabbitmq-ingressos.git
cd rabbitmq-ingressos

# Rodar com Maven
./mvnw spring-boot:run
```

A aplicação será iniciada na porta `8080`.

---

## 🔧 Configuração

`src/main/resources/application.properties`:

```properties
spring.application.name=RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

---

## 🧪 Testando o Endpoint

Usando **curl**:

```bash
curl -X POST http://localhost:8080/ingressos/comprar \
  -H "Content-Type: application/json" \
  -d '{"nomeCliente": "João Silva", "evento": "Rock in Rio", "quantidade": 2}'
```

Ou utilize **Postman** / **Insomnia** com o corpo da requisição apresentado acima.

---

## 📚 Documentação e Links Úteis

| Recurso | Link |
|---|---|
| Documentação Oficial RabbitMQ | https://www.rabbitmq.com/documentation.html |
| Spring AMQP Docs | https://docs.spring.io/spring-amqp/docs/current/reference/html/ |
| Guia Spring Boot + RabbitMQ | https://spring.io/guides/gs/messaging-rabbitmq/ |
| Gson GitHub | https://github.com/google/gson |
| Imagem Docker RabbitMQ | https://hub.docker.com/_/rabbitmq |
| Downloads Erlang | https://www.erlang.org/downloads |
| Plugin de Gerenciamento RabbitMQ | https://www.rabbitmq.com/management.html |

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT**.
