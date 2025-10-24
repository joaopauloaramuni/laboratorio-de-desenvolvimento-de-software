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

## 💬 Fluxo Interativo: Avaliação da Oficina

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
POST http://localhost:8080/webhook/enviar-botoes?numeroDestino=5531980402103
```
Enviar mensagem com botões.

```bash
POST http://localhost:8080/webhook/enviar-texto?numeroDestino=5531980402103&texto=Olá%20Mundo
```
Enviar mensagem de texto.

```bash
POST http://localhost:8080/webhook/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US
```
Enviar template via Webhook.

```bash
POST http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US
```
Enviar template via ZapSenderController.

---

## 🖼️ Captura de Tela

| <img src="https://joaopauloaramuni.github.io/java-imgs/ZapSender/imgs/Chat.png" alt="ZapSender" width="1000"/> |
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

### O que é o Meta for Developers?
É a plataforma oficial da **Meta** que fornece APIs, SDKs e ferramentas para desenvolvedores criarem integrações com produtos como **Facebook**, **Instagram** e **WhatsApp**.

Para utilizar a **WhatsApp Cloud API**, é necessário:
1. Criar um aplicativo dentro do [Meta for Developers](https://developers.facebook.com/).  
2. Adicionar o **produto WhatsApp** ao seu app.  
3. Obter um **Access Token** e um **Phone Number ID**.  
4. (Opcional) Configurar um **Webhook** para receber mensagens e eventos automaticamente.

---

## 🚀 Passo a Passo para Criar o App no Meta for Developers

Antes de enviar mensagens com a **WhatsApp Cloud API**, é necessário criar um **aplicativo** e vinculá-lo a uma **Conta Comercial (Business Portfolio)**.  
Esse vínculo habilita o produto **WhatsApp** no seu app e permite gerar as credenciais necessárias (Access Token, ID do telefone e endpoint da API).

### 🧩 1. Criar o aplicativo

1. Acesse o portal [Meta for Developers](https://developers.facebook.com/apps/) e clique em **Criar aplicativo**.
2. Escolha o tipo de aplicativo:
   - **Negócios (Business)**: para empresas que enviarão mensagens de forma comercial.
   - **Outro (Other)**: para testes ou integrações simples.
3. Dê um **nome ao seu aplicativo** e finalize a criação.

---

### 💼 2. Vincular a Conta Comercial (Business Portfolio)

Para que o produto **WhatsApp** apareça como opção no seu app:

1. Certifique-se de ter uma **Conta Comercial ativa** no [Meta Business](https://business.facebook.com/).
2. No painel do app, vá em **Configurações > Informações do App**.
3. Vincule o seu **Business Portfolio** à conta do aplicativo.
4. Após o vínculo, o produto **WhatsApp** será exibido para integração.

> ⚠️ **Observação:** Sem essa etapa, você não conseguirá adicionar o WhatsApp ao app, nem enviar mensagens via Cloud API.

---

### ✅ 3. Próximos passos

- Adicione o produto **WhatsApp** no painel do seu app.
- Configure um número de telefone (sandbox ou oficial) para testes.
- Crie templates de mensagens para poder enviar notificações ou mensagens automatizadas.

---

## 📝 Mensagens de Template (Message Templates)

### O que é um Template?
Templates (ou Modelos de Mensagem) são mensagens pré-aprovadas que devem ser usadas para iniciar conversas ou enviar notificações fora da janela de 24 horas de atendimento.  
Para usar a Cloud API, a primeira mensagem para um novo contato deve ser um Template, como o `hello_world` usado neste projeto.

### Passo a passo para criar um Template
1.  Acesse o **Gerenciador do WhatsApp** através do link: [Gerenciador de Templates](https://business.facebook.com/latest/whatsapp_manager/message_templates).
2.  Certifique-se de que está na conta de Negócios correta.
3.  Clique em **"Criar Modelo de Mensagem"**.
4.  Defina o **Nome** (ex.: `hello_world`), **Categoria** (ex.: Utilidade) e **Idioma**.
5.  Crie o corpo da mensagem.
6.  Envie o template para aprovação (em ambientes de teste, templates básicos são aprovados instantaneamente).

### ⏳ Prazo de Aprovação
O prazo de aprovação de um novo modelo de mensagem pela **Meta** pode levar **até 24 horas**.  
Esse tempo é necessário para que a equipe da Meta analise o conteúdo e garanta que o template esteja em conformidade com as **políticas do WhatsApp Business API**.

---

## 🌐 Criando e Configurando o Webhook

O **Webhook** é o mecanismo usado pela Meta para **enviar notificações e mensagens recebidas** para o seu servidor.  
Quando alguém envia uma mensagem para o seu número do WhatsApp Business, a Meta faz uma requisição `POST` para a **URL de callback** configurada no seu app.

---

### ⚙️ 4. Criar o Webhook no Meta for Developers

1. No painel do seu aplicativo, vá em **Produtos → WhatsApp → Configurações**.  
2. Na seção **Webhook**, clique em **“Configurar Webhook”**.  
3. Você verá dois campos:
   - **Callback URL:** o endereço público do seu servidor que receberá os eventos.  
     Exemplo (usando ngrok): `https://1234abcd.ngrok.io/webhook`
   - **Verify Token:** uma senha personalizada que você escolhe (ex.: `joaopauloaramuni`).  
     Esse token deve **coincidir com o valor da variável `VERIFY_TOKEN`** no arquivo `webhook.py`.

