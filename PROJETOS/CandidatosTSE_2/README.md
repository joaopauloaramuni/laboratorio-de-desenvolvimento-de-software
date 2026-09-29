# 🗳️ Projeto Candidatos TSE 2: Perfil dos Candidatos

Aplicação Spring Boot que lê o CSV de candidatos do TSE (eleições de 2026, MG) e exibe o **perfil** dos candidatos em uma tela única, com filtros por **gênero**, **escolaridade** e **faixa etária**, incluindo a foto oficial de cada um.

---

### Captura de Tela

- **Index**: A tela é dividida em duas colunas. À esquerda fica o painel de filtros: gênero (botões de opção), escolaridade (lista de seleção), faixa etária (idade mínima e máxima) e os botões **Filtrar** e **Limpar**. À direita aparecem a contagem de candidatos encontrados e a lista de cards horizontais, cada um com a foto oficial, nome de urna, número, cargo, partido, etiquetas de gênero e idade, escolaridade e ocupação. Em telas estreitas, o painel de filtros vai para cima da lista.

| <img src="https://joaopauloaramuni.github.io/java-imgs/CandidatosTSE_2/imgs/index.png" alt="Index" width="1000"/> |
|:----------------------------------------------------:|
|                        Index                         |

---

## 📁 Estrutura do projeto

```
📁 CandidatosTSE_2
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
│       │       │       └── Rota da tela única com filtros por gênero, escolaridade e faixa etária
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
│           │   │       └── Estilização da tela (painel lateral + cards horizontais)
│           │   │
│           │   └── 🖼️ images
│           │       └── 📁 candidatos
│           │           └── Fotos oficiais dos candidatos (padrão FMG<sq>_div.jpg)
│           │
│           └── 🌐 templates
│               └── index.html
│                   └── Tela única com painel de filtros e lista de candidatos
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

- **opencsv**: leitura do CSV do TSE (separador `;`, aspas `"`, charset `ISO-8859-1`)
- **Spring Web**: controller, rotas e servidor embutido
- **Thymeleaf**: motor de templates da tela (`index.html`)

---

## ⚙️ `CandidatosTseService`

Responsável por carregar, tratar e filtrar os dados dos candidatos.

| Função | O que faz |
|---|---|
| `carregarCsv()` | Lê o CSV uma única vez na inicialização (`@PostConstruct`), popula a lista de candidatos em memória e ordena por nome de urna. |
| `resolverFotosPorCpf()` | Para candidatos sem foto própria (ex.: titular de Senador), tenta reaproveitar a foto de outra candidatura da mesma pessoa (mesmo nome + número). |
| `fotoExisteNoDisco()` | Verifica se o arquivo `FMG<sqCandidato>_div.jpg` realmente existe no classpath. |
| `filtrarPerfil()` | Filtra a lista por gênero, escolaridade e/ou faixa etária (idade mínima e máxima). Qualquer parâmetro vazio é ignorado. |
| `listarGeneros()` / `listarEscolaridades()` | Retornam os valores distintos usados para montar os botões de opção de gênero e o `<select>` de escolaridade. |
| `listarTodos()` | Retorna a lista completa de candidatos. |
| `filtrar()` / `listarCargos()` / `listarPartidos()` | Filtros por cargo, partido e texto do projeto anterior. *Não são usados neste projeto.* |

---

## 🎮 `CandidatosTseController`

| Endpoint | Método | Descrição |
|---|---|---|
| `/` | `GET` | Tela única da aplicação. Aceita os parâmetros opcionais `genero`, `escolaridade`, `idadeMin` e `idadeMax`. Chama `filtrarPerfil()`, envia as opções dos filtros (`listarGeneros()` e `listarEscolaridades()`), devolve ao `Model` os valores atualmente selecionados e retorna a view `index`. |

Exemplo de URL com todos os filtros:

```
http://localhost:8080/?genero=FEMININO&escolaridade=SUPERIOR+COMPLETO&idadeMin=30&idadeMax=45
```

### Parâmetros

| Parâmetro | Tipo | Exemplo | Regra |
|---|---|---|---|
| `genero` | `String` | `FEMININO` | Compara com `getGenero()` (ignora maiúsculas/minúsculas). Vazio = todos. |
| `escolaridade` | `String` | `SUPERIOR COMPLETO` | Compara com `getGrauInstrucao()`. Vazio = todas. |
| `idadeMin` | `Integer` | `30` | Mantém quem tem `getIdade() >= idadeMin`. |
| `idadeMax` | `Integer` | `45` | Mantém quem tem `getIdade() <= idadeMax`. |

> Candidatos com data de nascimento inválida têm `getIdade() == -1` e ficam de fora sempre que algum filtro de idade é usado.

### Atributos enviados ao `Model`

| Atributo | Conteúdo |
|---|---|
| `candidatos` | Lista filtrada |
| `totalEncontrado` | Tamanho da lista filtrada |
| `generos` | `listarGeneros()` (para os botões de opção) |
| `escolaridades` | `listarEscolaridades()` (para o `<select>`) |
| `generoSelecionado` / `escolaridadeSelecionada` | Valores atuais do filtro (`""` quando não informados) |
| `idadeMin` / `idadeMax` | Valores atuais da faixa etária (`null` quando não informados) |

---

## 🧩 `Candidato` (model)

Representa uma linha do CSV do TSE, já traduzida para os campos usados na tela (nome, cargo, partido, foto, gênero, escolaridade, ocupação, idade calculada a partir da data de nascimento etc.). É o objeto que trafega entre o `Service` e o `Controller` e que o Thymeleaf usa diretamente no `index.html` para renderizar cada card.

---

## 🔗 Documentação e links úteis

- [Dados Abertos do TSE: Candidatos 2026](https://dadosabertos.tse.jus.br/dataset/candidatos-2026)
- [opencsv no Maven Repository](https://mvnrepository.com/artifact/com.opencsv/opencsv)

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.