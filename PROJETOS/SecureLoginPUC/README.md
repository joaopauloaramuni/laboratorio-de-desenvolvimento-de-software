# Projeto SecureLoginPUC

## Descrição
O SecureLoginPUC é um projeto de aplicação web que implementa um sistema de login seguro utilizando Spring Boot e Spring Security. O objetivo é permitir a autenticação de usuários, diferenciando entre usuários comuns e administradores, e garantindo o acesso apropriado às páginas da aplicação.

## Estrutura do Projeto

```
SecureLoginPUC
│
├── src
│   └── main
│       ├── java
│       │   └── com.example.SecureLoginPUC
│       │       ├── application
│       │       │   └── SecureLoginPUCApplication.java
│       │       ├── config
│       │       │   ├── SecurityConfig.java
│       │       │   └── UserConfig.java
│       │       └── controller
│       │           └── SecureLoginPUCController.java
│       └── resources
│           ├── application.properties
│           ├── static
│           │   ├── css
│           │   │   ├── login.css
│           │   │   ├── register.css
│           │   │   └── style.css
│           │   └── images
│           │       └── logo-puc-minas.png
│           └── templates
│               ├── admin.html
│               ├── error.html
│               ├── home.html
│               ├── login.html
│               ├── recoverpassword.html
│               └── register.html

```

## Configuração do application.properties

```properties
spring.application.name=SecureLoginPUC
app.user.username=joao
app.user.password=4321
app.admin.username=admin
app.admin.password=1234
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

| <img src="https://joaopauloaramuni.github.io/java-imgs/SecureLoginPUC/imgs/login_v2.png" alt="Login" width="1000"/> |
|:----------------------------------------------------:|
|                        Login                         |

| <img src="https://joaopauloaramuni.github.io/java-imgs/SecureLoginPUC/imgs/registro_v2.png" alt="Registro" width="1000"/> |
|:----------------------------------------------------------:|
|                          Registro                          |

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

http://localhost:8080/error

http://localhost:8080/admin

http://localhost:8080/register

http://localhost:8080/recoverpassword

## Licença
Este projeto está licenciado sob a MIT License.
