# 📧 SendEmail

Projeto desenvolvido com **Spring Boot** para envio de e-mails utilizando o serviço SMTP do **Gmail**.

A aplicação disponibiliza dois tipos de envio:

* ✉️ **E-mail em texto puro**
* 🌐 **E-mail com conteúdo HTML**

O projeto também possui tratamento personalizado de exceções para problemas durante o envio das mensagens.

---

## 🚀 Tecnologias utilizadas

* ☕ Java
* 🌱 Spring Boot
* 📧 Spring Boot Starter Mail
* 📩 Gmail SMTP
* 🛠️ Maven

---

## 📦 Dependências

### `pom.xml`

Adicione a dependência do Spring Mail:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

---

## ⚙️ Configuração do Gmail

Para que a aplicação consiga enviar e-mails utilizando uma conta Gmail, é necessário configurar o servidor SMTP e utilizar uma **Senha de app**.

No arquivo:

```text
src/main/resources/application.properties
```

adicione:

```properties
spring.application.name=SendEmail

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=joaopauloaramuni@gmail.com

# Senha de app criada na conta Google
spring.mail.password=senhadeapp

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> ⚠️ **Importante:** não utilize a senha normal da sua conta Google. Para aplicações que utilizam SMTP, utilize uma **Senha de app**.

---

# 🔐 Como criar uma Senha de app no Gmail

A Senha de app é uma senha específica gerada pela conta Google para permitir que aplicações e dispositivos utilizem determinados serviços da conta.

### 1️⃣ Acesse sua conta Google

Acesse:

[Conta Google](https://myaccount.google.com/?utm_source=chatgpt.com)

Faça login com a conta que será utilizada para enviar os e-mails.

### 2️⃣ Ative a verificação em duas etapas

A conta precisa ter a **Verificação em duas etapas** ativada para utilizar Senhas de app.

Acesse:

[Verificação em duas etapas](https://myaccount.google.com/signinoptions/two-step-verification?utm_source=chatgpt.com)

Ative a opção e conclua o processo solicitado pelo Google.

### 3️⃣ Acesse Senhas de app

Depois de ativar a verificação em duas etapas, acesse:

[Senhas de app](https://myaccount.google.com/apppasswords?utm_source=chatgpt.com)

### 4️⃣ Crie uma nova Senha de app

Na página **Senhas de app**:

1. Informe um nome para identificar a aplicação.
2. Por exemplo:

```text
SendEmail
```

3. Clique em **Criar**.

### 5️⃣ Copie a senha gerada

O Google exibirá uma senha de 16 caracteres.

Copie essa senha e coloque no:

```properties
spring.mail.password=senhadeapp
```

Por exemplo:

```properties
spring.mail.password=abcdefghijklmnop
```

> 🔒 **Nunca publique essa senha no GitHub ou em outro repositório público.**

### 6️⃣ Recomendações de segurança

Em um projeto real, evite colocar a senha diretamente no `application.properties`.

Uma alternativa é utilizar uma variável de ambiente:

```properties
spring.mail.password=${MAIL_PASSWORD}
```

E configurar a variável:

```bash
export MAIL_PASSWORD="sua-senha-de-app"
```

Assim, a senha não precisa ficar armazenada diretamente no código-fonte.

---

# 📁 Estrutura do projeto

A estrutura das pastas do projeto é:

```text
com.example.SendEmail
│
├── application
│   └── SendEmailApplication.java
│       └── Classe principal para inicializar a aplicação Spring Boot
│
├── controller
│   └── SendEmailController.java
│       └── Controlador responsável pelas requisições de envio
│
├── dto
│   └── EmailRequestDTO.java
│       └── Objeto utilizado para transportar os dados do e-mail
│
├── exception
│   ├── GlobalExceptionHandler.java
│   │   └── Tratamento global das exceções
│   │
│   └── SendEmailException.java
│       └── Exceção personalizada para falhas no envio
│
└── service
    └── SendEmailService.java
        └── Serviço responsável pelo envio dos e-mails
