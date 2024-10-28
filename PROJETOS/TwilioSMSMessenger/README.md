# TwilioSMSMessenger
Este projeto é uma aplicação Spring Boot que integra a API do Twilio para enviar mensagens SMS de maneira eficiente e simplificada. A aplicação permite que usuários enviem mensagens de texto para números de telefone registrados em [Phone numbers](https://console.twilio.com/us1/develop/phone-numbers/manage/incoming) na plataforma Twilio, utilizando números de telefone adquiridos e gerenciados diretamente pelo console do Twilio.

## Dependências

### pom.xml

```xml
<!-- Twilio SDK -->
<dependency>
    <groupId>com.twilio.sdk</groupId>
    <artifactId>twilio</artifactId>
    <version>10.6.2</version>
</dependency>
```

### application.properties

```properties
spring.application.name=TwilioWhatsAppMessenger
twilio_account_ssid=seuaccountssidaqui
twilio_auth_token=seuauthtokenaqui
twilio_sms_number=fromnumber #https://console.twilio.com/us1/develop/phone-numbers/manage/incoming
your_sms_number=5531980402103
```

## Estrutura do Projeto

```
application
└── TwilioWhatsAppMessengerApplication.java
config
└── TwilioConfig.java
controller
└── SMSMessageController.java
dto
└── MessageDTO.java
service
└── SMSMessageService.java
```

## Endpoints

### `/sms`

#### `POST /send`

```java
@PostMapping("/send")
public ResponseEntity<String> sendMessage(@RequestBody MessageDTO request) {
    return ResponseEntity.ok(smsMessageService.sendSMS(request.getTo(), request.getMessage()));
}
```

**Exemplo de requisição:**
```json
{
    "to": "5531980402103",
    "message": "Olá! Esta é uma mensagem de teste."
}
```

## Principal método de serviço

### `sendMessage`

```java
public String sendSMS(String to, String msg) {
    try {
        Message message = Message.creator(
                new PhoneNumber("+" + to),
                new PhoneNumber("+" + twilioConfig.getTwilioSMSNumber()),
                msg)
                            .create();
        return "Mensagem enviada com SID: " + message.getSid();
    }catch (Exception e) {
        e.printStackTrace();
        return "Erro ao enviar a mensagem: " + e.getMessage();
    }
}
```

## Links Úteis da Documentação

- [Twilio Docs](https://www.twilio.com/docs)
- [Twilio Messaging Guides](https://www.twilio.com/docs/messaging/guides/how-to-use-your-free-trial-account)

### Console Twilio

- [API Keys & Credentials](https://console.twilio.com/us1/account/keys-credentials/api-keys)
- [SMS Learn](https://console.twilio.com/us1/develop/sms/try-it-out/send-an-sms)
- [Content Template Builder](https://console.twilio.com/us1/develop/sms/content-template-builder)

## Licença

Este projeto é de código aberto e está licenciado sob a MIT License. Sinta-se livre para usá-lo e modificá-lo conforme necessário.
