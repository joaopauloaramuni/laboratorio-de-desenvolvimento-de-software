# 🤝 Guia de Contribuição ✨

Obrigado pelo seu interesse em contribuir com o **[Nome do Seu Projeto]**! Sua ajuda é muito importante para nós.

Este documento descreve as diretrizes, o fluxo de trabalho e o estilo de código que esperamos dos colaboradores.

---

## 🚀 Fluxo de Contribuição

Para garantir a organização e rastreabilidade das mudanças, pedimos que siga este fluxo:

1.  **Abra uma Issue (Opcional, mas Recomendado):** Antes de começar a codificar uma *feature* grande ou complexa, abra uma [Issue](<Link para a seção Issues>) para discutir a mudança e garantir que ela se alinhe com os objetivos do projeto.
2.  **Faça um Fork:** Crie uma cópia (*fork*) deste repositório para sua conta pessoal no GitHub.
3.  **Clone o Repositório:**
    ```bash
    git clone https://github.com/seu-usuario/nome-do-projeto.git
    cd nome-do-projeto
    ```
4.  **Crie uma Branch:** Crie uma *branch* nova e descritiva para sua contribuição.
    ```bash
    git checkout -b tipo/nome-da-sua-contribuicao
    # Exemplos: feature/login-social, bugfix/corrige-filtro-api
    ```
5.  **Codifique e Teste:** Implemente suas alterações e **execute os testes** (`npm run test`) para garantir que tudo esteja funcionando corretamente.
6.  **Commit:** Faça *commits* seguindo rigorosamente a **Convenção de Mensagens** (veja a próxima seção).
7.  **Push e Pull Request (PR):** Envie suas alterações (*push*) e abra um Pull Request para a *branch* principal (`main` ou `develop`) deste repositório.

---

## 📝 Convenção de Mensagens de Commit (Conventional Commits)

Nós utilizamos a especificação [**Conventional Commits**](https://www.conventionalcommits.org/en/v1.0.0/) para padronizar o histórico de *commits*. Isso facilita a leitura do histórico e permite a geração automática de *changelogs*.

O formato deve ser: **`<tipo>(<escopo>): <descrição>`**

| Tipo | O que significa? |
| :--- | :--- |
| **`feat`** | Nova *feature* ou funcionalidade. |
| **`fix`** | Correção de um *bug* (erro no código de produção). |
| **`docs`** | Alterações na documentação (README, Wiki, comentários de código). |
| **`style`** | Alterações de formatação (espaços, ponto-e-vírgula, etc.). |
| **`refactor`** | Mudança no código que não corrige bug nem adiciona feature. |
| **`test`** | Adição ou correção de testes. |
| **`chore`** | Tarefas de build, scripts ou atualização de dependências menores. |

**Exemplos de commits válidos:**
* `feat(auth): implementa rota de login com JWT`
* `fix(ui): ajusta padding em dispositivos moveis`
* `docs: atualiza secao de variaveis de ambiente`

---

## 📐 Estilo de Código e Formatação

A consistência visual e estrutural do código é vital. O PR só será aceito se seguir as regras de *linting* e formatação.

1.  **Formatação:** Utilizamos **[Nome do Formatador, Ex: Prettier]** para garantir um estilo uniforme.
2.  **Linting:** Utilizamos **[Nome do Linter, Ex: ESLint]** para identificar padrões problemáticos de código.

**Antes de submeter o PR, execute os comandos de verificação:**

```bash
# Roda a formatação e corrige erros (se o script existir)
npm run format
# Roda o linter e corrige erros (se o script existir)
npm run lint:fix
```

---

## ✅ Checklist do Pull Request (PR)

Antes de abrir seu PR, revise os seguintes pontos:

* [ ] A **mensagem de *commit*** segue a **Convenção de Commits**.
* [ ] O código foi **formatado** e **passou no *linter*** sem erros.
* [ ] Se a mudança é uma correção de *bug*, você adicionou um **teste de regressão**?
* [ ] Se a mudança é uma nova *feature*, ela está **coberta por testes**?
* [ ] A documentação relevante (README, *code comments* ou *wiki*) foi **atualizada**.
* [ ] O PR aponta para a *branch* correta (`main` ou `develop`).
