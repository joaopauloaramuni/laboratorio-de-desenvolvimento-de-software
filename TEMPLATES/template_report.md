# 📘 Relatório de Análise Crítica do Projeto 👨‍💻

## 1. Informações do grupo
- **🎓 Curso:** Engenharia de Software
- **📘 Disciplina:** Laboratório de Desenvolvimento de Software
- **🗓 Período:** 4° Período
- **👨‍🏫 Professor(a):** Prof. Dr. João Paulo Carneiro Aramuni
- **👥 Membros do Grupo:** [Lista de integrantes]

---

## 📌 2. Identificação do Projeto
- **Nome do projeto:** _Informe aqui_
- **Integrantes do outro grupo:** _Nomes_  
- **Link do repositório:** _https://github.com/exemplo/projeto_  
- **Pull requests submetidos pelo seu grupo:**
  
  | 👤 Integrante | 🔧 Refatoração | 🔗 Link do PR |
  |--------------|---------------|----------------|
  | :octocat: <a href="https://github.com/user1">Aluno 1</a> | Validação e Regras de Negócio | https://github.com/exemplo/projeto/pull/1 |
  | :octocat: <a href="https://github.com/user2">Aluno 2</a> | Estrutura e Eliminação de Código Duplicado | https://github.com/exemplo/projeto/pull/2 |
  | :octocat: <a href="https://github.com/user3">Aluno 3</a> | Controladores e Padronização de Endpoints | https://github.com/exemplo/projeto/pull/3 |
  | :octocat: <a href="https://github.com/user4">Aluno 4</a> | Organização de Pastas e Modularização do Backend | https://github.com/exemplo/projeto/pull/4 |

> [!NOTE]
> Os links acima são apenas exemplos. Substitua pelos PRs reais do seu grupo.

### 📝 Abrindo o Pull Request: Fluxos de Contribuição via PRs

#### 1. Opção 1 — Usando *Fork* (quando você **não é colaborador**)

1. Crie um **fork** (cópia) do repositório do outro grupo na sua conta.
2. **Clone o seu fork** localmente.
3. Crie um **branch**, faça as refatorações (commits) e envie (**push**) a branch para o seu fork.
4. No GitHub, acesse o **seu fork** e inicie o pull request clicando em **"Compare & pull request"**.
5. O PR deve propor mesclar o **seu branch** para a branch **main** do **repositório original** do outro grupo.
6. Adicione título/descrição e clique em **"Create pull request"**.

#### 2. Opção 2 — Como Membro/Colaborador (quando você **foi incluído** no repositório)

1. Peça para o outro grupo **adicionar seu usuário GitHub como colaborador**.
2. **Clone o repositório original** localmente.
3. Crie um **branch**, faça as refatorações (commits) e envie (**push**) a branch diretamente para o repositório original.
4. No GitHub, no repositório original, inicie o pull request clicando em **"Compare & pull request"**.
5. O PR deve propor mesclar o **seu branch** para a branch **main** do **mesmo repositório**.
6. Adicione título/descrição e clique em **"Create pull request"**.

> [!IMPORTANT]
> Cada integrante do grupo é responsável por enviar seu próprio pull request, contendo a refatoração que realizou.

---

## 🧱 3. Arquitetura e Tecnologias Utilizadas

O projeto utiliza uma arquitetura dividida entre backend e frontend, promovendo modularidade, separação de responsabilidades e facilidade de manutenção.

### 🏗️ Backend — Spring Boot
O backend foi desenvolvido utilizando **Spring Boot**, seguindo um padrão próximo ao **MVC**, com camadas bem definidas:

- **Controllers:** recebem requisições HTTP e encaminham para a lógica apropriada.  
- **Services:** concentram as regras de negócio e interações entre camadas.  
- **Repositories:** utilizam Spring Data JPA para acesso aos dados e persistência.  
- **Entities/Models:** representam as estruturas de dados do domínio.

