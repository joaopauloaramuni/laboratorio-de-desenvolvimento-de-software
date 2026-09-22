<!-- Este template foi criado para servir como referência e pode ser facilmente adaptado para diferentes projetos de desenvolvimento -->

---

# 🏷️ Nome do Projeto 👨‍💻

> [!NOTE]
> Breve descrição do projeto. **Foque no principal valor/benefício.**  
> Crie uma **logo** para o projeto que represente a aplicação em questão.

<table>
  <tr>
    <td width="800px">
      <div align="justify">
        Este <b>README.md</b> apresenta um template organizado, ideal para servir como referência acadêmica e profissional em projetos de desenvolvimento, desde trabalhos da faculdade até projetos desenvolvidos para etapas técnicas de processos seletivos. Ele reúne as <i>seções essenciais</i> recomendadas pelo <a href="https://github.com/joaopauloaramuni">Prof. Dr. João Paulo Aramuni</a>, permitindo <i>organização clara</i>, <i>documentação eficiente</i> e <i>padronização</i> entre diferentes trabalhos. O objetivo deste esqueleto é <b>facilitar a construção de projetos bem documentados</b>, oferecendo um <i>guia completo</i> que inclui <b>boas práticas</b>, instruções de execução, tecnologias utilizadas, arquitetura, estruturas de pastas, testes, links úteis e orientações para colaboração. Esse template ajuda estudantes a desenvolverem <b>documentação de qualidade profissional</b> desde os primeiros períodos, promovendo <i>clareza</i>, <i>reprodutibilidade</i> e <i>padronização</i> nos projetos.
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

## 🚧 Status do Projeto

