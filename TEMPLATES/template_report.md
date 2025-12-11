---

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
- **Link do repositório:** _https://github.com/..._  
- **Integrantes do grupo:** _Nomes_  
- **Pull requests submetidos:**  
  - Refatoração 1: https://github.com/exemplo/projeto/pull/123  
  - Refatoração 2: https://github.com/exemplo/projeto/pull/124  
  - Refatoração 3: https://github.com/exemplo/projeto/pull/125  

> [!NOTE]
> Os links acima são apenas exemplos. Substitua pelos PRs reais do seu grupo.

---

## 🧱 3. Arquitetura e Tecnologias Utilizadas

Descreva aqui a arquitetura analisada.

Exemplo:
- O projeto segue uma arquitetura **MVC**, separando responsabilidades entre _Model_, _View_ e _Controller_.  
- A API é construída com **FastAPI** e utiliza **PostgreSQL** como banco de dados.  
- O frontend é feito em **React**.

> [!TIP]
> Explique se as decisões arquiteturais ajudam ou atrapalham a evolução do sistema.

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
> O repositório é organizado, mas falta um padrão de mensagens de commit. Seria ideal usar Conventional Commits.

---

## 🖥️ 5. Dificuldade para Configuração do Ambiente

Explique como foi a experiência do grupo ao tentar rodar o projeto.

Exemplo:
- O projeto exige Python 3.12, mas essa informação não estava no README.  
- O script de inicialização falha caso o arquivo `.env` não exista.

> [!WARNING]
> Se a configuração exigiu passos não documentados, detalhe-os aqui.

---

## 🚀 6. Sugestões de Melhorias

Liste entre 3 e 7 sugestões claras para os autores do projeto.

Exemplos:
- Adicionar testes automatizados.  
- Melhorar a documentação do ambiente.  
- Padronizar pastas do backend.  
- Adicionar pipeline CI/CD com GitHub Actions.  

---

## 7. Refatorações Propostas (3 partes do código)

Cada refatoração deve conter:
1. **Arquivo e localização**  
2. **Código antes**  
3. **Código depois**  
4. **Tipo de refatoração aplicada**  
5. **Justificativa técnica**  
6. **Link do Pull Request**

---

### 1️⃣ Refatoração 1 – Extração de Função

**Arquivo:** `services/user_service.py`  
**Pull Request:** https://github.com/exemplo/projeto/pull/123  

#### 🔴 Antes
```python
def create_user(data):
    if "email" not in data or "@" not in data["email"]:
        raise Exception("Email inválido")
    if len(data.get("password", "")) < 8:
        raise Exception("Senha fraca")

    user = User(data["email"], data["password"])
    database.save(user)
    return user
```

#### 🟢 Depois
```python
def validate_user_data(data):
    if "email" not in data or "@" not in data["email"]:
        raise ValueError("Email inválido")
    if len(data.get("password", "")) < 8:
        raise ValueError("Senha fraca")

def create_user(data):
    validate_user_data(data)
    user = User(data["email"], data["password"])
    database.save(user)
    return user
```

#### ✔ Tipo de refatoração aplicada
- **Extract Function**  
- **Melhoria de mensagens de erro**

#### 📝 Justificativa
Separar a validação em uma função dedicada melhora clareza, testabilidade e reuso.

---

### 2️⃣ Refatoração 2 – Substituição de Código Duplicado

**Arquivo:** `utils/string_utils.js`  
**Pull Request:** https://github.com/exemplo/projeto/pull/124  

#### 🔴 Antes
```javascript
function capitalizeName(name) {
  return name.charAt(0).toUpperCase() + name.slice(1);
}

function capitalizeCity(city) {
  return city.charAt(0).toUpperCase() + city.slice(1);
}
```

#### 🟢 Depois
```javascript
function capitalize(text) {
  return text.charAt(0).toUpperCase() + text.slice(1);
}

const capitalizeName = capitalize;
const capitalizeCity = capitalize;
```

#### ✔ Tipo de refatoração aplicada
- **Generalização de função (Replace Duplicated Code with Parameter)**

#### 📝 Justificativa
Evita duplicação, reduz riscos de manutenção e padroniza comportamento.

---

### 3️⃣ Refatoração 3 – Melhoria de Nomes e Clareza

**Arquivo:** `controllers/product_controller.dart`  
**Pull Request:** https://github.com/exemplo/projeto/pull/125  

#### 🔴 Antes
```dart
void x(p) {
  if (p == null) return;
  db.save(p);
}
```

#### 🟢 Depois
```dart
void saveProduct(Product product) {
  if (product == null) return;
  db.save(product);
}
```

#### ✔ Tipo de refatoração aplicada
- **Rename Method / Rename Variable**

#### 📝 Justificativa
Nomes inadequados prejudicavam legibilidade e entendimento do papel da função.

---

## 8. 📄 Conclusão

Resuma:
- Impacto da análise crítica  
- Benefícios das refatorações  
- Sugestões mais relevantes  
- Principais problemas encontrados  

---

## 9. 📚 Referências
- Revisando alterações em Pull Requests:  
  https://docs.github.com/pt/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/commenting-on-a-pull-request

---
