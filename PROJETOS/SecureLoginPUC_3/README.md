# Projeto SecureLoginPUC

## Descrição
O SecureLoginPUC é um projeto de aplicação web que implementa um sistema de login seguro utilizando Spring Boot e Spring Security. O objetivo é permitir a autenticação de usuários, diferenciando entre usuários comuns e administradores, e garantindo o acesso apropriado às páginas da aplicação.

## Estrutura do Projeto

```text
📁 SecureLoginPUC
│
├── 📁 src
│   └── 📁 main
│       │
│       ├── ☕ java
│       │   └── 📦 com.example.SecureLoginPUC
│       │       │
│       │       ├── 🚀 application
│       │       │   └── SecureLoginPUCApplication.java
│       │       │       └── Classe principal da aplicação Spring Boot
│       │       │
│       │       ├── 🔐 config
│       │       │   ├── SecurityConfig.java
│       │       │   │   └── Configurações do Spring Security
│       │       │   │
│       │       │   ├── UserConfig.java
│       │       │   │   └── Configuração dos usuários e chaves do reCAPTCHA
│       │       │   │
│       │       │   └── RecaptchaFilter.java
│       │       │       └── Filtro responsável pela validação do reCAPTCHA
│       │       │
│       │       ├── 🎮 controller
│       │       │   └── SecureLoginController.java
│       │       │       └── Controladores e rotas da aplicação
│       │       │
│       │       ├── ⚠️ exception
│       │       │   ├── GlobalExceptionHandler.java
│       │       │   │   └── Tratamento global de exceções
│       │       │   │
│       │       │   └── SendEmailException.java
│       │       │       └── Exceção relacionada ao envio de e-mails
│       │       │
│       │       └── ⚙️ service
│       │           ├── SendEmailService.java
│       │           │   └── Serviço responsável pelo envio de e-mails
│       │           │
│       │           ├── UserService.java
│       │           │   └── Serviço responsável pelo gerenciamento dos usuários
│       │           │
│       │           ├── PasswordRecoveryService.java
│       │           │   └── Serviço responsável pela recuperação de senha
│       │           │
│       │           └── RecaptchaService.java
│       │               └── Serviço responsável pela validação do Google reCAPTCHA
│       │
│       └── 📁 resources
│           │
│           ├── ⚙️ application.properties
│           │   └── Configurações da aplicação, e-mail e reCAPTCHA
│           │
│           ├── 🎨 static
│           │   │
│           │   ├── 🎨 css
│           │   │   ├── admin.css
│           │   │   ├── error.css
│           │   │   ├── home.css
│           │   │   ├── login.css
│           │   │   ├── recoverpassword.css
│           │   │   ├── register.css
│           │   │   └── resetpassword.css
│           │   │       └── Arquivos de estilização das páginas
│           │   │
│           │   └── 🖼️ images
│           │       └── pucminas-logo.png
│           │           └── Imagens utilizadas pela aplicação
│           │
│           └── 🌐 templates
│               ├── admin.html
│               │   └── Página da área administrativa
│               │
│               ├── error.html
│               │   └── Página apresentada quando ocorre um erro
│               │
│               ├── home.html
│               │   └── Página inicial após autenticação
│               │
│               ├── login.html
│               │   └── Página de login com Google reCAPTCHA
│               │
│               ├── recoverpassword.html
│               │   └── Página de recuperação de senha
│               │
│               ├── resetpassword.html
│               │   └── Página para redefinição da senha
│               │
│               └── register.html
│                   └── Página de cadastro de usuários
│
└── 📄 pom.xml
    └── Dependências e configurações do Maven
```

## Configuração do application.properties

```properties
spring.application.name=SecureLoginPUC
app.user.username=joaopauloaramuni@gmail.com
app.user.password=4321
app.user.name=Joao
app.admin.username=admin
app.admin.password=1234
app.admin.name=Administrador
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=joaopauloaramuni@gmail.com
# https://myaccount.google.com/apppasswords
# sua senha de app aqui (É necessário ativar a autenticação de dois fatores antes no Gmail)
spring.mail.password=hzpjaczvuyuwnjmt
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
recaptcha.site-key=6LeEwsItAAAAAB4wX5NCt_c72PDo2Vpvds0BOO5Y
recaptcha.secret-key=6LeEwsItAAAAAKbWz-EEfZn1BxfoanhCeMPTvB_e
```

