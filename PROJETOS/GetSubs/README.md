# GetSubs com Spring Boot

Projeto para gerenciamento de legendas com API do [OpenSubtitles](https://www.opensubtitles.com/pt-BR), implementado em Spring Boot. Este README contém a documentação para acessar os endpoints da aplicação.

## Estrutura do projeto
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── GetSubs
│   │   │               ├── application
│   │   │               │   └── GetSubsApplication.java
│   │   │               ├── config
│   │   │               │   └── OpenSubtitlesConfig.java
│   │   │               ├── controller
│   │   │               │   └── GetSubsController.java
│   │   │               ├── dto
│   │   │               │   ├── DownloadByMovieNameRequestDTO.java
│   │   │               │   ├── DownloadRequestDTO.java
│   │   │               │   ├── LoginRequestDTO.java
│   │   │               │   └── SearchRequestDTO.java
│   │   │               ├── model
│   │   │               │   ├── Download.java
│   │   │               │   ├── File.java
│   │   │               │   └── Subtitle.java
│   │   │               └── service
│   │   │                   └── GetSubsService.java
│   │   └── resources
│   │       └── application.properties
```

## application.properties

```properties
spring.application.name=GetSubs
opensubtitles.apiUrl=https://api.opensubtitles.com/api/v1
opensubtitles.apiKey=suaapikeyaqui
opensubtitles.username=seuusernameaqui
opensubtitles.password=suasenhaaqui
```

## Endpoints

### Autenticação

**Endpoint:** `/api/getsubs/login`  
**Método:** `POST`  
**URL:** `http://localhost:8080/api/getsubs/login`

**Exemplo de request JSON:**
```json
{
  "username": "usuario",
  "password": "senha"
}
```

**Exemplo de Resposta:**
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJJSXJpVVlNc05tcnpIQ0NlQVRPU0IzSmluWlRBTEczUCIsImV4cCI6MTcyOTk1OTQ4NH0.0SgRVWq9POrPJHyTNuas8vxontPwXlGxhDo6pyScLcg
```

### Busca de legendas

**Endpoint:** `/api/getsubs/search`  
**Método:** `POST`  
**URL:** `http://localhost:8080/api/getsubs/search`

**Exemplo de request JSON:**
```json
{
  "query": "1997-titanic",
  "languages": "pt-br"
}
```

**Exemplo de resposta:**
```json
[
	{
		"id": "9388940",
		"language": "pt-BR",
		"subtitleId": "9388940",
		"downloadCount": 42,
		"hearingImpaired": false,
		"hd": true,
		"fps": 23.976,
		"release": "Titanic.1997.REMASTERED.2160p.4K.BluRay.x265.10bit.AAC5.1-[YTS.MX]",
		"comments": "Ripada, editada e sincronizada para esta versão do YTS.",
		"url": "https://www.opensubtitles.com/pt-BR/subtitles/legacy/12780428",
		"files": [
			{
				"fileId": 10303731,
				"cdNumber": 1,
				"fileName": "Titanic.1997.REMASTERED.2160p.4K.BluRay.x265.10bit.AAC5.1-[YTS.MX]"
			}
		]
	},
	{
		"id": "7738976",
		"language": "pt-BR",
		"subtitleId": "7738976",
		"downloadCount": 132,
		"hearingImpaired": false,
		"hd": true,
		"fps": 24.0,
		"release": "Titanic.1997.35mm.1080p.AC3.2.0.x265",
		"comments": "Versão para o release Titanic.1997.35mm.1080p.AC3.2.0.x265",
		"url": "https://www.opensubtitles.com/pt-BR/subtitles/legacy/10004413",
		"files": [
			{
				"fileId": 8663791,
				"cdNumber": 1,
				"fileName": "Titanic.1997.35mm.1080p.AC3.2.0"
			}
		]
	}
]
```

### Download de legenda

**Endpoint:** `/api/getsubs/download`  
**Método:** `POST`  
**URL:** `http://localhost:8080/api/getsubs/download`

**Cabeçalho:** `Authorization: Bearer JWT token`  
**Exemplo de Autorização:** 
`Authorization Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJJSXJpVVlNc05tcnpIQ0NlQVRPU0IzSmluWlRBTEczUCIsImV4cCI6MTcyOTk1OTQ4NH0.0SgRVWq9POrPJHyTNuas8vxontPwXlGxhDo6pyScLcg`

**Exemplo de request JSON:**
```json
{
	"fileId": 10303731
}
```

**Exemplo de resposta:**
```json
{
	"link": "https://www.opensubtitles.com/download/D35F5069516828D08B68EC40B01B979EAE90EF33EF2A21F3D52C184F33BEB09959813C13BAE442881A015EBBAB5A01EB0BE0B6488E9E73DC46C3D37C4D99D624C1A31B3BD7147638237C6C564153E5E78FDAB6FC1BF6331065EAE5B3A102BD5C1B2D1B5C0EDD41A85988A77F2D414B18E1F98476C7FBFEE6552323269FB55AF9FC358EA31419FD661C202AA63290F797189AC24BB3383FC22F6380B25D433595E81A59062B848F0F82312244FA50251B5978E705DAA34A4E8349C1346A004A1C13109B86403DDEF81FE624B41D21FFB63CF222CCCE9478FE00F4127A42FADF59182FD3D71C8E88A75323147E754A98A127D33FFC79EC79A40718E934CDDF2D1AC379B338533DDDAF609509F2236631F3C883A7611CE3B408BE032DD3CDAACE941E5D0B4283A2D26C9B80841910984F0916101871F9BD9C6DE0CF10201187923D45D68BAC8F80CAC81491714E91B632A6/subfile/Titanic.1997.REMASTERED.2160p.4K.BluRay.x265.10bit.AAC5.1-%5BYTS.MX%5D.srt",
	"fileName": "Titanic.1997.REMASTERED.2160p.4K.BluRay.x265.10bit.AAC5.1-[YTS.MX].srt",
	"requests": 3,
	"remaining": 17,
	"message": "Your quota will be renewed in 05 hours and 10 minutes (2024-10-25 23:59:58 UTC) ts=1729882140",
	"resetTime": "05 hours and 10 minutes",
	"resetTimeUtc": "2024-10-25T23:59:58.000Z"
}
```

### Download por nome do filme

**Endpoint:** `/api/getsubs/downloadByMovieName`  
**Método:** `POST`  
**URL:** `http://localhost:8080/api/getsubs/downloadByMovieName`

**Exemplo de request JSON:**
```json
{
	"movieName": "1997-titanic"
}
```

**Exemplo de resposta:**
```json
https://www.opensubtitles.com/download/D35F5069516828D08B68EC40B01B979EAE90EF33EF2A21F3D52C184F33BEB09959813C13BAE442881A015EBBAB5A01EB0BE0B6488E9E73DC46C3D37C4D99D624C1A31B3BD7147638237C6C564153E5E78FDAB6FC1BF6331065EAE5B3A102BD5C1B2D1B5C0EDD41A85988A77F2D414B18E1F98476C7FBFEE6552323269FB55AF9FC358EA31419FD661C202AA63290F797189AC24BB3383FC22F6380B25D433595E81A59062B848F0F82312244FA50251B5978E705DAA34A4E8349C1346A004A1C13109B86403DDEF81FE624B41D21FFB63CF222CCCE9478FE00F4127A42FADF59182FD3D71C8E88A75323147E754A98A127D33FFC79EC79A40718E934CDDF2D1AC379B338533DDDAF609509F2236631F3C883A7611CE3B408BE032DD3CDAACE941E5D0B4283A2D26C9B80841910984F0916101871F9BD9C6DE0CF10201187923D45D68BAC8F80CAC81491714E91B632A6/subfile/Titanic.1997.REMASTERED.2160p.4K.BluRay.x265.10bit.AAC5.1-%5BYTS.MX%5D.srt
```

## Exemplo de chamada oficial da API OpenSubtitles
**Endpoint:** `https://api.opensubtitles.com/api/v1/subtitles?query=1997-titanic&languages=pt-br`
**Método:** `POST`  
**Api-Key:** `sua Api-Key` 

### Links Úteis

- [Documentação Oficial da API](https://opensubtitles.stoplight.io/docs/opensubtitles-api/e3750fd63a100-getting-started)
- [Endpoint para Download de Legendas](https://opensubtitles.stoplight.io/docs/opensubtitles-api/6be7f6ae2d918-download)
- [Endpoint para Buscar Legendas](https://opensubtitles.stoplight.io/docs/opensubtitles-api/a172317bd5ccc-search-for-subtitles)
- [Endpoint para Login](https://opensubtitles.stoplight.io/docs/opensubtitles-api/73acf79accc0a-login)
- [Página de Consumidores](https://www.opensubtitles.com/pt-BR/consumers)
- [Página do Filme Titanic (1997)](https://www.opensubtitles.com/pt-BR/movies/1997-titanic)

## Licença

Este projeto está licenciado sob a Licença MIT.
