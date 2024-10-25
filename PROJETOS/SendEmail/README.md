# SendEmail

Este projeto é um serviço simples de envio de e-mails utilizando Spring Boot.

## Dependências

### pom.xml
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### application.properties
```
spring.application.name=SendEmail  
spring.mail.host=smtp.gmail.com  
spring.mail.port=587  
spring.mail.username=joaopauloaramuni@gmail.com  
# https://myaccount.google.com/apppasswords  
# sua senha de app aqui  
spring.mail.password=senhadeapp  
spring.mail.properties.mail.smtp.auth=true  
spring.mail.properties.mail.smtp.starttls.enable=true  
```

## Estrutura do projeto

A estrutura das pastas do projeto é a seguinte:

```
com.example.SendEmail
│
├── application
│   └── SendEmailApplication.java  # Classe principal para inicializar a aplicação Spring Boot
│
├── controller
│   └── SendEmailController.java  # Controlador que gerencia as requisições para enviar e-mail
│
├── dto
│   └── EmailRequestDTO.java  # Classe que representa o objeto de transferência de dados para a requisição de envio de e-mail
│
├── exception
│   └── GlobalExceptionHandler.java  # Classe que gerencia exceções globais da aplicação
│   └── SendEmailException.java  # Exceção personalizada para erros durante o envio de e-mails
│
└── service
    └── SendEmailService.java  # Serviço que contém a lógica para enviar e-mails

```

## Como rodar o projeto

Para rodar o projeto, siga os passos abaixo:

1. Certifique-se de ter o Maven instalado.
2. Navegue até a raiz do projeto no terminal.
3. Execute o comando para instalar as dependências do Maven:

```bash
mvn clean install
```

4. Para iniciar a aplicação, execute:

```bash
mvn spring-boot:run
```

## Endpoint

### POST /api/email/send

**Link para teste**:  
http://localhost:8080/api/email/send  

### Body JSON
{
  "to": "joaopauloaramuni@gmail.com",
  "subject": "Teste de Envio de Email",
  "body": "Este é um teste de envio de e-mail usando Spring Boot."
}

## DTO
public class EmailRequestDTO {

    private String to;
    private String subject;
    private String body;
    ...
}

## Método sendEmail

```java
package com.example.SendEmail.service;

import com.example.SendEmail.exception.SendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("joaopauloaramuni@gmail.com");

            mailSender.send(message);
        } catch (MailException e) {
            throw new SendEmailException("Falha ao enviar e-mail: " + e.getMessage());
        }
    }
}
```

## GlobalExceptionHandler e SendEmailException

A classe GlobalExceptionHandler é responsável por capturar exceções globais que ocorrem no aplicativo e retornar respostas adequadas. Ela pode ser configurada para lidar com exceções específicas, como a SendEmailException, que é lançada quando há uma falha no envio de e-mail.

## Licença

Este projeto está licenciado sob a Licença MIT.
