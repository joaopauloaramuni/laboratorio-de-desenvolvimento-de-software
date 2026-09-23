# Projeto Captcha

## Descrição
O **Captcha** é um projeto de aplicação web desenvolvido com Spring Boot e Thymeleaf que reúne, em um só lugar, **oito tipos diferentes de captcha** funcionando lado a lado. Cada captcha possui sua própria página de demonstração, seu próprio service e seu próprio controller, permitindo que o aluno teste todos, escolha o que melhor se encaixa no seu projeto e copie apenas os arquivos necessários.

Cada página de captcha também exibe um painel **"Para usar no seu projeto"**, com a lista de arquivos a copiar, o trecho do HTML, as propriedades do `application.properties`, o nome do campo recebido no POST e onde obter as chaves.

## Captura de Tela

| <img src="https://joaopauloaramuni.github.io/java-imgs/Captcha/imgs/index.png" alt="Index" width="1000"/> |
|:----------------------------------------------------:|
|                        Index                         |

## Captchas disponíveis

| Captcha | URL | O que o usuário faz | Cadastro | Funciona sem internet | Contra robôs |
|---|---|---|---|---|---|
| reCAPTCHA v2 checkbox | `/recaptcha-v2-checkbox` | Marca a caixa; às vezes escolhe imagens | Google | Não | Alta |
| reCAPTCHA v2 invisível | `/recaptcha-v2-invisivel` | Só clica em enviar | Google | Não | Alta |
| reCAPTCHA v3 | `/recaptcha-v3` | Nada; o servidor recebe uma nota | Google | Não | Alta |
| hCaptcha | `/hcaptcha` | Marca a caixa; às vezes escolhe imagens | hCaptcha | Não | Alta |
| Cloudflare Turnstile | `/turnstile` | Normalmente nada; às vezes um clique | Cloudflare | Não | Alta |
| ALTCHA | `/altcha` | Marca a caixa; o navegador calcula | Nenhum | Sim* | Média |
| Captcha de imagem | `/captcha-imagem` | Digita as letras da imagem | Nenhum | Sim | Média |
| Captcha matemático | `/captcha-matematico` | Resolve uma conta | Nenhum | Sim | Baixa |

\* Desde que o script `altcha.js` seja salvo dentro do projeto em vez de carregado pelo CDN.

### 🟦 Google reCAPTCHA v2 (checkbox)
A tradicional caixa **"Não sou um robô"**. O Google analisa o comportamento do visitante e, quando desconfia, apresenta um desafio de imagens (semáforos, faixas de pedestre, etc.). É a mesma versão utilizada no projeto SecureLoginPUC.

- **Campo enviado no POST:** `g-recaptcha-response`
- **Chave de teste:** sim, o Google fornece chaves que sempre aprovam.

### 🟦 Google reCAPTCHA v2 (invisível)
Mesma tecnologia do v2, mas sem a caixa de seleção. A verificação acontece quando o usuário clica no botão de enviar, e o desafio de imagens só aparece em caso de suspeita. Um pequeno selo do reCAPTCHA fica fixo no canto da tela.

- **Campo enviado no POST:** `g-recaptcha-response`
- **Observação:** o tipo de chave é diferente do checkbox. Ao criar a chave, escolha **v2 > Selo invisível**.

### 🟦 Google reCAPTCHA v3
Não exibe nenhum desafio. O Google observa a navegação e devolve uma **pontuação (score) de 0.0 a 1.0**, onde valores próximos de 1.0 indicam um humano. Cabe ao servidor decidir a partir de qual nota a requisição é aceita (neste projeto, `0.5`). Além do `success`, o service confere o `score` e a `action`.

- **Campo enviado no POST:** `g-recaptcha-response`
- **Chave de teste:** o Google **não** oferece chave de teste para o v3. É necessário criar uma chave real com o domínio `localhost`.

### 🟩 hCaptcha
Alternativa ao reCAPTCHA v2 com foco em privacidade. Funciona de forma quase idêntica: uma caixa de seleção com desafio de imagens quando necessário. No código, mudam apenas o script, a classe da `div`, a URL de validação e o nome do campo.

- **Campo enviado no POST:** `h-captcha-response`
- **Chave de teste:** sim.

### 🟧 Cloudflare Turnstile
Na maioria das vezes o usuário não precisa fazer nada: o widget verifica o navegador sozinho e fica verde. Em alguns casos, pede um clique. É gratuito e **não exige que o site utilize a Cloudflare**.

- **Campo enviado no POST:** `cf-turnstile-response`
- **Chave de teste:** sim.

