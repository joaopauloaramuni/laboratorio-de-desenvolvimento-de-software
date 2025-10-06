# 📘 Projeto BD_RestAPI_MongoDB

## 🧩 Sobre o Projeto

**BD_RestAPI_MongoDB** é uma aplicação **Spring Boot** desenvolvida em **Java 17**, que expõe uma **API RESTful** para manipulação de dados de usuários utilizando o **MongoDB** como banco de dados.

O projeto demonstra as operações básicas de CRUD, além de consultas personalizadas utilizando o **Spring Data MongoDB**.

---

## 🗃️ Estrutura do Projeto

```
BD_RestAPI_MongoDB/
├── src/
│   ├── main/java/com/example/RestAPI/
│   │   ├── application/RestApiApplication.java
│   │   ├── controller/UserController.java
│   │   ├── model/UserEntity.java
│   │   ├── repository/UserRepository.java
│   │   └── service/UserService.java
│   └── resources/
│       └── application.properties
├── pom.xml
└── README.md
```

---

## ⚙️ Tecnologias Utilizadas

* ☕ **Java 17**
* 🌱 **Spring Boot 3.3.4**
* 🟢 **MongoDB 6**
* 🧠 **Spring Data MongoDB**
* ⚙️ **Maven**
* 💾 **NoSQL Database**

---

## 🚀 Endpoints Disponíveis

|   Método   | Endpoint                                     | Descrição                                               |
| :--------: | :------------------------------------------- | :------------------------------------------------------ |
|   **GET**  | `/users`                                     | Retorna todos os usuários                               |
|   **GET**  | `/users/{id}`                                | Retorna um usuário por ID                               |
|  **POST**  | `/users`                                     | Insere um novo usuário                                  |
|   **PUT**  | `/users/{id}`                                | Atualiza um usuário existente                           |
| **DELETE** | `/users/{id}`                                | Exclui um usuário                                       |
|   **GET**  | `/users/buscarPorNome/{nome}`                | Busca usuários por nome exato                           |
|   **GET**  | `/users/buscarPorEmail/{email}`              | Busca usuários por email                                |
|   **GET**  | `/users/buscarPorNomeEEmail?nome=X&email=Y`  | Busca por nome e email                                  |
|   **GET**  | `/users/buscarPorNomeQueComecaCom/{prefixo}` | Busca usuários cujo nome começa com determinado prefixo |
|   **GET**  | `/users/buscarPorNomeQueContem/{substring}`  | Busca usuários cujo nome contém determinada substring   |

---

## 🛠️ Serviços da Aplicação

A camada de **Serviços** (Service Layer) da aplicação encapsula a lógica de negócio e interage com o repositório de dados. No projeto **BD_RestAPI_MongoDB**, temos o seguinte serviço:

### UserService

Responsável por gerenciar todas as operações relacionadas à entidade **UserEntity**:

* **getAllUsers()**
  Retorna todos os usuários cadastrados no banco de dados.

* **obterPorId(String id)**
  Retorna um usuário específico pelo ID. Retorna `null` se não encontrado.

* **inserir(UserEntity user)**
  Insere um novo usuário no banco de dados.

* **atualizar(String id, UserEntity user)**
  Atualiza os dados de um usuário existente. Retorna `null` se o usuário não existir.

* **excluir(String id)**
  Remove um usuário do banco de dados pelo ID.

* **buscarPorNome(String nome)**
  Retorna uma lista de usuários com o nome exato informado.

* **buscarPorEmail(String email)**
  Retorna uma lista de usuários com o email exato informado.

* **buscarPorNomeEEmail(String nome, String email)**
  Retorna usuários que correspondem tanto ao nome quanto ao email informados.

* **buscarPorNomeQueComecaCom(String prefixo)**
  Retorna usuários cujo nome começa com o prefixo informado.

* **buscarPorNomeQueContem(String substring)**
  Retorna usuários cujo nome contém a substring informada.

