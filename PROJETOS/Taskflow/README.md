# 📋 TaskFlow

Aplicação simples de **gerenciamento de tarefas**, construída para demonstrar, na prática, conceitos de **arquitetura back-end em camadas** e **design de APIs RESTful**.

O TaskFlow permite cadastrar tarefas, definir prazos, alterar o status (`PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`) e consultar um relatório simples de produtividade — tudo isso através de uma interface web (Thymeleaf) que consome uma API REST no back-end.

---

## 🧱 Arquitetura

O projeto segue uma separação clara de responsabilidades em camadas:

```
Front-end (Thymeleaf + JavaScript)
        │  HTTP (GET, POST, PUT, PATCH, DELETE) + JSON
        ▼
Controller  →  recebe a requisição HTTP e delega para o Service
        │
        ▼
Service     →  regras de negócio (validação de prazos, mudança de status, relatório)
        │
        ▼
Repository  →  comunicação com o banco de dados (Spring Data JPA)
        │
        ▼
Banco de dados (H2, em memória)
```

- **Controller** (`TaskRestController`, `WebController`): recebe requisições, não contém lógica de negócio.
- **Service** (`TaskService`): concentra as regras de negócio (ex.: não permitir prazos no passado, cálculo do relatório de produtividade).
- **Repository** (`TaskRepository`): interface Spring Data JPA, responsável apenas pela persistência.
- **Model** (`Task`, `TaskStatus`): entidade JPA que representa o recurso `tasks`.

O front-end (HTML + JavaScript puro em `static/js/app.js`) **não recebe dados diretamente do Controller via Model** — ele consome a API REST (`/api/tasks`) via `fetch()`, exatamente como faria uma aplicação front-end separada (ex.: React ou Next.js). Isso evidencia o contrato bem definido entre front-end e back-end (JSON + HTTP).

---

## 🔌 Endpoints da API REST

| Método | Endpoint                     | Descrição                                  |
|--------|-------------------------------|---------------------------------------------|
| GET    | `/api/tasks`                  | Lista todas as tarefas (aceita `?status=`) |
| GET    | `/api/tasks/{id}`             | Busca uma tarefa específica                |
| POST   | `/api/tasks`                  | Cria uma nova tarefa                       |
| PUT    | `/api/tasks/{id}`             | Atualiza uma tarefa existente              |
| PATCH  | `/api/tasks/{id}/status`      | Atualiza apenas o status da tarefa         |
| DELETE | `/api/tasks/{id}`             | Remove uma tarefa                          |
| GET    | `/api/tasks/report`           | Relatório simples de produtividade         |

Exemplo de corpo (JSON) para criar uma tarefa (`POST /api/tasks`):

```json
{
  "title": "Finalizar relatório mensal",
  "description": "Consolidar os números de vendas do mês",
  "deadline": "2026-09-01",
  "status": "PENDENTE"
}
```

Exemplo de resposta do relatório (`GET /api/tasks/report`):

```json
{
  "totalTarefas": 5,
  "pendentes": 2,
  "emAndamento": 1,
  "concluidas": 2,
  "atrasadas": 0,
  "percentualConcluido": 40.0
}
```

---

## 🛠️ Tecnologias utilizadas

- **Java 17**
- **Spring Boot 3.3.2**
  - Spring Web (MVC + REST)
  - Spring Data JPA
  - Spring Boot Validation
  - Thymeleaf (renderização do front-end)
- **H2 Database** (banco em memória, sem necessidade de instalação)
- **Maven** (gerenciador de dependências e build)
- **HTML5 / CSS3 / JavaScript (vanilla)** no front-end
- **JUnit 5 + Mockito** (testes da camada de Service)

---

## 📦 Dependências do projeto (`pom.xml`)

| Dependência                          | Finalidade                                       |
|---------------------------------------|---------------------------------------------------|
| `spring-boot-starter-web`             | Criação da API REST e servidor embutido (Tomcat) |
| `spring-boot-starter-thymeleaf`       | Renderização do front-end (páginas HTML)         |
| `spring-boot-starter-data-jpa`        | Persistência de dados via JPA/Hibernate          |
| `spring-boot-starter-validation`      | Validação de dados de entrada (Bean Validation)  |
| `com.h2database:h2`                  | Banco de dados em memória para desenvolvimento   |
| `spring-boot-starter-test`            | Testes unitários (JUnit 5 + Mockito)             |

---

## ✅ Pré-requisitos