### 🟪 ALTCHA
Utiliza **prova de trabalho (proof-of-work)**. Ao marcar a caixa, o navegador precisa encontrar um número secreto testando milhares de possibilidades até gerar o mesmo hash SHA-256 enviado pelo servidor. Para uma pessoa, leva menos de um segundo; para um robô enviando milhares de formulários, o custo se torna alto. Não depende de nenhuma empresa, não usa cookies e o desafio é **criado e validado pelo próprio Spring**, apenas com classes nativas do Java.

- **Campo enviado no POST:** `altcha` (texto em Base64)
- **Chave:** a chave HMAC é um texto aleatório inventado por você.
- **Observação:** o widget utiliza a Web Crypto do navegador, que só funciona em `https` ou em `localhost`.

### ⬜ Captcha de imagem
O captcha clássico: letras distorcidas desenhadas em uma imagem PNG gerada pelo próprio Java (`java.awt`), com linhas de ruído. O texto correto fica guardado na **sessão do usuário**, nunca no HTML, e é apagado a cada tentativa, certa ou errada. Não depende de internet nem de cadastro.

- **Campo enviado no POST:** `captcha`

### ⬜ Captcha matemático
Uma conta simples de somar, subtrair ou multiplicar. É o mais fácil de entender e implementar, sendo ideal para aprender o conceito de **desafio guardado na sessão**. Em compensação, é o mais fácil de ser resolvido por um robô.

- **Campo enviado no POST:** `resposta`

## Estrutura do Projeto

```text
📁 Captcha
│
├── 📁 src
│   └── 📁 main
│       │
│       ├── ☕ java
│       │   └── 📦 com.example.Captcha
│       │       │
│       │       ├── 🚀 CaptchaApplication.java
│       │       │   └── Classe principal da aplicação Spring Boot
│       │       │
│       │       ├── 🎮 controller
│       │       │   ├── HomeController.java
│       │       │   │   └── Página inicial com a tabela comparativa
│       │       │   ├── RecaptchaV2CheckboxController.java
│       │       │   ├── RecaptchaV2InvisivelController.java
│       │       │   ├── RecaptchaV3Controller.java
│       │       │   ├── HCaptchaController.java
│       │       │   ├── TurnstileController.java
│       │       │   ├── AltchaController.java
│       │       │   │   └── Inclui o endereço /altcha/desafio
│       │       │   ├── CaptchaImagemController.java
│       │       │   │   └── Inclui o endereço /captcha-imagem/imagem
│       │       │   └── CaptchaMatematicoController.java
│       │       │       └── GET exibe a página, POST valida o captcha
│       │       │
│       │       └── ⚙️ service
│       │           ├── RecaptchaV2Service.java
│       │           ├── RecaptchaV3Service.java
│       │           ├── HCaptchaService.java
│       │           ├── TurnstileService.java
│       │           ├── AltchaService.java
│       │           ├── CaptchaImagemService.java
│       │           └── CaptchaMatematicoService.java
│       │               └── Toda a lógica de validação de cada captcha
│       │
│       └── 📁 resources
│           │
│           ├── ⚙️ application.properties
│           │   └── Chaves e configurações de cada captcha
│           │
│           ├── 🎨 static
│           │   ├── 🎨 css
│           │   │   └── style.css
│           │   │       └── Estilo da vitrine (não precisa copiar)
│           │   │
│           │   └── 🖼️ images
│           │       └── robo.svg
│           │           └── Ilustração da página inicial
│           │
│           └── 🌐 templates
│               ├── index.html
│               │   └── Página inicial com a comparação dos captchas
│               ├── recaptcha-v2-checkbox.html
│               ├── recaptcha-v2-invisivel.html
│               ├── recaptcha-v3.html
│               ├── hcaptcha.html
│               ├── turnstile.html
│               ├── altcha.html
│               ├── captcha-imagem.html
│               ├── captcha-matematico.html
│               │   └── Uma página de demonstração por captcha
│               │
│               └── 📁 fragments
│                   └── layout.html
│                       └── Cabeçalho, head e mensagem de resultado compartilhados
│
└── 📄 pom.xml
    └── Dependências e configurações do Maven
```

## Services

### RecaptchaV2Service
Valida o token das duas versões do reCAPTCHA v2 (checkbox e invisível), que funcionam da mesma forma no servidor e mudam apenas o par de chaves. Envia o token e a secret key para `https://www.google.com/recaptcha/api/siteverify` e lê o campo `success` da resposta. Possui os métodos `validarCheckbox(token)` e `validarInvisivel(token)`. Se o seu projeto usar só uma das versões, basta apagar o método e a chave da outra.