```

---

# ▶️ Como executar o projeto

## 1️⃣ Pré-requisitos

Certifique-se de possuir:

* ☕ Java instalado
* 📦 Maven instalado
* 🔐 Uma conta Google configurada para envio
* 🔑 Uma Senha de app configurada

---

## 2️⃣ Clone o projeto

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta:

```bash
cd SendEmail
```

---

## 3️⃣ Configure o `application.properties`

Configure seu usuário do Gmail e sua Senha de app:

```properties
spring.mail.username=seuemail@gmail.com
spring.mail.password=suasenhadeapp
```

---

## 4️⃣ Instale as dependências

Execute:

```bash
mvn clean install
```

---

## 5️⃣ Execute a aplicação

Execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

---

# 📮 Endpoints

A API possui dois endpoints para envio de e-mails.

| Método | Endpoint               | Tipo       |
| ------ | ---------------------- | ---------- |
| `POST` | `/api/email/send-text` | Texto puro |
| `POST` | `/api/email/send-html` | HTML       |

---

# ✉️ Enviando e-mail em texto

## Endpoint

```http
POST /api/email/send-text
```

Localmente:

```text
http://localhost:8080/api/email/send-text
```

### 📦 Body JSON

```json
{
    "to": "destinatario@gmail.com",
    "subject": "Teste de envio de e-mail",
    "body": "Este é um teste de envio de e-mail usando Spring Boot."
}
```

O conteúdo será enviado como **texto puro**.

---

# 🌐 Enviando e-mail HTML

## Endpoint

```http
POST /api/email/send-html
```

Localmente:

```text
http://localhost:8080/api/email/send-html
```

### 📦 Body JSON

```json
{
    "to": "destinatario@gmail.com",
    "subject": "Bem-vindo!",
    "body": "<h1>Olá!</h1><p>Seu cadastro foi realizado com sucesso.</p>"
}
```

O conteúdo será interpretado como HTML pelo cliente de e-mail.

### 🎨 Exemplo mais completo

```json
{
    "to": "destinatario@gmail.com",
    "subject": "Confirmação de cadastro",
    "body": "<div style='font-family: Arial;'><h1>Olá!</h1><p>Seu cadastro foi realizado com sucesso.</p><p><strong>Obrigado por utilizar nossa aplicação!</strong></p></div>"
}
```

---

# 🧩 DTO

O `EmailRequestDTO` representa os dados recebidos pela API:

```java
public class EmailRequestDTO {

    private String to;
    private String subject;
    private String body;

