# 🚀 IBGE News API com Spring Boot + MongoDB

## 📌 Descrição
Aplicação desenvolvida com **Spring Boot** que consome a API pública do IBGE para obter **notícias e releases**, armazenando os dados em um banco **MongoDB**.

A aplicação expõe endpoints REST que permitem consultar os dados diretamente do IBGE e persisti-los localmente para futuras consultas.

---

# 🧰 Tecnologias Utilizadas
- ☕ Java 17
- 🌱 Spring Boot
- 🌐 Spring Web
- 🍃 Spring Data MongoDB
- 🗄️ MongoDB

---

# 📦 Dependências do Projeto
- `spring-boot-starter-web`
- `spring-boot-starter-test`
- `spring-boot-starter-data-mongodb`

```xml
	<dependencies>
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
	</dependencies>
```

---

# 📁 Estrutura do Projeto

```
com.example.RestAPI
│
├── application
│   └── RestApiApplication.java
│
├── controller
│   └── IBGEController.java
│
├── service
│   └── IBGEService.java
│
├── repository
│   ├── NewsRepository.java
│   └── ReleasesRepository.java
│
├── model
│   ├── NewsEntity.java
│   └── ReleasesEntity.java
│
└── resources
    └── application.properties
```

---

# 🔗 Endpoints da API

| Endpoint                | Descrição                        |
|------------------------|-----------------------------------|
| `/noticias`            | Retorna notícias do IBGE          |
| `/releases`            | Retorna releases                  |
| `/noticiasereleases`   | Retorna notícias e releases juntos|

---

# ⚙️ Configuração do MongoDB

No arquivo `application.properties`:

```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=bd-news
```

---

# 🛠️ Instalação do MongoDB

## 🪟 Windows
1. Acesse: https://www.mongodb.com/try/download/community  
2. Baixe o instalador (.msi)  
3. Execute o instalador  
4. Marque a opção **"Install MongoDB as a Service"**  
5. Finalize a instalação  
6. O MongoDB iniciará automaticamente  

---

## 🐧 Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install -y mongodb
sudo systemctl start mongodb
sudo systemctl enable mongodb
```

---

## 🍎 Mac (Homebrew)

```bash
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

---

# ▶️ Como Executar o Projeto

## 1. Clonar o repositório
```bash
git clone <URL_DO_REPOSITORIO>
```

## 2. Acessar a pasta
```bash
cd nome-do-projeto
```

## 3. Executar a aplicação
```bash
mvn spring-boot:run
```

Ou execute diretamente pela IDE a classe:

```bash
RestApiApplication.java
```

---

# 📡 Como funciona a aplicação

1. A aplicação faz requisições HTTP para a API do IBGE  
2. Os dados são retornados em formato JSON  
3. Os dados são armazenados no MongoDB  
4. Os endpoints expõem os dados para consumo  

---

# 🔗 Links Úteis

## 📚 Documentações
- IBGE API: https://servicodados.ibge.gov.br/api/docs  
- MongoDB Docs: https://www.mongodb.com/docs/  
- Spring Boot: https://spring.io/projects/spring-boot  

---

# 🛡 Licença

Este projeto está sob a licença **MIT**.