### RecaptchaV3Service
Valida o token do reCAPTCHA v3 na mesma URL do Google, mas vai além do `success`: confere se o `score` é maior ou igual ao mínimo configurado em `recaptcha.v3.score-minimo` e se a `action` retornada é igual à usada no `grecaptcha.execute(...)` do HTML. Retorna um `record ResultadoV3` com o resultado, o score e o motivo de uma eventual recusa.

### HCaptchaService
Valida o token do hCaptcha em `https://api.hcaptcha.com/siteverify`. Envia também a site key, o que garante que o token foi gerado pelo seu próprio widget.

### TurnstileService
Valida o token do Cloudflare Turnstile em `https://challenges.cloudflare.com/turnstile/v0/siteverify`. Envia também o IP do usuário (`remoteip`), que ajuda a Cloudflare a detectar abuso.

### AltchaService
Cria e valida os desafios do ALTCHA sem nenhuma biblioteca externa. O método `criarDesafio()` sorteia um salt com prazo de validade e um número secreto, calcula o hash SHA-256 e o assina com HMAC. O método `validar(payload)` decodifica o Base64 enviado pelo widget e confere quatro pontos: se o prazo não expirou, se o número encontrado gera o mesmo hash, se a assinatura é válida e se o desafio ainda não foi utilizado.

### CaptchaImagemService
Sorteia um texto, guarda na sessão e desenha a imagem PNG com letras de tamanhos, cores e inclinações diferentes, além de linhas de ruído. Letras facilmente confundíveis (como `0` e `O`, `1` e `I`) foram retiradas. O método `validar(session, resposta)` compara sem diferenciar maiúsculas e minúsculas e apaga o texto da sessão.

### CaptchaMatematicoService
Gera uma conta aleatória de soma, subtração ou multiplicação, guarda o resultado na sessão e confere a resposta digitada. Na subtração, o maior número vem primeiro para o resultado nunca ser negativo.

## Configuração do application.properties

As propriedades usam o formato `${VARIAVEL_DE_AMBIENTE:valor_padrao}`. O Spring usa a variável de ambiente quando ela existe e, caso contrário, o valor padrão. Os valores padrão abaixo são as **chaves de teste oficiais** de cada provedor, que sempre aprovam o captcha. Em produção, defina as variáveis de ambiente com as suas chaves reais.

```properties
spring.application.name=Captcha

# Google reCAPTCHA v2 - checkbox
recaptcha.v2.checkbox.site-key=${RECAPTCHA_V2_CHECKBOX_SITE_KEY:6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI}
recaptcha.v2.checkbox.secret-key=${RECAPTCHA_V2_CHECKBOX_SECRET_KEY:6LeIxAcTAAAAAGG-vFI1TRnWxEg4jB4kFGeWtxJ-FXM}

# Google reCAPTCHA v2 - invisível
recaptcha.v2.invisivel.site-key=${RECAPTCHA_V2_INVISIVEL_SITE_KEY:6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI}
recaptcha.v2.invisivel.secret-key=${RECAPTCHA_V2_INVISIVEL_SECRET_KEY:6LeIxAcTAAAAAGG-vFI1TRnWxEg4jB4kFGeWtxJ-FXM}

# Google reCAPTCHA v3 (não possui chave de teste)
recaptcha.v3.site-key=${RECAPTCHA_V3_SITE_KEY:COLOQUE_SUA_SITE_KEY_V3}
recaptcha.v3.secret-key=${RECAPTCHA_V3_SECRET_KEY:COLOQUE_SUA_SECRET_KEY_V3}
recaptcha.v3.score-minimo=0.5

# hCaptcha
hcaptcha.site-key=${HCAPTCHA_SITE_KEY:10000000-ffff-ffff-ffff-000000000001}
hcaptcha.secret-key=${HCAPTCHA_SECRET_KEY:0x0000000000000000000000000000000000000000}

# Cloudflare Turnstile
turnstile.site-key=${TURNSTILE_SITE_KEY:1x00000000000000000000AA}
turnstile.secret-key=${TURNSTILE_SECRET_KEY:1x0000000000000000000000000000000AA}

# ALTCHA (a chave HMAC é um texto aleatório criado por você)
altcha.hmac-key=${ALTCHA_HMAC_KEY:troque-esta-chave-por-um-texto-longo-e-aleatorio}
altcha.max-number=100000
altcha.validade-minutos=10

# Captcha de imagem
captcha.imagem.tamanho=5
```

> ⚠️ A **site key** é pública e vai no HTML. A **secret key** nunca pode aparecer no HTML, no JavaScript ou em um repositório público. Se uma secret key real for publicada no GitHub, gere uma nova no painel do provedor: apagar o commit não basta, pois o histórico continua acessível.