Antes de rodar o projeto, tenha instalado:

- **JDK 17** ou superior ([Adoptium Temurin](https://adoptium.net/))
- **Maven 3.9+** ([instalação](https://maven.apache.org/install.html)) — *ou use o Maven Wrapper, se preferir adicioná-lo ao projeto*
- Uma IDE de sua preferência (recomendado: **IntelliJ IDEA** ou **VS Code** com extensão Java)

Para verificar se o Java e o Maven estão instalados corretamente:

```bash
java -version
mvn -version
```

---

## 🚀 Passo a passo para rodar o projeto

### 1. Clone ou extraia o projeto

```bash
git clone <url-do-seu-repositorio>
cd taskflow
```

> Se você recebeu o projeto como um `.zip`, apenas extraia e entre na pasta `taskflow`.

### 2. Compile o projeto e baixe as dependências

```bash
mvn clean install
```

### 3. Execute a aplicação

```bash
mvn spring-boot:run
```

Alternativamente, você pode gerar o `.jar` e executá-lo diretamente:

```bash
mvn clean package
java -jar target/taskflow.jar
```

### 4. Acesse a aplicação

- **Interface web (Thymeleaf):** [http://localhost:8080](http://localhost:8080)
- **API REST:** [http://localhost:8080/api/tasks](http://localhost:8080/api/tasks)
- **Console do banco H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:taskflowdb`
  - Usuário: `sa`
  - Senha: *(em branco)*

### 5. Testando a API (opcional, via `curl`)

```bash
# Criar uma tarefa
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Estudar arquitetura REST","description":"Revisar camadas","deadline":"2026-09-10"}'

# Listar tarefas
curl http://localhost:8080/api/tasks

# Ver relatório de produtividade
curl http://localhost:8080/api/tasks/report
```

### 6. Rodar os testes

```bash
mvn test
```

---

## 📁 Estrutura de pastas

```
taskflow/
├── pom.xml
├── LICENSE
├── README.md
└── src
    ├── main
    │   ├── java/com/taskflow
    │   │   ├── TaskFlowApplication.java
    │   │   ├── controller/
    │   │   │   ├── TaskRestController.java   (API REST /api/tasks)
    │   │   │   └── WebController.java        (página HTML)
    │   │   ├── service/
    │   │   │   └── TaskService.java          (regras de negócio)
    │   │   ├── repository/
    │   │   │   └── TaskRepository.java       (persistência - Spring Data JPA)
    │   │   ├── model/
    │   │   │   ├── Task.java
    │   │   │   └── TaskStatus.java
    │   │   └── exception/
    │   │       ├── ResourceNotFoundException.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       ├── application.properties
    │       ├── templates/index.html          (front-end Thymeleaf)
    │       └── static/
    │           ├── css/style.css
    │           └── js/app.js                 (consome a API via fetch)
    └── test/java/com/taskflow
        └── TaskServiceTest.java
```

---

## 🔭 Possíveis evoluções

Graças à separação em camadas, a aplicação pode evoluir sem comprometer a organização existente:

- Autenticação e autorização de usuários (Spring Security + JWT)
- Versionamento de API (ex.: `/api/v1/tasks`, `/api/v2/tasks`)
- Migração do banco H2 para PostgreSQL ou MySQL
- Substituição do front-end Thymeleaf por um front-end desacoplado (React, Next.js), consumindo a mesma API REST
- Relatórios mais avançados e paginação de resultados
- Deploy em nuvem (Render, Railway, AWS, etc.)

A mesma arquitetura (Controller → Service → Repository) pode ser reaproveitada em outros contextos, como sistemas de chamados técnicos, plataformas educacionais ou aplicativos corporativos internos.

---

## 🔗 Links úteis e documentação

- [Spring Boot - Documentação oficial](https://docs.spring.io/spring-boot/index.html)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Thymeleaf - Documentação oficial](https://www.thymeleaf.org/documentation.html)
- [Bean Validation (Jakarta)](https://beanvalidation.org/)
- [Banco de dados H2](https://www.h2database.com/html/main.html)
- [Guia REST API - RESTful Web Services](https://restfulapi.net/)
- [Maven - Guia de início rápido](https://maven.apache.org/guides/getting-started/index.html)
- [Spring Initializr](https://start.spring.io/) (para gerar novos projetos Spring Boot)

---

## 📄 Licença

Este projeto está licenciado sob a licença **MIT** — veja o arquivo [LICENSE](./LICENSE) para mais detalhes.
