# 📄 Projeto PdfTranslator

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Um tradutor de PDFs do inglês para o português brasileiro, desenvolvido com **Spring Boot**, **Thymeleaf** e **Apache PDFBox**. O projeto extrai o texto de arquivos PDF, divide em blocos otimizados e traduz utilizando a API pública do Google Translate, entregando um arquivo `.txt` pronto para download.

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

---

## 🛡 Licença

Este projeto está sob a licença **MIT**.
