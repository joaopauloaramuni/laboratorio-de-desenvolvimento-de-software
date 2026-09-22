<!-- Este template foi criado para servir como referência e pode ser facilmente adaptado para diferentes projetos de desenvolvimento -->

---

# 🏷️ Project Name 👨‍💻

> [!NOTE]
> Brief description of the project. **Focus on the main value/benefit.**  
> Create a **logo** for the project that represents the application in question.

<table>
  <tr>
    <td width="800px">
      <div align="justify">
        This <b>README.md</b> presents an organized template, ideal to serve as an academic and professional reference in development projects, ranging from university assignments to projects developed for technical stages of recruitment processes. It brings together the <i>essential sections</i> recommended by <a href="https://github.com/joaopauloaramuni">Prof. Dr. João Paulo Aramuni</a>, enabling <i>clear organization</i>, <i>efficient documentation</i>, and <i>standardization</i> across different works. The purpose of this skeleton is to <b>facilitate the construction of well-documented projects</b>, offering a <i>comprehensive guide</i> that includes <b>best practices</b>, execution instructions, technologies used, architecture, folder structures, tests, useful links, and collaboration guidelines. This template helps students develop <b>professional-quality documentation</b> from the early stages of their studies, promoting <i>clarity</i>, <i>reproducibility</i>, and <i>standardization</i> in projects.
      </div>
    </td>
    <td>
      <div>
        <img src="https://joaopauloaramuni.github.io/image/logo_ES_vertical.png" alt="Logo do Projeto" width="120px"/>
      </div>
    </td>
  </tr> 
</table>

---

## 🚧 Project Status