    // Getters e Setters
}
```

Os atributos representam:

| Atributo  | Descrição                |
| --------- | ------------------------ |
| `to`      | Endereço do destinatário |
| `subject` | Assunto do e-mail        |
| `body`    | Conteúdo do e-mail       |

---

# ⚙️ SendEmailService

O serviço possui dois métodos diferentes para atender aos dois tipos de envio.

## ✉️ Envio de texto

O método `sendTextEmail()` utiliza `SimpleMailMessage`:

```java
public void sendTextEmail(String to, String subject, String body) {
    try {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("joaopauloaramuni@gmail.com");

        mailSender.send(message);

    } catch (MailException e) {
        throw new SendEmailException(
            "Falha ao enviar e-mail: " + e.getMessage()
        );
    }
}
```

---

## 🌐 Envio de HTML

Para enviar HTML é utilizado `MimeMessage` com `MimeMessageHelper`:

```java
public void sendHtmlEmail(String to, String subject, String html) {
    try {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
            new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("joaopauloaramuni@gmail.com");
        helper.setText(html, true);

        mailSender.send(message);

    } catch (MailException | MessagingException e) {
        throw new SendEmailException(
            "Falha ao enviar e-mail HTML: " + e.getMessage()
        );
    }
}
```

O segundo parâmetro `true` em:

```java
helper.setText(html, true);
```

indica que o conteúdo deve ser interpretado como **HTML**.

---

# 🎮 SendEmailController

O controller disponibiliza os dois endpoints:

```java
@PostMapping("/send-text")
public String sendTextEmail(@RequestBody EmailRequestDTO emailRequest) {

    sendEmailService.sendTextEmail(
        emailRequest.getTo(),
        emailRequest.getSubject(),
        emailRequest.getBody()
    );

    return "E-mail de texto enviado com sucesso!";
}
```

E:

```java
@PostMapping("/send-html")
public String sendHtmlEmail(@RequestBody EmailRequestDTO emailRequest) {

    sendEmailService.sendHtmlEmail(
        emailRequest.getTo(),
        emailRequest.getSubject(),
        emailRequest.getBody()
    );

    return "E-mail HTML enviado com sucesso!";
}
```

---

# 🚨 Tratamento de exceções

O projeto possui uma exceção personalizada chamada:

```text
SendEmailException
```

Ela é utilizada quando ocorre uma falha durante o envio do e-mail.

Exemplo:

```java
throw new SendEmailException(
    "Falha ao enviar e-mail: " + e.getMessage()
);
```

O `GlobalExceptionHandler` é responsável por capturar essas exceções e retornar uma resposta adequada para a API.

Essa abordagem evita colocar o tratamento de erros diretamente em cada controller.

---

# 🔄 Fluxo da aplicação

### ✉️ E-mail em texto

```text
Cliente → SendEmailController → SendEmailService → JavaMailSender → Gmail SMTP → 📧 Destinatário
```

### ✉️ E-mail em html

```text
Cliente → SendEmailController → SendEmailService → MimeMessage → JavaMailSender → Gmail SMTP → 🌐 Destinatário
```

---

# 🧪 Testando a API

Você pode testar os endpoints utilizando ferramentas como:

* Postman
* Insomnia
* Thunder Client
* cURL

### Exemplo com cURL — texto

```bash
curl -X POST http://localhost:8080/api/email/send-text \
-H "Content-Type: application/json" \
-d '{
    "to": "destinatario@gmail.com",
    "subject": "Teste",
    "body": "Olá! Este é um teste."
}'
```

### Exemplo com cURL — HTML

```bash
curl -X POST http://localhost:8080/api/email/send-html \
-H "Content-Type: application/json" \
-d '{
    "to": "destinatario@gmail.com",
    "subject": "Teste HTML",
    "body": "<h1>Olá!</h1><p>Este é um e-mail HTML.</p>"
}'
```

---

# 📚 Documentação e links úteis

### 🌱 Spring Boot

[Spring Boot Documentation](https://docs.spring.io/spring-boot/?utm_source=chatgpt.com)

Documentação oficial do Spring Boot.

### 📧 Spring Email

[Spring Boot Email / JavaMailSender](https://docs.spring.io/spring-boot/reference/io/email.html?utm_source=chatgpt.com)

Documentação relacionada ao envio de e-mails utilizando Spring Boot.

### ☕ Jakarta Mail

[Jakarta Mail Documentation](https://jakarta.ee/specifications/mail/?utm_source=chatgpt.com)

Especificação utilizada para trabalhar com mensagens de e-mail, incluindo mensagens MIME e HTML.

### 📦 Maven

[Maven Documentation](https://maven.apache.org/guides/?utm_source=chatgpt.com)

Documentação oficial do Maven.

### 🔐 Conta Google

[Conta Google](https://myaccount.google.com/?utm_source=chatgpt.com)

Gerenciamento da conta Google.

### 🔑 Senhas de app

[Google — Senhas de app](https://myaccount.google.com/apppasswords?utm_source=chatgpt.com)

Página utilizada para criar e gerenciar Senhas de app.

### 🔒 Verificação em duas etapas

[Google — Verificação em duas etapas](https://myaccount.google.com/signinoptions/two-step-verification?utm_source=chatgpt.com)

Configuração necessária para utilizar Senhas de app em contas compatíveis.

---

# 🛡️ Boas práticas de segurança

Nunca coloque uma senha real diretamente no código-fonte ou publique credenciais no GitHub.

❌ Evite:

```properties
spring.mail.password=abcdefghijklmnop
```

Prefira:

```properties
spring.mail.password=${MAIL_PASSWORD}
```

E configure a variável de ambiente:

```bash
export MAIL_PASSWORD="abcdefghijklmnop"
```

Também é recomendado adicionar arquivos contendo credenciais ao `.gitignore`:

```gitignore
application-local.properties
.env
```

---

# 📄 Licença

Este projeto está licenciado sob a **Licença MIT**.
