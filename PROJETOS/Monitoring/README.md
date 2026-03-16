# 📊 Projeto Monitoring

Projeto utilizando **Spring Boot** para demonstrar monitoramento
de aplicações com:

-   Spring Boot Actuator
-   Micrometer
-   Prometheus

A aplicação expõe métricas através do **Actuator**, que são coletadas pelo **Prometheus** para monitoramento da aplicação.

----

# 🚀 Tecnologias utilizadas

-   Java
-   Spring Boot
-   Spring Boot Actuator
-   Micrometer
-   Prometheus

----

# 📖 Conceitos

## Spring Boot Actuator

O **Spring Boot Actuator** fornece endpoints prontos para monitoramento e gerenciamento de aplicações Spring.

Com ele é possível expor informações como:

-   status da aplicação (`/actuator/health`)
-   métricas (`/actuator/metrics`)
-   informações da aplicação (`/actuator/info`)
-   logs (`/actuator/loggers`)
-   mapeamento de endpoints (`/actuator/mappings`)

Esses endpoints são muito utilizados em **observabilidade e
monitoramento de sistemas em produção**.

Documentação:
- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html

----

## Micrometer

O **Micrometer** é uma biblioteca de métricas utilizada pelo Spring Boot.

Ele funciona como uma **camada de abstração para sistemas de monitoramento**, permitindo enviar métricas para diferentes ferramentas como:

- Prometheus
- Datadog
- New Relic
- Graphite

No Spring Boot, o Micrometer é integrado automaticamente ao **Actuator**.

Documentação: https://micrometer.io/

----

## Prometheus

O **Prometheus** é uma ferramenta open source de monitoramento e coleta de métricas.

Ele funciona através de um modelo chamado **pull**, onde o Prometheus periodicamente consulta aplicações para coletar métricas.

No caso deste projeto:

1.  A aplicação Spring expõe métricas em:

```
/actuator/prometheus
```

2.  O Prometheus coleta essas métricas automaticamente.

Site oficial: https://prometheus.io/

----

# ⚙️ Configuração do Actuator

No arquivo `application.properties`, os endpoints do Actuator são expostos:

```
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
management.endpoint.info.enabled=true
management.endpoint.health.enabled=true
management.endpoint.metrics.enabled=true
management.endpoint.loggers.enabled=true
management.endpoint.mappings.enabled=true
```

Isso permite acessar todos os endpoints de monitoramento.

Exemplo:
```
http://localhost:8080/actuator
```

Endpoint específico para Prometheus:
```
http://localhost:8080/actuator/prometheus
```

----

# 📡 Arquivo prometheus.yml

O arquivo **prometheus.yml** define como o Prometheus irá coletar métricas.

Exemplo do projeto:
```yml
    global:
      scrape_interval: 15s
      evaluation_interval: 15s
    
    # rule_files:
    #   - "first.rules"
    #   - "second.rules"
    
    scrape_configs:
      - job_name: prometheus
        static_configs:
          - targets: ['localhost:9090']
    
      - job_name: 'spring-actuator'
        metrics_path: '/actuator/prometheus'
        scrape_interval: 5s
        static_configs:
          - targets: ['localhost:8080']
```

## Explicação

- `global` → define configurações globais do Prometheus que serão aplicadas a todos os serviços monitorados, salvo quando houver configuração específica.
- `scrape_interval` → determina o intervalo padrão de coleta das métricas. No exemplo, o Prometheus realiza a coleta a cada **15 segundos**.
- `evaluation_interval` → define o intervalo em que o Prometheus avalia regras de alerta e regras de gravação (recording rules).
- `rule_files` → permite definir arquivos que contêm **regras do Prometheus**. Essas regras podem ser usadas para criar **alertas automáticos** ou **métricas derivadas** a partir das métricas coletadas.
- `scrape_configs` → seção onde são configurados os serviços que o Prometheus irá monitorar.
- `job_name` → nome do serviço ou aplicação que está sendo monitorado.
- `static_configs` → define uma lista estática de endpoints que serão monitorados.
- `targets` → especifica o endereço (host e porta) das aplicações de onde o Prometheus irá coletar as métricas.
- `metrics_path` → caminho do endpoint onde as métricas da aplicação estão disponíveis.

Neste exemplo, existem dois serviços monitorados:

- O próprio **Prometheus**, disponível em `localhost:9090`.
- A aplicação **Spring Boot**, disponível em `localhost:8080`, que expõe métricas através do endpoint `/actuator/prometheus`.

Para a aplicação Spring, o intervalo de coleta foi configurado para **5 segundos**, sobrescrevendo o valor padrão definido na configuração global.

----

# 📦 Dependências

Dependências utilizadas no projeto:
```xml
    <dependencies>
		<!-- Spring Boot Starter -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<!-- Prometheus -->
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-prometheus</artifactId>
			<scope>runtime</scope>
		</dependency>
	</dependencies>
```

----

# 📂 Endpoint de teste

A aplicação possui um endpoint simples para teste:
```
GET /hello
```

Resposta:
```
Hello, World!
```

----

# 🔗 Links úteis

Spring Boot  
- https://spring.io/projects/spring-boot  
- https://github.com/spring-projects/spring-boot

Spring Boot Actuator  
- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html  
- https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-actuator

Micrometer  
- https://micrometer.io/  
- https://github.com/micrometer-metrics/micrometer

Prometheus  
- https://prometheus.io/  
- https://github.com/prometheus/prometheus

Prometheus Documentation  
- https://prometheus.io/docs/introduction/overview/

----

# 📄 Licença

Este projeto está licenciado sob a licença **MIT**.
