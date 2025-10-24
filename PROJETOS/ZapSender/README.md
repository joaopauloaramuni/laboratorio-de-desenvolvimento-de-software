# 🤖 ZapSender: Envio e Recebimento de Mensagens via WhatsApp Cloud API

O **ZapSender** é um projeto em **Java 17 / Spring Boot** que demonstra, de forma prática, como integrar-se à **WhatsApp Cloud API** (Meta for Developers) para **enviar** e **receber** mensagens de forma automatizada.

Ele contém dois componentes principais:

- 🟢 **`ZapSenderService`** — envia mensagens de *template* pré-aprovadas (como `hello_world`) para contatos via API oficial da Meta.  
- 🟣 **`WebHookService`** — processa mensagens recebidas e interações do WhatsApp via **Webhooks**, permitindo enviar mensagens de texto e mensagens interativas com botões.

O objetivo é fornecer uma base **simples**, **segura** e **reproduzível** para entender o fluxo completo de automação com a WhatsApp Cloud API — desde o envio de templates até o recebimento de mensagens e eventos em tempo real.

---

## 📖 Sumário

* [💬 Fluxo Interativo: Avaliação da Oficina](#-fluxo-interativo-avaliação-da-oficina)
* [💡 Estrutura do Projeto](#-estrutura-do-projeto)
* [📘 Meta for Developers e a Cloud API](#-meta-for-developers-e-a-cloud-api)
* [🚀 Passo a Passo para Criar o App no Meta for Developers](#-passo-a-passo-para-criar-o-app-no-meta-for-developers)
* [📝 Mensagens de Template (Message Templates)](#-mensagens-de-template-message-templates)
* [🌐 Criando e Configurando o Webhook](#-criando-e-configurando-o-webhook)
* [📝 Explicação do Código Java](#-explicação-do-código-java)
* [📌 Pré-requisitos](#-pré-requisitos)
* [⚡ Execução](#-execução)
* [📚 Documentação e Links Úteis](#-documentação-e-links-úteis)
* [🤝 Contribuição](#-contribuição)
* [🧾 Licença](#-licença)

---

## 💬 Fluxo Interativo: Avaliação da Oficina (WhatsApp + Spring Boot)

O **ZapSender** implementa um bot via **WhatsApp Cloud API**, permitindo avaliar a oficina do DevLabs de forma **interativa** e **flexível**.

---

### 🧠 Lógica de Funcionamento

> **Observação:** você é quem dispara as ações via Postman ou Insomnia.

1. Você **envia uma requisição para enviar um template** (ex.: "Hello World").  
2. Em seguida, você **envia uma requisição para enviar uma mensagem com botões**:  
   > "Olá! Me diga: o que você achou da oficina do DevLabs? Sua opinião é muito importante!"  
   Botões disponíveis:  
   - 🟢 Ótima  
   - 🟡 Boa  
   - 🔵 Regular  
3. Você **clica em um botão** na interface do WhatsApp, e o bot **responde confirmando a escolha**:  
   > "Você escolheu: [opção]. Agradeço muito seu feedback! 😊"  
4. Você pode **enviar uma nova requisição de texto**, por exemplo solicitando uma nota de 0 a 10:  
   > "Olá! Já que iniciamos a conversa, me diga: de 0 a 10, o que você achou da oficina do DevLabs? Sua opinião é muito importante!"  
5. O bot **responde confirmando a nota escolhida**:  
   > "Você escolheu: [nota]! Agradeço muito seu feedback. Qualquer coisa estou à disposição! 😊"  
6. Todas as respostas (botão ou nota) são **registradas no terminal** e podem ser **armazenadas em banco de dados** futuramente.

---

### 🔗 Endpoints para Testes (Postman / Insomnia)

```bash
GET http://localhost:8080/webhook?hub.mode=subscribe&hub.verify_token=joaopauloaramuni&hub.challenge=12345
```
Verificação do webhook.

```bash
GET http://localhost:8080/webhook/enviar-botoes?numeroDestino=5531980402103
```
Enviar mensagem com botões.

```bashGET http://localhost:8080/webhook/enviar-texto?numeroDestino=5531980402103&texto=Olá%20Mundo
```
Enviar mensagem de texto.

```bash
GET http://localhost:8080/webhook/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US
```
Enviar template via Webhook.

```bash
GET http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US
```
Enviar template via ZapSenderController.

---

## 🖼️ Captura de Tela

| <img src="https://joaopauloaramuni.github.io/python-imgs/ZapSender/imgs/Chat.png" alt="ZapSender" width="1000"/> |
|:---------:|
| ZapSender |

---

## 💡 Estrutura do Projeto

```
ZapSender/
├─ src/main/java/com/example/ZapSender/
│  ├─ config/
│  │  └─ ApiConfig.java           # Configuração da API (tokens, phone ID, URL da API)
│  ├─ controller/
│  │  ├─ WebHookController.java   # Endpoints REST para webhook
│  │  └─ ZapSenderController.java # Endpoints REST para envio de templates
│  ├─ service/
│  │  ├─ WebHookService.java      # Lógica de processamento do webhook e envio de mensagens interativas
│  │  └─ ZapSenderService.java    # Lógica de envio de templates via WhatsApp Cloud API
│  └─ ZapSenderApplication.java   # Classe principal Spring Boot
├─ src/main/resources/
│  └─ application.properties      # Configurações (tokens, phone ID, URL da API)
└─ pom.xml                        # Gerenciador de dependências Maven
```

* **ZapSenderApplication.java**: classe principal do Spring Boot, inicializa a aplicação.  
* **ApiConfig.java**: configuração da API, contém tokens, Phone Number ID e URL da API do WhatsApp Cloud.  
* **ZapSenderService.java**: envia templates via WhatsApp Cloud API.  
* **WebHookService.java**: recebe mensagens, envia texto e mensagens com botões.  
* **ZapSenderController.java**: endpoints para envio de templates.  
* **WebHookController.java**: endpoints para receber webhooks e interações.  
* **application.properties**: arquivo de configuração da aplicação (tokens, phone ID, URL da API).  
* **pom.xml**: gerenciador de dependências Maven e configuração do projeto.

---

## 📘 Meta for Developers e a Cloud API

Para usar a **WhatsApp Cloud API**, você precisa:

1. Criar um aplicativo no [Meta for Developers](https://developers.facebook.com/).
2. Adicionar o produto **WhatsApp** ao app.
3. Obter **Access Token** e **Phone Number ID**.
4. Configurar o **Webhook** para receber mensagens e eventos.

---

## 🚀 Passo a Passo para Criar o App no Meta for Developers

1. Acesse [Meta for Developers](https://developers.facebook.com/apps/) e clique em **Criar aplicativo**.
2. Escolha o tipo: **Negócios (Business)** ou **Outro (Other)**.
3. Dê um nome ao aplicativo e finalize.
4. Vincule a conta comercial para habilitar o WhatsApp.
5. Configure número de telefone e crie templates.

---

## 📝 Mensagens de Template (Message Templates)

Templates são mensagens pré-aprovadas obrigatórias para iniciar conversas fora da janela de 24h.

1. Acesse [Gerenciador de Templates](https://business.facebook.com/latest/whatsapp_manager/message_templates).
2. Crie o modelo, selecione categoria, idioma e corpo da mensagem.
3. Envie para aprovação.

---

## 🌐 Criando e Configurando o Webhook

1. Vá em **Produtos → WhatsApp → Configurações → Webhook**.
2. Configure:

   * **Callback URL**: URL pública do seu servidor Spring Boot (ex.: via ngrok: `https://1234abcd.ngrok.io/webhook`).
   * **Verify Token**: token de validação (deve coincidir com `zapsender.verify.token` em `application.properties`).
3. Clique em **Verificar e Salvar**.
4. Assine o campo `messages` para receber notificações.

Para testes locais, use ngrok:

###

ngrok http 8080

###

Copie a URL gerada e use `/webhook` como callback.

---

## 📝 Explicação do Código Java

* **ZapSenderService**: envia mensagens de template.
* **WebHookService**: envia mensagens de texto, interativas e processa payloads do webhook.
* **Controllers** expõem endpoints REST para envio e recebimento.
* **RestTemplate** é usado para fazer requisições HTTP para a API do WhatsApp.

Fluxo de recebimento:

1. WhatsApp envia `POST` para `/webhook`.
2. WebHookController recebe o JSON.
3. WebHookService processa e envia respostas automáticas.

---

## 📌 Pré-requisitos

* Java 17+
* Maven 3.8+
* Conta Meta for Developers com WhatsApp Cloud API habilitado
* Dependências Maven:

###

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
###

---

## ⚡ Execução

1. Configure **application.properties** com seus tokens e IDs.
2. Execute o Spring Boot:

###

mvn spring-boot:run

###

3. Para enviar um template:

###

curl -X POST '[http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US](http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US)'

###

4. Para testar webhook, exponha a porta 8080 com ngrok e envie mensagens para o número configurado.

---

## 📚 Documentação e Links Úteis

| Recurso                  | Link                                                                                                                                                         |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Meta Apps                | [https://developers.facebook.com/apps/](https://developers.facebook.com/apps/)                                                                               |
| Gerenciador de Templates | [https://business.facebook.com/latest/whatsapp_manager/message_templates](https://business.facebook.com/latest/whatsapp_manager/message_templates)           |
| Guia Cloud API           | [https://developers.facebook.com/docs/whatsapp/cloud-api/get-started](https://developers.facebook.com/docs/whatsapp/cloud-api/get-started)                   |
| Guia de Envio            | [https://developers.facebook.com/docs/whatsapp/cloud-api/guides/send-messages](https://developers.facebook.com/docs/whatsapp/cloud-api/guides/send-messages) |
| Webhooks                 | [https://developers.facebook.com/docs/whatsapp/cloud-api/guides/webhooks](https://developers.facebook.com/docs/whatsapp/cloud-api/guides/webhooks)           |
| ngrok                    | [https://ngrok.com/](https://ngrok.com/)                                                                                                                     |
| Spring Boot Docs         | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)                                                                             |

---

## 🤝 Contribuição

Contribuições são bem-vindas via issues ou pull requests.

---

## 🧾 Licença

Este projeto está sob licença **MIT**.
