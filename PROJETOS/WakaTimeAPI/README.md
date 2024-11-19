# Projeto WakaTimeAPI

Este projeto é uma integração com a API do WakaTime para exibir as estatísticas de tempo de programação de um usuário. A API é chamada para coletar dados sobre o tempo gasto em diferentes linguagens, máquinas, projetos e outras informações relacionadas. O objetivo é criar um painel simples para exibir essas informações de maneira visual utilizando o Thymeleaf para renderizar os dados em uma página HTML.

## Dependências:

No arquivo **pom.xml**, adicione a dependência do Thymeleaf para renderizar templates HTML:

```xml
<!-- Thymeleaf (para templates HTML) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

## Exemplo de chamada à API:

Faça uma requisição para a API do WakaTime para obter as estatísticas de tempo do usuário. Substitua `waka_seuapitokenaqui` pelo seu token de API:

```
https://api.wakatime.com/api/v1/users/current/stats/all_time?api_key=waka_seuapitokenaqui
```

## Estrutura do projeto:

- **application** -> WakaTimeApiApplication
- **config** -> ApiConfig
- **controller** -> WakaTimeApiController
- **model** -> BestDay, Category, Data, Dependency, Editor, Language, Machine, OperatingSystem, Project
- **service** -> WakaTimeApiService
- **resources** -> static -> css -> style.css
- **templates** -> home.html

## Endpoint:

O endpoint da aplicação, responsável por pegar as estatísticas do WakaTime e renderizar os dados na página HTML, é:

```java
@GetMapping("/")
public String home(Model model) {
    WakaTimeApiResponse response = wakaTimeApiService.getWakatimeData();
    model.addAttribute("data", response.getData()); // Passando apenas a parte 'data' do objeto response
    return "home";
}
```

## Dados da Consulta da API

A consulta à API retornará os seguintes dados:

- **best_day**: O melhor dia de atividade registrado.
- **categories**: Lista de categorias relacionadas à atividade de programação.
- **created_at**: Data de criação dos dados.
- **daily_average**: Média diária de tempo gasto em programação.
- **daily_average_including_other_language**: Média diária de tempo gasto em programação, incluindo outras linguagens.
- **days_including_holidays**: Número de dias incluindo feriados.
- **days_minus_holidays**: Número de dias excluindo feriados.
- **dependencies**: Lista de dependências utilizadas nos projetos.
- **editors**: Lista de editores de código usados.
- **end**: Data de término do período de dados.
- **holidays**: Número de feriados registrados.
- **human_readable_daily_average**: Média diária formatada de forma legível.
- **human_readable_daily_average_including_other_language**: Média diária, incluindo outras linguagens, formatada de forma legível.
- **human_readable_range**: Intervalo de tempo formatado de forma legível.
- **human_readable_total**: Total de tempo gasto em programação, formatado de forma legível.
- **human_readable_total_including_other_language**: Total de tempo, incluindo outras linguagens, formatado de forma legível.
- **id**: Identificador único dos dados.
- **is_already_updating**: Indica se os dados estão sendo atualizados.
- **is_cached**: Indica se os dados são armazenados em cache.
- **is_coding_activity_visible**: Indica se a atividade de programação está visível.
- **is_including_today**: Indica se o dia de hoje está incluído nos dados.
- **is_other_usage_visible**: Indica se o uso de outras atividades está visível.
- **is_stuck**: Indica se a consulta está travada.
- **is_up_to_date**: Indica se os dados estão atualizados.
- **is_up_to_date_pending_future**: Indica se os dados estão pendentes de atualização futura.
- **languages**: Lista de linguagens utilizadas nos projetos.
- **machines**: Lista de máquinas utilizadas.
- **modified_at**: Data de modificação dos dados.
- **operating_systems**: Lista de sistemas operacionais utilizados.
- **percent_calculated**: Percentual de dados calculados.
- **projects**: Lista de projetos realizados.
- **range**: Intervalo de tempo da consulta.
- **start**: Data de início do período de dados.
- **status**: Status da consulta.
- **timeout**: Tempo limite da consulta.
- **timezone**: Fuso horário utilizado.
- **total_seconds**: Total de segundos registrados de atividade.
- **total_seconds_including_other_language**: Total de segundos de atividade, incluindo outras linguagens.
- **user_id**: Identificador do usuário.
- **username**: Nome de usuário.
- **writes_only**: Indica se o usuário só faz escrita (sem leitura).

## Documentação e links úteis:

- [API Docs](https://wakatime.com/developers)
- [Token](https://wakatime.com/settings/account)

## Licença:

Este projeto está licenciado sob a MIT License.