💡 **Dica:** Esta camada centraliza regras de negócio e mantém os **controllers** mais limpos, focados em lidar com as requisições HTTP.

---

## 🧠 Exemplo de Entidade


```java
@Document(collection = "user")
public class UserEntity {

    @Id
    private String id;
    private String nome;
    private String email;
}
```

---

## 🧰 Configuração do Banco de Dados

Arquivo: `src/main/resources/application.properties`

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=bd-user
```

---

## ⚠️ Observação Importante sobre o Banco de Dados

Antes de executar o projeto, certifique-se de que o **MongoDB** está **instalado e em execução** na sua máquina.
Sem o banco ativo, a aplicação **não conseguirá conectar ao banco de dados**.

### ▶️ Como iniciar o MongoDB

#### 🪟 Windows

* Use o **MongoDB Compass** ou inicie o serviço do MongoDB manualmente:

```powershell
net start MongoDB
```

#### 🍎 macOS

* Se instalado via **Homebrew**, inicie o serviço com:

```bash
brew services start mongodb-community
```

* Para verificar o status:

```bash
brew services list
```

#### 🐧 Linux (Ubuntu/Debian)

* Para iniciar o MongoDB:

```bash
sudo systemctl start mongod
```

* Para verificar o status:

```bash
sudo systemctl status mongod
```

---

💡 **Dica:** Após o MongoDB estar rodando, você pode confirmar o acesso executando no terminal:

```bash
mongo --eval 'db.runCommand({ connectionStatus: 1 })'
```

Se receber um status `ok: 1`, o banco está pronto para uso pela aplicação.

---

## ▶️ Executando o Projeto

### ✅ Pré-requisitos

- Java 17+ instalado
- MongoDB rodando localmente
- Maven configurado

### 💻 Rodar o projeto

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:
👉 **http://localhost:8080/users**

---

## 🧪 Testando a API

### Inserindo um novo usuário

Exemplo de requisição `POST` via **cURL**:

```bash
curl -X POST http://localhost:8080/users \
     -H "Content-Type: application/json" \
     -d '{"nome":"João Paulo","email":"joao@example.com"}'
```

### Listando todos os usuários

Exemplo de requisição `GET` via **cURL**:

```bash
curl -X GET http://localhost:8080/users
```

#### Exemplo de saída no terminal:

```json
[
  {
    "id": "651a9f1b6e0f1c3d5a0b1234",
    "nome": "João Paulo",
    "email": "joao@example.com"
  },
  {
    "id": "651a9f1b6e0f1c3d5a0b5678",
    "nome": "Maria Silva",
    "email": "maria@example.com"
  }
]
```

---

## 📚 Documentação e Links Úteis

### 🧩 Spring Boot

- [Documentação Oficial do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Guia Rápido – Criando uma API REST com Spring Boot](https://spring.io/guides/gs/rest-service/)

### 🗃️ Spring Data MongoDB

- [Documentação Oficial do Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [Baeldung: Spring Data MongoDB Query Methods](https://www.baeldung.com/spring-data-mongodb-query)

### 🟢 MongoDB

- [Documentação Oficial do MongoDB](https://www.mongodb.com/docs/)
- [Instalação do MongoDB no Windows, macOS e Linux](https://www.mongodb.com/docs/manual/installation/)
- [MongoDB – Guia de comandos básicos](https://www.mongodb.com/docs/manual/reference/)

### ⚙️ Maven

- [Documentação do Maven](https://maven.apache.org/guides/index.html)
- [Central de Dependências Maven](https://mvnrepository.com/)

### 🧠 Recursos Extras

- [Postman – Testando APIs REST](https://www.postman.com/)
- [JSON Formatter & Validator](https://jsonformatter.curiousconcept.com/)
- [HTTP Status Codes – Lista Completa](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)

---

## 📜 Licença

Este projeto está licenciado sob a Licença **MIT**.

---
