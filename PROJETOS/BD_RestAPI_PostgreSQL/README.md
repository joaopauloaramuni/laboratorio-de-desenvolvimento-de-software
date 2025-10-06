# 📘 Projeto BD_RestAPI_PostgreSQL

## 🧩 Sobre o Projeto

**BD_RestAPI_PostgreSQL** é uma aplicação **Spring Boot** desenvolvida em **Java 17**, que expõe uma **API RESTful** para manipulação de dados de livros utilizando o **PostgreSQL** como banco de dados.

O projeto demonstra as operações básicas de CRUD, além de consultas personalizadas utilizando o **Spring Data JPA**.

---

## 🗃️ Estrutura do Projeto

```
BD_RestAPI_PostgreSQL/
├── src/
│   ├── main/java/com/example/BD_RestAPI_PostgreSQL/
│   │   ├── application/BdRestApiPostgreSqlApplication.java
│   │   ├── controller/BookController.java
│   │   ├── model/BookEntity.java
│   │   ├── repository/BookRepository.java
│   │   └── service/BookService.java
│   └── resources/
│       ├── sql/Script_Livraria.sql
│       └── application.properties
├── pom.xml
└── README.md
```

---

## ⚙️ Tecnologias Utilizadas

- ☕ **Java 17**
- 🌱 **Spring Boot 3.3.4**
- 🐘 **PostgreSQL 16**
- 🧠 **Spring Data JPA**
- ⚙️ **Maven**
- 🧾 **Hibernate ORM**
- 💚 **Thymeleaf (para integração futura de frontend)**

---

## 🚀 Endpoints Disponíveis

| Método | Endpoint | Descrição |
|:--:|:--|:--|
| **GET** | `/books` | Retorna todos os livros |
| **GET** | `/books/{id}` | Retorna um livro por ID |
| **POST** | `/books` | Insere um novo livro |
| **PUT** | `/books/{id}` | Atualiza um livro existente |
| **DELETE** | `/books/{id}` | Exclui um livro |
| **GET** | `/books/buscarPorTitulo/{titulo}` | Busca livros por título exato |
| **GET** | `/books/buscarPorAutor/{autor}` | Busca livros por autor |
| **GET** | `/books/buscarPorTituloEAutor?titulo=X&autor=Y` | Busca por título e autor |
| **GET** | `/books/buscarPorTituloQueComecaCom/{prefixo}` | Busca livros com título começando com determinado prefixo |
| **GET** | `/books/buscarPorTituloQueContem/{contem}` | Busca livros contendo o texto informado |

---

## 🧠 Exemplo de Entidade

```java
@Entity
@Table(name = "book_entity")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
}
```

---

## 🧰 Configuração do Banco de Dados

Arquivo: `src/main/resources/application.properties`

```properties
spring.application.name=BD_RestAPI_PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/livraria
spring.datasource.username=aramuni
spring.datasource.password=aramuni
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

## 🗄️ Script SQL de Criação

```sql
-- CREATE USER aramuni WITH SUPERUSER PASSWORD 'aramuni';

-- Database: livraria

-- DROP DATABASE IF EXISTS livraria;

CREATE DATABASE livraria
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE book_entity (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);

INSERT INTO book_entity (title, author) 
VALUES 
    ('O Hobbit', 'J.R.R. Tolkien'),
	('O Senhor dos Anéis', 'J.R.R. Tolkien'),
    ('1984', 'George Orwell'),
    ('A Metamorfose', 'Franz Kafka');

