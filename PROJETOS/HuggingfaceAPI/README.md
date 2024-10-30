# Projeto HuggingfaceAPI

Este projeto utiliza a API de inferência da Hugging Face, que é uma ferramenta baseada em inteligência artificial (IA), para gerar imagens a partir de descrições de texto.

## Como Funciona

O código envia uma solicitação POST para a API de inferência da Hugging Face, que utiliza modelos de IA treinados para gerar uma imagem com base na descrição fornecida. Esse processo demonstra como a IA pode ser aplicada para transformar texto em representações visuais, facilitando a criação de conteúdo visual a partir de descrições verbais.

```properties
# application.properties
spring.application.name=HuggingfaceAPI
api.url=https://api-inference.huggingface.co/models/black-forest-labs/FLUX.1-dev
api.token=hf_seutoken
```

## Estrutura do projeto
```
application
    └── HuggingfaceApiApplication.java
config
    └── ApiConfig.java
controller
    └── HuggingfaceApiController.java
service
    └── HuggingfaceApiService.java
```

## Endpoint:
**POST** `/api/generate` @RequestParam String texto

### Exemplos de chamada:
- `http://localhost:8080/api/generate?texto=Astronaut%20riding%20a%20horse`
- `http://localhost:8080/api/generate?texto=Bearded%20software%20engineering%20professor`

## Principal método do projeto:
```java
public String gerarImagem(String texto) throws IOException {
    RestTemplate restTemplate = new RestTemplate();

    // Configurar cabeçalhos
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + apiConfig.getApiToken());

    // Criar o corpo da requisição
    String jsonPayload = String.format("{"inputs":"%s"}", texto);
    HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

    // Fazer a requisição POST
    ResponseEntity<byte[]> response = restTemplate.exchange(apiConfig.getApiUrl(), HttpMethod.POST, entity, byte[].class);

    // Verificar se a resposta é válida
    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        byte[] imageBytes = response.getBody();
        // Salvar a imagem
        try (FileOutputStream fos = new FileOutputStream("imagens/imagem_gerada2.png")) {
            fos.write(imageBytes);
            return "Imagem salva como 'imagens/imagem_gerada2.png'.";
        } catch (IOException e) {
            // Tratar erro ao salvar a imagem
            throw new IOException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    } else {
        // Lançar uma exceção mais adequada em caso de erro de API
        throw new RuntimeException("Erro ao chamar a API: " + response.getStatusCode());
    }
}
```

A imagem será salva na pasta imagens na raiz do projeto.

Para este exemplo, foram escolhidas as imagens:
- Astronaut riding a horse (Astronauta andando a cavalo)
- Bearded software engineering professor (Professor barbudo de engenharia de software)

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
