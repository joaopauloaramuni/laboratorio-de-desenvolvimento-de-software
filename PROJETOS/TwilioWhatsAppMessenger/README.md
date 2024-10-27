# TwilioWhatsAppMessenger

Este projeto é uma aplicação Spring Boot que utiliza a API do Twilio para enviar mensagens via WhatsApp. A aplicação permite o envio de mensagens simples e lembretes de compromissos para números de WhatsApp.

## Dependências

### pom.xml

```xml
<!-- Twilio SDK -->
<dependency>
    <groupId>com.twilio.sdk</groupId>
    <artifactId>twilio</artifactId>
    <version>10.6.2</version>
</dependency>

<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20240303</version>
</dependency>
```

### application.properties

```properties
spring.application.name=TwilioWhatsAppMessenger
twilio_account_ssid=seuaccountssidaqui
twilio_auth_token=seuauthtokenaqui
twilio_whatsapp_number=14155238886
your_whatsapp_number=553180402103
```

## Estrutura do Projeto

```
application
└── TwilioWhatsAppMessengerApplication.java
config
└── TwilioConfig.java
controller
└── WhatsAppMessageController.java
dto
├── AppointmentReminderDTO.java
└── MessageDTO.java
service
└── WhatsAppMessageService.java
```

## Endpoints

### `/whatsapp`

#### `POST /sendMessage`

```java
@PostMapping("/sendMessage")
public ResponseEntity<String> sendMessage(@RequestBody MessageDTO request) {
    return ResponseEntity.ok(whatsAppService.sendMessage(request.getTo(), request.getMessage()));
}
```

**Exemplo de requisição:**
```json
{
    "to": "553180402103",
    "message": "Olá! Esta é uma mensagem de teste."
}
```

#### `POST /sendAppointmentReminder`

```java
@PostMapping("/sendAppointmentReminder")
public ResponseEntity<String> sendAppointmentReminder(@RequestBody AppointmentReminderDTO request) {
    return ResponseEntity.ok(whatsAppService.sendAppointmentReminder(request.getTo(), request.getMessage(), request.getContentSid(), request.getContentVariables()));
}
```

**Exemplo de requisição:**
```json
{
    "to": "553180402103",
    "message": "Mensagem de teste.",
    "contentSid": "HXb5b62575e6e4ff6129ad7c8efe1f983e",
    "contentVariables": {
        "1": "12/1",
        "2": "3pm"
    }
}
```

## Principais métodos de serviço

### `sendMessage`

```java
public String sendMessage(String to, String msg) {
    try {
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:+" + to),
                        new PhoneNumber("whatsapp:+" + twilioConfig.getTwiliowhatsappNumber()),
                        msg)
                .create();
        return "Mensagem enviada com SID: " + message.getSid();
    } catch (Exception e) {
        e.printStackTrace();
        return "Erro ao enviar a mensagem: " + e.getMessage();
    }
}
```

### `sendAppointmentReminder`

```java
public String sendAppointmentReminder(String to, String msg, String contentSid, Map<String, String> contentVariables) {
    try {
        String contentVariablesJson = new JSONObject(contentVariables).toString();
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:+" + to),
                        new PhoneNumber("whatsapp:+" + twilioConfig.getTwiliowhatsappNumber()),
                        msg)
                .setContentSid(contentSid)
                .setContentVariables(contentVariablesJson)
                .create();
        return "Lembrete de compromisso enviado com SID: " + message.getSid();
    } catch (Exception e) {
        e.printStackTrace();
        return "Erro ao enviar lembrete de compromisso: " + e.getMessage();
    }
}
```

## Links Úteis da Documentação

- [Twilio Docs](https://www.twilio.com/docs)
- [Twilio Messaging Guides](https://www.twilio.com/docs/messaging/guides/how-to-use-your-free-trial-account)

### Console Twilio

- [API Keys & Credentials](https://console.twilio.com/us1/account/keys-credentials/api-keys)
- [WhatsApp Learn](https://console.twilio.com/us1/develop/sms/try-it-out/whatsapp-learn)
- [Content Template Builder](https://console.twilio.com/us1/develop/sms/content-template-builder)

## Licença

Este projeto é de código aberto e está licenciado sob a MIT License. Sinta-se livre para usá-lo e modificá-lo conforme necessário.