Para gerar uma chave HMAC aleatória para o ALTCHA:

```bash
openssl rand -base64 48
```

## Versão do Java e do Spring Boot

- **Java:** 17
- **Spring Boot:** 4.1.1

O Spring Boot 4 utiliza o **Jackson 3**, cujo pacote mudou de `com.fasterxml.jackson.databind` para `tools.jackson.databind`. Os services deste projeto já utilizam os novos imports:

```java
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
```

## Dependências

```xml
<!-- Dependência do Thymeleaf para o Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Dependência do Spring Web MVC (inclui o Tomcat e o Jackson) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<!-- Dependência de testes do Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Dependência de testes do Spring Web MVC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>
```

Nenhum captcha exige biblioteca adicional no `pom.xml`. O restante vem do próprio Java:

- `java.net.http.HttpClient`: chamada às APIs do Google, hCaptcha e Cloudflare.
- `java.awt` e `javax.imageio`: geração da imagem do captcha de imagem.
- `java.security.MessageDigest` e `javax.crypto.Mac`: SHA-256 e HMAC do ALTCHA.
- `HttpSession`: armazenamento das respostas dos captchas de imagem e matemático.

A parte visual de cada captcha é carregada por `<script>` diretamente do provedor (Google, hCaptcha, Cloudflare e jsDelivr, no caso do ALTCHA).

## Como executar

```bash
mvn spring-boot:run
```

Acesse `http://localhost:8080`. Utilize `localhost` em vez de `127.0.0.1`, pois é o domínio liberado nas chaves de teste e é considerado seguro pelo navegador, requisito do ALTCHA.

## Como levar um captcha para o seu projeto

1. **Service:** copie o arquivo da pasta `service/` e ajuste o `package`.
2. **HTML:** nos templates, o captcha fica entre os comentários `CAPTCHA: INÍCIO` e `CAPTCHA: FIM`. Copie esse trecho para dentro do seu `<form>`, junto com o `<script>` do fim da página.
3. **Propriedades:** copie o bloco correspondente do `application.properties`.
4. **Controller:** envie a `siteKey` para o template no GET e chame o service no POST antes de processar o formulário.

Para proteger o login do **Spring Security**, valide o captcha em um filtro que roda antes da autenticação, como o `RecaptchaFilter` do projeto SecureLoginPUC, e registre-o com `addFilterBefore(..., UsernamePasswordAuthenticationFilter.class)`. Lembre-se também de liberar no `permitAll()` os endereços extras utilizados por alguns captchas: `/captcha-imagem/imagem` e `/altcha/desafio`.

## Urls do projeto:
http://localhost:8080/

http://localhost:8080/recaptcha-v2-checkbox

http://localhost:8080/recaptcha-v2-invisivel

http://localhost:8080/recaptcha-v3

http://localhost:8080/hcaptcha

http://localhost:8080/turnstile

http://localhost:8080/altcha

http://localhost:8080/captcha-imagem

http://localhost:8080/captcha-matematico

## Documentação e links úteis

### Google reCAPTCHA
- [Painel de administração (criar chaves)](https://www.google.com/recaptcha/admin)
- [reCAPTCHA v2 checkbox](https://developers.google.com/recaptcha/docs/display)
- [reCAPTCHA v2 invisível](https://developers.google.com/recaptcha/docs/invisible)
- [reCAPTCHA v3](https://developers.google.com/recaptcha/docs/v3)
- [Validação no servidor (siteverify)](https://developers.google.com/recaptcha/docs/verify)
- [FAQ e chaves de teste](https://developers.google.com/recaptcha/docs/faq)

### hCaptcha
- [Painel (criar chaves)](https://dashboard.hcaptcha.com)
- [Documentação oficial](https://docs.hcaptcha.com)

### Cloudflare Turnstile
- [Painel da Cloudflare](https://dash.cloudflare.com)
- [Documentação oficial](https://developers.cloudflare.com/turnstile/)
- [Chaves de teste](https://developers.cloudflare.com/turnstile/troubleshooting/testing/)

### ALTCHA
- [Documentação oficial](https://altcha.org/docs/)
- [Repositório no GitHub](https://github.com/altcha-org/altcha)

### Spring, Thymeleaf e Java
- [Documentação do Spring Boot](https://docs.spring.io/spring-boot/)
- [Documentação do Thymeleaf](https://www.thymeleaf.org/documentation.html)
- [Jackson (GitHub)](https://github.com/FasterXML/jackson)
- [HttpClient do Java 17](https://docs.oracle.com/en/java/javase/17/docs/api/java.net.http/java/net/http/HttpClient.html)

## Licença
Este projeto está licenciado sob a MIT License.