Tecnologias empregadas:
- Spring Boot  
- Spring Data JPA  
- H2/PostgreSQL (dependendo do ambiente)  
- Spring Web

### 🎨 Views com Thymeleaf
A camada de apresentação do backend utiliza **Thymeleaf** para renderizar páginas HTML dinâmicas.  
Isso permite:
- Geração server-side de páginas,  
- Integração direta com modelos Java,  
- Reutilização de fragments e templates,  
- Validações e mensagens integradas com Spring.

### 🌐 Frontend — Next.js
O projeto pode complementar o backend com um frontend moderno baseado em **Next.js**, utilizado para criar interfaces reativas e componentes reutilizáveis.

Principais características:
- Renderização híbrida (**SSR** e **SSG**) para melhorar SEO e performance.  
- Componentização com React.  
- Roteamento otimizado.  
- Estilização modular (CSS Modules, Tailwind, etc., se utilizado).  
- Comunicação com o backend via APIs REST.

### 🔄 Integração entre Camadas
- O **backend expõe endpoints REST** que podem ser consumidos pelo Next.js.  
- Alternativamente, parte das páginas podem ser servidas diretamente via Thymeleaf.  
- Essa dualidade permite ao projeto combinar **renderização server-side clássica** (Thymeleaf) e **componentização moderna** (Next.js).

> [!TIP]
> Destaque se o uso combinado de Thymeleaf e Next.js trouxe benefícios (flexibilidade, compatibilidade) ou dificuldades (duplicidade de camadas de view, esforço maior de manutenção).

---

## 🗂️ 4. Organização do GitHub e Fluxo de Trabalho Colaborativo

Avalie as práticas de Engenharia de Software Colaborativa do projeto, focando na clareza, padronização e rastreabilidade.

### 4.1. Estrutura do Repositório e Documentação
* **Estrutura de Pastas:** A organização dos diretórios (`src`, `config`, `frontend`, etc.) segue as convenções padrão do Spring Boot/Next.js? A separação de Backend e Frontend é clara e lógica?
* **Documentação Essencial:** O arquivo `README.md` é completo e útil? Verifique se ele contém:
    * **Descrição** do projeto e suas funcionalidades.
    * **Requisitos** de ambiente (Java, Node, versões específicas, etc.).
    * **Instruções claras de inicialização** (incluindo banco de dados e comandos de *build*).

### 4.2. Gerenciamento de Tarefas (Issues)
* **Uso de Issues:** O grupo utilizou o sistema de Issues para:
    * Rastrear bugs, funcionalidades e tarefas?
    * Gerenciar o backlog e priorizar o trabalho (com *labels* ou *milestones*)?
    * A descrição das Issues é suficientemente detalhada para guiar o desenvolvimento?

### 4.3. Fluxo de Trabalho (Pull Requests e Branches)
* **Branches:** O fluxo de *branching* é claro (e.g., usa *main/master*, *develop* e *feature branches*)?
* **Pull Requests (PRs):** Qual a qualidade e o uso dos Pull Requests?
    * Possuem **descrições** detalhadas e explicam o propósito das mudanças?
    * Estão **vinculados** às Issues correspondentes?
    * Foram usados para **Revisão de Código (Code Review)** antes do *merge*?

### 4.4. Padrões de Commits e Versionamento
* **Padrão de Commits:** Existe um padrão de mensagens de commit (e.g., usando prefixos como `feat:`, `fix:`, `refactor:`)?
    > **Sugestão:** Se não houver, mencione que a adoção de [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) melhoraria drasticamente a rastreabilidade e a geração automática de *changelogs*.
* **Versionamento (Releases/Tags):** O projeto utiliza **Tags** ou **Releases** para marcar versões estáveis ou marcos importantes (e.g., `v1.0.0`)?

---

## 🖥️ 5. Dificuldade para Configuração do Ambiente

