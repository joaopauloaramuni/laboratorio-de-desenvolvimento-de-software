# Barcode Generator - Spring Boot Project

Este projeto é um gerador de código de barras simples usando Spring Boot e a biblioteca Barcode4J.

## Dependências

Adicione a seguinte dependência ao seu `pom.xml`:

```xml
<!-- Barcode4J -->
<dependency>
    <groupId>net.sf.barcode4j</groupId>
    <artifactId>barcode4j</artifactId>
    <version>2.1</version>
</dependency>
```

## Estrutura do Projeto

A estrutura de pacotes e classes é a seguinte:

```
src
│
├── main
│   ├── java
│   │   ├── application
│   │   │   └── BarCodeGeneratorApplication.java  # Classe principal para inicializar a aplicação
│   │   ├── controller
│   │   │   └── BarCodeController.java  # Controlador que gerencia as requisições de código de barras
│   │   └── service
│   │       └── BarCodeService.java  # Serviço que gera o código de barras a partir de texto
│   │
│   └── resources
│       └── static
│           └── barcode.png

```

## Como Executar

1. Clone este repositório e navegue até o diretório do projeto.
2. Certifique-se de que você possui o Java 11 ou superior instalado.
3. Execute o comando Maven para compilar o projeto:

```
mvn clean install
```

4. Inicie a aplicação com o seguinte comando:

```
mvn spring-boot:run
```

5. Acesse o serviço no navegador ou via ferramenta de requisições como Insomnia ou Postman.

## Endpoint

O serviço disponibiliza o seguinte endpoint para geração de códigos de barras:

```java
@GetMapping("/generate-barcode")
```

### Exemplo de chamada

Acesse o seguinte endpoint para gerar um código de barras:

```
http://localhost:8080/generate-barcode?text=1234567890
```

O código de barras será gerado com base no texto fornecido no parâmetro `text`.

## O que faz esse serviço?

O serviço responsável pela geração do código de barras é o seguinte:

```java
public byte[] generateBarcode(String barcodeText)
```

Esse método recebe uma string `barcodeText` e gera a imagem correspondente do código de barras. A imagem será disponibilizada como um arquivo PNG que pode ser baixado diretamente pelo navegador.

## Licença

Este projeto está licenciado sob a Licença MIT.
