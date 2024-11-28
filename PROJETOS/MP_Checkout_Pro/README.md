# MP_Checkout_Pro

O projeto **MP_Checkout_Pro** é uma implementação de integração com o Mercado Pago utilizando o **Checkout Pro**.
Ele permite criar preferências de pagamento e redirecionar o cliente para o fluxo de pagamento seguro do Mercado Pago.

## Captura de Tela

- **Tela Mercado Pago**: O link gerado redireciona para esta tela.

| ![Tela Mercado Pago](imgs/mp.png) |
|:-------------------:|
| Tela Mercado Pago |

## Dependências

Certifique-se de adicionar a seguinte dependência ao seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>com.mercadopago</groupId>
    <artifactId>sdk-java</artifactId>
    <version>2.1.29</version>
</dependency>
```

## Estrutura do Projeto

O projeto segue a seguinte estrutura de diretórios:

```
/MP_Checkout_Pro
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── exemplo
│   │   │           └── MP_Checkout_Pro
│   │   │               ├── application
│   │   │               │   └── MpCheckoutProApplication.java
│   │   │               ├── config
│   │   │               │   └── ApiConfig.java
│   │   │               ├── controller
│   │   │               │   └── MpCheckoutProController.java
│   │   │               └── service
│   │   │                   └── MpCheckoutProService.java
```

## Endpoints

### GET /iniciar-pagamento

Este endpoint permite iniciar um pagamento criando uma preferência no Mercado Pago. 

#### Parâmetros:

- **titulo**: Nome do produto.
- **quantidade**: Quantidade do produto.
- **precoUnitario**: Preço unitário do produto.

#### Exemplo de Chamada:

```
http://localhost:8080/iniciar-pagamento?titulo=Produto+Teste&quantidade=1&precoUnitario=100.00
```

#### Exemplo de Resposta:

```
https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=50756373-120c20cd-291e-4409-9d6c-7c967d77d6e1
```

## Serviço - Detalhes

A classe `MpCheckoutProService` é responsável pela lógica de criação de preferências de pagamento no Mercado Pago. 

1. **Configuração Inicial**: Durante a inicialização do serviço, o token de acesso é configurado utilizando a classe `ApiConfig`.

2. **Criação de Preferência**:
   - Um objeto `PreferenceItemRequest` é criado para representar o item a ser vendido, contendo:
     - **Título**: Nome do produto.
     - **Quantidade**: Quantidade do produto.
     - **Preço Unitário**: Valor unitário do produto.
   - Esse item é então utilizado para criar uma requisição do tipo `PreferenceRequest`, que pode conter uma lista de itens.
   - A requisição é enviada para o Mercado Pago utilizando a classe `PreferenceClient`, que retorna uma instância de `Preference`.
   - O link gerado (chamado de **init point**) redireciona para a tela de pagamento do Mercado Pago.

## Documentação e Links Úteis

- [Mercado Pago - Documentação Geral](https://www.mercadopago.com.br/developers/pt/docs)
- [Referência de API](https://www.mercadopago.com.br/developers/pt/reference)
- [Pagamentos Online](https://www.mercadopago.com.br/developers/pt/docs#online-payments)
- [Checkout Pro - Landing Page](https://www.mercadopago.com.br/developers/pt/docs/checkout-pro/landing)
- [Checkout Pro - Integração de Preferências](https://www.mercadopago.com.br/developers/pt/docs/checkout-pro/integrate-preferences)
- [Live Demo - Checkout Pro](https://www.mercadopago.com.br/developers/pt/live-demo/checkout-pro)
- [Vídeo Explicativo - Checkout Pro - Como gerar um access token](https://www.youtube.com/watch?v=WWcGuv74vbs)
- [Painel de Aplicações](https://www.mercadopago.com.br/developers/panel/app/)
- [SDK Mercado Pago no Maven](https://mvnrepository.com/artifact/com.mercadopago/sdk-java)

## Licença

Este projeto está licenciado sob a **MIT License**.