SELECT * FROM book_entity
```

---

## ⚠️ Observação Importante sobre o Banco de Dados

Antes de executar o projeto, certifique-se de que o **PostgreSQL** está **instalado e em execução** na sua máquina.
Sem o banco ativo, a aplicação **não conseguirá conectar ao banco de dados**.

### ▶️ Como iniciar o PostgreSQL

#### 🪟 **Windows**

* O PostgreSQL geralmente é instalado como um serviço do Windows.
* Para iniciar manualmente:

  1. Pressione `Win + R`, digite `services.msc` e pressione Enter.
  2. Localize **PostgreSQL** na lista.
  3. Clique com o botão direito e selecione **Iniciar**.
* Alternativamente, use o **pgAdmin** (interface gráfica oficial) e verifique se o servidor está rodando.

#### 🍎 **macOS**

* Se instalado via **Homebrew**, você pode iniciar o serviço com:

  ```bash
  brew services start postgresql
  ```
* Para verificar o status:

  ```bash
  brew services list
  ```
* Para parar o serviço:

  ```bash
  brew services stop postgresql
  ```

#### 🐧 **Linux (Ubuntu/Debian)**

* Para iniciar o PostgreSQL:

  ```bash
  sudo systemctl start postgresql
  ```
* Para verificar o status:

  ```bash
  sudo systemctl status postgresql
  ```
* Para habilitar inicialização automática:

  ```bash
  sudo systemctl enable postgresql
  ```

---

💡 **Dica:** Após o PostgreSQL estar rodando, você pode confirmar o acesso executando no terminal:

```bash
psql -U aramuni -d livraria
```

Se conectar com sucesso, o banco está pronto para uso pela aplicação.

---

## ▶️ Executando o Projeto

### ✅ Pré-requisitos
- Java 17+ instalado
- PostgreSQL rodando localmente
- Maven configurado

### 💻 Rodar o projeto
```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:  
👉 **http://localhost:8080/books**

---

## 🧪 Testando a API

### Inserindo um novo livro

Exemplo de requisição `POST` via **cURL**:

```bash
curl -X POST http://localhost:8080/books \
     -H "Content-Type: application/json" \
     -d '{"title":"Clean Code","author":"Robert C. Martin"}'
```

### Listando todos os livros

Exemplo de requisição `GET` via **cURL**:

```bash
curl -X GET http://localhost:8080/books
```

#### Exemplo de saída no terminal:

```json
[
  {
    "id": 1,
    "title": "O Hobbit",
    "author": "J.R.R. Tolkien"
  },
  {
    "id": 2,
    "title": "O Senhor dos Anéis",
    "author": "J.R.R. Tolkien"
  },
  {
    "id": 3,
    "title": "1984",
    "author": "George Orwell"
  },
  {
    "id": 4,
    "title": "A Metamorfose",
    "author": "Franz Kafka"
  },
  {
    "id": 5,
    "title": "Clean Code",
    "author": "Robert C. Martin"
  }
]
```

---

## 📚 Documentação e Links Úteis

### 🧩 Spring Boot
- [Documentação Oficial do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Guia Rápido – Criando uma API REST com Spring Boot](https://spring.io/guides/gs/rest-service/)

### 🗃️ Spring Data JPA
- [Documentação Oficial do Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [JPQL e Métodos de Consulta no Spring Data](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation)
- [Baeldung: Spring Data JPA Query Methods](https://www.baeldung.com/spring-data-derived-queries)

### 🐘 PostgreSQL
- [Documentação Oficial do PostgreSQL](https://www.postgresql.org/docs/)
- [Instalação do PostgreSQL no Windows, macOS e Linux](https://www.postgresql.org/download/)
- [PostgreSQL – Guia de comandos básicos](https://www.postgresqltutorial.com/)

### ⚙️ Maven
- [Documentação do Maven](https://maven.apache.org/guides/index.html)
- [Central de Dependências Maven](https://mvnrepository.com/)
- [Configuração do Plugin Spring Boot Maven](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)

### 🧠 Recursos Extras
- [Postman – Testando APIs REST](https://www.postman.com/)
- [JSON Formatter & Validator](https://jsonformatter.curiousconcept.com/)
- [HTTP Status Codes – Lista Completa](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
- [Cheat Sheet – Anotações Spring Boot](https://springframework.guru/spring-framework-annotation-cheat-sheet/)

---

## 📜 Licença

Este projeto está licenciado sob a Licença **MIT**.

---