## Dependências
```xml
<!-- Dependência do Spring Boot Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Dependência do Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Dependência do Thymeleaf para o Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Dependência do Spring Mail para o envio de email -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

```

# Thymeleaf

Thymeleaf é um motor de templates para Java que permite a criação de páginas HTML dinâmicas de forma simples e eficiente. Ele é frequentemente utilizado em aplicações Spring, proporcionando uma maneira intuitiva de gerar conteúdo HTML e manipular dados diretamente nas páginas.

## Principais Características

- **Natural Templating**: Os templates Thymeleaf são válidos como documentos HTML, permitindo que sejam visualizados em navegadores sem processamento.
- **Integração com Spring**: Thymeleaf se integra perfeitamente com o Spring Framework, facilitando a injeção de dependências e o acesso a beans do Spring.
- **Expressões de Template**: Utiliza uma sintaxe simples e expressiva para manipular dados, permitindo a criação de lógicas condicionais e loops diretamente nas páginas.

## Exemplo de Uso

Aqui está um exemplo simples de um template Thymeleaf:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Exemplo Thymeleaf</title>
</head>
<body>
    <h1 th:text="${titulo}">Título do Documento</h1>
    <ul>
        <li th:each="item : ${itens}" th:text="${item}"></li>
    </ul>
</body>
</html>
```

Neste exemplo, o título e a lista de itens são preenchidos dinamicamente com dados fornecidos pelo controlador Spring.

Thymeleaf é uma escolha poderosa para desenvolvedores que desejam criar interfaces web dinâmicas e interativas em aplicações Java. Com sua sintaxe intuitiva e forte integração com o Spring, ele se tornou uma ferramenta popular no ecossistema de desenvolvimento Java.

## Interface Gráfica

A interface gráfica permite ao usuário inserir seus dados de login e, após a autenticação, ser redirecionado para a página correspondente, onde terá acesso às funcionalidades e informações de acordo com suas credenciais.

### Captura de Tela

- **Login**: A página de login possui campos para inserir o nome de usuário e a senha. Ela  também exibe o logo da PUC Minas, proporcionando uma identificação visual clara da instituição. Abaixo do formulário de login, existem links para os usuários que ainda não possuem cadastro, direcionando-os para a página de registro, e para aqueles que esqueceram a senha, levando-os à página de recuperação de senha.

- **Register**: A página de registro permite que novos usuários criem uma conta na plataforma. Ela inclui campos para inserir **nome completo, e-mail, CPF, RG, endereço, instituição e senha**, garantindo que todas as informações necessárias para cadastro sejam coletadas. A lateral exibe o **logo da PUC Minas**, mantendo a identidade visual da instituição. Abaixo do formulário, há um link para os usuários que já possuem conta, direcionando-os de volta para a página de login.

| <img src="https://joaopauloaramuni.github.io/java-imgs/SecureLoginPUC_3/imgs/Login_v2.png" alt="Login" width="1000"/> |
|:----------------------------------------------------:|
|                        Login                         |

| <img src="https://joaopauloaramuni.github.io/java-imgs/SecureLoginPUC_3/imgs/Register_v2.png" alt="Register" width="1000"/> |
|:-------------------------------------------------------:|
|                        Register                         |

## Métodos da Classe SecurityConfig

### @Configuration
Indica que a classe contém métodos de configuração que geram beans para o contexto da aplicação.

### @EnableWebSecurity
Ativa a segurança da web, permitindo a configuração de regras de segurança para as URLs da aplicação.

### public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
Configura as regras de segurança das requisições HTTP, permitindo o acesso público às páginas de login e arquivos CSS, restringindo o acesso às páginas do administrador.

### public UserDetailsService userDetailsService()
Configura o gerenciamento de usuários em memória, criando um usuário comum e um administrador, codificando as senhas.

### public PasswordEncoder passwordEncoder()
Define o codificador de senhas a ser utilizado na aplicação, utilizando o BCryptPasswordEncoder.

## Urls do projeto:
http://localhost:8080/login

http://localhost:8080/login?logout=true

http://localhost:8080/home

http://localhost:8080/admin

http://localhost:8080/error

http://localhost:8080/register

http://localhost:8080/recoverpassword

## Licença
Este projeto está licenciado sob a MIT License.
