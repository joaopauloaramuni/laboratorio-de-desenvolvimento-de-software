

# QrCodeGenerator

Este é um projeto simples de geração de QR Codes usando Spring Boot.

## Estrutura do Projeto

```
src
│
├── main
│   ├── java
│   │   ├── application
│   │   │   └── QrCodeGeneratorApplication.java
│   │   ├── controller
│   │   │   └── QrCodeController.java
│   │   └── service
│   │       └── QrCodeService.java
│   │
│   └── resources
│       └── static
│           └── QRCode.png

```

## Endpoints

### 1. Gerar QR Code como PNG
- **URL:** `/generateQRCode`
- **Método:** `GET`
- **Exemplo de Requisição:**
```
http://localhost:8080/generateQRCode?text=https://example.com&width=400&height=400
```

#### Parâmetros:
- `text` (obrigatório): O texto ou URL que será codificado no QR Code.
- `width` (opcional): Largura da imagem do QR Code (padrão: 300).
- `height` (opcional): Altura da imagem do QR Code (padrão: 300).

### 2. Salvar QR Code em arquivo local
- **URL:** `/saveQRCode`
- **Método:** `GET`
- **Exemplo de Requisição:**
```
http://localhost:8080/saveQRCode?text=https://example.com&width=300&height=300
```

#### Parâmetros:
- `text` (obrigatório): O texto ou URL que será codificado no QR Code.
- `width` (opcional): Largura da imagem do QR Code (padrão: 300).
- `height` (opcional): Altura da imagem do QR Code (padrão: 300).

## Dependências
Adicione as seguintes dependências ao seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.5.3</version>
</dependency>

<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>javase</artifactId>
    <version>3.5.3</version>
</dependency>
```

## Como Executar
1. Certifique-se de ter o Java JDK e Maven instalados.
2. Clone o repositório.
3. Navegue até a pasta do projeto e execute:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse os endpoints usando um navegador ou uma ferramenta como Postman.

## Observações
A imagem gerada do QR Code será salva em `src/main/resources/static/QRCode.png` quando o endpoint `/saveQRCode` for chamado.

## Licença

Este projeto está licenciado sob a MIT License.

