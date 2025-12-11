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
- **Link do repositório:** _https://github.com/exemplo/projeto_  
- **Integrantes do outro grupo:** _Nomes_  
- **Pull requests submetidos:**  
  - **Aluno 1 — Refatoração 1:** https://github.com/exemplo/projeto/pull/1  
  - **Aluno 2 — Refatoração 2:** https://github.com/exemplo/projeto/pull/2  
  - **Aluno 3 — Refatoração 3:** https://github.com/exemplo/projeto/pull/3  
  - **Aluno 4 — Refatoração 4:** https://github.com/exemplo/projeto/pull/4  

> [!NOTE]
> Os links acima são apenas exemplos. Substitua pelos PRs reais do seu grupo.

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

## 🗂️ 4. Organização do GitHub

Avalie:
- Estrutura de pastas  
- Uso de issues  
- Uso de pull requests  
- Versionamento  
- Padrões de commits  
- Releases/Tags (se existirem)

Exemplo de comentário:
> O repositório é organizado, mas falta um padrão de mensagens de commit. Seria ideal usar [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

---

## 🖥️ 5. Dificuldade para Configuração do Ambiente

Descreva aqui como foi a experiência do grupo ao configurar e executar o projeto com Spring Boot.

Exemplos:
- O projeto exige **Java 17**, mas essa informação não estava documentada no README.  
- O **Maven**/Gradle não baixou todas as dependências automaticamente devido a versões incompatíveis.  
- O projeto não iniciava porque o arquivo `application.properties` ou `application.yml` não estava configurado (ex.: variáveis de banco de dados ausentes).  
- O banco **PostgreSQL** precisava ser criado manualmente, mas isso não estava explicado.  
- O container Docker do banco não iniciava devido à falta de instruções ou variáveis de ambiente.  
- A aplicação falhava por falta do plugin correto (ex.: `spring-boot-maven-plugin` ausente no `pom.xml`).  
- Dependências do **Thymeleaf** ou módulos Web não estavam declaradas corretamente, impedindo o build.  

Aspectos a analisar:
- Versão do Java utilizada.  
- Ferramenta de build (Maven ou Gradle).  
- Dependências e compatibilidades.  
- Banco de dados e forma de inicialização (local, Docker, memória).  
- Arquivos de configuração necessários.  
- Passos para subir backend + frontend (caso use Next.js).

> [!WARNING]
> Caso o grupo tenha enfrentado erros por falta de documentação, versões inconsistentes ou dependências quebradas, **descreva detalhadamente cada passo necessário para a execução**, incluindo como corrigiram os problemas.

---

## 🚀 6. Sugestões de Melhorias

Liste entre 3 e 7 sugestões claras para os autores do projeto.

Exemplos:
- Adicionar testes automatizados.  
- Melhorar a documentação do ambiente.  
- Padronizar pastas do backend.  
- Adicionar pipeline CI/CD com GitHub Actions.  

---

## 🔧 7. Refatorações Propostas (3 partes do código)

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
**Pull Request:** https://github.com/exemplo/projeto/pull/123  

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
**Pull Request:** https://github.com/exemplo/projeto/pull/124  

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
**Pull Request:** https://github.com/exemplo/projeto/pull/125  

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

## 8. 📄 Conclusão

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

## 9. 📚 Referências
- Revisando alterações em Pull Requests:  
  https://docs.github.com/pt/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/commenting-on-a-pull-request

- Guia oficial de **Conventional Commits**:  
  https://www.conventionalcommits.org/pt-br/v1.0.0/

- Documentação do Spring Boot (boas práticas, segurança e performance):  
  https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

- OWASP Cheat Sheets (segurança em aplicações web):  
  https://cheatsheetseries.owasp.org/

---
