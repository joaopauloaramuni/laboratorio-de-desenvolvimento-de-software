# MontyHallAPI (Spring Boot + MongoDB Atlas) 🚪🐐🚗

![Java](https://img.shields.io/badge/Java-17-e63946?style=for-the-badge&logo=openjdk&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.9.11-e63946?style=for-the-badge&logo=apachemaven&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-e63946?style=for-the-badge&logo=springboot&logoColor=white) ![MongoDB](https://img.shields.io/badge/MongoDB_Atlas-e63946?style=for-the-badge&logo=mongodb&logoColor=white) ![JUnit 5](https://img.shields.io/badge/JUnit_5-e63946?style=for-the-badge&logo=junit5&logoColor=white) ![Mockito](https://img.shields.io/badge/Mockito-e63946?style=for-the-badge&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-e63946?style=for-the-badge&logo=docker&logoColor=white)

## 📖 Descrição

API REST feita com **Spring Boot** que reproduz o famoso **problema de Monty Hall**: 3 portas, 1 carro e 2 bodes. Cada partida é gravada no **MongoDB Atlas** (banco na nuvem), e a API ainda mostra o placar de quem **trocou** de porta contra quem **manteve**, além de simular milhares de partidas de uma vez.

Não há interação pelo terminal: o jogo inteiro acontece por **requisições HTTP**. Cada etapa é uma chamada, e o servidor lembra em que ponto cada partida está.

```mermaid
flowchart LR
    A["🧑 Você<br/>Postman, Insomnia,<br/>curl ou um front-end"] -- "HTTP + JSON<br/>POST, GET, DELETE" --> B["☕ Spring Boot<br/>esta API<br/>localhost:8080"]
    B -- "connection string" --> C[("🍃 MongoDB Atlas<br/>coleção jogos")]
```

---

## 🎬 O problema de Monty Hall

| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/porta.svg" alt="Porta 1" width="80"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/porta.svg" alt="Porta 2" width="80"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/porta.svg" alt="Porta 3" width="80"/> |
|:--------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------:|
|                                                    Porta 1                                                     |                                                    Porta 2                                                     |                                                    Porta 3                                                     |

> Você está num programa de TV. Há **3 portas fechadas**: atrás de uma está um **carro 0 km** e atrás das outras duas há **bodes**. Você escolhe uma porta. O apresentador, **que sabe onde está o carro**, abre uma das outras duas e mostra um bode. Então ele pergunta: **"Quer trocar de porta?"**
>
> Vale a pena trocar?

A resposta que quase todo mundo dá é *"tanto faz, agora são duas portas, então é 50% para cada"*. **Está errada.** Quem troca ganha o carro em **2 de cada 3 vezes (≈ 66,7%)**, e quem mantém a porta ganha só em **1 de cada 3 (≈ 33,3%)**.

Este é um dos quebra-cabeças de probabilidade mais famosos do mundo justamente porque a resposta correta parece absurda à primeira vista, e já enganou professores, doutores e até um dos maiores matemáticos do século XX.

### 📺 A origem: *Let's Make a Deal*, na TV americana

| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/lets-make-a-deal.jpeg" alt="Let's Make a Deal" width="480"/> |
|:-------------------------------------------------------------------------------------------------------------------------------------:|
|                                                           Let's Make a Deal                                                           |

O problema leva o nome de **Monty Hall** (1921–2017), apresentador canadense-americano que criou, junto com Stefan Hatos, o programa de auditório **_Let's Make a Deal_** ("Vamos fazer um negócio"), que estreou em **1963** na TV americana e ganhou várias versões ao longo das décadas.

No programa, os participantes faziam trocas com o apresentador: aceitavam um prêmio certo ou arriscavam no que estava escondido atrás de uma cortina, de uma caixa ou de uma das **três grandes portas numeradas** do palco. Atrás delas podia haver um carro... ou um "zonk", um prêmio de brincadeira, como um animal de fazenda.

| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/lets-make-a-deal-palco.png" alt="Palco de Let's Make a Deal" width="480"/> |
|:---------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                 O palco de Let's Make a Deal, com as portas 1, 2 e 3                                                |

O nome "problema de Monty Hall" apareceu pela primeira vez em **1975**, quando o estatístico **Steve Selvin** publicou duas cartas sobre ele na revista científica *The American Statistician*. Mas foi em 1990 que o problema virou um fenômeno, como você vai ver [mais abaixo](#-marilyn-vos-savant-e-a-polêmica-de-1990).

### 🇧🇷 E no Brasil: a Porta dos Desesperados

| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/porta-dos-desesperados-logo.jpg" alt="Porta dos Desesperados" width="480"/> |
|:----------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                                Porta dos Desesperados                                                                |

Por aqui, a dinâmica das três portas ficou famosa com a **Porta dos Desesperados**, quadro que marcou a TV brasileira nos anos 1990 com **Sérgio Mallandro** e seus bordões (*"Ié ié!"*, *"Rá!"*, *"Glu-glu!"*). Crianças escolhiam uma entre três portas: algumas escondiam prêmios e uma escondia um **monstro**, que saía correndo atrás do participante.

| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/porta-dos-desesperados-cenario.jpg" alt="Cenário" width="300"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/porta-dos-desesperados-portas.jpg" alt="Portas 1 e 2" width="400"/> |
|:----------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                      O cenário com as portas 2 e 3                                                       |                                                      As portas 1 e 2 e o boné de hélice                                                      |

> 💡 A Porta dos Desesperados tem o mesmo clima, mas **não as mesmas regras** do problema matemático: lá o apresentador não abria obrigatoriamente uma porta "ruim" antes de oferecer a troca. Esta API segue as **regras exatas do problema de Monty Hall**, e é isso que faz a matemática funcionar (veja [o detalhe que faz tudo funcionar](#️-o-detalhe-que-faz-tudo-funcionar)).

### 🎲 As regras (exatamente como a API joga)

| Etapa | O que acontece | Na API |
| --- | --- | --- |
| 1 | O carro é escondido atrás de uma das 3 portas, ao acaso. Os outros dois são bodes. | `POST /api/jogos` |
| 2 | Você escolhe uma porta. Ela **continua fechada**. | `POST /api/jogos/{id}/escolha` |
| 3 | O apresentador, **que sabe onde está o carro**, abre **outra** porta, **sempre com bode**. | (automático, na mesma resposta) |
| 4 | Ele oferece a troca pela única porta que sobrou fechada. Você decide: trocar ou manter. | `POST /api/jogos/{id}/decisao` |
| 5 | Todas as portas são abertas. | (na mesma resposta) |

| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/carro.svg" alt="Carro" width="160"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" alt="Bode" width="115"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" alt="Bode" width="115"/> |
|:-------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------:|
|                                                   Carro 0 km                                                  |                                                     Bode                                                    |                                                     Bode                                                    |

### 🤔 Por que a intuição falha

Depois que o apresentador abre uma porta, sobram **duas portas fechadas**. O cérebro conclui: "duas opções, então 50% cada". O erro está em achar que as duas portas que sobraram são **iguais**. Elas não são:

- **A sua porta** foi escolhida **às cegas**, quando você não sabia nada. Ela tinha 1/3 de chance e **nada do que o apresentador fez muda isso**: ele sempre consegue abrir um bode, esteja o carro onde estiver.
- **A outra porta** sobreviveu a um **filtro**: o apresentador, que sabe tudo, podia tê-la aberto e **escolheu não abrir**. Isso é informação.

Vamos provar de cinco jeitos diferentes.

---

### ✅ Prova 1: listando todos os casos

Imagine que você escolheu a **porta 1** (o raciocínio é igual para qualquer porta). O carro pode estar em 3 lugares, cada um com a mesma chance:

| Porta 1 (sua) | Porta 2 | Porta 3 | O apresentador abre | Se você **mantém** | Se você **troca** |
| :---: | :---: | :---: | :---: | :---: | :---: |
| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/carro.svg" width="70"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" width="55"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" width="55"/> | a 2 **ou** a 3 | 🚗 **ganha** | 🐐 perde |
| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" width="55"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/carro.svg" width="70"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" width="55"/> | **só pode** abrir a 3 | 🐐 perde | 🚗 **ganha** |
| <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" width="55"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/bode.svg" width="55"/> | <img src="https://joaopauloaramuni.github.io/java-imgs/MontyHallAPI/imgs/carro.svg" width="70"/> | **só pode** abrir a 2 | 🐐 perde | 🚗 **ganha** |
| | | | **Total** | **1 de 3** | **2 de 3** |

Quem troca só perde no caso em que acertou de primeira, e acertar de primeira é o que acontece **apenas 1 vez em 3**.

### ✅ Prova 2: a chance da sua porta não muda

Antes de qualquer porta ser aberta:

$$P(\text{carro na sua porta}) = \frac{1}{3} \qquad P(\text{carro em uma das outras duas}) = \frac{2}{3}$$

O apresentador **sempre** consegue mostrar um bode numa das outras duas, então abrir a porta não traz nenhuma informação sobre a **sua**: ela continua com 1/3. Mas os 2/3 que estavam espalhados entre as outras duas portas agora ficam **concentrados na única que sobrou fechada**, porque a porta aberta tem chance zero.

```text
Antes:      [ sua: 1/3 ]   [ outra: 1/3 ]   [ outra: 1/3 ]
                            \_______________ 2/3 _______/

Depois:     [ sua: 1/3 ]   [ outra: 2/3 ]   [ aberta: 0 🐐 ]
```

Trocar de porta é, na prática, **ficar com as duas outras portas ao mesmo tempo**: se o carro estiver em qualquer uma delas, você ganha.

### ✅ Prova 3: pelo Teorema de Bayes

Para quem quer a versão formal: você escolheu a porta 1 e o apresentador abriu a porta 3. Chame de $C_i$ o evento "o carro está na porta $i$" e de $A_3$ o evento "o apresentador abriu a porta 3".

No início, $P(C_1) = P(C_2) = P(C_3) = \frac{1}{3}$. A chance de o apresentador abrir a porta 3 em cada caso:

| Onde está o carro | O apresentador abre a porta 3 com probabilidade | Por quê |
| --- | :---: | --- |
| $C_1$ (na sua porta) | $\frac{1}{2}$ | as portas 2 e 3 têm bode; ele sorteia uma |
| $C_2$ | $1$ | ele não pode abrir a 1 (sua) nem a 2 (carro) |
| $C_3$ | $0$ | ele nunca revela o carro |

Probabilidade total de ele abrir a porta 3:

$$P(A_3) = \frac{1}{2}\cdot\frac{1}{3} + 1\cdot\frac{1}{3} + 0\cdot\frac{1}{3} = \frac{1}{6} + \frac{2}{6} = \frac{1}{2}$$

Aplicando o Teorema de Bayes:

$$P(C_1 \mid A_3) = \frac{P(A_3 \mid C_1)\,P(C_1)}{P(A_3)} = \frac{\frac{1}{2}\cdot\frac{1}{3}}{\frac{1}{2}} = \frac{1}{3} \quad\leftarrow \text{manter}$$

$$P(C_2 \mid A_3) = \frac{P(A_3 \mid C_2)\,P(C_2)}{P(A_3)} = \frac{1\cdot\frac{1}{3}}{\frac{1}{2}} = \frac{2}{3} \quad\leftarrow \text{trocar}$$

### ✅ Prova 4: e se fossem 100 portas?

Agora são **100 portas**, 1 carro e 99 bodes. Você escolhe a porta 1 (chance de 1%). O apresentador, que sabe onde está o carro, abre **98 portas**, todas com bode, e deixa fechadas só a sua e a **porta 74**.

Você acha mesmo que a porta 1, escolhida no escuro, tem a mesma chance que a única porta que o apresentador **fez questão de pular**? A sua continua com **1%**; a porta 74 fica com **99%**. Com 3 portas o efeito é o mesmo, só que menos gritante: 1/3 contra 2/3.

### ✅ Prova 5: simulando (e esta API faz isso por você)

Quando a intuição não aceita a matemática, o computador resolve: é só jogar milhares de vezes e contar. A árvore abaixo mostra por que o resultado converge para 2/3:

```mermaid
flowchart TD
    A["🚪 Você escolhe uma porta"] --> B["🚗 Acertou o carro<br/>chance 1/3"]
    A --> C["🐐 Escolheu um bode<br/>chance 2/3"]
    B --> D["O apresentador abre<br/>um dos dois bodes"]
    C --> E["O apresentador abre<br/>o ÚNICO outro bode"]
    D --> F["Manter: GANHA 🚗"]
    D --> G["Trocar: perde 🐐"]
    E --> H["Manter: perde 🐐"]
    E --> I["Trocar: GANHA 🚗"]
```

Abra no navegador, com a API rodando:

```text
http://localhost:8080/api/simulacoes?rodadas=100000
```

```json
{
  "rodadas": 100000,
  "trocando": { "partidas": 100000, "vitorias": 66712, "derrotas": 33288, "percentualVitorias": 66.71 },
  "mantendo": { "partidas": 100000, "vitorias": 33288, "derrotas": 66712, "percentualVitorias": 33.29 },
  "teoricoTrocando": 66.67,
  "teoricoMantendo": 33.33,
  "conclusao": "Trocando: 66.71% de vitórias. Mantendo: 33.29%. Trocar de porta foi a melhor estratégia (a teoria diz 2/3 contra 1/3)."
}
```

> Os números mudam a cada execução (o sorteio é aleatório), mas sempre ficam muito perto de 66,67% e 33,33%. Quanto mais rodadas, mais perto: é a **Lei dos Grandes Números**. Com 1.000.000 de rodadas, o resultado costuma bater com a teoria até a segunda casa decimal.

---

### 📰 Marilyn vos Savant e a polêmica de 1990

Em **setembro de 1990**, um leitor chamado Craig F. Whitaker enviou o problema para a coluna **"Ask Marilyn"**, da revista americana *Parade*, que chegava a milhões de lares. A colunista era **Marilyn vos Savant**, que tinha ficado conhecida por constar no *Guinness Book* com o QI mais alto já registrado.

A resposta dela foi direta: **sim, você deve trocar**; quem troca tem 2/3 de chance de ganhar.

O que veio depois virou história da divulgação científica. A revista recebeu **cerca de 10 mil cartas**, **quase mil delas assinadas por pessoas com doutorado**, muitas de professores de matemática e estatística, e a grande maioria dizia que ela estava **errada**: que a chance era 50%, que ela estava espalhando ignorância, que deveria se retratar. Algumas cartas eram bem pouco educadas.

Marilyn não recuou. Voltou ao assunto em colunas seguintes, explicou com a tabela de casos e com a versão das muitas portas, e chegou a propor que **professores fizessem o experimento em sala de aula**, com copos e uma moeda ou cartas de baralho. Milhares de turmas fizeram, e os resultados confirmaram: trocar vence em cerca de 2/3 das vezes. Muitos que tinham escrito para corrigi-la mandaram novas cartas admitindo o erro.

Ela não foi a única a provocar desconfiança:

- **Paul Erdős**, um dos matemáticos mais produtivos da história, também se recusou a acreditar na resposta. Segundo o relato do livro *O Homem que Só Gostava de Números*, de Paul Hoffman, ele só se convenceu depois que o colega Andrew Vázsonyi lhe mostrou uma **simulação de computador**, exatamente o que o endpoint `/api/simulacoes` faz.
- Em **1991**, o jornal *The New York Times* publicou uma reportagem em que o próprio **Monty Hall** encenou o jogo com o jornalista John Tierney. Hall lembrou um detalhe importante: no programa real **ele não era obrigado** a abrir uma porta nem a oferecer a troca, e podia usar isso psicologicamente contra o participante. A resposta de Marilyn vale para as regras do enunciado, em que o apresentador **sempre** abre um bode e **sempre** oferece a troca.

### ⚠️ O detalhe que faz tudo funcionar

O resultado 2/3 depende de três condições, todas garantidas por esta API (classe `service/Apresentador.java`):

1. O apresentador **sabe** onde está o carro.
2. Ele **sempre** abre uma porta diferente da sua, e **sempre com bode**.
3. Ele **sempre** oferece a troca.

Se qualquer uma mudar, a resposta muda. O exemplo mais conhecido: se o apresentador abrisse uma das outras portas **ao acaso** (sem saber onde está o carro) e, por sorte, aparecesse um bode, aí sim as duas portas restantes teriam **50% cada**. É a **informação do apresentador** que transforma o 1/3 + 1/3 das outras portas em 2/3 para a porta oferecida.

---

## 📦 Pacotes e responsabilidades

```text
com.montyhall.api
├── application   → classe main (sobe a aplicação)
├── config        → lê o application.properties e configura MongoDB e CORS
├── controller    → recebe as requisições HTTP
├── service       → regras do jogo, sorteios e simulação
├── dto           → o que entra e sai em JSON (esconde o carro até o fim)
├── model         → classe Jogo (documento do MongoDB), portas, prêmios e etapas
├── repository    → acesso ao MongoDB (MongoRepository)
└── exception     → exceções próprias + tratamento global de erros
```

**Caminho de uma requisição:**

```text
Cliente → Controller → Service → Repository → MongoDB Atlas
              │            │
              │            └─ se algo der errado → Exception → GlobalExceptionHandler → JSON de erro
              └─ converte o Jogo em JogoResposta (DTO) antes de devolver
```

### 📁 application

| Classe | Responsabilidade |
| --- | --- |
| `MontyHallApplication` | Método `main`. Como fica no pacote `application`, usa `scanBasePackages` e `@EnableMongoRepositories` para o Spring achar os outros pacotes. |

### ⚙️ config

| Classe | Responsabilidade |
| --- | --- |
| `MongoDBProperties` | Lê `app.mongodb.connection-string` e `app.mongodb.database` |
| `CorsProperties` | Lê `app.cors.allowed-origins` |
| `MongoConfig` | Usa o `MongoDBProperties` para conectar ao MongoDB Atlas (desiste após 5 s sem resposta) |
| `CorsConfig` | Usa o `CorsProperties` para liberar um front-end no navegador (métodos `GET`, `POST`, `DELETE`) |

Como o valor sai do arquivo e chega ao código:

```text
application.properties                                   config/MongoDBProperties.java
─────────────────────────────────────────────           ─────────────────────────────────────
app.mongodb.connection-string=${MONGODB_URI:mongo...} → private String connectionString;
app.mongodb.database=${MONGODB_DATABASE:montyhall}    → private String database;
        ▲                                                         │
        │ @ConfigurationProperties(prefix = "app.mongodb")        │ injetado no construtor
        │                                                         ▼
        └─────────────────────────────────────────── config/MongoConfig.java → MongoClient
```

#### 🔐 O formato `${VARIAVEL:valor-padrão}`

```properties
app.mongodb.connection-string=${MONGODB_URI:mongodb+srv://SEU_USUARIO:SUA_SENHA@...}
```

- **Existe a variável `MONGODB_URI`?** (ex.: no Render) → o Spring usa o valor dela.
- **Não existe?** (ex.: no seu computador) → vale o texto depois dos primeiros dois-pontos.

| Variável | Propriedade | Valor padrão |
| --- | --- | --- |
| `MONGODB_URI` | `app.mongodb.connection-string` | texto de exemplo (troque pelo seu) |
| `MONGODB_DATABASE` | `app.mongodb.database` | `montyhall` |
| `CORS_ORIGINS` | `app.cors.allowed-origins` | `http://localhost:5173,https://montyhall.vercel.app` |

### 🎮 controller

| Classe | Responsabilidade |
| --- | --- |
| `JogoController` | Partidas em `/api/jogos`: criar, escolher porta, decidir, listar, buscar, excluir e estatísticas |
| `SimulacaoController` | `GET /api/simulacoes`: joga milhares de partidas automáticas, sem gravar no banco |
| `StatusController` | `GET /api/status`: diz se a API está no ar e se o MongoDB responde |

### 🧠 service

| Classe | Responsabilidade |
| --- | --- |
| `JogoService` | Regras de uma partida: confere se a jogada está na etapa certa, valida a porta, calcula a porta final e se o jogador venceu |
| `Apresentador` | `@Component` que **esconde o carro** e **abre uma porta com bode**. Recebe um `RandomGenerator` (interface do Java 17) para que os testes possam controlar o sorteio com o Mockito |
| `SimulacaoService` | Joga N rodadas em memória e conta as vitórias de cada estratégia |
| `StatusService` | Manda um `ping` para o MongoDB para saber se ele está acessível |

### 📨 dto

**Por que este projeto tem DTOs e o AraCoffee não?** No AraCoffee, o `Produto` pode ser devolvido do jeito que está no banco. Aqui, o `Jogo` guarda a **porta premiada desde o início**: se ele fosse devolvido direto, bastaria olhar o JSON para saber onde está o carro. O `JogoResposta` só revela o conteúdo das portas que já foram abertas.

| Classe | Responsabilidade |
| --- | --- |
| `EscolhaRequisicao` | Corpo da escolha: `{ "porta": 1 }` |
| `DecisaoRequisicao` | Corpo da decisão: `{ "trocar": true }` |
| `JogoResposta` | A partida como o jogador pode vê-la, com a **fala do apresentador** em `mensagem` |
| `PortaResposta` | Uma porta: `numero`, `aberta`, `conteudo` (`CARRO`, `BODE` ou `null` se fechada) e `escolhida` |
| `EstrategiaResposta` | Partidas, vitórias, derrotas e percentual de uma estratégia |
| `EstatisticasResposta` | Placar das partidas gravadas: trocando x mantendo |
| `SimulacaoResposta` | Resultado da simulação, com os valores teóricos para comparar |

### 🧩 model

| Classe | Responsabilidade |
| --- | --- |
| `Jogo` | `@Document(collection = "jogos")` com as portas premiada, escolhida, aberta e final, se trocou, se venceu, a etapa e as datas |
| `StatusJogo` | Etapas: `AGUARDANDO_ESCOLHA` → `AGUARDANDO_DECISAO` → `FINALIZADO` |
| `Premio` | `CARRO` ou `BODE` |
| `Portas` | As portas `1, 2, 3` e o método `excluindo(...)`, que diz quais portas sobram |

Como uma partida finalizada fica no Atlas:

```json
{
  "_id": { "$oid": "6742a0c9a1b2c3d4e5f60718" },
  "portaPremiada": 2,
  "portaEscolhida": 1,
  "portaAberta": 3,
  "portaFinal": 2,
  "trocou": true,
  "venceu": true,
  "status": "FINALIZADO",
  "criadoEm": { "$date": "2026-11-23T18:00:00.512Z" },
  "finalizadoEm": { "$date": "2026-11-23T18:00:40.904Z" },
  "_class": "com.montyhall.api.model.Jogo"
}
```

### 🗄️ repository

| Método | Consulta que o Spring Data monta |
| --- | --- |
| `findAllByOrderByCriadoEmDesc` | todas as partidas, das mais novas para as mais antigas |
| `findByStatusOrderByCriadoEmDesc` | só as partidas de uma etapa |
| `countByTrocou` | quantas partidas terminaram trocando (`true`) ou mantendo (`false`) |
| `countByTrocouAndVenceu` | quantas dessas foram vencidas |

> O campo `trocou` só é preenchido quando a partida termina, então as contagens já consideram apenas partidas finalizadas.

### 🚨 exception

| Classe | Responsabilidade |
| --- | --- |
| `JogoNaoEncontradoException` | id inexistente → **404** |
| `DadosInvalidosException` | porta fora de 1..3, `trocar` ausente, rodadas fora do limite → **400** |
| `JogadaInvalidaException` | jogada na etapa errada → **409** |
| `ErroResposta` | Formato do JSON de erro |
| `GlobalExceptionHandler` | `@RestControllerAdvice` que transforma cada exceção no código HTTP certo |

```json
{
  "status": 409,
  "erro": "Conflict",
  "mensagem": "Escolha uma porta antes de decidir se quer trocar.",
  "caminho": "/api/jogos/6742a0c9a1b2c3d4e5f60718/decisao",
  "timestamp": "2026-11-23T18:30:00.123"
}
```

| Situação | Código |
| --- | --- |
| Partida não existe | 404 |
| Porta fora de 1..3, `trocar` ausente, status desconhecido, rodadas fora de 1..1.000.000 | 400 |
| JSON mal formado (ou `porta` enviada como texto) | 400 |
| Parâmetro da URL com tipo errado (ex.: `?rodadas=muitas`) | 400 |
| Escolher porta duas vezes, decidir antes de escolher, jogar numa partida finalizada | 409 |
| Endpoint inexistente | 404 |
| Método HTTP errado | 405 |
| MongoDB Atlas inacessível | 503 |
| Qualquer outro erro | 500 |

---

## 📂 Estrutura do Projeto

```text
📁 MontyHallAPI
│
├── 📁 .mvn/wrapper
│   └── maven-wrapper.properties  (versão do Maven usada pelo mvnw)
│
├── 📁 src/main
│   ├── 📁 java/com/montyhall/api
│   │   ├── 🚀 application
│   │   │   └── MontyHallApplication.java
│   │   │
│   │   ├── ⚙️ config
│   │   │   ├── MongoDBProperties.java  └── Lê app.mongodb.*
│   │   │   ├── CorsProperties.java     └── Lê app.cors.*
│   │   │   ├── MongoConfig.java        └── Conecta ao MongoDB Atlas
│   │   │   └── CorsConfig.java         └── Libera o navegador (CORS)
│   │   │
│   │   ├── 🎮 controller
│   │   │   ├── JogoController.java      └── /api/jogos
│   │   │   ├── SimulacaoController.java └── /api/simulacoes
│   │   │   └── StatusController.java    └── /api/status
│   │   │
│   │   ├── 🧠 service
│   │   │   ├── JogoService.java         └── Regras da partida
│   │   │   ├── Apresentador.java        └── Esconde o carro e abre o bode
│   │   │   ├── SimulacaoService.java    └── Milhares de partidas em memória
│   │   │   └── StatusService.java       └── Ping no MongoDB
│   │   │
│   │   ├── 📨 dto
│   │   │   ├── EscolhaRequisicao.java   └── { "porta": 1 }
│   │   │   ├── DecisaoRequisicao.java   └── { "trocar": true }
│   │   │   ├── JogoResposta.java        └── Partida sem revelar o carro
│   │   │   ├── PortaResposta.java       └── Uma porta (aberta ou fechada)
│   │   │   ├── EstrategiaResposta.java  └── Vitórias e percentual
│   │   │   ├── EstatisticasResposta.java└── Placar trocar x manter
│   │   │   ├── SimulacaoResposta.java   └── Resultado da simulação
│   │   │   └── Conclusao.java           └── Texto de conclusão
│   │   │
│   │   ├── 🧩 model
│   │   │   ├── Jogo.java                └── Documento da coleção "jogos"
│   │   │   ├── StatusJogo.java          └── Etapas da partida
│   │   │   ├── Premio.java              └── CARRO ou BODE
│   │   │   └── Portas.java              └── Portas 1, 2, 3
│   │   │
│   │   ├── 🗄️ repository
│   │   │   └── JogoRepository.java      └── MongoRepository<Jogo, String>
│   │   │
│   │   └── 🚨 exception
│   │       ├── JogoNaoEncontradoException.java  └── 404
│   │       ├── DadosInvalidosException.java     └── 400
│   │       ├── JogadaInvalidaException.java     └── 409
│   │       ├── ErroResposta.java                └── Formato do JSON de erro
│   │       └── GlobalExceptionHandler.java      └── Exceção → código HTTP
│   │
│   └── 📁 resources
│       └── application.properties   └── Porta, MongoDB e CORS
│
├── 📁 src/test/java/com/montyhall/api   (veja "Testes")
│
├── 📄 Dockerfile                    └── Build em duas etapas, usado no deploy do Render
├── 📄 .dockerignore
├── 📄 mvnw / mvnw.cmd               └── Maven Wrapper (macOS-Linux / Windows)
└── 📄 pom.xml                       └── Spring Web MVC, Spring Data MongoDB e testes
```

---

## 🌐 Endpoints

| Método | Endpoint | O que faz | Resposta |
| --- | --- | --- | --- |
| GET | `/api/status` | Status da API e do MongoDB | 200 |
| POST | `/api/jogos` | Começa uma partida (o carro é escondido) | 201 |
| POST | `/api/jogos/{id}/escolha` | Escolhe a porta; o apresentador abre um bode | 200 / 400 / 404 / 409 |
| POST | `/api/jogos/{id}/decisao` | Troca ou mantém; revela tudo | 200 / 400 / 404 / 409 |
| GET | `/api/jogos` | Lista as partidas (mais novas primeiro) | 200 |
| GET | `/api/jogos?status=FINALIZADO` | Filtra pela etapa | 200 / 400 |
| GET | `/api/jogos/{id}` | Busca uma partida | 200 / 404 |
| GET | `/api/jogos/estatisticas` | Placar das partidas gravadas: trocar x manter | 200 |
| DELETE | `/api/jogos/{id}` | Exclui uma partida | 204 / 404 |
| GET | `/api/simulacoes?rodadas=10000` | Simula N partidas (padrão 10.000, máximo 1.000.000) | 200 / 400 |

> `/estatisticas` não é confundido com `/{id}`: no Spring, caminhos fixos têm prioridade sobre caminhos com variável.

### 🕹️ Uma partida completa, passo a passo

```mermaid
sequenceDiagram
    participant J as 🧑 Jogador
    participant A as ☕ MontyHallAPI
    participant M as 🍃 MongoDB Atlas

    J->>A: POST /api/jogos
    A->>M: grava { portaPremiada: 2, status: AGUARDANDO_ESCOLHA }
    A-->>J: 201 · 3 portas fechadas (o carro NÃO aparece)
    J->>A: POST /api/jogos/{id}/escolha { "porta": 1 }
    Note over A: Apresentador: carro na 2, jogador na 1<br/>→ só pode abrir a 3
    A->>M: grava { portaEscolhida: 1, portaAberta: 3 }
    A-->>J: 200 · porta 3 = BODE · "Quer trocar para a porta 2?"
    J->>A: POST /api/jogos/{id}/decisao { "trocar": true }
    A->>M: grava { portaFinal: 2, venceu: true, status: FINALIZADO }
    A-->>J: 200 · todas abertas · "ganhou o CARRO! 🚗"
```

**1. Começar** (`POST /api/jogos`, sem corpo):

```json
{
  "id": "6742a0c9a1b2c3d4e5f60718",
  "status": "AGUARDANDO_ESCOLHA",
  "mensagem": "O carro está atrás de uma das 3 portas. Escolha a sua: POST /api/jogos/6742a0c9a1b2c3d4e5f60718/escolha com { \"porta\": 1 }, 2 ou 3.",
  "portas": [
    { "numero": 1, "aberta": false, "conteudo": null, "escolhida": false },
    { "numero": 2, "aberta": false, "conteudo": null, "escolhida": false },
    { "numero": 3, "aberta": false, "conteudo": null, "escolhida": false }
  ],
  "portaEscolhida": null,
  "portaAberta": null,
  "portaOferecida": null,
  "trocou": null,
  "portaFinal": null,
  "venceu": null,
  "portaPremiada": null,
  "criadoEm": "2026-11-23T18:00:00.512Z",
  "finalizadoEm": null
}
```

**2. Escolher a porta** (`POST /api/jogos/{id}/escolha` com `{ "porta": 1 }`):

```json
{
  "status": "AGUARDANDO_DECISAO",
  "mensagem": "Você escolheu a porta 1. O apresentador abriu a porta 3 e... é um bode! 🐐 Quer trocar para a porta 2? Responda em POST /api/jogos/6742a0c9a1b2c3d4e5f60718/decisao com { \"trocar\": true } ou { \"trocar\": false }.",
  "portas": [
    { "numero": 1, "aberta": false, "conteudo": null,   "escolhida": true  },
    { "numero": 2, "aberta": false, "conteudo": null,   "escolhida": false },
    { "numero": 3, "aberta": true,  "conteudo": "BODE", "escolhida": false }
  ],
  "portaEscolhida": 1,
  "portaAberta": 3,
  "portaOferecida": 2,
  "portaPremiada": null
}
```

**3. Decidir** (`POST /api/jogos/{id}/decisao` com `{ "trocar": true }`):

```json
{
  "status": "FINALIZADO",
  "mensagem": "Você trocou para a porta 2 e ganhou o CARRO! 🚗",
  "portas": [
    { "numero": 1, "aberta": true, "conteudo": "BODE",  "escolhida": false },
    { "numero": 2, "aberta": true, "conteudo": "CARRO", "escolhida": true  },
    { "numero": 3, "aberta": true, "conteudo": "BODE",  "escolhida": false }
  ],
  "trocou": true,
  "portaFinal": 2,
  "venceu": true,
  "portaPremiada": 2,
  "finalizadoEm": "2026-11-23T18:00:40.904Z"
}
```

*(Nos exemplos 2 e 3 alguns campos foram omitidos para encurtar.)*

### 🧪 Testando pelo terminal

```bash
# Status
curl http://localhost:8080/api/status

# 1. Começar (copie o "id" da resposta)
curl -X POST http://localhost:8080/api/jogos

# 2. Escolher a porta 1
curl -X POST http://localhost:8080/api/jogos/COLE_O_ID_AQUI/escolha \
  -H "Content-Type: application/json" \
  -d '{"porta": 1}'

# 3. Trocar de porta
curl -X POST http://localhost:8080/api/jogos/COLE_O_ID_AQUI/decisao \
  -H "Content-Type: application/json" \
  -d '{"trocar": true}'

# Placar de todas as partidas
curl http://localhost:8080/api/jogos/estatisticas

# Simulação com 100 mil rodadas
curl "http://localhost:8080/api/simulacoes?rodadas=100000"

# Erro 409: decidir de novo numa partida finalizada
curl -X POST http://localhost:8080/api/jogos/COLE_O_ID_AQUI/decisao \
  -H "Content-Type: application/json" \
  -d '{"trocar": false}'
```

> No **Windows (PowerShell)**, use `curl.exe` no lugar de `curl` e aspas duplas escapadas no corpo: `-d "{\"porta\": 1}"`. Ou use o Postman/Insomnia.

---

## 🧪 Testes (JUnit 5 + Mockito)

Os testes ficam em `src/test/java` e **não precisam do MongoDB**: o banco é sempre simulado com **Mockito**, e os controllers são testados com **MockMvc em modo standalone**, sem subir o Spring.

```text
📁 src/test/java/com/montyhall/api
├── ⚙️ config
│   ├── MongoConfigTest.java        └── Connection string, timeout e pacote mapeado
│   └── CorsConfigTest.java         └── Origens, curingas e métodos liberados
├── 🎮 controller
│   ├── JogoControllerTest.java     └── Todos os endpoints, códigos HTTP e o carro escondido no JSON
│   ├── SimulacaoControllerTest.java└── Parâmetro padrão, limites e ?rodadas=muitas
│   └── StatusControllerTest.java   └── MongoDB online/offline
├── 📨 dto
│   ├── JogoRespostaTest.java       └── O carro só aparece no FINALIZADO; mensagens do apresentador
│   ├── EstrategiaRespostaTest.java └── Percentuais (2/3 = 66.67), conclusões e valores teóricos
│   └── RequisicaoTest.java         └── Corpos da escolha e da decisão
├── 🚨 exception
│   └── GlobalExceptionHandlerTest.java └── Cada exceção → código HTTP e ErroResposta
├── 🧩 model
│   ├── JogoTest.java               └── Getters e setters
│   └── PortasTest.java             └── Portas válidas e "quais portas sobram"
└── 🧠 service
    ├── JogoServiceTest.java        └── Etapas, validações, os 4 resultados possíveis e o placar
    ├── ApresentadorTest.java       └── Nunca abre o carro nem a porta do jogador
    ├── SimulacaoServiceTest.java   └── Contagem controlada por mock e convergência para 2/3
    └── StatusServiceTest.java      └── Ping no MongoDB
```

Exemplos do que é testado:

| Teste | O que garante |
| --- | --- |
| `JogoServiceTest.Decidir.resultados` | Os 4 cenários: errou e trocou (**ganha**), errou e manteve (perde), acertou e manteve (**ganha**), acertou e trocou (perde) |
| `ApresentadorTest.nuncaAbrePremiadaNemEscolhida` | Em 1.800 sorteios reais, o apresentador nunca abre o carro nem a porta do jogador |
| `ApresentadorTest.jogadorErrouSoSobraUmBode` | Com o `RandomGenerator` mockado: se o jogador errou, o apresentador é **obrigado** a abrir o único bode |
| `SimulacaoServiceTest.leiDosGrandesNumeros` | Em 100 mil rodadas, trocar fica a menos de 1 ponto de 66,67% |
| `JogoRespostaTest` e `JogoControllerTest` | `portaPremiada` e o conteúdo das portas fechadas **não aparecem** no JSON antes do fim |

Rodando:

```bash
# macOS / Linux
./mvnw test

# Windows
.\mvnw.cmd test
```

---

## 🍃 Configurando o MongoDB Atlas

### 1. Criar o cluster

1. Crie uma conta em [mongodb.com/cloud/atlas](https://www.mongodb.com/cloud/atlas/register).
2. Crie um cluster **Free (M0)**. Escolha a região mais próxima (ex.: São Paulo).

### 2. Criar o usuário do banco

Em **Security > Database Access > Add New Database User**:

- Método: **Password**
- Usuário e senha: anote os dois (dica: use uma senha só com letras e números)
- Permissão: **Read and write to any database**

### 3. Liberar o acesso pela rede

Em **Security > Network Access > Add IP Address**, clique em **Allow Access from Anywhere** (`0.0.0.0/0`).

> O plano gratuito do Render não tem IP fixo, então o Atlas precisa aceitar conexões de qualquer IP. Sem isso, a API funciona no seu computador mas não no Render.

### 4. Copiar a connection string

Em **Database > Connect > Drivers**, escolha **Java** e copie a string:

```text
mongodb+srv://USUARIO:<db_password>@cluster0.abcde.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
```

Troque `<db_password>` pela senha do usuário (sem os sinais `< >`).

> ⚠️ Se a senha tiver caracteres especiais, eles precisam ser codificados: `@` → `%40`, `:` → `%3A`, `/` → `%2F`, `?` → `%3F`, `#` → `%23`, `%` → `%25`.

### 5. Colar no `application.properties`

Troque **só o texto depois de `MONGODB_URI:`** (mantenha o `${` no começo e o `}` no fim):

```properties
app.mongodb.connection-string=${MONGODB_URI:mongodb+srv://joao:minhasenha123@cluster0.abcde.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0}
app.mongodb.database=${MONGODB_DATABASE:montyhall}
```

Não precisa criar o banco nem a coleção: na primeira partida (`POST /api/jogos`), o Atlas cria o banco `montyhall` e a coleção `jogos`. Para ver os dados, abra **Database > Browse Collections**.

> ⚠️ **Cuidado ao subir para o GitHub:** se a senha estiver escrita no `application.properties`, quem vir o repositório vê a senha. Prefira deixar o valor de exemplo no arquivo e usar a variável `MONGODB_URI` no Render, ou use um **repositório privado**.

---

## 📄 `application.properties` completo

```properties
spring.application.name=MontyHallAPI

# Porta da API
server.port=8080

# MongoDB Atlas (lido por config/MongoDBProperties.java)
app.mongodb.connection-string=${MONGODB_URI:mongodb+srv://SEU_USUARIO:SUA_SENHA@SEU_CLUSTER.xxxxxxx.mongodb.net/?retryWrites=true&w=majority}
app.mongodb.database=${MONGODB_DATABASE:montyhall}

# CORS (lido por config/CorsProperties.java)
app.cors.allowed-origins=${CORS_ORIGINS:http://localhost:5173,https://montyhall.vercel.app}
```

> **CORS** só importa quando um **front-end no navegador** chama a API (ex.: um React em `localhost:5173`). Postman, Insomnia e `curl` não passam por essa verificação. Escreva as origens **sem barra no final**.

---

## 🚀 Como rodar

### Pré-requisitos

- **JDK 17** instalado. Confira com `java -version`.
- MongoDB Atlas configurado (seção acima).
- Maven **não** precisa estar instalado: o **Maven Wrapper** (`mvnw` / `mvnw.cmd`) baixa o Maven 3.9.11 sozinho na primeira execução.

```bash
# macOS / Linux
./mvnw spring-boot:run

# Windows (cmd ou PowerShell)
.\mvnw.cmd spring-boot:run
```

Ou abra o projeto no IntelliJ e rode a classe `MontyHallApplication`.

Depois abra [http://localhost:8080/api/status](http://localhost:8080/api/status):

```json
{ "api": "online", "mongodb": "online" }
```

Se aparecer `"mongodb": "offline"`, a API está funcionando, mas não alcançou o Atlas: confira a connection string e o Network Access. A **simulação** (`/api/simulacoes`) funciona mesmo sem o banco, porque não grava nada.

---

## 🐳 Docker

```dockerfile
# ETAPA 1 - BUILD
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# ETAPA 2 - EXECUÇÃO
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
```

- **Etapa 1** usa uma imagem com Maven e JDK 17 só para gerar o `.jar`. O `pom.xml` é copiado antes do código para o Docker reaproveitar as dependências baixadas (cache).
- **Etapa 2** usa uma imagem bem menor, só com o Java para **rodar**, e limita o Java a 75% da memória (o plano gratuito do Render tem 512 MB).

```bash
docker build -t montyhall-api .
docker run -p 8080:8080 -e MONGODB_URI="mongodb+srv://..." montyhall-api
```

---

## ☁️ Deploy no Render

1. Suba o projeto para o GitHub.
2. No [Render](https://render.com/), clique em **New > Web Service** e conecte o repositório.
3. Preencha:

| Campo | Valor |
| --- | --- |
| **Name** | `montyhall-api` (vira parte da URL) |
| **Language / Runtime** | `Docker` |
| **Branch** | `main` |
| **Root Directory** | vazio (o projeto está na raiz do repositório) |
| **Instance Type** | `Free` |

4. Em **Environment Variables**, adicione:

| Key | Value |
| --- | --- |
| `PORT` | `8080` |
| `MONGODB_URI` | a connection string completa do Atlas, com a senha |
| `CORS_ORIGINS` | a URL do seu front-end, se tiver um (ex.: `https://SEU-PROJETO.vercel.app`) |

5. Clique em **Deploy Web Service** e acompanhe os **Logs** até aparecer `Started MontyHallApplication`.
6. Abra `https://montyhall-api.onrender.com/api/status` e confira `"mongodb": "online"`.

> 💤 **Plano gratuito:** o Render "dorme" a API depois de 15 minutos sem acesso. A primeira requisição depois disso leva **até 1 minuto**.

---

## 🛠️ Problemas comuns

| Sintoma | Causa provável | Solução |
| --- | --- | --- |
| `409 Conflict` ao decidir | A porta ainda não foi escolhida, ou a partida já terminou | Siga a ordem: `POST /api/jogos` → `/escolha` → `/decisao`. A `mensagem` da resposta diz o próximo passo |
| `400` com "O corpo da requisição não é um JSON válido" | Faltou o `Content-Type: application/json` ou o JSON está mal formado | Confira o cabeçalho e as aspas do corpo |
| `"mongodb": "offline"` no `/api/status` | Connection string errada ou IP bloqueado | Confira usuário/senha e o **Network Access** (`0.0.0.0/0`) |
| A API não sobe e fala de "connection string inválida" | Texto de exemplo ou `${...}` apagado sem querer | Mantenha o formato `${MONGODB_URI:sua-string}` |
| Estatísticas mostram "manter" na frente | Poucas partidas jogadas: com amostras pequenas o acaso pesa | Jogue mais partidas ou rode `/api/simulacoes` com muitas rodadas |
| `Port 8080 was already in use` | Outra aplicação usando a porta | Pare a outra aplicação |
| `./mvnw: Permission denied` (Mac/Linux) | O arquivo perdeu a permissão de execução | `chmod +x mvnw` |
| `JAVA_HOME is not defined` | JDK 17 não configurado | Instale o JDK 17 e configure o `JAVA_HOME` |

---

## 📚 Links Úteis

| Recurso | Link |
| --- | --- |
| Problema de Monty Hall (Wikipédia) | [https://pt.wikipedia.org/wiki/Problema_de_Monty_Hall](https://pt.wikipedia.org/wiki/Problema_de_Monty_Hall) |
| Teorema de Bayes (Wikipédia) | [https://pt.wikipedia.org/wiki/Teorema_de_Bayes](https://pt.wikipedia.org/wiki/Teorema_de_Bayes) |
| Spring Boot | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| Spring Data MongoDB | [https://spring.io/projects/spring-data-mongodb](https://spring.io/projects/spring-data-mongodb) |
| MongoDB Atlas | [https://www.mongodb.com/atlas](https://www.mongodb.com/atlas) |
| Mockito | [https://site.mockito.org](https://site.mockito.org) |
| Render (Docker) | [https://render.com/docs/docker](https://render.com/docs/docker) |

---

## 🛡 Licença

Este projeto está sob a licença **MIT**.
