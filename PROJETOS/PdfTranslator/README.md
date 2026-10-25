# 📄 Projeto PdfTranslator

![Java](https://img.shields.io/badge/Java-17-007ec6?style=for-the-badge&logo=openjdk&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.9.9-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-007ec6?style=for-the-badge&logo=springboot&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.1-007ec6?style=for-the-badge&logo=thymeleaf&logoColor=white) ![GitHub repo size](https://img.shields.io/github/repo-size/joaopauloaramuni/pdf-translator?style=for-the-badge&logo=files) ![GitHub directory file count](https://img.shields.io/github/directory-file-count/joaopauloaramuni/pdf-translator?style=for-the-badge&logo=files) ![GitHub stars](https://img.shields.io/github/stars/joaopauloaramuni/pdf-translator?style=for-the-badge&logo=github) ![GitHub forks](https://img.shields.io/github/forks/joaopauloaramuni/pdf-translator?style=for-the-badge&logo=git) ![GitHub language count](https://img.shields.io/github/languages/count/joaopauloaramuni/pdf-translator?style=for-the-badge&logo=python) ![GitHub license](https://img.shields.io/github/license/joaopauloaramuni/pdf-translator?style=for-the-badge&color=007ec6&logo=opensourceinitiative) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/joaopauloaramuni/pdf-translator?style=for-the-badge&color=007ec6&logo=gitkraken) ![GitHub last commit](https://img.shields.io/github/last-commit/joaopauloaramuni/pdf-translator?style=for-the-badge&logo=clockify) ![Views Counter](https://views-counter.vercel.app/badge?pageId=https%3A%2F%2Fgithub%2Ecom%2Fjoaopauloaramuni%2Fpdf-translator&leftColor=555555&rightColor=007ec6&type=total&label=RepoViews)![GitHub Actions](https://img.shields.io/github/actions/workflow/status/joaopauloaramuni/pdf-translator/keep-render-awake.yml?style=for-the-badge&logo=githubactions&label=RenderAlive)

Um tradutor de PDFs do inglês para o português brasileiro, desenvolvido com **Spring Boot**, **Thymeleaf** e **Apache PDFBox**. O projeto extrai o texto de arquivos PDF, divide em blocos otimizados e traduz utilizando a API pública do Google Translate, entregando um arquivo `.txt` pronto para download.

---

## 🖼️ Capturas de Tela

| <img src="https://joaopauloaramuni.github.io/java-imgs/PdfTranslator/imgs/home.png" alt="Home"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/PdfTranslator/imgs/translate.png" alt="Translate"/> |
|:--:|:--:|
| Home | Translate |

---

## ✨ Funcionalidades

- 🚀 **Upload Simples** – Interface web intuitiva com suporte a arrastar e soltar (*drag and drop*).
- 📤 **Extração de Texto** – Utiliza **Apache PDFBox** para extrair o conteúdo textual do PDF.
- 🌎 **Tradução Automática** – Conecta-se à API do Google Translate para traduzir **EN → PT-BR**.
- 🧹 **Pós‑processamento Inteligente** – Remove artefatos de codificação (`%0A`, `%2C`, `,,`) e corrige pontuações duplicadas.
- 📥 **Download Instantâneo** – Retorna um arquivo `.txt` com o texto traduzido.
- 💅 **Interface Moderna** – Desenvolvida com **Thymeleaf**, **CSS3** e **JavaScript** puro (AJAX).

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia       | Descrição                                      |
| ---------------- | ---------------------------------------------- |
| **Java 17**      | Linguagem de programação principal             |
| **Spring Boot**  | Framework para construção da aplicação web     |
| **Thymeleaf**    | Motor de templates para a camada visual        |
| **Apache PDFBox**| Biblioteca para manipulação e extração de PDFs |
| **Maven**        | Gerenciador de dependências e build            |
| **AJAX/Fetch**   | Comunicação assíncrona com o backend           |

---

## 📦 Dependências

| Grupo                     | Artefato                     | Versão  | Descrição                          |
| ------------------------- | ---------------------------- | ------- | ---------------------------------- |
| `org.springframework.boot`| `spring-boot-starter-web`    | 4.0.5   | Suporte para aplicações web REST   |
| `org.springframework.boot`| `spring-boot-starter-test`   | 4.0.5   | Testes unitários e de integração   |
| `org.springframework.boot`| `spring-boot-starter-thymeleaf` | 4.0.5 | Motor de templates Thymeleaf       |
| `org.apache.pdfbox`       | `pdfbox`                     | 3.0.7   | Extração e manipulação de PDFs     |

---

## 📁 Estrutura do Projeto

```
PdfTranslator/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/PdfTranslator/
│   │   │       ├── application/
│   │   │       │   └── PdfTranslatorApplication.java
│   │   │       ├── controller/
│   │   │       │   └── PdfTranslatorController.java
│   │   │       ├── dto/
│   │   │       │   └── GoogleTranslateResponseDTO.java
│   │   │       └── service/
│   │   │           └── PdfTranslatorService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   │   └── translator.js
│   │       │   └── images/
│   │       │       ├── favicon.ico
│   │       │       ├── pdf.png
│   │       │       └── pdf2.png
│   │       └── templates/
│   │           └── home.html
├── pom.xml
└── README.md
```

---

## ⚙️ Como o Service Funciona

O coração da aplicação está na classe **`PdfTranslatorService`**. O fluxo de processamento é o seguinte:

1. **Extração** – O método `extractTextFromPdf()` utiliza o **PDFBox** para ler o arquivo enviado e extrair todo o texto.
2. **Normalização** – `normalizeEncoding()` corrige caracteres especiais e possíveis problemas de codificação (`%20`, `%C3%A7`, etc.).
3. **Limpeza** – `cleanText()` remove quebras de linha excessivas e espaços desnecessários.
4. **Divisão Inteligente** – `split()` quebra o texto em **blocos de até 4500 caracteres**, respeitando parágrafos e frases para não estourar o limite da API do Google.
5. **Tradução** – Para cada bloco, o método `translate()` faz uma requisição `GET` para: `https://translate.googleapis.com/translate_a/single`.
6. **Pós‑processamento** – `postProcessTranslation()` é aplicado na resposta para:
- Decodificar artefatos URL (`%0A` → `\n`, `%2C` → `,`).
- Remover vírgulas e pontuações duplicadas (`,,` → `,`, `;,` → `;`).
- Garantir espaçamento correto.
7. **Download** – O texto completo traduzido é convertido em `byte[]` e enviado como anexo.

---

## 🌐 Endpoints da API

| Método | Rota          | Descrição                                    |
|--------|---------------|----------------------------------------------|
| `GET`  | `/`           | Retorna a página inicial (`home.html`)       |
| `POST` | `/translate`  | Recebe o PDF e retorna o arquivo TXT traduzido |

**Exemplo de requisição `POST` com cURL:**
```bash
curl -F "file=@documento.pdf" http://localhost:8080/translate --output traducao.txt
```

---

## 🚀 Como Rodar o Projeto

### Pré‑requisitos
- **Java 17** ou superior
- **Maven** (3.6+)

### Passos

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/PdfTranslator.git
cd PdfTranslator
```

2. **Compile e empacote com Maven:**
```bash
./mvnw clean package
```
*(No Windows, use `mvnw.cmd clean package`)*

3. **Execute a aplicação:**
```bash
java -jar target/PdfTranslator-0.0.1-SNAPSHOT.jar
```

4. **Acesse no navegador:** 
`http://localhost:8080`


### Rodando diretamente com Maven
```bash
./mvnw spring-boot:run
```

---

## ☁️ Deploy no Render com Docker

A aplicação foi implantada na plataforma **Render** utilizando a opção de **Web Service com suporte a Docker**.

### 🐳 Dockerfile

O projeto utiliza o seguinte `Dockerfile` para empacotar e executar a aplicação:

```dockerfile
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/PdfTranslator-0.0.1-SNAPSHOT.jar"]
```

#### 🔍 Explicação de cada instrução

- **FROM eclipse-temurin:17-jdk**  
  Define a imagem base do container.  
  Neste caso, utiliza o **Java 17 (JDK)** da distribuição Eclipse Temurin.

- **WORKDIR /app**  
  Define o diretório de trabalho dentro do container.  
  Todos os próximos comandos serão executados dentro de `/app`.

- **COPY . .**  
  Copia todos os arquivos do projeto (diretório atual) para o container.

- **RUN ./mvnw clean package -DskipTests**  
  Executa o build da aplicação usando o Maven Wrapper (`mvnw`):
    - `clean` → limpa builds anteriores
    - `package` → gera o `.jar`
    - `-DskipTests` → pula a execução dos testes (mais rápido no build)

- **EXPOSE 8080**  
  Informa que a aplicação dentro do container utiliza a porta `8080`.  
  (Importante para integração com plataformas como o Render)

- **CMD ["java", "-jar", "target/PdfTranslator-0.0.1-SNAPSHOT.jar"]**  
  Define o comando padrão que será executado ao iniciar o container:
    - Executa o arquivo `.jar` gerado
    - Inicia a aplicação Spring Boot

#### 🔍 O que esse Dockerfile faz?

- **Base da imagem:** utiliza o Java 17 (`eclipse-temurin`)
- **Diretório de trabalho:** define `/app` como diretório principal
- **Cópia do projeto:** copia todos os arquivos para dentro do container
- **Build da aplicação:** executa o Maven Wrapper (`mvnw`) para gerar o `.jar`
- **Exposição da porta:** expõe a porta `8080`
- **Execução:** inicia a aplicação Spring Boot via `java -jar`

---

### ⚙️ Configuração no Render

O deploy foi feito como um **Web Service**, utilizando Docker para build e execução.

O Render injeta automaticamente a variável de ambiente `PORT`, que é utilizada no `application.properties`:

```properties
spring.application.name=PdfTranslator

# Server configuration
server.port=${PORT:8080}
server.servlet.context-path=/

# File upload settings - LIMITE DE 10MB
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Thymeleaf cache
spring.thymeleaf.cache=false
```

---

### 📄 Arquivo render.yaml

O projeto também inclui um arquivo `render.yaml`:

```yaml
services:
  - type: web
    name: pdf-translator
    runtime: java
    repo: https://github.com/joaopauloaramuni/pdf-translator
    plan: free
    envVars:
      - key: JAVA_OPTS
        value: "-Xmx512m -Xms256m"
      - key: SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE
        value: "10MB"
      - key: SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE
        value: "10MB"
    buildCommand: |
      chmod +x mvnw
      ./mvnw clean package -DskipTests
    startCommand: |
      java -jar -Dserver.port=$PORT target/PdfTranslator-0.0.1-SNAPSHOT.jar
```

#### ❓ O que é o render.yaml?

O `render.yaml` é um arquivo de **Infraestrutura como Código (IaC)** que permite definir como o serviço será configurado no Render, incluindo:

- Tipo de serviço (web service)
- Variáveis de ambiente
- Comandos de build e execução
- Plano de hospedagem

Ele facilita a automação do deploy e versionamento da configuração junto ao código.

---

#### 🤔 Obrigatoriedade do arquivo `render.yaml`

O arquivo `render.yaml` é obrigatório? 
- **Não.**

Se você estiver utilizando **Docker**, o `render.yaml` **não é necessário**.

👉 O Render consegue fazer o deploy apenas com o `Dockerfile`, pois:
- Ele detecta automaticamente o Dockerfile
- Executa o build da imagem
- Inicia o container

#### ✅ Quando usar cada um?

- **Apenas Dockerfile:** suficiente para a maioria dos casos (mais simples)
- **render.yaml:** útil quando você quer:
    - Automatizar totalmente a configuração do serviço
    - Versionar a infraestrutura junto ao código
    - Configurar múltiplos serviços ou variáveis complexas

---

## 📚 Links Úteis

| Recurso                    | Link                                                                 |
| -------------------------- | -------------------------------------------------------------------- |
| Apache PDFBox              | [https://pdfbox.apache.org/](https://pdfbox.apache.org/)              |
| GitHub PDFBox              | [https://github.com/apache/pdfbox](https://github.com/apache/pdfbox)  |
| Maven Repository PDFBox    | [https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox/3.0.7](https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox/3.0.7) |
| Flaticon (ícones PDF)      | [https://www.flaticon.com/search/2?word=pdf](https://www.flaticon.com/search/2?word=pdf) |
| Favicon.io (gerador)       | [https://favicon.io/favicon-converter/](https://favicon.io/favicon-converter/) |
| Thymeleaf                  | [https://www.thymeleaf.org/](https://www.thymeleaf.org/)              |
| Google Translate (não oficial) | [https://translate.googleapis.com/](https://translate.googleapis.com/) |
| Render (Deploy)            | [https://render.com/](https://render.com/)                            |
| Docker                     | [https://www.docker.com/](https://www.docker.com/)                    |

---

## 🛡 Licença

Este projeto está sob a licença **MIT**.