### Exemplos de badges básicos:

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/joaopauloaramuni/joaopauloaramuni/main.yml?branch=main)](https://github.com/joaopauloaramuni/joaopauloaramuni/actions/workflows/main.yml)
[![Test Coverage](https://codecov.io/gh/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/branch/main/graph/badge.svg)](https://codecov.io/gh/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software)
[![Versão](https://img.shields.io/badge/Versão-v1.0.0-blue)](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/releases)
[![Licença](https://img.shields.io/github/license/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software)](#licença)

### Outros exemplos de badges:

[![Versão](https://img.shields.io/badge/Versão-v1.0.0-blue?style=for-the-badge)](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/releases) ![React](https://img.shields.io/badge/React-19.1.1-007ec6?style=for-the-badge&logo=react&logoColor=white) ![Vite](https://img.shields.io/badge/Vite-7.1.2-007ec6?style=for-the-badge&logo=vite&logoColor=white) ![Java](https://img.shields.io/badge/Java-17-007ec6?style=for-the-badge&logo=openjdk&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.9.9-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-007ec6?style=for-the-badge&logo=springboot&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.1-007ec6?style=for-the-badge&logo=thymeleaf&logoColor=white) ![GitHub repo size](https://img.shields.io/github/repo-size/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=files) ![GitHub directory file count](https://img.shields.io/github/directory-file-count/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=files) ![GitHub stars](https://img.shields.io/github/stars/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=github) ![GitHub forks](https://img.shields.io/github/forks/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=git) ![GitHub language count](https://img.shields.io/github/languages/count/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=python) ![GitHub license](https://img.shields.io/github/license/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&color=007ec6&logo=opensourceinitiative) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&color=007ec6&logo=gitkraken) ![GitHub last commit](https://img.shields.io/github/last-commit/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software?style=for-the-badge&logo=clockify) ![Vercel](https://vercelbadge.vercel.app/api/joaopauloaramuni/joaopauloaramuni-portfolio?style=for-the-badge) ![Views Counter](https://views-counter.vercel.app/badge?pageId=https%3A%2F%2Fgithub%2Ecom%2Fjoaopauloaramuni%2Flaboratorio-de-desenvolvimento-de-software&leftColor=555555&rightColor=007ec6&type=total&label=RepoViews)

---

## 📚 Índice
- [Links Úteis](#-links-úteis)
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura](#-arquitetura)
  - [Exemplos de diagramas](#exemplos-de-diagramas)
- [Instalação e Execução](#-instalação-e-execução)
  - [Pré-requisitos](#pré-requisitos)
  - [Variáveis de Ambiente](#-variáveis-de-ambiente)
     - [1 Back-end (Spring Boot)](#1-back-end-spring-boot)
     - [2 Front-end (React, Vite)](#2-front-end-react-vite)
     - [3 Exemplos de Variáveis de Ambiente na Vercel](#3-exemplos-de-variáveis-de-ambiente-na-vercel)
  - [Instalação de Dependências](#-instalação-de-dependências)
    - [Front-end (React)](#front-end-react)
    - [Back-end (Spring Boot)](#back-end-spring-boot)
  - [Inicialização do Banco de Dados (PostgreSQL)](#-inicialização-do-banco-de-dados-postgresql)
  - [Como Executar a Aplicação](#-como-executar-a-aplicação)
    - [Terminal 1: Back-end (Spring Boot)](#terminal-1-back-end-spring-boot)
    - [Terminal 2: Front-end (React, Vite)](#terminal-2-front-end-react-vite)
    - [Execução Local Completa com Docker Compose (Incluindo Banco de Dados)](#-execução-local-completa-com-docker-compose-incluindo-banco-de-dados)
    - [Passos para build, inicialização e execução](#-passos-para-build-inicialização-e-execução)
- [Deploy](#-deploy)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Demonstração](#-demonstração)
  - [Aplicativo Mobile](#-aplicativo-mobile)
  - [Aplicação Web](#-aplicação-web)
  - [Exemplo de saída no Terminal (para Back-end, API, CLI)](#-exemplo-de-saída-no-terminal-para-back-end-api-cli)
- [Testes](#-testes)
- [Documentações utilizadas](#-documentações-utilizadas)
- [Autores](#-autores)
- [Contribuição](#-contribuição)
- [Agradecimentos](#-agradecimentos)
- [Licença](#-licença)

---

## 🔗 Links Úteis
* 🌐 **Demo Online:** [Acesse a Aplicação Web](<link-da-demo-web>)
  > 💻 **Descrição:** Link para a aplicação em ambiente de produção (Ex: hospedado na Vercel, Netlify ou AWS S3).
* 📱 **Download Mobile:** [App Store](<link-app-store>) | [Google Play](<link-google-play>) | [APK Direto](<link-para-apk-direto>)
  > 📱 **Descrição:** Links diretos para download nas lojas de aplicativos (se aplicável) e para o arquivo de instalação direta no Android (APK).
* 📖 **Documentação:** [Leia a Wiki/Docs](<link-para-docs>)
  > 📚 **Descrição:** Acesso à documentação técnica completa do projeto (Ex: Swagger/OpenAPI para API, ou Wiki interna).

---

## 📝 Sobre o Projeto
Nesta seção, descreva de forma clara e objetiva **o propósito do seu projeto**, explicando:

- **Por que ele existe** — qual motivação levou à sua criação.  
- **Qual problema ele resolve** — que dor, necessidade ou oportunidade ele atende.  
- **Qual o contexto** — acadêmico, profissional, pessoal, experimental, etc.  
- **Onde ele pode ser utilizado** — cenários reais ou simulados.  

Procure responder perguntas como:
- *Qual foi a ideia inicial do projeto?*
- *O que ele entrega de valor ao usuário?*
- *Por que alguém utilizaria ou contribuiria com esse projeto?*
- *O que o torna relevante ou interessante?*

Escreva de forma objetiva, mas completa, para que qualquer pessoa entenda rapidamente **o que é** e **por que importa**.

> [!NOTE]
> Esta seção segue boas práticas de documentação profissional e deve ser ajustada conforme o tipo e o objetivo do seu projeto.

---

## ✨ Funcionalidades Principais
Liste as funcionalidades de forma clara e objetiva.

- 🔐 **Autenticação Segura:** Login, Cadastro e Recuperação de Senha.
- 📈 **Painel de Controle:** Visualização de dados em tempo real com gráficos.
- ⚙️ **Gerenciamento de CRUD:** Criação, Leitura, Atualização e Deleção de recursos (e.g., Usuários, Itens, Posts).
- 📊 **Relatórios Exportáveis:** Exportação de dados em PDF, CSV ou Excel.
- 🌐 **Internacionalização (i18n):** Suporte a múltiplos idiomas.
- 🧵 **Logs e Monitoramento:** Registro detalhado de atividades e análise de desempenho.
- 🔄 **Integração com APIs Externas:** Conexão com serviços de terceiros (pagamentos, mapas, autenticação, etc.).
- 📨 **Sistema de Notificações:** Envio de alertas por e-mail, push ou notificações internas.

---

## 🛠 Tecnologias Utilizadas

As seguintes ferramentas, frameworks e bibliotecas foram utilizados na construção deste projeto. Recomenda-se o uso das versões listadas (ou superiores) para garantir a compatibilidade.

### 💻 Front-end

* **Framework/Biblioteca:** [Ex: React v18, Vue.js v3, Angular v17]
* **Linguagem/Superset:** [Ex: TypeScript, JavaScript ES6+]
* **Estilização:** [Ex: Tailwind CSS, Sass, Styled Components, Material UI]
* **Gerenciamento de Estado:** [Ex: Redux Toolkit, Zustand, Context API]
* **Build Tool:** [Ex: Vite, Webpack]

### 🖥️ Back-end

* **Linguagem/Runtime:** [Ex: Java 17 (JDK), Node.js v20, Python 3.11]
* **Framework:** [Ex: Spring Boot 3.x, NestJS, Express, Django]
* **Banco de Dados:** [Ex: PostgreSQL, MySQL, MongoDB]
* **ORM / Query Builder:** [Ex: Hibernate/JPA, Prisma, TypeORM]
* **Autenticação:** [Ex: JWT, OAuth2, Spring Security]

### 📱 Mobile (Opcional)

* **Framework:** [Ex: React Native, Flutter, Kotlin Multiplatform]
* **Ferramentas:** [Ex: Expo, Android Studio, Xcode]

### ⚙️ Infraestrutura & DevOps

* **Containerização:** [Ex: Docker, Docker Compose]
* **Orquestração:** [Ex: Kubernetes (K8s)]
* **Cloud:** [Ex: AWS (EC2, RDS, S3), Vercel, Heroku, Google Cloud]
* **CI/CD:** [Ex: GitHub Actions, Jenkins, SonarQube]

---

## 🏗 Arquitetura

Descreva aqui a **arquitetura completa do sistema**, explicando como as camadas, módulos e componentes foram organizados. Informe também **por que** essa arquitetura foi escolhida e **quais problemas ela ajuda a resolver**.

Você pode incluir:

- **Visão geral da arquitetura** (ex.: camadas, módulos, microsserviços, monólito modular, hexagonal, MVC etc.)
- **Principais componentes** e o papel de cada um
- **Padrões de design adotados** (ex.: Repository, Service Layer, DTOs, Factory, Observer)
- **Fluxo de dados** entre as partes do sistema
- **Tecnologias utilizadas em cada camada**
- **Decisões arquiteturais importantes**
- **Trade-offs** ou limitações relevantes

### Exemplos de diagramas

Para melhor visualização e entendimento da estrutura do sistema, os diagramas principais estão organizados lado a lado.

| Diagrama de Arquitetura | Detalhe da Arquitetura |
| :---: | :---: |
| **Visão Geral (Macro)** | **Camada de Serviço (Micro)** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Visão Geral do Sistema" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Componentes ou Serviço X" width="120px" height="120px"> |
| **Modelo de Dados (Entidades)** | **Fluxo de Autenticação** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Entidade-Relacionamento (DER)" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Sequência de Login" width="120px" height="120px"> |
| **Infraestrutura (Cloud)** | **API Gateway (Rotas)** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Diagrama de Deploy na AWS/Vercel" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Mapa de Endpoints da API" width="120px" height="120px"> |

---

## 🔧 Instalação e Execução

### Pré-requisitos
Certifique-se de que o usuário tenha o ambiente configurado.

* **Java JDK:** Versão **17** ou superior (Necessário para o **Back-end Spring Boot**)
* **Node.js:** Versão LTS (v18.x ou superior) (Necessário para o **Front-end React**)
* **Gerenciador de Pacotes:** npm ou yarn
* **Docker** (Opcional, mas **altamente recomendado** para rodar o Banco de Dados)

---

### 🔑 Variáveis de Ambiente

Crie arquivos `.env` específicos e/ou configure as variáveis de ambiente no seu sistema para cada parte da aplicação.

#### 1 Back-end (Spring Boot)

Configure estas variáveis como **variáveis de ambiente do sistema** ou em um arquivo de configuração do Spring (ex: `application.properties`/`application.yml`).

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `SERVER_PORT` | Porta onde o Back-end será executado. | `8080` |
| `SPRING_DATASOURCE_URL` | URL de conexão JDBC (PostgreSQL). | `jdbc:postgresql://localhost:5432/meubanco` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco de dados. | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco de dados. | `senha-segura-123` |
| `JWT_SECRET` | Chave secreta para assinatura de tokens (Opcional). | `chave_super_segura_base64` |

#### 2 Front-end (React, Vite)

Crie um arquivo **`.env`** na raiz da pasta `/frontend` e use o prefixo `VITE_` (ou `REACT_APP_` se estiver usando CRA) para expor as variáveis ao *bundle* da aplicação.

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `VITE_API_URL` | URL base do endpoint do Backend Spring Boot. | `http://localhost:8080/api` |
| `VITE_EMAILJS_PUBLIC_KEY` | Chave pública para serviços de e-mail (Exemplo). | `sua_public_key_aqui` |
| `VITE_GOOGLE_MAPS_KEY` | Chave de API para serviços de mapas (Opcional). | `AIzaSy...` |

---

#### 3. Exemplos de Variáveis de Ambiente na Vercel

A Vercel permite configurar variáveis no painel (Project Settings > Environment Variables).
Aqui estão exemplos comuns utilizadas em aplicações front-end e full-stack:

---

##### **Exemplo 1 – Front-end com Next.js usando API externa**

```
NEXT_PUBLIC_API_URL=https://meu-backend.vercel.app/api
NEXT_PUBLIC_GOOGLE_ANALYTICS_ID=G-seu_google_analytics_id_aqui
```

---

##### **Exemplo 2 – Aplicação Full-stack (Next.js + Prisma + PostgreSQL)**

```
DATABASE_URL=postgresql://admin:senha-super-segura@ep-meu-banco.aws.neon.tech:5432/verceldb
NEXTAUTH_SECRET=uma_chave_muito_longa_e_segura
NEXTAUTH_URL=https://meu-sistema.vercel.app
```

---

##### **Exemplo 3 – Integração com APIs externas**

```
STRIPE_SECRET_KEY=sk_live_seu_stripe_key_aqui
OPENAI_API_KEY=sk-sua_openai_key_aqui
SENDGRID_API_KEY=SG.sua_sendgrid_key_aqui
```

---

##### **Exemplo 4 – Frontend com Vite (EmailJS)**

```
VITE_EMAILJS_SERVICE_ID=seu_service_id_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_ME=seu_template_id_for_me_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_SENDER=seu_template_id_for_sender_aqui
VITE_EMAILJS_PUBLIC_KEY=sua_public_key_aqui
```

> **Obs:** As variáveis de ambiente em projetos **Vite** precisam começar com `VITE_` para que o Vite as reconheça e as inclua no *bundle* do frontend; variáveis sem esse prefixo não ficam disponíveis no código do cliente.

---

Para adicionar essas variáveis:

1.  Acesse a página de Environment Variables do seu projeto no Vercel (ex.: `https://vercel.com/<seu-usuario>/<seu-projeto>/settings/environment-variables`)
2.  Clique em **"Add"** para adicionar cada variável com o nome e valor correspondente.

Alternativamente, se estiver desenvolvendo localmente, crie um arquivo **`.env.local`** dentro da pasta **`frontend`** do seu projeto com o seguinte conteúdo:

```
# Variável essencial para conectar ao Back-end Spring Boot rodando localmente (normalmente na porta 8080)
VITE_API_URL=http://localhost:8080/api

# Variáveis para integrações externas de serviço de e-mail
VITE_EMAILJS_SERVICE_ID=seu_service_id_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_ME=seu_template_id_for_me_aqui
VITE_EMAILJS_TEMPLATE_ID_FOR_SENDER=seu_template_id_for_sender_aqui
VITE_EMAILJS_PUBLIC_KEY=sua_public_key_aqui

# Outras chaves de serviço
VITE_GOOGLE_MAPS_KEY=AIzaSy...
```

> 💡 **Localização:** Garanta que este arquivo esteja em **`/frontend/.env.local`** para que o **Vite** consiga carregá-lo e disponibilizar as variáveis para o Front-end durante o desenvolvimento.

### 📦 Instalação de Dependências

Clone o repositório e instale as dependências.

1.  **Clone o Repositório:**

```bash
git clone <URL_DO_SEU_REPOSITÓRIO>
cd <pasta-do-projeto>
```

2.  **Instale as Dependências (Monorepo):**

Como o projeto está dividido, você precisa instalar as dependências separadamente para o Front-end (React, usando NPM/Yarn) e garantir que o Back-end (Spring Boot, usando Maven/Gradle Wrapper) tenha suas dependências resolvidas.

#### Front-end (React)

Acesse a pasta do Front-end e instale as dependências do Node.js:

```bash
cd frontend
npm install
# ou
yarn install
cd .. # Retorna para a raiz
```

#### Back-end (Spring Boot)

O Spring Boot utiliza o **Maven Wrapper** (`./mvnw`) ou **Gradle Wrapper** (`./gradlew`) para gerenciar dependências. Execute o comando de instalação/build limpo antes de rodar.

* **Usando Maven (`pom.xml`):**
    ```bash
    cd backend
    ./mvnw clean install
    cd ..
    ```

* **Usando Gradle (`build.gradle`):**
    ```bash
    cd backend
    ./gradlew clean build
    cd ..
    ```

---

### 💾 Inicialização do Banco de Dados (PostgreSQL)

O projeto utiliza **PostgreSQL**. A forma mais fácil de inicializar o banco é via Docker (para execução sem `docker-compose`):

1. **Rode o Container do PostgreSQL:**  
   (Certifique-se que o Docker está em execução)

```bash
docker run --name minha_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=senha-segura-123 -e POSTGRES_DB=nome_do_banco -p 5432:5432 -d postgres:16
```

2. **Execute as Migrações:**  
   O Back-end **Spring Boot** geralmente gerencia o schema do banco de dados automaticamente no startup (via Hibernate `ddl-auto`) ou utilizando ferramentas como **Flyway** ou **Liquibase**.

* **Se o Spring Boot gerencia o schema (padrão):** Nenhuma ação manual é necessária, basta rodar o Back-end (veja a próxima seção).
* **Se usar Flyway/Liquibase via Maven:**
    ```bash
    cd backend
    ./mvnw flyway:migrate
    # ou
    ./mvnw liquibase:update
    ```
---

### ⚡ Como Executar a Aplicação
Execute a aplicação em modo de desenvolvimento em **dois terminais separados**.

#### Terminal 1: Back-end (Spring Boot)

Inicie a API do Spring Boot. Ela tentará se conectar ao banco de dados rodando no Docker.

```bash
cd backend
./mvnw spring-boot:run
```
🚀 *O Back-end estará disponível em **http://localhost:8080**.*

---

#### Terminal 2: Front-end (React, Vite)

Inicie o servidor de desenvolvimento do Front-end.

```bash
cd frontend
npm run dev
# ou
yarn dev
```
🎨 *O Front-end estará disponível em **http://localhost:5173** (ou a porta configurada no Vite/CRA).*

---

#### 🐳 Execução Local Completa com Docker Compose (Incluindo Banco de Dados)

Para uma execução local que inclui o serviço de Back-end (**Spring Boot**), Front-end (**React**) e o banco de dados **PostgreSQL**, usaremos o **`docker-compose`** para orquestração.

Antes de tudo, certifique-se de que o **Docker Desktop** (no Mac/Windows) ou o **serviço Docker** (em Linux) está em execução.

- **No Mac/Windows**: basta abrir o aplicativo **Docker Desktop**.
- **No Linux**: rode o comando abaixo para iniciar o serviço:

```bash
sudo systemctl start docker
```

---

#### 📦 Passos para build, inicialização e execução

1. Acesse a pasta raiz do projeto (onde o arquivo `docker-compose.yml` está localizado):

```bash
cd /caminho/do/projeto/nome-do-projeto
```

2. Suba todos os serviços (Back-end, Front-end e Banco de Dados) definidos no `docker-compose.yml`:

```bash
docker-compose up --build -d
```

> [!NOTE]
> 💡 O parâmetro `--build` garante que as imagens mais recentes do projeto sejam geradas, e `-d` executa em segundo plano.

3. Verifique se os containers estão rodando:

```bash
docker ps
```

4. **Execute as Migrações do Banco de Dados:**
   O Back-end **Spring Boot** geralmente gerencia o schema do banco de dados (via Flyway/Liquibase ou Hibernate) na **inicialização do serviço**.

* **Verificação:** Se o serviço de Back-end (`backend` ou `api`) for o responsável pelas migrações, verifique os logs para confirmar se o processo foi concluído.
    ```bash
    docker logs <nome_do_container_backend>
    ```
* *Atenção:* O comando `npm run db:migrate` é exclusivo para Node.js e **não** deve ser usado.

5. Abra no navegador:
   O Front-end deve estar acessível na porta configurada no `docker-compose` (Exemplo: <http://localhost:3000> ou <http://localhost:5173>)

6. Para parar e remover todos os containers, redes e volumes (exceto volumes nomeados):

```bash
docker-compose down
```

✅ **Em resumo:** Usar **`docker-compose`** simplifica a execução do ambiente completo, isolando as dependências de **Java (Spring Boot)** e **Node.js (React)** e garantindo que o PostgreSQL esteja disponível.

---

## 🚀 Deploy
Instruções claras para deploy em produção.

1.  **Build do Projeto:**
    Execute o build separadamente para os dois artefatos (JAR para o Back-end e arquivos estáticos para o Front-end).

```bash
# 1. Build do Front-end (React/Vite) - Gera a pasta /dist com arquivos estáticos
cd frontend
npm run build

# 2. Build do Back-end (Spring Boot/Maven) - Gera o arquivo .jar executável em /target
cd ../backend
./mvnw clean package
```

2.  **Configuração do Ambiente de Produção:** Defina as variáveis de ambiente no seu provedor (e.g., Vercel, Railway, Heroku, DigitalOcean).

> 🔑 **Variáveis Cruciais:** Certifique-se de configurar as variáveis de **conexão com o banco de dados** (`SPRING_DATASOURCE_URL`, etc.) para o Back-end e a **URL da API de produção** (`VITE_API_URL`) para o Front-end.

3.  **Execução em Produção:**
    A forma de execução depende do seu provedor, mas geralmente envolve o seguinte:

```bash
# ☕ Execução do Back-end Spring Boot (Java JAR)
# Este comando inicia a API usando o artefato JAR gerado.
java -jar backend/target/nome-do-do-projeto-0.0.1-SNAPSHOT.jar

# 🟢 Execução do Front-end (React/Vite)
# O Front-end (arquivos estáticos) não é executado via Node, mas servido por um servidor web.
# Exemplo de servidor de arquivos estáticos (usando Nginx, Vercel, Netlify, etc.)
# Para simular a produção localmente ou rodar em uma VPS simples, use o pacote 'serve':
npm install -g serve
serve -s frontend/dist
```

---

## 📂 Estrutura de Pastas

Descreva o propósito das pastas principais.

```
.
├── .editorconfig                # ✍️ Padronização de estilo de código.
├── .env.local                   # 🔒 Variáveis SENSÍVEIS do ambiente LOCAL (não versionado).
├── .env.test                    # 🧪 Variáveis de ambiente para TESTES AUTOMATIZADOS.
├── .env.staging                 # ☁️ Variáveis de ambiente para STAGING/HOMOLOGAÇÃO.
├── .env.example                 # 🧩 Exemplo de TODAS as variáveis necessárias (sem valores sensíveis).
├── .gitignore                   # 🧹 Ignora arquivos/pastas não versionadas (.env, node_modules, target, etc.).
├── .vscode/                     # ⚙️ Configurações de ambiente da IDE (opcional).
├── .github/                     # 🤖 CI/CD (Actions), templates de Issues e Pull Requests.
├── README.md                    # 📘 Documentação principal do projeto.
├── CONTRIBUTING.md              # 🤝 Guia de contribuição.
├── LICENSE                      # ⚖️ Licença do projeto.
├── docker-compose.yml           # 🐳 Orquestração dos containers (front/back/db/etc).
├── docker-compose.override.yml  # 🐳 Configurações extras apenas para desenvolvimento.
│
├── /frontend                    # 📁 Aplicação React
│   ├── .env.example             # 🧩 Variáveis de ambiente do Front-end.
│   ├── Dockerfile               # 🐳 Docker build do Front-end.
│   ├── .eslintrc.js             # ✨ Regras do ESLint.
│   ├── .prettierrc              # 🎨 Configuração do Prettier.
│   ├── /public                  # 📂 Arquivos estáticos e index.html.
│   ├── /src                     # 📂 Código-fonte React
│   │   ├── /components          # 🧱 Componentes reutilizáveis (UI).
│   │   ├── /pages               # 📄 Páginas/rotas da aplicação.
│   │   ├── /services            # 🔌 Serviços e chamadas HTTP.
│   │   ├── /hooks               # 🎣 Hooks personalizados.
│   │   ├── /styles              # 🎨 Estilos globais, temas, Design System.
│   │   ├── /assets              # 🖼️ Recursos estáticos importados
│   │   │   ├── /images          # 🖼️ Imagens.
│   │   │   ├── /icons           # 💡 Ícones.
│   │   │   └── /fonts           # ✒️ Fontes personalizadas.
│   │   └── /utils               # 🛠️ Funções utilitárias.
│   ├── package.json             # 📦 Dependências e scripts.
│   └── yarn.lock / package-lock.json # 🔒 Lockfile das dependências.
│
├── /backend                     # 📁 Aplicação Spring Boot
│   ├── .env.example             # 🧩 Variáveis de ambiente do Back-end.
│   ├── Dockerfile               # 🐳 Docker build do Back-end.
│   │
│   ├── /src/main/java           # 📂 Código-fonte Java
│   │   └── /com/exemplo/app
│   │       ├── /controller      # 🎮 Endpoints REST.
│   │       ├── /service         # ⚙️ Regras e lógica de negócio.
│   │       ├── /repository      # 🗄️ Repositórios (JPA/Hibernate).
│   │       ├── /model           # 🧬 Entidades persistentes (JPA).
│   │       ├── /domain          # 🌐 Objetos de Domínio puro (sem anotações).
│   │       ├── /dto             # ✉️ Data Transfer Objects.
│   │       ├── /config          # 🔧 Configurações gerais (DB, Swagger, CORS, etc.).
│   │       ├── /exception       # 💥 Exceptions e handlers globais.
│   │       └── /security        # 🛡️ Autenticação e Autorização (Spring Security).
│   │
│   ├── /src/main/resources      # 📂 Recursos do Spring Boot
│   │   ├── application.yml         # ⚙️ Configuração principal da aplicação
│   │   ├── application-dev.yml     # 🧪 Configurações específicas do ambiente de DESENVOLVIMENTO
│   │   ├── application-prod.yml    # 🚀 Configurações específicas para PRODUÇÃO
│   │   ├── application-test.yml    # 🧪 Configurações usadas nos testes automatizados
│   │   ├── /static                # 🌐 Arquivos estáticos (HTML/CSS/JS).
│   │   ├── /templates             # 🖼️ Templates Thymeleaf/Freemarker.
│   │   ├── /messages              # 🌎 Arquivos de internacionalização (i18n).
│   │   └── /db                    # 💾 Scripts de banco usados pela aplicação
│   │       └── /migration         # 📜 Migrações do banco (Flyway/Liquibase).
│   │
│   ├── /src/test/java            # 🧪 Testes unitários e de integração.
│   └── pom.xml / build.gradle    # 🛠️ Build e dependências.
│
├── /scripts                      # 📜 Scripts de automação
│   ├── dev.sh                    # 🚀 Ambiente de desenvolvimento completo.
│   ├── build_all.sh              # 🛠️ Build geral (front + back).
│   └── deploy.sh                 # ☁️ Deploy em produção/homologação.
│
├── /docs                         # 📚 Documentação, arquitetura, modelos C4, Swagger/OpenAPI.
└── /tests                        # 🧪 Testes End-to-End (Cypress/Playwright).
```

---

## 🎥 Demonstração

Use GIFs e prints para mostrar o projeto em ação.  

> [!WARNING]
> Dê preferência a hospedar suas imagens em um **CDN** (Content Delivery Network) ou no **GitHub Pages** para garantir que elas carreguem rapidamente e não quebrem. Saiba mais sobre o GitHub Pages clicando [aqui](https://github.com/joaopauloaramuni/joaopauloaramuni.github.io).

### 📱 Aplicativo Mobile

- GIF de demonstração (exemplo de fluxo de usuário):  

| Demonstração 1 | Demonstração 2 | Demonstração 3 | Demonstração 4 |
|----------------|----------------|----------------|----------------|
| <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 1" height="400"> | <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 2" height="400"> | <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 3" height="400"> | <img src="https://joaopauloaramuni.github.io/image/fundo_mobile_engsoft.jpeg" alt="Demonstração 4" height="400"> |
| _Sua gif aqui_ | _Sua gif aqui_ | _Sua gif aqui_ | _Sua gif aqui_ |

Para melhor visualização, as telas principais estão organizadas lado a lado.

| Tela | Captura de Tela |
| :---: | :---: |
| **Tela Inicial (Home)** | **Tela de Perfil / Settings** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 1 do Mobile" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 2 do Mobile" width="120px" height="120px"> |
| **Tela de Cadastro** | **Tela de Lista / Detalhes** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 3 do Mobile" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela 4 do Mobile" width="120px" height="120px"> |

### 🌐 Aplicação Web

Para melhor visualização, as telas principais estão organizadas lado a lado.

| Tela | Captura de Tela |
| :---: | :---: |
| **Página Inicial (Home)** | **Página de Login** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela Inicial da Aplicação Web" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Login" width="120px" height="120px"> |
| **Cadastro de Clientes** | **Cadastro de Produtos** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Cadastro de Clientes" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Cadastro de Produtos" width="120px" height="120px"> |
| **Dashboard (Visão Geral)** | **Página Admin / Configurações** |
| <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela de Dashboard" width="120px" height="120px"> | <img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" alt="Tela Administrativa" width="120px" height="120px"> |

### 💻 Exemplo de Saída no Terminal (para Back-end, API, CLI)

Caso o projeto seja focado em serviços de Back-end (API, microserviço, CLI), utilize esta seção para demonstrar a interação com o sistema e a resposta esperada.

#### 1. Demonstração da API (Exemplo com cURL)

Mostra uma chamada simples para um endpoint da API (ex: GET de listagem).

```bash
# Chama o endpoint de listagem de usuários com o token de autenticação
curl -X GET 'http://localhost:3000/api/v1/users' \
     -H 'Authorization: Bearer <seu-jwt-token>'
```

**Saída Esperada:**
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

#### 2. Demonstração de Execução de CLI/Script

Mostra como executar uma ferramenta de linha de comando ou um script de manutenção do projeto (ex: rodar migrações ou um job agendado).

```bash
# Executa a ferramenta de validação de Schema
npm run cli validate:schema --target=production
```

**Saída Esperada:**
```text
[INFO] Iniciando validação do banco de dados...
[SUCCESS] 15/15 tabelas verificadas.
[WARNING] Coluna 'descricao' na tabela 'produtos' é nullable.
[SUCCESS] Validação concluída. Nenhum erro crítico encontrado.
Tempo de execução: 1.25s
```

---

## 🧪 Testes

### Testes Unitários e de Integração
Para rodar os testes da unidade e integração:

```
npm run test
```
*Ferramenta utilizada: Jest, Vitest, Mocha, etc.*

### Testes End-to-End (E2E)
Para rodar os testes de ponta a ponta (E2E):

```
npm run test:e2e
```
*Ferramenta utilizada: Cypress, Playwright, Selenium, etc.*

---

## 🔗 Documentações utilizadas

Liste aqui links para documentação técnica, referências de bibliotecas complexas ou guias de estilo que foram cruciais para o projeto.

* 📖 **Framework/Biblioteca (Front-end):** [Documentação Oficial do **React**](https://react.dev/reference/react)
* 📖 **Build Tool (Front-end):** [Guia de Configuração do **Vite**](https://vitejs.dev/config/)
* 📖 **Framework (Back-end):** [Documentação Oficial do **Spring Boot**](https://docs.spring.io/spring-boot/docs/current/reference/html/)
* 📖 **Containerização:** [Documentação de Referência do **Docker**](https://docs.docker.com/)
* 📖 **Guia de Estilo:** [**Conventional Commits** (Padrão de Mensagens)](https://www.conventionalcommits.org/en/v1.0.0/)
* 📖 **Documentação Interna:** [Design System do Projeto](./docs/design-system.md)

---

## 👥 Autores
Liste os principais contribuidores. Você pode usar links para seus perfis.

| 👤 Nome | 🖼️ Foto | :octocat: GitHub | 💼 LinkedIn | 📤 Gmail |
|---------|----------|-----------------|-------------|-----------|
| Nome 1  | <div align="center"><img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/user1"><img src="https://joaopauloaramuni.github.io/image/github6.png" width="50px" height="50px"></a></div> | <div align="center"><a href="https://www.linkedin.com/in/user1"><img src="https://joaopauloaramuni.github.io/image/linkedin2.png" width="50px" height="50px"></a></div> | <div align="center"><a href="mailto:user1@gmail.com"><img src="https://joaopauloaramuni.github.io/image/gmail3.png" width="50px" height="50px"></a></div> |
| Nome 2  | <div align="center"><img src="https://joaopauloaramuni.github.io/image/aramunilogo.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/user2"><img src="https://joaopauloaramuni.github.io/image/github6.png" width="50px" height="50px"></a></div> | <div align="center"><a href="https://www.linkedin.com/in/user2"><img src="https://joaopauloaramuni.github.io/image/linkedin2.png" width="50px" height="50px"></a></div> | <div align="center"><a href="mailto:user2@gmail.com"><img src="https://joaopauloaramuni.github.io/image/gmail3.png" width="50px" height="50px"></a></div> |

> [!TIP]
> 💡 **Dica:** Escolha uma foto profissional, preferencialmente de rosto, evitando imagens com baixa qualidade, filtros excessivos ou elementos distrativos.

---

## 🤝 Contribuição
Guia para contribuições ao projeto.

1.  Faça um `fork` do projeto.
2.  Crie uma branch para sua feature (`git checkout -b feature/minha-feature`).
3. Commit suas mudanças (`git commit -m 'feat: Adiciona nova funcionalidade X'`). **(Utilize [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/))**
4.  Faça o `push` para a branch (`git push origin feature/minha-feature`).
5.  Abra um **Pull Request (PR)**.

> [!IMPORTANT]
> 📝 **Regras:** Por favor, verifique o arquivo [`CONTRIBUTING.md`](./CONTRIBUTING.md) para detalhes sobre nosso guia de estilo de código e o processo de submissão de PRs.

---

## 🙏 Agradecimentos
Em ambiente acadêmico, citar fontes e inspirações é crucial (integridade acadêmica). Em ambiente profissional, mostra humildade e conexão com a comunidade.

Gostaria de agradecer aos seguintes canais e pessoas que foram fundamentais para o desenvolvimento deste projeto:

* [**Engenharia de Software PUC Minas**](https://www.instagram.com/engsoftwarepucminas/) - Pelo apoio institucional, estrutura acadêmica e fomento à inovação e boas práticas de engenharia.
* [**Prof. Dr. João Paulo Aramuni**](https://github.com/joaopauloaramuni) - Pelos valiosos ensinamentos sobre **Arquitetura de Software** e **Padrões de Projeto**.
* [**Fernanda Kipper**](https://www.instagram.com/kipper.dev/) - Pelos valiosos ensinamentos em **Desenvolvimento Web**, **DevOps** e melhores práticas em **Front-end**.
* [**Rodrigo Branas**](https://branas.io/) - Pela didática excepcional em **Clean Architecture** e **Clean Code**.
* [**Código Fonte TV**](https://codigofonte.tv/) - Pelo vasto conteúdo e cobertura de notícias, tutoriais e apoio à comunidade de **Desenvolvimento Web**.

---

## 📄 Licença

Este projeto é distribuído sob a **[Licença MIT](https://github.com/joaopauloaramuni/laboratorio-de-desenvolvimento-de-software/blob/main/LICENSE)**.

---
