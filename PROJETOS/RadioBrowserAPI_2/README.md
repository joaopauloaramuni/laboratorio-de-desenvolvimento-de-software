# RadioBrowserAPI

## Descrição do Projeto

O RadioBrowserAPI é um projeto que consome a [Radio Browser API](https://api.radio-browser.info/) para exibir estações de rádio, permitindo ao usuário filtrar por **país** e **estado**, marcar estações como **favoritas** e ouvi-las diretamente pelo navegador. A aplicação permite que os usuários visualizem informações sobre as estações de rádio, incluindo detalhes como nome, URL, tags, votos, cliques, bitrate e codec. A interface é construída utilizando o Thymeleaf, que fornece uma maneira simples e eficiente de gerar páginas HTML dinâmicas.

Os favoritos são **persistidos em um banco PostgreSQL hospedado no [Supabase](https://supabase.com/)**, usando **Spring Data JPA**. Assim, as estações favoritas continuam salvas mesmo depois de reiniciar a aplicação.

No projeto RadioBrowserAPI, utilizamos HTML5 para criar uma interface web interativa e moderna. Uma das funcionalidades principais da aplicação é a reprodução de estações de rádio, que é possibilitada pelo uso da tag `<audio>` do HTML5.

A tag `<audio>` permite incorporar áudio diretamente nas páginas da web, oferecendo aos usuários a capacidade de ouvir as rádios de forma simples e eficiente. Com essa tag, é possível incluir controles de reprodução, como play, pause e volume, proporcionando uma experiência de usuário intuitiva e acessível.

Graças à integração do Thymeleaf, a aplicação é capaz de gerar dinamicamente elementos de áudio para cada uma das estações de rádio disponíveis, permitindo que os usuários selecionem e ouçam suas rádios favoritas com facilidade. A combinação do HTML5 e do Thymeleaf garante que a interface não apenas seja funcional, mas também responsiva e atraente.

## Funcionalidades

- **Filtro por país e estado**: os dropdowns de país e estado são carregados dinamicamente a partir da API (`/countries` e `/states`), permitindo pesquisar estações de qualquer lugar do mundo. Por padrão, a busca é feita para `country=Brazil` e `state=Minas Gerais`.
- **Favoritos persistidos no Supabase**: cada estação pode ser marcada/desmarcada como favorita com um clique. As favoritas sobem para o topo da listagem (mantendo a ordenação por votos dentro de cada grupo). Os UUIDs das estações favoritas ficam gravados na tabela `favorites` de um banco PostgreSQL no Supabase, então **não se perdem ao reiniciar a aplicação**.
- **Ordenação**: as estações são ordenadas por número de votos (decrescente); países e estados são ordenados alfabeticamente conforme as regras do português do Brasil (`Collator` com `Locale("pt", "BR")`).
- **Player embutido**: reprodução das rádios diretamente na página via tag `<audio>` do HTML5.

## Captura de Tela

- **Home**: Exibe as rádios recuperadas de acordo com o país/estado selecionado

| ![Home](https://joaopauloaramuni.github.io/java-imgs/RadioBrowserAPI/imgs/home2.png) |
|:-------------------------------:|
|         Home - Versão nova      |

| ![Home](https://joaopauloaramuni.github.io/java-imgs/RadioBrowserAPI/imgs/home.png) |
|:---------------------------------:|
|         Home - Versão antiga      |

## Dependências

O projeto utiliza as seguintes dependências em seu `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Persistência dos favoritos no Supabase (PostgreSQL) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Thymeleaf

Thymeleaf é um motor de templates para Java que permite a criação de páginas HTML dinâmicas de forma simples e eficiente. Ele é frequentemente utilizado em aplicações Spring, proporcionando uma maneira intuitiva de gerar conteúdo HTML e manipular dados diretamente nas páginas.

**Principais Características**

- **Natural Templating**: Os templates Thymeleaf são válidos como documentos HTML, permitindo que sejam visualizados em navegadores sem processamento.
- **Integração com Spring**: Thymeleaf se integra perfeitamente com o Spring Framework, facilitando a injeção de dependências e o acesso a beans do Spring.
- **Expressões de Template**: Utiliza uma sintaxe simples e expressiva para manipular dados, permitindo a criação de lógicas condicionais e loops diretamente nas páginas.

### Spring Data JPA + PostgreSQL

- **`spring-boot-starter-data-jpa`**: traz o Hibernate (implementação do JPA) e o Spring Data JPA. Com ele, basta criar uma interface que estende `JpaRepository` para ter métodos prontos como `save`, `findAll`, `existsById` e `deleteById`, sem escrever SQL. Também traz o pool de conexões **HikariCP**.
- **`postgresql`**: driver JDBC do PostgreSQL, usado pelo Hibernate para se conectar ao banco do Supabase. Tem escopo `runtime` porque o código não usa classes do driver diretamente.

### Supabase

O [Supabase](https://supabase.com/) é uma plataforma que oferece, entre outros serviços, um **banco PostgreSQL gerenciado na nuvem**, com plano gratuito. Neste projeto ele funciona apenas como um **PostgreSQL remoto**: a aplicação se conecta via JDBC, como faria com qualquer outro Postgres. Não é usado o SDK do Supabase, a API REST nem a chave `anon`/`publishable`.

## Estrutura do Projeto

/RadioBrowserAPI
```
│
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 com
│   │   │       └── 📁 example
│   │   │           └── 📁 RadioBrowserAPI
│   │   │               ├── 📁 application
│   │   │               │   └── ☕ RadioBrowserApiApplication.java     # Classe main + @EntityScan / @EnableJpaRepositories
│   │   │               ├── 📁 config
│   │   │               │   └── ⚙️ ApiConfig.java                      # Monta as URLs da Radio Browser API a partir do properties
│   │   │               ├── 📁 controller
│   │   │               │   └── 🌐 RadioBrowserApiController.java      # Endpoints: home (filtros) e toggle de favoritos
│   │   │               ├── 📁 model
│   │   │               │   ├── 📻 RadioStation.java                   # Representa uma estação de rádio (vinda da API)
│   │   │               │   └── ⭐ FavoriteStation.java                # Entidade JPA da tabela "favorites" no Supabase
│   │   │               ├── 📁 repository
│   │   │               │   └── 🗄️ FavoriteRepository.java             # Spring Data JPA: acesso à tabela "favorites"
│   │   │               └── 📁 service
│   │   │                   ├── 🔎 RadioBrowserApiService.java         # Busca/ordena estações, países e estados na API
│   │   │                   └── ⭐ FavoriteService.java                # Regras dos favoritos (lê/grava no Supabase)
│   │   ├── 📁 resources
│   │   │   ├── 🔧 application.properties                              # URL da API + conexão com o Supabase + JPA
│   │   │   ├── 📁 static
│   │   │   │   └── 📁 css
│   │   │   │       └── 🎨 style.css                                   # Estilos da interface
│   │   │   │   └── 📁 imgs
│   │   │   │       └── 🖼️ aradio.webp                                 # Favicon padrão quando a estação não tem um
│   │   │   └── 📁 templates
│   │   │       └── 🖥️ home.html                                       # Página Home (Thymeleaf): lista, filtros, player e favoritos
│   └── 📁 test
│       └── 📁 java
│           └── 📁 com
│               └── 📁 example
│                   └── 📁 RadioBrowserAPI
│                       └── ✅ RadioBrowserApiApplicationTests.java     # Testes da aplicação (sobe o contexto e conecta no banco)
│
├── 🗃️ supabase_favorites.sql                                           # Script SQL opcional para criar a tabela no Supabase
├── 📦 pom.xml                                                          # Dependências e build do Maven
├── 📄 README.md                                                        # Este arquivo
```

## Configuração do Supabase (passo a passo)

### 1. Criar a conta e o projeto

1. Acesse [supabase.com](https://supabase.com/) e crie uma conta (dá para entrar com o GitHub).
2. No dashboard, clique em **New project**.
3. Preencha:
   - **Name**: por exemplo, `radio-browser`.
   - **Database Password**: crie uma senha e **guarde-a**. Ela será usada no `application.properties`. Prefira uma senha sem caracteres especiais como `@`, `#`, `%` e `/`, que costumam causar problemas em URLs de conexão.
   - **Region**: escolha a região mais próxima de você. No Brasil, use **South America (São Paulo)** (`sa-east-1`). ⚠️ **A região não pode ser alterada depois**, e um banco em outro continente deixa cada consulta bem mais lenta.
4. Clique em **Create new project** e aguarde alguns minutos até o banco ficar pronto.

### 2. Pegar os dados de conexão do Session pooler

1. Dentro do projeto, clique no botão **Connect**, na barra superior.
2. Na aba **Connection String**:
   - **Type**: selecione **JDBC**.
   - **Method**: selecione **Session pooler**.
3. Anote os dados exibidos. Eles têm este formato:

   ```
   host=aws-0-<regiao>.pooler.supabase.com
   port=5432
   database=postgres
   user=postgres.<project-ref>
   ```

   O `<project-ref>` é o identificador do seu projeto (o mesmo que aparece na URL `https://<project-ref>.supabase.co`).

> 💡 **Por que o Session pooler e não a conexão direta?**
>
> O Supabase oferece três formas de conexão ao Postgres:
>
> | Método | Host / Porta | Usuário | Quando usar |
> |--------|--------------|---------|-------------|
> | **Direct connection** | `db.<project-ref>.supabase.co:5432` | `postgres` | Só funciona via **IPv6** (ou com o add-on pago de IPv4). Em muitas redes domésticas e universitárias dá erro `NoRouteToHostException` / `UnknownHostException`. |
> | **Session pooler** ✅ | `aws-0-<regiao>.pooler.supabase.com:5432` | `postgres.<project-ref>` | Funciona via **IPv4** e mantém uma conexão por sessão. É o ideal para aplicações Spring Boot com pool de conexões (HikariCP). **É o usado neste projeto.** |
> | **Transaction pooler** | `aws-0-<regiao>.pooler.supabase.com:6543` | `postgres.<project-ref>` | Pensado para ambientes serverless. Não suporta *prepared statements* do jeito que o driver JDBC usa por padrão, o que causa erros como `prepared statement "S_1" already exists`. **Evite com JPA/Hibernate.** |
>
> Repare que no pooler o **usuário muda** de `postgres` para `postgres.<project-ref>`.

### 3. Configurar o `application.properties`

Com os dados do passo anterior, preencha o `src/main/resources/application.properties`:

```properties
spring.application.name=RadioBrowserAPI
radio.api.base.url=https://de1.api.radio-browser.info/json

# ---------- Supabase (PostgreSQL) - Session pooler (IPv4) ----------
spring.datasource.url=jdbc:postgresql://aws-0-<regiao>.pooler.supabase.com:5432/postgres?sslmode=require
spring.datasource.username=postgres.<project-ref>
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=org.postgresql.Driver

# Pool pequeno: o plano free do Supabase limita o número de conexões
spring.datasource.hikari.maximum-pool-size=5

# ---------- JPA / Hibernate ----------
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
# Cria/atualiza a tabela "favorites" automaticamente a partir da entidade
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=false
```

| Propriedade | Para que serve |
|-------------|----------------|
| `spring.datasource.url` | URL JDBC do Session pooler. O `?sslmode=require` obriga a conexão a usar SSL. Sem ele o driver usa `prefer`, que também conecta com SSL no Supabase. |
| `spring.datasource.username` | Usuário do pooler, no formato `postgres.<project-ref>`. |
| `spring.datasource.password` | Senha do banco definida na criação do projeto. |
| `spring.datasource.hikari.maximum-pool-size` | Máximo de conexões abertas pelo HikariCP. No plano gratuito é bom manter baixo. |
| `spring.jpa.database-platform` | Informa o dialeto do PostgreSQL ao Hibernate. Assim, se a conexão falhar, o log mostra o erro real em vez de `Unable to determine Dialect`. |
| `spring.jpa.hibernate.ddl-auto=update` | Cria a tabela `favorites` na primeira execução, se ela ainda não existir. |
| `spring.jpa.open-in-view=false` | Fecha a sessão do Hibernate ao fim do service, evitando consultas ao banco durante a renderização da página. |

> 🔐 **Cuidado com a senha no Git.** Se o repositório for público, **não faça commit da senha real**: qualquer pessoa com ela consegue ler e apagar o banco. Uma alternativa é usar variáveis de ambiente:
>
> ```properties
> spring.datasource.password=${SUPABASE_DB_PASSWORD}
> ```
>
> ```bash
> export SUPABASE_DB_PASSWORD="sua-senha"
> ./mvnw spring-boot:run
> ```
>
> No IntelliJ, defina a variável em **Run → Edit Configurations → Environment variables**. Se a senha vazar, redefina-a em **Project Settings → Database → Reset database password**.

### 4. (Opcional) Criar a tabela manualmente

Com `ddl-auto=update`, o Hibernate cria a tabela sozinho. Se preferir criá-la você mesmo, abra o **SQL Editor** no Supabase e execute o conteúdo de `supabase_favorites.sql`:

```sql
create table if not exists public.favorites (
    station_uuid varchar(64) primary key,
    created_at   timestamptz not null default now()
);

-- A app conecta direto no Postgres (usuário postgres), que ignora RLS.
-- Habilitar RLS sem policies bloqueia o acesso público via API REST/anon key.
alter table public.favorites enable row level security;
```

> ℹ️ O Supabase expõe automaticamente as tabelas do schema `public` por uma API REST. Habilitar o **RLS (Row Level Security)** sem criar policies impede que alguém use a chave pública do projeto para ler ou alterar os favoritos por essa API. A aplicação Spring não é afetada, porque conecta com o usuário `postgres`, que ignora o RLS.

### 5. Rodar e verificar

```bash
./mvnw clean install
./mvnw spring-boot:run
```

1. Acesse [http://localhost:8080/](http://localhost:8080/) e clique na estrela (☆) de alguma rádio.
2. No Supabase, abra o **Table Editor** e selecione a tabela `favorites`: o `station_uuid` da rádio deve aparecer lá.
3. Reinicie a aplicação: a rádio continua marcada como favorita (★) e no topo da lista.

> ⚠️ O teste `RadioBrowserApiApplicationTests.contextLoads` sobe a aplicação inteira, **inclusive a conexão com o banco**. Se o Supabase não estiver configurado, o `mvn install` falha nos testes. Para gerar o build sem testar, use `./mvnw clean install -DskipTests`.

> 💤 No plano gratuito, projetos sem atividade por cerca de uma semana são **pausados**. Se a aplicação parar de conectar depois de um tempo sem uso, entre no dashboard do Supabase e clique em **Restore project**.

### Problemas comuns

| Erro | Causa provável | Solução |
|------|----------------|---------|
| `java.net.NoRouteToHostException: No route to host` / `UnknownHostException` | Uso da **Direct connection** (`db.<ref>.supabase.co`), que só funciona via IPv6. | Trocar para o **Session pooler** (passo 2). |
| `Unable to determine Dialect without JDBC metadata` | O Hibernate não conseguiu abrir a conexão (URL, usuário ou senha errados, ou variáveis de ambiente não definidas). | Conferir o `application.properties` e definir `spring.jpa.database-platform`, para o log mostrar o erro real. |
| `FATAL: password authentication failed` | Senha incorreta. | Redefinir em **Project Settings → Database → Reset database password**. |
| `FATAL: Tenant or user not found` | Usuário sem o sufixo `.<project-ref>` ou host de outra região. | Usar `postgres.<project-ref>` e copiar o host exatamente como aparece no **Connect**. |
| `prepared statement "S_1" already exists` | Uso do **Transaction pooler** (porta `6543`). | Usar o **Session pooler** (porta `5432`). |
| `No qualifying bean of type 'FavoriteRepository'` | A classe principal está no pacote `.application` e o Spring não encontra o repositório nem a entidade. | Manter `@EntityScan` e `@EnableJpaRepositories` na classe principal (veja abaixo). |
| `package jakarta.persistence does not exist` | A dependência `spring-boot-starter-data-jpa` não foi baixada ou a IDE não recarregou o Maven. | Conferir o `pom.xml` e recarregar o Maven (IntelliJ: *Load Maven Changes*; VS Code: *Java: Clean Java Language Server Workspace*). |

## Endpoints

### `GET /`

Lista as estações de rádio de acordo com o país e o estado informados, já marcando quais são favoritas e ordenando-as (favoritas primeiro, depois por votos). Os favoritos são carregados do Supabase **numa única consulta**.

```java
@GetMapping("/")
public String listRadioStations(
        @RequestParam(defaultValue = "Brazil") String country,
        @RequestParam(required = false, defaultValue = "Minas Gerais") String state,
        Model model) {

    List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(country, state);

    // Marca quais estações já são favoritas (uma única consulta ao Supabase)
    Set<String> favoriteUuids = favoriteService.getFavoriteUuids();
    radioStations.forEach(station ->
            station.setFavorite(favoriteUuids.contains(station.getStationuuid())));

    radioStations.sort(Comparator.comparing(RadioStation::isFavorite).reversed());

    model.addAttribute("stations", radioStations);
    model.addAttribute("countries", radioBrowserApiService.listCountries());
    model.addAttribute("states", radioBrowserApiService.listStates());
    model.addAttribute("selectedCountry", country);
    model.addAttribute("selectedState", state);

    return "home";
}
```

> ⚡ Evite chamar `favoriteService.isFavorite(...)` dentro do `forEach`: isso faz **uma consulta ao banco para cada estação** (centenas por página), o que deixa a Home muito lenta com um banco remoto.

**Parâmetros de query**

| Parâmetro | Obrigatório | Padrão         | Descrição                          |
|-----------|-------------|----------------|-------------------------------------|
| `country` | Não         | `Brazil`       | País usado para filtrar as estações |
| `state`   | Não         | `Minas Gerais` | Estado usado para filtrar as estações |

Acesse a página inicial em: [http://localhost:8080/](http://localhost:8080/)

### `POST /favorites/toggle`

Adiciona ou remove uma estação da tabela `favorites` no Supabase e redireciona de volta para a Home, preservando o filtro de país/estado atualmente selecionado.

```java
@PostMapping("/favorites/toggle")
public String toggleFavorite(
        @RequestParam String stationuuid,
        @RequestParam(defaultValue = "Brazil") String country,
        @RequestParam(required = false) String state) throws UnsupportedEncodingException {

    favoriteService.toggle(stationuuid);

    String encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8);
    String redirectUrl = "redirect:/?country=" + encodedCountry;

    if (StringUtils.hasText(state)) {
        redirectUrl += "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);
    }

    return redirectUrl;
}
```

**Parâmetros de formulário**

| Parâmetro     | Obrigatório | Descrição                                             |
|---------------|-------------|--------------------------------------------------------|
| `stationuuid` | Sim         | UUID da estação a favoritar/desfavoritar               |
| `country`     | Não         | País do filtro atual, usado no redirect (padrão `Brazil`) |
| `state`       | Não         | Estado do filtro atual, usado no redirect               |

**Informações da Home**

| #  | Favicon | Nome | URL | Tags | Votos | Cliques | Bitrate | Codec | Favorito | Player |
|----|---------|------|-----|------|-------|---------|---------|-------|----------|--------|

## Configuração da Radio Browser API

A partir da propriedade `radio.api.base.url` do `application.properties`, a classe `ApiConfig` monta as três URLs consumidas pela aplicação:

```java
@Configuration
public class ApiConfig {

    @Value("${radio.api.base.url}")
    private String baseUrl;

    public String getSearchUrl() {
        return baseUrl + "/stations/search";
    }

    public String getCountriesUrl() {
        return baseUrl + "/countries";
    }

    public String getStatesUrl() {
        return baseUrl + "/states";
    }
}
```

## Persistência dos Favoritos

### Tabela `favorites`

| Coluna         | Tipo           | Descrição                                   |
|----------------|----------------|----------------------------------------------|
| `station_uuid` | `varchar(64)`  | **Chave primária**. UUID da estação na Radio Browser API. |
| `created_at`   | `timestamptz`  | Data/hora em que a estação foi favoritada.   |

### Entidade `FavoriteStation`

Mapeia a tabela `favorites` com anotações do JPA (`jakarta.persistence`):

```java
@Entity
@Table(name = "favorites")
public class FavoriteStation {

    @Id
    @Column(name = "station_uuid", nullable = false, length = 64)
    private String stationUuid;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    // construtores, getters e setters
}
```

### Repositório `FavoriteRepository`

Interface do Spring Data JPA. Não precisa de anotação nem de implementação: o Spring gera a classe em tempo de execução.

```java
public interface FavoriteRepository extends JpaRepository<FavoriteStation, String> {

    @Query("select f.stationUuid from FavoriteStation f")
    List<String> findAllStationUuids();
}
```

### Classe principal

Como a classe `RadioBrowserApiApplication` está no pacote `com.example.RadioBrowserAPI.application`, o Spring Data só procuraria entidades e repositórios **dentro desse pacote**. Por isso, é preciso indicar explicitamente onde eles estão:

```java
@SpringBootApplication(scanBasePackages = {"com.example"})
@EntityScan(basePackages = "com.example.RadioBrowserAPI.model")
@EnableJpaRepositories(basePackages = "com.example.RadioBrowserAPI.repository")
public class RadioBrowserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadioBrowserApiApplication.class, args);
    }
}
```

## Serviços

### `RadioBrowserApiService`

- `List<RadioStation> listRadioStations(String country, String state)`: busca as estações filtradas por país/estado (parâmetros opcionais), ordenadas por votos (decrescente).
- `List<Map<String, Object>> listCountries()`: lista todos os países disponíveis na Radio Browser API, ordenados alfabeticamente (pt-BR).
- `List<Map<String, Object>> listStates()`: lista todos os estados disponíveis na Radio Browser API, ordenados alfabeticamente (pt-BR), independente do país selecionado.
- `List<RadioStation> extractRadioStations(List<Map<String, Object>> stationsData)`: converte a resposta bruta da API em objetos `RadioStation`.

### `FavoriteService`

Serviço responsável pelos favoritos, lidos e gravados na tabela `favorites` do Supabase por meio do `FavoriteRepository`.

- `Set<String> getFavoriteUuids()`: retorna os UUIDs de todas as estações favoritas **numa única consulta**. É o método usado pela Home.
- `boolean isFavorite(String stationUuid)`: verifica se uma estação específica é favorita (faz uma consulta ao banco por chamada).
- `void toggle(String stationUuid)`: se a estação já é favorita, remove da tabela; senão, insere. Roda dentro de uma transação (`@Transactional`).

## Dicas de Desempenho

- **Região do banco**: cada consulta faz uma viagem de ida e volta até o servidor do Supabase. Um banco em `us-east-1` acessado do Brasil leva cerca de 150 ms por consulta, enquanto em `sa-east-1` (São Paulo) esse tempo cai bastante.
- **Uma consulta por página**: use `getFavoriteUuids()` na Home, e não `isFavorite()` por estação.
- **Players de áudio**: com centenas de `<audio>` na página, o navegador pode abrir uma conexão com cada stream ao carregar. Usar `preload="none"` faz o áudio só ser baixado ao clicar em play:

  ```html
  <audio controls preload="none">
      <source th:src="${station.url}" type="audio/mpeg">
  </audio>
  ```

## API Radio Browser — Endpoints Úteis

O RadioBrowserAPI (este projeto) consome apenas três endpoints da [Radio Browser API](https://api.radio-browser.info/): busca avançada de estações, países e estados. O webservice oferece bem mais recursos que podem ser úteis para evoluir a aplicação (paginação, filtros extras, tags, idiomas, votos, cliques etc.). Abaixo, um resumo dos endpoints mais relevantes. Todos aceitam os prefixos `json/`, `xml/` ou `csv/`, e a base usada aqui é `https://de1.api.radio-browser.info/json`.

### Já usados neste projeto

| Endpoint | Usado por | Descrição |
|----------|-----------|-----------|
| `GET /stations/search` | `RadioBrowserApiService.listRadioStations()` | Busca avançada de estações, com filtros como `country`, `state`, `countrycode`, `tag`, `language`, `bitrateMin/Max`, `is_https`, `order`, `reverse`, `limit`, `offset`, `hidebroken`, entre outros. |
| `GET /countries` | `RadioBrowserApiService.listCountries()` | Lista todos os países cadastrados, com contagem de estações (`stationcount`). Aceita `order`, `reverse`, `hidebroken`, `offset`, `limit`. |
| `GET /states` | `RadioBrowserApiService.listStates()` | Lista todos os estados/regiões cadastrados. Aceita filtro opcional por `country` na própria URL (`/states/{country}/{filter}`) ou por query. |

### Outros endpoints do webservice (não usados ainda, mas úteis para futuras features)

| Endpoint | Descrição |
|----------|-----------|
| `GET /stations/bytag/{tag}`, `/bytagexact/{tag}` | Busca estações por tag (gênero), exata ou parcial. |
| `GET /stations/bylanguage/{lang}` | Busca estações por idioma. |
| `GET /stations/bycountrycodeexact/{code}` | Busca estações por código de país (ISO 3166-1 alpha-2), ex.: `BR`. |
| `GET /stations/topvote/{n}` | As `n` estações mais votadas, ótimo para uma seção "Destaques". |
| `GET /stations/topclick/{n}` | As `n` estações mais clicadas. |
| `GET /stations/lastclick/{n}` | Estações clicadas recentemente. |
| `GET /stations/broken` | Estações que falharam no teste de conexão (útil para checagem/limpeza). |
| `GET /stations/byuuid?uuids=...` | Busca uma ou mais estações por UUID exato. Poderia ser usado para montar uma página "Minhas favoritas" a partir da tabela `favorites`. |
| `GET /tags` | Lista todas as tags/gêneros disponíveis, com contagem de estações. Pode alimentar um filtro por gênero na Home. |
| `GET /languages` | Lista todos os idiomas disponíveis, com contagem de estações. |
| `GET /codecs` | Lista todos os codecs disponíveis (MP3, AAC, OGG etc.), com contagem. |
| `GET /url/{stationuuid}` | Deve ser chamado sempre que o usuário der play em uma estação, para contabilizar o clique oficialmente na API. **Hoje o projeto não chama este endpoint.** |
| `POST /vote/{stationuuid}` | Registra um voto (like) para a estação, limitado a 1 voto por IP a cada 10 minutos. Poderia complementar o sistema de favoritos. |
| `GET /stats` | Estatísticas gerais do servidor (total de estações, tags, idiomas, cliques na última hora/dia etc.). |
| `GET /servers` | Lista os servidores-espelho (mirrors) da API, para balanceamento/fallback. |

> ⚠️ **Observação importante**: a documentação da Radio Browser API recomenda enviar um `User-Agent` descritivo em todas as requisições (ex.: `RadioBrowserAPI/1.0`), para facilitar a identificação da aplicação pelos mantenedores do serviço. Isso ainda não está implementado no `RestTemplate` usado por `RadioBrowserApiService` e é uma melhoria recomendada.

## Documentação e Links Úteis

**Radio Browser API**

- [API Radio Browser — Documentação oficial](https://api.radio-browser.info/)
- [Radio Browser — Site do projeto](https://www.radio-browser.info/)
- [Busca avançada de estações](https://de1.api.radio-browser.info/json/stations/search)
- [Estações em Minas Gerais](https://de1.api.radio-browser.info/json/stations/search?country=Brazil&state=Minas%20Gerais)
- [Estação Itatiaia](https://de1.api.radio-browser.info/json/stations/search?name=itatiaia)
- [Lista de Países](https://de1.api.radio-browser.info/json/countries)
- [Lista de Estados](https://de1.api.radio-browser.info/json/states)
- [Lista de Tags/Gêneros](https://de1.api.radio-browser.info/json/tags)
- [Lista de Idiomas](https://de1.api.radio-browser.info/json/languages)
- [Lista de Codecs](https://de1.api.radio-browser.info/json/codecs)
- [Top estações por votos](https://de1.api.radio-browser.info/json/stations/topvote/10)
- [Estatísticas do servidor](https://de1.api.radio-browser.info/json/stats)
- [Lista de servidores-espelho](https://de1.api.radio-browser.info/json/servers)

**Supabase / Spring Data JPA**

- [Supabase — Dashboard](https://supabase.com/dashboard)
- [Supabase — Conectando ao banco (Direct, Session e Transaction pooler)](https://supabase.com/docs/guides/database/connecting-to-postgres)
- [Supabase — Row Level Security](https://supabase.com/docs/guides/database/postgres/row-level-security)
- [Spring Data JPA — Documentação](https://docs.spring.io/spring-data/jpa/reference/)
- [Spring Boot — Configurando o DataSource](https://docs.spring.io/spring-boot/how-to/data-access.html)

## Licença

Este projeto está licenciado sob a MIT License.
