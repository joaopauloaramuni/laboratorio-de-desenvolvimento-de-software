# RadioBrowserAPI

## Descrição do Projeto

O RadioBrowserAPI é um projeto que consome a [Radio Browser API](https://api.radio-browser.info/) para exibir estações de rádio, permitindo ao usuário filtrar por **país** e **estado**, marcar estações como **favoritas** e ouvi-las diretamente pelo navegador. A aplicação permite que os usuários visualizem informações sobre as estações de rádio, incluindo detalhes como nome, URL, tags, votos, cliques, bitrate e codec. A interface é construída utilizando o Thymeleaf, que fornece uma maneira simples e eficiente de gerar páginas HTML dinâmicas.

No projeto RadioBrowserAPI, utilizamos HTML5 para criar uma interface web interativa e moderna. Uma das funcionalidades principais da aplicação é a reprodução de estações de rádio, que é possibilitada pelo uso da tag `<audio>` do HTML5.

A tag `<audio>` permite incorporar áudio diretamente nas páginas da web, oferecendo aos usuários a capacidade de ouvir as rádios de forma simples e eficiente. Com essa tag, é possível incluir controles de reprodução, como play, pause e volume, proporcionando uma experiência de usuário intuitiva e acessível.

Graças à integração do Thymeleaf, a aplicação é capaz de gerar dinamicamente elementos de áudio para cada uma das estações de rádio disponíveis, permitindo que os usuários selecionem e ouçam suas rádios favoritas com facilidade. A combinação do HTML5 e do Thymeleaf garante que a interface não apenas seja funcional, mas também responsiva e atraente.

## Funcionalidades

- **Filtro por país e estado**: os dropdowns de país e estado são carregados dinamicamente a partir da API (`/countries` e `/states`), permitindo pesquisar estações de qualquer lugar do mundo. Por padrão, a busca é feita para `country=Brazil` e `state=Minas Gerais`.
- **Favoritos**: cada estação pode ser marcada/desmarcada como favorita com um clique. As favoritas sobem para o topo da listagem (mantendo a ordenação por votos dentro de cada grupo). Os favoritos são guardados **em memória**, enquanto a aplicação estiver rodando — ou seja, são zerados a cada reinício e não exigem banco de dados ou arquivo de persistência.
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

O projeto utiliza a seguinte dependência em seu `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### Thymeleaf

Thymeleaf é um motor de templates para Java que permite a criação de páginas HTML dinâmicas de forma simples e eficiente. Ele é frequentemente utilizado em aplicações Spring, proporcionando uma maneira intuitiva de gerar conteúdo HTML e manipular dados diretamente nas páginas.

**Principais Características**

- **Natural Templating**: Os templates Thymeleaf são válidos como documentos HTML, permitindo que sejam visualizados em navegadores sem processamento.
- **Integração com Spring**: Thymeleaf se integra perfeitamente com o Spring Framework, facilitando a injeção de dependências e o acesso a beans do Spring.
- **Expressões de Template**: Utiliza uma sintaxe simples e expressiva para manipular dados, permitindo a criação de lógicas condicionais e loops diretamente nas páginas.

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
│   │   │               │   └── ☕ RadioBrowserApiApplication.java     # Classe main, sobe a aplicação Spring Boot
│   │   │               ├── 📁 config
│   │   │               │   └── ⚙️ ApiConfig.java                      # Monta as URLs da Radio Browser API a partir do properties
│   │   │               ├── 📁 controller
│   │   │               │   └── 🌐 RadioBrowserApiController.java      # Endpoints: home (filtros) e toggle de favoritos
│   │   │               ├── 📁 model
│   │   │               │   └── 📻 RadioStation.java                   # Representa uma estação de rádio
│   │   │               └── 📁 service
│   │   │                   ├── 🔎 RadioBrowserApiService.java         # Busca/ordena estações, países e estados na API
│   │   │                   └── ⭐ FavoriteService.java                # Guarda os favoritos em memória (sem persistência)
│   │   ├── 📁 resources
│   │   │   ├── 🔧 application.properties                              # Configurações da aplicação (ex: URL base da API)
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
│                       └── ✅ RadioBrowserApiApplicationTests.java     # Testes da aplicação
│
├── 📦 pom.xml                                                          # Dependências e build do Maven
├── 📄 README.md                                                        # Este arquivo
```

## Endpoints

### `GET /`

Lista as estações de rádio de acordo com o país e o estado informados, já marcando quais são favoritas e ordenando-as (favoritas primeiro, depois por votos).

```java
@GetMapping("/")
public String listRadioStations(
        @RequestParam(defaultValue = "Brazil") String country,
        @RequestParam(required = false, defaultValue = "Minas Gerais") String state,
        Model model) {

    List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(country, state);

    radioStations.forEach(station ->
            station.setFavorite(favoriteService.isFavorite(station.getStationuuid())));

    radioStations.sort(Comparator.comparing(RadioStation::isFavorite).reversed());

    model.addAttribute("stations", radioStations);
    model.addAttribute("countries", radioBrowserApiService.listCountries());
    model.addAttribute("states", radioBrowserApiService.listStates());
    model.addAttribute("selectedCountry", country);
    model.addAttribute("selectedState", state);

    return "home";
}
```

**Parâmetros de query**

| Parâmetro | Obrigatório | Padrão         | Descrição                          |
|-----------|-------------|----------------|-------------------------------------|
| `country` | Não         | `Brazil`       | País usado para filtrar as estações |
| `state`   | Não         | `Minas Gerais` | Estado usado para filtrar as estações |

Acesse a página inicial em: [http://localhost:8080/](http://localhost:8080/)

### `POST /favorites/toggle`

Adiciona ou remove uma estação da lista de favoritos (em memória) e redireciona de volta para a Home, preservando o filtro de país/estado atualmente selecionado.

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

## Configuração

O arquivo `application.properties` contém as seguintes configurações:

```properties
spring.application.name=RadioBrowserAPI
radio.api.base.url=https://de1.api.radio-browser.info/json
```

A partir da `radio.api.base.url`, a classe `ApiConfig` monta as três URLs consumidas pela aplicação:

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

## Serviços

### `RadioBrowserApiService`

- `List<RadioStation> listRadioStations(String country, String state)` — busca as estações filtradas por país/estado (parâmetros opcionais), ordenadas por votos (decrescente).
- `List<Map<String, Object>> listCountries()` — lista todos os países disponíveis na Radio Browser API, ordenados alfabeticamente (pt-BR).
- `List<Map<String, Object>> listStates()` — lista todos os estados disponíveis na Radio Browser API, ordenados alfabeticamente (pt-BR), independente do país selecionado.
- `List<RadioStation> extractRadioStations(List<Map<String, Object>> stationsData)` — converte a resposta bruta da API em objetos `RadioStation`.

### `FavoriteService`

Serviço responsável por manter, **em memória** (`ConcurrentHashMap.newKeySet()`), os UUIDs das estações marcadas como favoritas. Não há persistência em banco de dados ou arquivo — a lista de favoritos é reiniciada a cada restart da aplicação.

- `boolean isFavorite(String stationUuid)` — verifica se uma estação é favorita.
- `void toggle(String stationUuid)` — adiciona a estação aos favoritos se ainda não estiver lá, ou remove se já estiver.

## API Radio Browser — Endpoints Úteis

O RadioBrowserAPI (este projeto) consome apenas três endpoints da [Radio Browser API](https://api.radio-browser.info/) — busca avançada de estações, países e estados — mas o webservice oferece bem mais recursos que podem ser úteis para evoluir a aplicação (paginação, filtros extras, tags, idiomas, votos, cliques etc.). Abaixo, um resumo dos endpoints mais relevantes (todos aceitam os prefixos `json/`, `xml/` ou `csv/`, e a base usada aqui é `https://de1.api.radio-browser.info/json`):

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
| `GET /stations/topvote/{n}` | As `n` estações mais votadas — ótimo para uma seção "Destaques". |
| `GET /stations/topclick/{n}` | As `n` estações mais clicadas. |
| `GET /stations/lastclick/{n}` | Estações clicadas recentemente. |
| `GET /stations/broken` | Estações que falharam no teste de conexão (útil para checagem/limpeza). |
| `GET /stations/byuuid?uuids=...` | Busca uma ou mais estações por UUID exato. |
| `GET /tags` | Lista todas as tags/gêneros disponíveis, com contagem de estações — pode alimentar um filtro por gênero na Home. |
| `GET /languages` | Lista todos os idiomas disponíveis, com contagem de estações. |
| `GET /codecs` | Lista todos os codecs disponíveis (MP3, AAC, OGG etc.), com contagem. |
| `GET /url/{stationuuid}` | Deve ser chamado sempre que o usuário der play em uma estação, para contabilizar o clique oficialmente na API. **Hoje o projeto não chama este endpoint.** |
| `POST /vote/{stationuuid}` | Registra um voto (like) para a estação — limitado a 1 voto por IP a cada 10 minutos. Poderia complementar o sistema de favoritos local. |
| `GET /stats` | Estatísticas gerais do servidor (total de estações, tags, idiomas, cliques na última hora/dia etc.). |
| `GET /servers` | Lista os servidores-espelho (mirrors) da API, para balanceamento/fallback. |

> ⚠️ **Observação importante**: a documentação da Radio Browser API recomenda enviar um `User-Agent` descritivo em todas as requisições (ex.: `RadioBrowserAPI/1.0`), para facilitar a identificação da aplicação pelos mantenedores do serviço. Isso ainda não está implementado no `RestTemplate` usado por `RadioBrowserApiService` e é uma melhoria recomendada.

## Documentação e Links Úteis

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

## Licença

Este projeto está licenciado sob a MIT License.
