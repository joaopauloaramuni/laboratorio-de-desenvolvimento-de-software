# ImageCompressor

## Descrição do Projeto
O projeto **ImageCompressor** é uma aplicação Spring Boot que permite a compressão de imagens enviadas pelo usuário. Através de um endpoint, o usuário pode fazer upload de uma imagem e especificar a qualidade desejada para a compressão. O projeto é útil para reduzir o tamanho das imagens, facilitando o armazenamento e a transferência.

## Configurações
As configurações do projeto podem ser encontradas no arquivo `application.properties`:

```properties
spring.application.name=ImageCompressor
# Define o limite máximo para o tamanho de upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## Estrutura do Projeto
- `application` -> `ImageCompressorApplication.java`
- `controller` -> `ImageCompressorController.java`
- `service` -> `ImageCompressorService.java`

## Endpoints
### POST /images/compress
Esse endpoint permite que o usuário envie uma imagem para compressão.

#### Parâmetros:
- `file`: O arquivo da imagem a ser comprimido.
- `quality`: A qualidade da compressão (opcional, padrão é `0.7`).

#### Exemplo de Requisição:
```http
POST http://localhost:8080/images/compress
```

A requisição pode ser encontrada na pasta `requests` na raiz do projeto.

## Métodos do Service
### compressImage
```java
public ByteArrayResource compressImage(InputStream imageInputStream, float quality) throws IOException
```
Esse método recebe um `InputStream` de uma imagem e a qualidade desejada. Ele comprime a imagem utilizando a biblioteca `ImageIO` e salva a imagem comprimida na pasta `images` na raiz do projeto.

### saveCompressedImage
```java
private void saveCompressedImage(byte[] imageData) throws IOException
```
Esse método salva a imagem comprimida em um arquivo na pasta `images`.

No exemplo apresentado, o arquivo original `image.jpg`, com um tamanho de 3.2 MB, foi comprimido para `compressed_image.jpg`, que agora ocupa apenas 893 KB. Essa compressão foi realizada utilizando um parâmetro de qualidade de 0.5, demonstrando uma redução significativa no tamanho do arquivo, mantendo uma qualidade aceitável da imagem.

## Licença
Este projeto é licenciado sob a Licença MIT.
