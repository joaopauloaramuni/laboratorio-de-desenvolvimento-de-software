# Projeto HuggingfaceAPIWithUI

Este projeto utiliza a API de inferência da Hugging Face, que é uma ferramenta baseada em inteligência artificial (IA), para gerar imagens a partir de descrições de texto. Agora, ele conta com uma interface `home.html` utilizando `Thymeleaf`, que permite ao usuário inserir descrições de imagem e definir o diretório para salvar o arquivo gerado.

## Como Funciona

O código envia uma solicitação POST para a API de inferência da Hugging Face, que utiliza modelos de IA treinados para gerar uma imagem com base na descrição fornecida. Esse processo demonstra como a IA pode ser aplicada para transformar texto em representações visuais, facilitando a criação de conteúdo visual a partir de descrições verbais.

```properties
# application.properties
spring.application.name=HuggingfaceAPIWithUI
api.url=https://api-inference.huggingface.co/models/black-forest-labs/FLUX.1-dev
api.token=hf_seutoken
```

No arquivo `pom.xml`, adicione a dependência do Thymeleaf:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

## Estrutura do projeto
```
application
    └── HuggingfaceApiWithUIApplication.java
config
    └── ApiConfig.java
controller
    └── HuggingfaceApiWithUIController.java
service
    └── HuggingfaceApiWithUIService.java
```

## Endpoints:

### 1. Página inicial
**GET** `/`

Renderiza a interface da página `home.html`, onde é possível inserir uma descrição e um diretório para salvar a imagem gerada.

### 2. Gerar imagem
**POST** `/generate` @RequestParam String texto, @RequestParam String diretorio

Exemplo de chamada:
```bash
curl -X POST "http://localhost:8080/generate" -d "texto=Astronaut riding a horse" -d "diretorio=/Users/joaopauloaramuni/Downloads"
```

### Thymeleaf

Thymeleaf é um motor de templates para Java que permite a criação de páginas HTML dinâmicas de forma simples e eficiente. Ele é frequentemente utilizado em aplicações Spring, proporcionando uma maneira intuitiva de gerar conteúdo HTML e manipular dados diretamente nas páginas.

**Principais Características**

- **Natural Templating**: Os templates Thymeleaf são válidos como documentos HTML, permitindo que sejam visualizados em navegadores sem processamento.
- **Integração com Spring**: Thymeleaf se integra perfeitamente com o Spring Framework, facilitando a injeção de dependências e o acesso a beans do Spring.
- **Expressões de Template**: Utiliza uma sintaxe simples e expressiva para manipular dados, permitindo a criação de lógicas condicionais e loops diretamente nas páginas.

## Interface Gráfica

A interface gráfica permite que o usuário faça upload de imagens para compressão. O usuário pode selecionar uma imagem do seu dispositivo, especificar a qualidade desejada e iniciar o processo de compressão com um clique. Após a compressão, a imagem resultante pode ser baixada diretamente pela interface.

### Captura de Tela

- **Tela de Home**: A tela inicial apresenta um formulário onde o usuário pode inserir uma descrição para a imagem que deseja gerar, especificar o diretório onde a imagem será salva e iniciar o processo de criação. Além disso, há um rótulo para exibir mensagens de status, orientando o usuário sobre as etapas necessárias.

| <img src="imagens/home2.png" alt="Home" width="600"/> |
|:------------------------:|
|         Home             |

**Exemplo de Uso**

Aqui está um exemplo simples de um template Thymeleaf:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Exemplo Thymeleaf</title>
</head>
<body>
    <h1 th:text="${titulo}">Título do Documento</h1>
    <ul>
        <li th:each="item : ${itens}" th:text="${item}"></li>
    </ul>
</body>
</html>
```

Neste exemplo, o título e a lista de itens são preenchidos dinamicamente com dados fornecidos pelo controlador Spring.

Thymeleaf é uma escolha poderosa para desenvolvedores que desejam criar interfaces web dinâmicas e interativas em aplicações Java. Com sua sintaxe intuitiva e forte integração com o Spring, ele se tornou uma ferramenta popular no ecossistema de desenvolvimento Java.

## Principal método do projeto:
```java
public String gerarImagem(String texto, String diretorio) throws IOException {
    RestTemplate restTemplate = new RestTemplate();

    // Configurar cabeçalhos
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + apiConfig.getApiToken());

    // Criar o corpo da requisição
    String jsonPayload = String.format("{\"inputs\":\"%s\"}", texto);
    HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

    // Fazer a requisição POST
    ResponseEntity<byte[]> response = restTemplate.exchange(apiConfig.getApiUrl(), HttpMethod.POST, entity, byte[].class);

    // Verificar se a resposta é válida
    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        byte[] imageBytes = response.getBody();
        // Salvar a imagem no diretório especificado pelo usuário
        try (FileOutputStream fos = new FileOutputStream(diretorio + "/imagem_gerada2.png")) {
            fos.write(imageBytes);
            return "Imagem salva como '" + diretorio + "/imagem_gerada2.png'.";
        } catch (IOException e) {
            throw new IOException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    } else {
        throw new RuntimeException("Erro ao chamar a API: " + response.getStatusCode());
    }
}
```

## Diretório

Se diretório = imagens
- A imagem será salva na pasta imagens na raiz do projeto.

Se diretório = /Users/nomedeusuario/Downloads
- A imagem será salva na pasta Downloads (Mac OS)

Se diretório = C:/imagens
- A imagem será salva na pasta imagens no C:/ (Windows)

Se diretório = /home/nomedeusuario/imagens
- A imagem será salva na pasta imagens dentro do diretório home do usuário (Linux).

Para este exemplo, foram escolhidas as imagens:
- Astronaut riding a bike (Astronauta andando de bicicleta)
- Astronaut riding a car (Astronauta dirigindo um carro)

## Documentação
- [Hugging Face Website](https://huggingface.co/)
- [Link da documentação da API](https://huggingface.co/docs/api-inference/tasks/text-to-image?code=python#text-to-image)

## Token
Para gerar seu próprio token, visite: [Configurações de Token](https://huggingface.co/settings/tokens)

As requests utilizadas estão localizadas na pasta requests.

## Como Executar
1. Certifique-se de ter o Java JDK 17 e Maven instalados.
2. Clone o repositório.
3. Navegue até a pasta do projeto e execute:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse os endpoints usando o Postman ou o Insomnia.

## Licença

Este projeto está licenciado sob a MIT License.
