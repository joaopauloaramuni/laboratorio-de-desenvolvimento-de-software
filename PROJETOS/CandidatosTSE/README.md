# 🗳️ Projeto Candidatos TSE

Aplicação Spring Boot que lê o CSV de candidatos do TSE (eleições de 2026, MG) e exibe os candidatos em uma tela única com filtros por cargo, partido e busca por nome/número — incluindo a foto oficial de cada um.

---

### Captura de Tela

- **Index**: A tela única exibe o cabeçalho com o título da aplicação e, logo abaixo, o formulário de filtros (busca por nome/número, cargo e partido) com os botões **Filtrar** e **Limpar**. Em seguida, é exibida a contagem de candidatos encontrados e a grade de cards, cada um trazendo a foto oficial do candidato, número, cargo, partido, idade, ocupação, escolaridade e nome civil. Quando a foto não está disponível, um placeholder "Sem foto" é exibido no lugar.

| <img src="https://joaopauloaramuni.github.io/java-imgs/CandidatosTSE/imgs/index.png" alt="Index" width="1000"/> |
|:----------------------------------------------------:|
|                        Index                         |

| <img src="https://joaopauloaramuni.github.io/java-imgs/CandidatosTSE/imgs/governador.png" alt="Index" width="1000"/> |
|:---------------------------------------------------------------------------:|
|                        Filtro Governardor - Exemplo                         |

| <img src="https://joaopauloaramuni.github.io/java-imgs/CandidatosTSE/imgs/senador.png" alt="Index" width="1000"/> |
|:-----------------------------------------------------------------------:|
|                        Filtro Senador - Exemplo                         |

---

## 📁 Estrutura do projeto

```
📁 CandidatosTSE
│
├── 📁 src
│   └── 📁 main
│       │
│       ├── ☕ java
│       │   └── 📦 com.example.CandidatosTSE
│       │       │
│       │       ├── 🚀 application
│       │       │   └── CandidatosTseApplication.java
│       │       │       └── Classe principal da aplicação Spring Boot
│       │       │
│       │       ├── 🎮 controller
│       │       │   └── CandidatosTseController.java
│       │       │       └── Controlador e rota da tela única de candidatos
│       │       │
│       │       ├── 🧩 model
│       │       │   └── Candidato.java
│       │       │       └── Representa um candidato lido do CSV do TSE
│       │       │
│       │       └── ⚙️ service
│       │           └── CandidatosTseService.java
│       │               └── Serviço responsável por carregar, tratar e filtrar os candidatos
│       │
│       └── 📁 resources
│           │
│           ├── 📊 data
│           │   └── 📁 candidatos
│           │       └── consulta_cand_2026_MG.csv
│           │           └── Base de dados oficial do TSE (candidatos de MG, 2026)
│           │
│           ├── 🎨 static
│           │   │
│           │   ├── 🎨 css
│           │   │   └── style.css
│           │   │       └── Estilização da tela de candidatos
│           │   │
│           │   └── 🖼️ images
│           │       └── 📁 candidatos
│           │           └── Fotos oficiais dos candidatos (padrão FMG<sq>_div.jpg)
│           │
│           └── 🌐 templates
│               └── index.html
│                   └── Tela única com filtros e listagem dos candidatos
│
└── 📄 pom.xml
    └── Dependências e configurações do Maven
```

---

## 📦 Dependências

```xml
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.12.0</version>
</dependency>
```

- **opencsv** — leitura do CSV do TSE (separador `;`, aspas `"`, charset `ISO-8859-1`)
- **Spring Web** — controller, rotas e servidor embutido
- **Thymeleaf** — motor de templates da tela (`index.html`)

---

## ⚙️ `CandidatosTseService`

Responsável por carregar, tratar e filtrar os dados dos candidatos.

| Função | O que faz |
|---|---|
| `carregarCsv()` | Lê o CSV uma única vez na inicialização (`@PostConstruct`), popula a lista de candidatos em memória e ordena por nome de urna. |
| `resolverFotosPorCpf()` | Para candidatos sem foto própria (ex.: titular de Senador), tenta reaproveitar a foto de outra candidatura da mesma pessoa (mesmo nome + número), já que o TSE às vezes mascara o CPF nesses casos. |
| `fotoExisteNoDisco()` | Verifica se o arquivo `FMG<sqCandidato>_div.jpg` realmente existe no classpath. |
| `filtrar()` | Filtra a lista por cargo, partido e/ou texto (nome, nome de urna ou número) — qualquer parâmetro vazio é ignorado. |
| `listarCargos()` / `listarPartidos()` | Retornam os valores distintos usados para popular os `<select>` de filtro na tela. |

---

## 🎮 `CandidatosTseController`

| Endpoint | Método | Descrição |
|---|---|---|
| `/` | `GET` | Tela única da aplicação. Aceita os parâmetros opcionais `cargo`, `partido` e `texto` para filtrar a lista exibida. Monta o `Model` com os candidatos filtrados, as opções de filtro e os valores atualmente selecionados (para manter o filtro marcado na tela), e retorna a view `index`. |

---

## 🧩 `Candidato` (model)

Representa uma linha do CSV do TSE, já traduzida para os campos usados na tela (nome, cargo, partido, foto, situação da candidatura, idade calculada a partir da data de nascimento, etc.). É o objeto que trafega entre o `Service` e o `Controller` e que o Thymeleaf usa diretamente no `index.html` para renderizar cada card de candidato — mantendo a lógica de leitura do CSV isolada do restante da aplicação.

---

## 🔗 Documentação e links úteis

- [Dados Abertos do TSE — Candidatos 2026](https://dadosabertos.tse.jus.br/dataset/candidatos-2026)
- [opencsv no Maven Repository](https://mvnrepository.com/artifact/com.opencsv/opencsv)

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT — veja o arquivo [LICENSE](LICENSE) para mais detalhes.