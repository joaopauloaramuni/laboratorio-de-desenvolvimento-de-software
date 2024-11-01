# Projeto Tests_JUnit_Mockito

Este projeto é uma aplicação Spring Boot que utiliza JUnit e Mockito para realizar testes unitários em um controlador de usuários. O controlador interage com um serviço de usuários para executar operações CRUD e consultas personalizadas.

## Dependências

As seguintes dependências estão incluídas no arquivo `pom.xml`:

```xml
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
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

## Configuração do MongoDB

As configurações do MongoDB estão especificadas no arquivo `application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=bd-user
```

## Estrutura do projeto
```
main
└── java
    └── com.example.RestAPI
        ├── application
        │   └── ApplicationConfig
        ├── RestApiApplication
        ├── controller
        │   └── UserController
        ├── exception
        │   ├── UserAlreadyExistsException
        │   └── UserNotFoundException
        ├── model
        │   └── UserEntity
        ├── repository
        │   └── UserRepository
        └── service
            └── UserService
test
└── java
    └── com.example.RestAPI
        ├── UserControllerTests
        └── UserServiceTests

```

## Endpoints da API de Usuários

## Obter Usuários
- `GET /users` - Obtém todos os usuários.
- `GET /users/{id}` - Obtém um usuário pelo ID.

## Criar e Atualizar Usuários
- `POST /users` - Insere um novo usuário.
- `PUT /users/{id}` - Atualiza um usuário existente pelo ID.

## Excluir Usuário
- `DELETE /users/{id}` - Exclui um usuário pelo ID.

## Consultas Personalizadas
- `GET /users/buscarPorNome/{nome}` - Busca usuários pelo nome.
- `GET /users/buscarPorEmail/{email}` - Busca usuários pelo e-mail.
- `GET /users/buscarPorNomeEEmail` - Busca usuários pelo nome e e-mail (parâmetros: `nome`, `email`).
- `GET /users/buscarPorNomeQueComecaCom/{prefixo}` - Busca usuários cujo nome começa com o prefixo.
- `GET /users/buscarPorNomeQueContem/{contem}` - Busca usuários cujo nome contém a substring.

## Consultas Avançadas
- `GET /users/buscar/exato/{nome}` - Busca usuários pelo nome exato.
- `GET /users/buscar/email-contem/{email}` - Busca usuários cujo e-mail contém a substring.
- `GET /users/buscar/prefixo-query/{prefixo}` - Busca usuários cujo nome começa com o prefixo (usando @Query).
- `GET /users/buscar/contem-query/{substring}` - Busca usuários cujo nome contém a substring (usando @Query).
- `GET /users/buscar/exato-nome-email` - Busca usuários pelo nome e e-mail exatos (parâmetros: `nome`, `email`).
- `GET /users/buscar/idade-maior-ou-igual/{idade}` - Busca usuários com idade maior ou igual a um valor.
- `GET /users/buscar/data-de-cadastro-depois` - Busca usuários cadastrados após uma determinada data (parâmetro: `data`).
- `GET /users/buscar/idade-entre` - Busca usuários com idade entre dois valores (parâmetros: `idadeInicio`, `idadeFim`).
- `GET /users/buscar/nome-ou-email-contem/{substring}` - Busca usuários cujo nome ou e-mail contém a substring.
- `GET /users/buscar/idade-ou-cadastro-antes` - Busca usuários com idade ou data de cadastro antes de um valor (parâmetros: `idade`, `data`).
- `GET /users/buscar/nome-nao-contem/{substring}` - Busca usuários cujo nome não contém a substring.

Exemplo: POST http://localhost:8080/users
```json
{
    "id": "1234",
    "nome": "joao",
    "email": "joao@gmail.com",
    "idade": 33,
    "dataDeCadastro": "2024-11-01T12:00:00Z"
}
```

## Testes

O projeto inclui testes para o controlador de usuários, utilizando JUnit e Mockito. Os testes abrangem operações CRUD e consultas personalizadas.

### Métodos das Classes de Testes

### UserControllerTests

#### Métodos CRUD

- **testObterTodos**: Verifica se a operação de obter todos os usuários funciona corretamente.
- **testObterPorId**: Verifica se a operação de obter um usuário por ID funciona corretamente.
- **testInserir**: Verifica se a operação de inserir um novo usuário funciona corretamente.
- **testAtualizar**: Verifica se a operação de atualizar um usuário existente funciona corretamente.
- **testExcluir**: Verifica se a operação de excluir um usuário funciona corretamente.

#### Métodos utilizando consultas personalizadas

- **testBuscarUsuariosPorNome**: Verifica se a busca de usuários por nome retorna os resultados esperados.
- **testBuscarUsuariosPorEmail**: Verifica se a busca de usuários por email retorna os resultados esperados.
- **testBuscarUsuariosPorNomeEEmail**: Verifica se a busca de usuários por nome e email retorna os resultados esperados.
- **testBuscarUsuariosPorNomeQueComecaCom**: Verifica se a busca de usuários cujo nome começa com uma substring retorna os resultados esperados.
- **testBuscarUsuariosPorNomeQueContem**: Verifica se a busca de usuários cujo nome contém uma substring retorna os resultados esperados.

#### Métodos adicionais para consultas com @Query

- **testBuscarPorNomeExato**: Verifica se a busca de usuários por nome exato retorna os resultados esperados.
- **testBuscarPorEmailContem**: Verifica se a busca de usuários por email que contém uma substring retorna os resultados esperados.
- **testBuscarPorNomeComecandoComQuery**: Verifica se a busca de usuários cujo nome começa com uma substring retorna os resultados esperados.
- **testBuscarPorNomeContemQuery**: Verifica se a busca de usuários cujo nome contém uma substring retorna os resultados esperados.
- **testBuscarPorNomeEEmailExato**: Verifica se a busca de usuários por nome e email exatos retorna os resultados esperados.
- **testBuscarPorIdadeMaiorOuIgual**: Verifica se a busca de usuários com idade maior ou igual a um valor específico retorna os resultados esperados.
- **testBuscarPorDataDeCadastroDepois**: Verifica se a busca de usuários cadastrados após uma determinada data retorna os resultados esperados.
- **testBuscarPorIdadeEntre**: Verifica se a busca de usuários com idade dentro de um intervalo retorna os resultados esperados.
- **testBuscarPorNomeOuEmailContendo**: Verifica se a busca de usuários cujo nome ou email contém uma substring retorna os resultados esperados.
- **testBuscarPorIdadeOuCadastroAntes**: Verifica se a busca de usuários com idade ou cadastrados antes de uma determinada data retorna os resultados esperados.
- **testBuscarPorNomeNaoContendo**: Verifica se a busca de usuários cujo nome não contém uma substring retorna os resultados esperados.

### UserServiceTests

#### Testes de Operações do Serviço de Usuários

- **testInserirUsuarioExistente**: Verifica se uma exceção é lançada ao tentar inserir um usuário que já existe.
- **testAtualizarUsuarioNaoEncontrado**: Verifica se uma exceção é lançada ao tentar atualizar um usuário que não existe.
- **testObterTodosUsuarios**: Testa se todos os usuários são retornados corretamente.
- **testObterUsuarioPorIdExistente**: Verifica se um usuário é retornado corretamente pelo seu ID.
- **testObterUsuarioPorIdNaoExistente**: Verifica se uma exceção é lançada ao tentar obter um usuário que não existe.
- **testInserirUsuarioNovo**: Testa a inserção de um novo usuário que ainda não existe.
- **testAtualizarUsuarioExistente**: Verifica se um usuário existente é atualizado corretamente.
- **testExcluirUsuarioExistente**: Testa a exclusão de um usuário existente.
- **testExcluirUsuarioNaoEncontrado**: Verifica se uma exceção é lançada ao tentar excluir um usuário que não existe.
- **testBuscarPorNome**: Verifica se a busca por nome retorna o usuário correto.
- **testBuscarPorEmail**: Verifica se a busca por e-mail retorna o usuário correto.
- **testBuscarPorNomeQueComecaCom**: Verifica se a busca por nome que começa com um prefixo retorna o usuário correto.
- **testBuscarPorNomeQueContem**: Verifica se a busca por nome que contém uma substring retorna o usuário correto.
- **testBuscarPorNomeComVariosResultados**: Verifica se a busca por nome que contém retorna múltiplos usuários corretamente.
- **testBuscarPorEmailComVariosResultados**: Verifica se a busca por e-mail com vários resultados retorna usuários corretamente.

## Execução dos Testes

Para executar os testes, utilize o seguinte comando:

```bash
mvn test
```

## Contribuições

Sinta-se à vontade para contribuir com melhorias ou relatar problemas.

## Licença

Este projeto está licenciado sob a MIT License.