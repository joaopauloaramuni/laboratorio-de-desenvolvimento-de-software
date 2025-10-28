# 🌎 IBGE_Locations

Projeto **Spring Boot** + **Thymeleaf** que permite consultar estados e municípios do Brasil usando a API pública do IBGE.

---

## 🛠 Tecnologias

* Java 17
* Spring Boot
* Maven
* Thymeleaf
* HTML, CSS e JS

---

## ⚡ Funcionalidades

* Listar todos os estados (UFs)
* Carregar municípios dinamicamente ao selecionar um estado
* Interface simples e responsiva
* Consumo da API pública do IBGE: [https://servicodados.ibge.gov.br/api/v1/localidades](https://servicodados.ibge.gov.br/api/v1/localidades)

---

## 🖼️ Captura de Tela

| <img src="https://joaopauloaramuni.github.io/java-imgs/IBGE_Locations/imgs/home.png" alt="Home" width="600"/> |
|:---------:|
| Home |

---

## 📦 Dependências Maven

* **spring-boot-starter-web**: Para criar a aplicação web.
* **spring-boot-starter-thymeleaf**: Para renderizar templates HTML com Thymeleaf.
* **spring-boot-starter-test**: Para testes.

Exemplo no `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 📄 Estrutura do Projeto

```
IBGE_Locations/
│
├── src/main/java/com/example/IBGE_Locations
│   ├── controller/IbgeLocationsController.java
│   ├── service/IbgeLocationsService.java
│   └── model/Uf.java / Municipio.java
│
├── src/main/resources/templates/home.html
├── src/main/resources/static/css/style.css
└── src/main/resources/static/js/municipios.js
```

---

## 📝 Como Executar

1. Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
```

2. Navegue até a pasta do projeto e execute:

```bash
mvn spring-boot:run
```

3. Acesse no navegador:

```
http://localhost:8080
```

---

## 🌐 API IBGE

* **Base URL:** `https://servicodados.ibge.gov.br/api/v1/localidades`
* **Estados:** `/estados`
* **Municípios de um estado:** `/estados/{UF}/municipios`

Exemplo de requisição para listar municípios de São Paulo (SP):

```
https://servicodados.ibge.gov.br/api/v1/localidades/estados/SP/municipios
```

### 📚 Documentação oficial

* [Documentação da API de Localidades IBGE](https://servicodados.ibge.gov.br/api/docs/localidades#api-Municipios-estadosUFMunicipiosGet)

### 🔗 Links úteis

* [Spring Boot](https://spring.io/projects/spring-boot) 🚀
* [Thymeleaf](https://www.thymeleaf.org/) 📝
* [API IBGE](https://servicodados.ibge.gov.br/api/docs/localidades) 🌐
* [Maven](https://maven.apache.org/) 📦

---

## 🛡 Licença

Este projeto está sob a **Licença MIT**.

---