Descreva aqui como foi a experiência do grupo ao configurar e executar o projeto com Spring Boot.

### 5.1. Requisitos de Linguagem e Ferramentas de Build
* **Versão do Java:** O projeto exige **Java 17**, mas essa informação não estava documentada no README? _(Exemplo: O projeto exigia Java 17, mas o grupo tentou rodar com Java 11. Descreva a correção.)_
* **Ferramenta de Build:** O **Maven**/Gradle não baixou todas as dependências automaticamente devido a versões incompatíveis? A aplicação falhava por falta do plugin correto (ex.: `spring-boot-maven-plugin` ausente no `pom.xml`)?
* **Dependências e Compatibilidade:** Dependências do **Thymeleaf** ou módulos Web não estavam declaradas corretamente, impedindo o *build*?

### 5.2. Configuração de Persistência e Variáveis de Ambiente
* **Arquivos de Configuração:** O projeto não iniciava porque o arquivo `application.properties` ou `application.yml` não estava configurado (ex.: variáveis de banco de dados ausentes)?
* **Variáveis de Ambiente:** O projeto dependia de variáveis de ambiente cruciais (`API_KEY`, `SERVER_PORT`, `DB_PASSWORD`, etc.) que **não estavam documentadas** no README ou que eram difíceis de configurar?  _(Descreva a variável e onde ela foi definida/corrigida)_
* **Banco de Dados Local:** O banco **PostgreSQL** precisava ser criado manualmente, mas isso não estava explicado? O container Docker do banco não iniciava devido à falta de instruções ou variáveis de ambiente?

### 5.3. Aspectos a Analisar e Soluções Aplicadas
* **Aspectos a Analisar:** Versão do Java utilizada, Ferramenta de build (Maven ou Gradle), Dependências e compatibilidades, Banco de Dados e forma de inicialização (local, Docker, memória), Arquivos de Configuração necessários, Passos para subir backend + frontend (caso use Next.js).
* **Passos para Subir:** Quais passos extras foram necessários para subir o backend e/ou frontend (caso use Next.js)?
* **Soluções Aplicadas:** Descreva **detalhadamente cada passo necessário para a execução**, incluindo como o grupo corrigiu os problemas de documentação, versões ou configuração.

> [!WARNING]
> Caso o grupo tenha enfrentado erros por falta de documentação, versões inconsistentes ou dependências quebradas, **descreva detalhadamente cada passo necessário para a execução**, incluindo como corrigiram os problemas.

---

## 🔎 6. Análise de Qualidade do Código e Testes

### 6.1. Design e Princípios SOLID
* **Coesão e Acoplamento:** Existem classes com muitas responsabilidades (**God Class**)? O acoplamento entre módulos é alto?
* **Princípios SOLID Violados (Se aplicável):** (Ex: O Controller faz validação e acesso ao banco, violando o **S**ingle Responsibility Principle - SRP).
* **Code Smells:** Identifique a presença de **Long Method** (métodos com muitas linhas) ou **Duplicated Code** fora das áreas que vocês refatoraram.
    * **Evidência/Exemplo:** _(Cite o arquivo e a linha onde um problema foi encontrado)_

### 6.2. Testabilidade e Cobertura
* **Presença de Testes:** O projeto possui testes (Unitários, Integração, End-to-End)?
* **Cobertura (Estimada/Medida):** Qual é a cobertura de código (Se houver ferramenta para medir, cite o percentual)?
    * **Qualidade dos Testes:** Os testes focam na lógica de negócio (camada Service) ou apenas na integração do sistema (testando o Controller e persistência)?
    * **Mocking:** O uso de *mocks* e *stubs* é adequado para isolar as dependências e testar unidades de código?
    * **Evidência/Exemplo:** _(Cite o diretório de testes (`src/test`) e mencione a ausência ou presença de testes para uma funcionalidade crítica)_

### 6.3. Segurança e Tratamento de Erros (OWASP Top 10)
Avalie o projeto com base em vulnerabilidades comuns, como as citadas no OWASP Top 10. 