### Examples of basic badges:

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/joaopauloaramuni/joaopauloaramuni/main.yml?branch=main)](https://github.com/joaopauloaramuni/joaopauloaramuni/actions/workflows/main.yml)
[![Test Coverage](https://codecov.io/gh/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/branch/main/graph/badge.svg)](https://codecov.io/gh/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software)
[![Versão](https://img.shields.io/badge/Versão-v1.0.0-blue)](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/releases)
[![Licença](https://img.shields.io/github/license/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software)](#licença)

### Other examples of badges:

[![Versão](https://img.shields.io/badge/Versão-v1.0.0-blue?style=for-the-badge)](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/releases) ![React](https://img.shields.io/badge/React-19.1.1-007ec6?style=for-the-badge&logo=react&logoColor=white) ![Vite](https://img.shields.io/badge/Vite-7.1.2-007ec6?style=for-the-badge&logo=vite&logoColor=white) ![Java](https://img.shields.io/badge/Java-17-007ec6?style=for-the-badge&logo=openjdk&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.9.9-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-007ec6?style=for-the-badge&logo=springboot&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.1-007ec6?style=for-the-badge&logo=thymeleaf&logoColor=white) ![GitHub repo size](https://img.shields.io/github/repo-size/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=files) ![GitHub directory file count](https://img.shields.io/github/directory-file-count/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=files) ![GitHub stars](https://img.shields.io/github/stars/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=github) ![GitHub forks](https://img.shields.io/github/forks/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=git) ![GitHub language count](https://img.shields.io/github/languages/count/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=python) ![GitHub license](https://img.shields.io/github/license/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&color=007ec6&logo=opensourceinitiative) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&color=007ec6&logo=gitkraken) ![GitHub last commit](https://img.shields.io/github/last-commit/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=clockify) ![Vercel](https://vercelbadge.vercel.app/api/joaopauloaramuni/joaopauloaramuni-portfolio?style=for-the-badge) ![Views Counter](https://views-counter.vercel.app/badge?pageId=https%3A%2F%2Fgithub%2Ecom%2Fjoaopauloaramuni%2Flaboratorio-de-desenvolvimento-de-software&leftColor=555555&rightColor=007ec6&type=total&label=RepoViews)

---

## 📚 Table of Contents
- [Useful Links](#-useful-links)
- [About the Project](#-about-the-project)
- [Main Features](#-main-features)
- [Technologies Used](#-technologies-used)
- [Architecture](#-architecture)
  - [Diagram examples](#diagram-examples)
- [Installation and Execution](#-installation-and-execution)
  - [Prerequisites](#prerequisites)
  - [Environment Variables](#-environment-variables)
     - [1 Back-end (Spring Boot)](#1-back-end-spring-boot)
     - [2 Front-end (React, Vite)](#2-front-end-react-vite)
     - [3 Examples of Environment Variables on Vercel](#3-examples-of-environment-variables-on-vercel)
  - [Dependency Installation](#-dependency-installation)
    - [Front-end (React)](#front-end-react)
    - [Back-end (Spring Boot)](#back-end-spring-boot)
  - [Database Initialization (PostgreSQL)](#-database-initialization-postgresql)
  - [How to Run the Application](#-how-to-run-the-application)
    - [Terminal 1: Back-end (Spring Boot)](#terminal-1-back-end-spring-boot)
    - [Terminal 2: Front-end (React, Vite)](#terminal-2-front-end-react-vite)
    - [Full Local Execution with Docker Compose (Including Database)](#-full-local-execution-with-docker-compose-including-database)
    - [Steps for build, initialization, and execution](#-steps-for-build-initialization-and-execution)
- [Deploy](#-deploy)
- [Folder Structure](#-folder-structure)
- [Demonstration](#-demonstration)
  - [Mobile Application](#-mobile-application)
  - [Web Application](#-web-application)
  - [Example of Terminal Output (for Back-end, API, CLI)](#-example-of-terminal-output-for-back-end-api-cli)
- [Tests](#-tests)
- [Documentation Used](#-documentation-used)
- [Authors](#-authors)
- [Contribution](#-contribution)
- [Acknowledgements](#-acknowledgements)
- [License](#-license)

---

## 🔗 Useful Links
* 🌐 **Online Demo:** [Access the Web Application](<link-da-demo-web>)
  > 💻 **Description:** Link to the application in the production environment (e.g., hosted on Vercel, Netlify, or AWS S3).
* 📱 **Mobile Download:** [App Store](<link-app-store>) | [Google Play](<link-google-play>) | [Direct APK](<link-para-apk-direto>)
  > 📱 **Description:** Direct links for downloading from app stores (if applicable) and for the direct Android installation file (APK).
* 📖 **Documentation:** [Read the Wiki/Docs](<link-para-docs>)
  > 📚 **Description:** Access to the complete technical documentation of the project (e.g., Swagger/OpenAPI for APIs, or an internal Wiki).

---

## 📝 About the Project
In this section, clearly and objectively describe **the purpose of your project**, explaining:

- **Why it exists** — what motivation led to its creation.  
- **What problem it solves** — which pain point, need, or opportunity it addresses.  
- **What the context is** — academic, professional, personal, experimental, etc.  
- **Where it can be used** — real or simulated scenarios.  

Try to answer questions such as:
- *What was the initial idea of the project?*
- *What value does it deliver to the user?*
- *Why would someone use or contribute to this project?*
- *What makes it relevant or interesting?*

Write in an objective but complete way, so that anyone can quickly understand **what it is** and **why it matters**.

> [!NOTE]
> This section follows professional documentation best practices and should be adjusted according to the type and objective of your project.

---

## ✨ Main Features
List the features in a clear and objective way.

- 🔐 **Secure Authentication:** Login, Sign Up, and Password Recovery.
- 📈 **Dashboard:** Real-time data visualization with charts.
- ⚙️ **CRUD Management:** Create, Read, Update, and Delete resources (e.g., Users, Items, Posts).
- 📊 **Exportable Reports:** Data export in PDF, CSV, or Excel formats.
- 🌐 **Internationalization (i18n):** Support for multiple languages.
- 🧵 **Logs and Monitoring:** Detailed activity logging and performance analysis.
- 🔄 **Integration with External APIs:** Connection with third-party services (payments, maps, authentication, etc.).
- 📨 **Notification System:** Sending alerts via email, push, or in-app notifications.

---

## 🛠 Technologies Used

The following tools, frameworks, and libraries were used to build this project. It is recommended to use the listed versions (or higher) to ensure compatibility.

### 💻 Front-end

* **Framework/Library:** [e.g., React v18, Vue.js v3, Angular v17]
* **Language/Superset:** [e.g., TypeScript, JavaScript ES6+]
* **Styling:** [e.g., Tailwind CSS, Sass, Styled Components, Material UI]
* **State Management:** [e.g., Redux Toolkit, Zustand, Context API]
* **Build Tool:** [e.g., Vite, Webpack]

### 🖥️ Back-end

* **Language/Runtime:** [e.g., Java 17 (JDK), Node.js v20, Python 3.11]
* **Framework:** [e.g., Spring Boot 3.x, NestJS, Express, Django]
* **Database:** [e.g., PostgreSQL, MySQL, MongoDB]
* **ORM / Query Builder:** [e.g., Hibernate/JPA, Prisma, TypeORM]
* **Authentication:** [e.g., JWT, OAuth2, Spring Security]

### 📱 Mobile (Optional)

* **Framework:** [e.g., React Native, Flutter, Kotlin Multiplatform]
* **Tools:** [e.g., Expo, Android Studio, Xcode]

### ⚙️ Infrastructure & DevOps

* **Containerization:** [e.g., Docker, Docker Compose]
* **Orchestration:** [e.g., Kubernetes (K8s)]
* **Cloud:** [e.g., AWS (EC2, RDS, S3), Vercel, Heroku, Google Cloud]
* **CI/CD:** [e.g., GitHub Actions, Jenkins, SonarQube]

---

## 🏗 Architecture

Describe here the **complete system architecture**, explaining how layers, modules, and components are organized. Also explain **why** this architecture was chosen and **which problems it helps to solve**.

You may include:

- **Architecture overview** (e.g., layers, modules, microservices, modular monolith, hexagonal, MVC, etc.)
- **Main components** and the role of each one
- **Adopted design patterns** (e.g., Repository, Service Layer, DTOs, Factory, Observer)
- **Data flow** between system parts
- **Technologies used in each layer**
- **Important architectural decisions**
- **Trade-offs** or relevant limitations

### Diagram examples

For better visualization and understanding of the system structure, the main diagrams are organized side by side.

| Architecture Diagram | Architecture Detail |
| :---: | :---: |
| **General Overview (Macro)** | **Service Layer (Micro)** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Visão Geral do Sistema" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Componentes ou Serviço X" width="120px" height="120px"> |
| **Data Model (Entities)** | **Authentication Flow** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Entidade-Relacionamento (DER)" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Sequência de Login" width="120px" height="120px"> |
| **Infrastructure (Cloud)** | **API Gateway (Routes)** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Deploy na AWS/Vercel" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Mapa de Endpoints da API" width="120px" height="120px"> |

---

## 🔧 Installation and Execution

### Prerequisites
Make sure the user has the environment properly configured.

* **Java JDK:** Version **17** or higher (Required for the **Spring Boot Back-end**)
* **Node.js:** LTS version (v18.x or higher) (Required for the **React Front-end**)
* **Package Manager:** npm or yarn
* **Docker** (Optional, but **highly recommended** to run the Database)

---

### 🔑 Environment Variables

Create specific `.env` files and/or configure environment variables in your system for each part of the application.

#### 1 Back-end (Spring Boot)

Configure these variables as **system environment variables** or in a Spring configuration file (e.g., `application.properties` / `application.yml`).

| Variable | Description | Example |
| :--- | :--- | :--- |
| `SERVER_PORT` | Port where the Back-end will run. | `8080` |
| `SPRING_DATASOURCE_URL` | JDBC connection URL (PostgreSQL). | `jdbc:postgresql://localhost:5432/mydatabase` |
| `SPRING_DATASOURCE_USERNAME` | Database username. | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password. | `secure-password-123` |
| `JWT_SECRET` | Secret key for token signing (Optional). | `super_secure_base64_key` |

#### 2 Front-end (React, Vite)

Create a **`.env`** file at the root of the `/frontend` folder and use the `VITE_` prefix (or `REACT_APP_` if using CRA) to expose variables to the application *bundle*.

| Variable | Description | Example |
| :--- | :--- | :--- |
| `VITE_API_URL` | Base URL of the Spring Boot Backend endpoint. | `http://localhost:8080/api` |
| `VITE_EMAILJS_PUBLIC_KEY` | Public key for email services (Example). | `your_public_key_here` |
| `VITE_GOOGLE_MAPS_KEY` | API key for map services (Optional). | `AIzaSy...` |

---

#### 3. Examples of Environment Variables on Vercel

Vercel allows you to configure variables in the dashboard (Project Settings > Environment Variables).
Below are common examples used in front-end and full-stack applications:

---

##### **Example 1 – Front-end with Next.js using an external API**

```
NEXT_PUBLIC_API_URL=https://meu-backend.vercel.app/api
NEXT_PUBLIC_GOOGLE_ANALYTICS_ID=G-seu_google_analytics_id_aqui
```

---

##### **Example 2 – Full-stack Application (Next.js + Prisma + PostgreSQL)**

```
DATABASE_URL=postgresql://admin:senha-super-segura@ep-meu-banco.aws.neon.tech:5432/verceldb
NEXTAUTH_SECRET=uma_chave_muito_longa_e_segura
NEXTAUTH_URL=https://meu-sistema.vercel.app
```

---

##### **Example 3 – Integration with external APIs**

```
STRIPE_SECRET_KEY=sk_live_seu_stripe_key_aqui
OPENAI_API_KEY=sk-sua_openai_key_aqui
SENDGRID_API_KEY=SG.sua_sendgrid_key_aqui
```

---

##### **Example 4 – Front-end with Vite (EmailJS)**

```
VITE_EMAILJS_SERVICE_ID=seu_service_id_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_ME=seu_template_id_for_me_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_SENDER=seu_template_id_for_sender_aqui
VITE_EMAILJS_PUBLIC_KEY=sua_public_key_aqui
```

> **Note:** Environment variables in **Vite** projects must start with `VITE_` so that Vite can recognize them and include them in the frontend *bundle*; variables without this prefix are not available in client-side code.

---

To add these variables:

1. Access the Environment Variables page of your project on Vercel (e.g., `https://vercel.com/<your-username>/<your-project>/settings/environment-variables`)
2. Click **"Add"** to add each variable with its corresponding name and value.

Alternatively, if you are developing locally, create a **`.env.local`** file inside the **`frontend`** folder of your project with the following content:

```
# Essential variable to connect to the locally running Spring Boot Back-end (usually on port 8080)
VITE_API_URL=http://localhost:8080/api

# Variables for external email service integrations
VITE_EMAILJS_SERVICE_ID=seu_service_id_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_ME=seu_template_id_for_me_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_SENDER=seu_template_id_for_sender_aqui
VITE_EMAILJS_PUBLIC_KEY=sua_public_key_aqui

# Other service keys
VITE_GOOGLE_MAPS_KEY=AIzaSy...
```

> 💡 **Location:** Make sure this file is located at **`/frontend/.env.local`** so that **Vite** can load it and make the variables available to the Front-end during development.

### 📦 Dependency Installation

Clone the repository and install the dependencies.

1. **Clone the Repository:**

```bash
git clone <URL_DO_SEU_REPOSITÓRIO>
cd <pasta-do-projeto>
```

2. **Install the Dependencies (Monorepo):**

As the project is divided, you need to install the dependencies separately for the Front-end (React, using NPM/Yarn) and ensure that the Back-end (Spring Boot, using Maven/Gradle Wrapper) has its dependencies resolved.

#### Front-end (React)

Navigate to the Front-end folder and install the Node.js dependencies:

```bash
cd frontend
npm install
# ou
yarn install
cd .. # Retorna para a raiz
```

#### Back-end (Spring Boot)

Spring Boot uses the **Maven Wrapper** (`./mvnw`) or **Gradle Wrapper** (`./gradlew`) to manage dependencies. Run the clean install/build command before running the application.

* **Using Maven (`pom.xml`):**
    ```bash
    cd backend
    ./mvnw clean install
    cd ..
    ```

* **Using Gradle (`build.gradle`):**
    ```bash
    cd backend
    ./gradlew clean build
    cd ..
    ```

---

### 💾 Database Initialization (PostgreSQL)

The project uses **PostgreSQL**. The easiest way to initialize the database is via Docker (for execution without `docker-compose`):

1. **Run the PostgreSQL Container:**  
   (Make sure Docker is running)

```bash
docker run --name minha_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=senha-segura-123 -e POSTGRES_DB=nome_do_banco -p 5432:5432 -d postgres:16
```

2. **Run the Migrations:**  
   The **Spring Boot** Back-end usually manages the database schema automatically at startup (via Hibernate `ddl-auto`) or by using tools such as **Flyway** or **Liquibase**.

* **If Spring Boot manages the schema (default):** No manual action is required; just run the Back-end (see the next section).
* **If using Flyway/Liquibase via Maven:**
    ```bash
    cd backend
    ./mvnw flyway:migrate
    # ou
    ./mvnw liquibase:update
    ```
---

### ⚡ How to Run the Application
Run the application in development mode in **two separate terminals**.

#### Terminal 1: Back-end (Spring Boot)

Start the Spring Boot API. It will attempt to connect to the database running in Docker.

```bash
cd backend
./mvnw spring-boot:run
```
🚀 *The Back-end will be available at **http://localhost:8080**.*

---

#### Terminal 2: Front-end (React, Vite)

Start the Front-end development server.

```bash
cd frontend
npm run dev
# ou
yarn dev
```
🎨 *The Front-end will be available at **http://localhost:5173** (or the port configured in Vite/CRA).*

---

#### 🐳 Full Local Execution with Docker Compose (Including Database)

For a full local execution that includes the Back-end service (**Spring Boot**), Front-end (**React**), and the **PostgreSQL** database, we will use **`docker-compose`** for orchestration.

First, make sure that **Docker Desktop** (on Mac/Windows) or the **Docker service** (on Linux) is running.

- **On Mac/Windows**: simply open the **Docker Desktop** application.
- **On Linux**: run the command below to start the service:

```bash
sudo systemctl start docker
```

---

#### 📦 Steps for build, initialization, and execution

1. Access the root folder of the project (where the `docker-compose.yml` file is located):

```bash
cd /caminho/do/projeto/nome-do-projeto
```

2. Bring up all services (Back-end, Front-end, and Database) defined in the `docker-compose.yml`:

```bash
docker-compose up --build -d
```

> [!NOTE]
> 💡 The `--build` parameter ensures that the latest project images are generated, and `-d` runs the services in detached mode.

3. Check if the containers are running:

```bash
docker ps
```

4. **Run the Database Migrations:**
   The **Spring Boot** Back-end usually manages the database schema (via Flyway/Liquibase or Hibernate) during **service startup**.

* **Verification:** If the Back-end service (`backend` or `api`) is responsible for the migrations, check the logs to confirm that the process has completed.
    ```bash
    docker logs <nome_do_container_backend>
    ```
* *Attention:* The `npm run db:migrate` command is exclusive to Node.js and **must not** be used.

5. Open it in the browser:
   The Front-end should be accessible on the port configured in `docker-compose` (Example: <http://localhost:3000> or <http://localhost:5173>)

6. To stop and remove all containers, networks, and volumes (except named volumes):

```bash
docker-compose down
```

✅ **In summary:** Using **`docker-compose`** simplifies running the complete environment, isolating the dependencies of **Java (Spring Boot)** and **Node.js (React)** and ensuring that PostgreSQL is available.

---

## 🚀 Deploy
Clear instructions for production deployment.

1. **Build the Project:**
   Run the build separately for both artifacts (JAR for the Back-end and static files for the Front-end).

```bash
# 1. Front-end Build (React/Vite) – Generates the /dist folder with static files
cd frontend
npm run build

# 2. Back-end Build (Spring Boot/Maven) – Generates the executable .jar file in /target
cd ../backend
./mvnw clean package
```

2. **Production Environment Configuration:** Set the environment variables in your provider (e.g., Vercel, Railway, Heroku, DigitalOcean).

> 🔑 **Crucial Variables:** Make sure to configure the **database connection** variables (`SPRING_DATASOURCE_URL`, etc.) for the Back-end and the **production API URL** (`VITE_API_URL`) for the Front-end.

3. **Production Execution:**
   The execution method depends on your provider, but generally involves the following:

```bash
# ☕ Running the Spring Boot Back-end (Java JAR)
# This command starts the API using the generated JAR artifact.
java -jar backend/target/nome-do-do-projeto-0.0.1-SNAPSHOT.jar

# 🟢 Running the Front-end (React/Vite)
# The Front-end (static files) is not run via Node, but served by a web server.
# Example of a static file server (using Nginx, Vercel, Netlify, etc.)
# To simulate production locally or run on a simple VPS, use the 'serve' package:
npm install -g serve
serve -s frontend/dist
```

---

## 📂 Folder Structure

Describe the purpose of the main folders.

```
.
├── .editorconfig                # ✍️ Code style standardization.
├── .env.local                   # 🔒 SENSITIVE environment variables for LOCAL development (not versioned).
├── .env.test                    # 🧪 Environment variables for AUTOMATED TESTS.
├── .env.staging                 # ☁️ Environment variables for STAGING.
├── .env.example                 # 🧩 Example of ALL required variables (without sensitive values).
├── .gitignore                   # 🧹 Ignores unversioned files/folders (.env, node_modules, target, etc.).
├── .vscode/                     # ⚙️ IDE environment settings (optional).
├── .github/                     # 🤖 CI/CD (Actions), Issue and Pull Request templates.
├── README.md                    # 📘 Main project documentation.
├── CONTRIBUTING.md              # 🤝 Contribution guide.
├── LICENSE                      # ⚖️ Project license.
├── docker-compose.yml           # 🐳 Orchestration of containers (front/back/db/etc).
├── docker-compose.override.yml  # 🐳 Additional configurations only for development.
│
├── /frontend                    # 📁 React Application
│   ├── .env.example             # 🧩 Front-end environment variables.
│   ├── Dockerfile               # 🐳 Front-end Docker build.
│   ├── .eslintrc.js             # ✨ ESLint rules.
│   ├── .prettierrc              # 🎨 Prettier configuration.
│   ├── /public                  # 📂 Static files and index.html.
│   ├── /src                     # 📂 React source code
│   │   ├── /components          # 🧱 Reusable components (UI).
│   │   ├── /pages               # 📄 Application pages/routes.
│   │   ├── /services            # 🔌 Services and HTTP calls.
│   │   ├── /hooks               # 🎣 Custom hooks.
│   │   ├── /styles              # 🎨 Global styles, themes, Design System.
│   │   ├── /assets              # 🖼️ Imported static resources
│   │   │   ├── /images          # 🖼️ Images.
│   │   │   ├── /icons           # 💡 Icons.
│   │   │   └── /fonts           # ✒️ Custom fonts.
│   │   └── /utils               # 🛠️ Utility functions.
│   ├── package.json             # 📦 Dependencies and scripts.
│   └── yarn.lock / package-lock.json # 🔒 Dependency lockfile.
│
├── /backend                     # 📁 Spring Boot Application
│   ├── .env.example             # 🧩 Back-end environment variables.
│   ├── Dockerfile               # 🐳 Back-end Docker build.
│   │
│   ├── /src/main/java           # 📂 Java source code
│   │   └── /com/example/app
│   │       ├── /controller      # 🎮 REST endpoints.
│   │       ├── /service         # ⚙️ Business logic.
│   │       ├── /repository      # 🗄️ Repositories (JPA/Hibernate).
│   │       ├── /model           # 🧬 Persistent entities (JPA).
│   │       ├── /domain          # 🌐 Pure Domain objects (no annotations).
│   │       ├── /dto             # ✉️ Data Transfer Objects.
│   │       ├── /config          # 🔧 General configurations (DB, Swagger, CORS, etc.).
│   │       ├── /exception       # 💥 Exceptions and global handlers.
│   │       └── /security        # 🛡️ Authentication and Authorization (Spring Security).
│   │
│   ├── /src/main/resources      # 📂 Spring Boot resources
│   │   ├── application.yml         # ⚙️ Main application configuration
│   │   ├── application-dev.yml     # 🧪 Development-specific configurations
│   │   ├── application-prod.yml    # 🚀 Production-specific configurations
│   │   ├── application-test.yml    # 🧪 Configurations for automated tests
│   │   ├── /static                # 🌐 Static files (HTML/CSS/JS).
│   │   ├── /templates             # 🖼️ Thymeleaf/Freemarker templates.
│   │   ├── /messages              # 🌎 Internationalization files (i18n).
│   │   └── /db                    # 💾 Database scripts used by the application
│   │       └── /migration         # 📜 Database migrations (Flyway/Liquibase).
│   │
│   ├── /src/test/java            # 🧪 Unit and integration tests.
│   └── pom.xml / build.gradle    # 🛠️ Build and dependencies.
│
├── /scripts                      # 📜 Automation scripts
│   ├── dev.sh                    # 🚀 Complete development environment.
│   ├── build_all.sh              # 🛠️ Full build (front + back).
│   └── deploy.sh                 # ☁️ Production/staging deployment.
│
├── /docs                         # 📚 Documentation, architecture, C4 models, Swagger/OpenAPI.
└── /tests                        # 🧪 End-to-End tests (Cypress/Playwright).
```

---

## 🎥 Demonstration

Use GIFs and screenshots to show the project in action.  

> [!WARNING]
> Prefer hosting your images on a **CDN** (Content Delivery Network) or **GitHub Pages** to ensure fast loading and avoid broken links. Learn more about GitHub Pages [here](https://github.com/joaopauloaramuni/joaopauloaramuni.github.io).

### 📱 Mobile Application

- Demo GIF (example of user flow):  

| Demo 1 | Demo 2 | Demo 3 | Demo 4 |
|----------------|----------------|----------------|----------------|
| <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 1" height="400"> | <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 2" height="400"> | <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 3" height="400"> | <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 4" height="400"> |
| _Your GIF here_ | _Your GIF here_ | _Your GIF here_ | _Your GIF here_ |

For better visualization, the main screens are arranged side by side.

| Screen | Screenshot |
| :---: | :---: |
| **Initial Screen (Home)** | **Profile / Settings Screen** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 1 do Mobile" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 2 do Mobile" width="120px" height="120px"> |
| **Registration Screen** | **List / Details Screen** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 3 do Mobile" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 4 do Mobile" width="120px" height="120px"> |

### 🌐 Web Application

For better visualization, the main screens are arranged side by side.

| Screen | Screenshot |
| :---: | :---: |
| **Home Page** | **Login Page** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela Inicial da Aplicação Web" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Login" width="120px" height="120px"> |
| **Customer Registration** | **Product Registration** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Cadastro de Clientes" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Cadastro de Produtos" width="120px" height="120px"> |
| **Dashboard (Overview)** | **Admin / Settings Page** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Dashboard" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela Administrativa" width="120px" height="120px"> |

### 💻 Example Terminal Output (for Back-end, API, CLI)

If the project is focused on Back-end services (API, microservice, CLI), use this section to demonstrate interaction with the system and the expected response.

#### 1. API Demonstration (Example with cURL)

Shows a simple call to an API endpoint (e.g., GET listing).

```bash
# Calls the user listing endpoint with the authentication token
curl -X GET 'http://localhost:3000/api/v1/users' \
     -H 'Authorization: Bearer <seu-jwt-token>'
```

**Expected Output:**
```json
{
  "total": 2,
  "users": [
    {
      "id": "1a2b3c",
      "name": "Prof. Aramuni",
      "email": "contato@exemplo.com",
      "status": "active"
    },
    {
      "id": "4d5e6f",
      "name": "Colaborador Teste",
      "email": "teste@exemplo.com",
      "status": "inactive"
    }
  ]
}
```

---

#### 2. CLI/Script Execution Demonstration

Shows how to run a command-line tool or a project maintenance script (e.g., run migrations or a scheduled job).

```bash
# Runs the Schema validation tool
npm run cli validate:schema --target=production
```

**Expected Output:**
```text
[INFO] Starting database validation...
[SUCCESS] 15/15 tables verified.
[WARNING] Column 'descricao' in table 'produtos' is nullable.
[SUCCESS] Validation completed. No critical errors found.
Execution time: 1.25s
```

---

## 🧪 Tests

### Unit and Integration Tests
To run unit and integration tests:

```
npm run test
```
*Tool used: Jest, Vitest, Mocha, etc.*

### End-to-End (E2E) Tests
To run end-to-end (E2E) tests:

```
npm run test:e2e
```
*Tool used: Cypress, Playwright, Selenium, etc.*

---

## 🔗 Documentation Used

List links to technical documentation, references for complex libraries, or style guides that were crucial for the project.

* 📖 **Framework/Library (Front-end):** [Official **React** Documentation](https://react.dev/reference/react)
* 📖 **Build Tool (Front-end):** [**Vite** Configuration Guide](https://vitejs.dev/config/)
* 📖 **Framework (Back-end):** [Official **Spring Boot** Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
* 📖 **Containerization:** [**Docker** Reference Documentation](https://docs.docker.com/)
* 📖 **Style Guide:** [**Conventional Commits** (Commit Message Standard)](https://www.conventionalcommits.org/en/v1.0.0/)
* 📖 **Internal Documentation:** [Project Design System](./docs/design-system.md)

---

## 👥 Authors
List the main contributors. You can use links to their profiles.

| 👤 Name | 🖼️ Photo | :octocat: GitHub | 💼 LinkedIn | 📤 Gmail |
|---------|----------|-----------------|-------------|-----------|
| Name 1  | <div align="center"><img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/user1"><img src="https://joaopauloaramuni.github.io/image/github6.png" width="50px" height="50px"></a></div> | <div align="center"><a href="https://www.linkedin.com/in/user1"><img src="https://joaopauloaramuni.github.io/image/linkedin2.png" width="50px" height="50px"></a></div> | <div align="center"><a href="mailto:user1@gmail.com"><img src="https://joaopauloaramuni.github.io/image/gmail3.png" width="50px" height="50px"></a></div> |
| Name 2  | <div align="center"><img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/user2"><img src="https://joaopauloaramuni.github.io/image/github6.png" width="50px" height="50px"></a></div> | <div align="center"><a href="https://www.linkedin.com/in/user2"><img src="https://joaopauloaramuni.github.io/image/linkedin2.png" width="50px" height="50px"></a></div> | <div align="center"><a href="mailto:user2@gmail.com"><img src="https://joaopauloaramuni.github.io/image/gmail3.png" width="50px" height="50px"></a></div> |

> [!TIP]
> 💡 **Tip:** Choose a professional photo, preferably a headshot, avoiding low-quality images, excessive filters, or distracting elements.

---

## 🤝 Contribution
Guide for contributing to the project.

1. Fork the project.
2. Create a branch for your feature (`git checkout -b feature/my-feature`).
3. Commit your changes (`git commit -m 'feat: Add new feature X'`). **(Use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/))**
4. Push to the branch (`git push origin feature/my-feature`).
5. Open a **Pull Request (PR)**.

> [!IMPORTANT]
> 📝 **Rules:** Please check the [`CONTRIBUTING.md`](./CONTRIBUTING.md) file for details about our code style guide and PR submission process.

---

## 🙏 Acknowledgements
In an academic environment, citing sources and inspirations is crucial (academic integrity). In a professional environment, it shows humility and connection with the community.

I would like to thank the following channels and people who were fundamental for the development of this project:

* [**Software Engineering PUC Minas**](https://www.instagram.com/engsoftwarepucminas/) - For institutional support, academic structure, and promotion of innovation and best engineering practices.
* [**Prof. Dr. João Paulo Aramuni**](https://github.com/joaopauloaramuni) - For valuable teachings on **Software Architecture** and **Design Patterns**.
* [**Fernanda Kipper**](https://www.instagram.com/kipper.dev/) - For valuable lessons in **Web Development**, **DevOps**, and best practices in **Front-end**.
* [**Rodrigo Branas**](https://branas.io/) - For exceptional teaching in **Clean Architecture** and **Clean Code**.
* [**Código Fonte TV**](https://codigofonte.tv/) - For extensive content, tutorials, news coverage, and support for the **Web Development** community.

---

## 📄 License

This project is distributed under the **[MIT License](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/blob/main/LICENSE)**.

---