4. Clique em **Verificar e Salvar**.  
   O Meta enviará uma requisição `GET` ao seu endpoint `/webhook` com parâmetros de verificação.  
   Se o seu servidor (`webhook.py`) estiver rodando corretamente, ele retornará o `hub.challenge` e a verificação será concluída com sucesso.

---

### 🔔 5. Assinar o Campo “messages”

Após configurar o Webhook:

1. Ainda no painel de **Webhooks**, clique em **“Gerenciar campos”**.  
2. Marque a opção **`messages`** para que seu servidor receba notificações sempre que novas mensagens forem enviadas ou recebidas.  
3. Clique em **Salvar alterações**.

> 💡 **Importante:** sem assinar o campo `messages`, o seu endpoint `/webhook` não receberá nenhum evento de mensagem.

---

### 🧪 Testando o Webhook

1. Execute o servidor Spring Boot diretamente com:
   ``bash
   ./mvnw spring-boot:run
   ```

3. Configure seu ngrok com o token pessoal (somente na primeira execução):
   ```bash
   ngrok config add-authtoken SEU_TOKEN_AQUI
   ```

5. Inicie o túnel HTTPS para expor a porta local 8080:
   ```bash
   ngrok http 8080
   ```

7. Copie o link HTTPS gerado e adicione **`/webhook`** ao final.  
   Use esse endereço completo como **Callback URL** no painel da Meta.  
   Exemplo final: `https://1234abcd.ngrok.io/webhook`

8. Envie uma mensagem para o número de teste configurado.  
   Você verá o **payload JSON** aparecer no terminal, confirmando que o webhook está recebendo os dados corretamente.

---

### 🔍 Inspect / HTTP

- Acesse `http://127.0.0.1:4040` no navegador para abrir o painel do ngrok.

- Vá na aba **Inspect → HTTP** para visualizar todas as requisições recebidas pelo webhook em tempo real.

- Clique em cada requisição para ver detalhes como:
  - Headers HTTP
  - Payload JSON
  - Resposta do servidor

---

## 📝 Explicação do Código Java

* **ZapSenderService**: envia mensagens de *template* via WhatsApp Cloud API.  
* **WebHookService**: envia mensagens de texto, mensagens interativas com botões e processa payloads recebidos pelo webhook.  
* **ZapSenderController**: endpoints REST para envio de templates via requisição HTTP.  
* **WebHookController**: endpoints REST para receber webhooks e enviar mensagens interativas ou de texto.  
* **ApiConfig**: configura tokens, URLs e outras credenciais da API do WhatsApp.  
* **RestTemplate** é usado para realizar requisições HTTP à API do WhatsApp.

### 🔄 Fluxo de Requisições

O fluxo de interação ocorre **por meio de requisições HTTP feitas pelo usuário** (Postman, Insomnia ou navegador):

1. Enviar `POST` para `/webhook/enviar-template` ou `/zapsender/enviar-template` → **ZapSenderService** envia o template desejado.  
2. Enviar `POST` para `/webhook/enviar-botoes` → **WebHookService** envia mensagem interativa com botões.  
3. Enviar `POST` para `/webhook/enviar-texto` → **WebHookService** envia mensagem de texto simples.  
4. Receber notificações via webhook (`POST /webhook`) → **WebHookController** recebe o JSON e **WebHookService** processa e responde conforme necessário.  

> 🔹 Observação: **nenhum envio ocorre automaticamente**. Todas as mensagens são acionadas pelas requisições do usuário.

---

## 📌 Pré-requisitos

* Java 17+
* Maven 3.8+
* Conta Meta for Developers com WhatsApp Cloud API habilitado
* Dependências Maven:

```pom
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
```

---

## ⚡ Execução

1. Configure **application.properties** com seus tokens e IDs.
2. Execute o Spring Boot:

```bash
mvn spring-boot:run
```

3. Para enviar um template:

```bash
curl -X POST '[http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US](http://localhost:8080/zapsender/enviar-template?numeroDestino=5531980402103&nomeTemplate=hello_world&codigoIdioma=en_US)'
```

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