* **Validação de Entrada (Input Validation):** Existem validações rigorosas em todos os dados recebidos (DTOs)? Há sanitização de *input* para prevenir **Injeção de SQL/Scripting (XSS)**?
* **Tratamento de Credenciais:** O tratamento de senhas é seguro (uso de `BCrypt` ou algoritmo forte)? As credenciais de acesso ao banco estão expostas no código ou em *logs*?
* **Tratamento de Exceções:** O tratamento de exceções é adequado? A aplicação retorna mensagens de erro genéricas (status 500) ou expõe detalhes do erro e da arquitetura (vazamento de informações)?
    * **Evidência/Exemplo:** _(Cite um ponto fraco, Ex: "O campo de busca não tem sanitização, potencial XSS" ou "As senhas não estão criptografadas")_

---

## 🚀 7. Sugestões de Melhorias

Liste **entre 5 e 7 sugestões claras e prioritárias** para os autores do projeto, baseadas nas análises acima (Seções 3, 4, 5 e 6).

1. **Melhoria da Documentação:** Criar um arquivo `CONTRIBUTING.md`, adicionar instruções completas de configuração do ambiente (Java, Maven, variáveis de ambiente e scripts de inicialização) e incluir uma seção de troubleshooting no `README.md`.
2. **Padronização do Código:** Adotar **Conventional Commits**, habilitar ferramentas como **Spotless**, **Checkstyle** ou **SonarLint** para manter consistência e detectar code smells automaticamente.
3. **Testes Automatizados:** Implementar testes unitários na camada de **Service** e testes de integração com **Spring Boot Test**, buscando ao menos **80% de cobertura** nas funcionalidades principais.
4. **Melhorias de Segurança:** Utilizar **Spring Validation** para validação de DTOs, adicionar tratamento centralizado de erros com `@ControllerAdvice`, remover informações sensíveis de logs e revisar dependências vulneráveis usando `mvn dependency-check`.
5. **Organização do Repositório:** Padronizar a estrutura de pastas, adicionar templates de Pull Request e Issues, além de configurar Branch Protection para `main`.
6. **Performance e Otimização:** Analisar pontos de gargalo no carregamento de dados, reduzir consultas redundantes, aplicar cache quando adequado e revisar métodos que fazem processamento excessivo no backend.
7. **Automação e CI/CD:** Criar uma pipeline no **GitHub Actions** para rodar testes, verificar estilo, validar segurança das dependências e realizar build automático a cada PR.

---

## 🔧 8. Refatorações Propostas (3 partes do código)

Cada refatoração deve conter:
1. **Arquivo e localização**  
2. **Código antes**  
3. **Código depois**  
4. **Tipo de refatoração aplicada**  
5. **Justificativa técnica**  
6. **Link do Pull Request**

---

### 1️⃣ Refatoração 1 – Extração de Método (Extract Method)

**Arquivo:** `src/main/java/com/example/service/UserService.java`  
**Pull Request:** https://github.com/exemplo/projeto/pull/1  

#### 🔴 Antes
```java
public User createUser(UserDTO dto) {
    if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
        throw new IllegalArgumentException("Email inválido");
    }
    if (dto.getPassword() == null || dto.getPassword().length() < 8) {
        throw new IllegalArgumentException("Senha fraca");
    }

    User user = new User(dto.getEmail(), dto.getPassword());
    return userRepository.save(user);
}
```

#### 🟢 Depois
```java
private void validateUserDTO(UserDTO dto) {
    if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
        throw new IllegalArgumentException("Email inválido");
    }
    if (dto.getPassword() == null || dto.getPassword().length() < 8) {
        throw new IllegalArgumentException("Senha fraca");
    }
}

public User createUser(UserDTO dto) {
    validateUserDTO(dto);
    User user = new User(dto.getEmail(), dto.getPassword());
    return userRepository.save(user);
}
```

#### ✔ Tipo de refatoração aplicada
- **Extract Method**  

#### 📝 Justificativa
Melhora a clareza, responsabilidade única e testabilidade.

---

### 2️⃣ Refatoração 2 – Remoção de Código Duplicado

**Arquivo:** `src/main/java/com/example/util/StringUtils.java`  
**Pull Request:** https://github.com/exemplo/projeto/pull/2  

#### 🔴 Antes
```java
public String capitalizeName(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
}

public String capitalizeCity(String city) {
    return city.substring(0, 1).toUpperCase() + city.substring(1);
}

```

#### 🟢 Depois
```java
public String capitalize(String text) {
    return text.substring(0, 1).toUpperCase() + text.substring(1);
}

public String capitalizeName(String name) {
    return capitalize(name);
}

public String capitalizeCity(String city) {
    return capitalize(city);
}
```

#### ✔ Tipo de refatoração aplicada
- **Replace Duplicated Code with Method**

#### 📝 Justificativa
Elimina duplicação e facilita manutenção.

---

### 3️⃣ Refatoração 3 – Melhoria de Nomes (Rename)

**Arquivo:** `src/main/java/com/example/controller/ProductController.java`  
**Pull Request:** https://github.com/exemplo/projeto/pull/3  

#### 🔴 Antes
```java
@PostMapping("/x")
public void x(@RequestBody Product p) {
    if (p == null) return;
    service.save(p);
}
```

#### 🟢 Depois
```java
@PostMapping("/save")
public void saveProduct(@RequestBody Product product) {
    if (product == null) return;
    service.save(product);
}
```

#### ✔ Tipo de refatoração aplicada
- **Rename Method / Rename Parameter**

#### 📝 Justificativa
Melhora a clareza e expressividade do código.

---

## 9. 📄 Conclusão

A análise crítica permitiu identificar aspectos importantes relacionados à **arquitetura**, **qualidade do código** e **organização geral do projeto**. A investigação detalhada evidenciou pontos positivos, como boas escolhas tecnológicas, mas também expôs problemas que comprometem a **manutenibilidade**, **segurança** e **performance** do sistema.

As refatorações propostas tiveram impacto direto na **melhoria da legibilidade**, **redução de duplicidade**, **aumento da coesão** e **clareza das responsabilidades**. Além disso, práticas recomendadas foram introduzidas para tornar o código mais robusto, mitigando riscos como:
- validações inconsistentes;
- trechos suscetíveis a falhas de segurança (ex.: falta de sanitização ou verificações frágeis);
- métodos extensos e difíceis de testar;
- rotinas com potencial para degradação de desempenho.

A análise também mostrou que melhorias adicionais podem ser adotadas, como:
- padronização da comunicação via **Conventional Commits**;
- reforço das práticas de **segurança** (validações mais estruturadas, tratamento de exceções, prevenção de vulnerabilidades comuns);
- otimizações de **performance**, incluindo redução de operações redundantes e melhor organização das responsabilidades do backend;
- ampliação e atualização da **documentação**, incluindo requisitos de ambiente e instruções claras de execução;
- tratamento cuidadoso das dependências utilizadas no projeto.

Por fim, o processo reforçou a importância da **refatoração contínua**, **revisão estruturada de código** e **boas práticas de engenharia**, fundamentais para manter um software sustentável, escalável e seguro ao longo de seu ciclo de vida.

---

## 10. 📚 Referências
- Revisando alterações em Pull Requests:  
  https://docs.github.com/pt/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/commenting-on-a-pull-request

- Guia oficial de **Conventional Commits**:  
  https://www.conventionalcommits.org/pt-br/v1.0.0/

- Documentação do Spring Boot (boas práticas, segurança e performance):  
  https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

- OWASP Cheat Sheets (segurança em aplicações web):  
  https://cheatsheetseries.owasp.org/

---
