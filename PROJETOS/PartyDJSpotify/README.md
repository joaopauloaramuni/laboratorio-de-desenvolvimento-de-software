# Projeto PartyDJSpotify

PartyDJSpotify é um sistema que integra o Spotify, permitindo a criação de um DJ automatizado para festas. Ele utiliza a API do Spotify para manipular faixas, álbuns, playlists e a fila de reprodução, além de registrar votos nas músicas e controlar a reprodução.

## Configurações

As configurações para o Spotify devem ser adicionadas no arquivo `application.properties` conforme abaixo:

```properties
spring.application.name=PartyDJSpotify
spotify.api.url=https://api.spotify.com/v1
spotify.token.url=https://accounts.spotify.com/api/token
spotify.auth.url=https://accounts.spotify.com/authorize
spotify.redirect.uri=http://localhost:8080/party/callback
spotify.client.id=seuclientidaqui
spotify.client.secret=seuclientsecretaqui
spotify.album.id=albumid
spotify.playlist.id=playlistid
```

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes e classes:

- **application**: PartyDJSpotifyApplication
- **config**: SpotifyConfig
- **context**: AuthorizationCodeContext, TokenContext
- **controller**: PartyDJSpotifyController, AuthController
- **service**: PartyDJSpotifyService, TokenService, VoteService

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── PartyDJSpotify
│   │   │               ├── application
│   │   │               │   └── PartyDJSpotifyApplication.java
│   │   │               ├── config
│   │   │               │   └── SpotifyConfig.java
│   │   │               ├── context
│   │   │               │   ├── AuthorizationCodeContext.java
│   │   │               │   └── TokenContext.java
│   │   │               ├── controller
│   │   │               │   ├── PartyDJSpotifyController.java
│   │   │               │   └── AuthController.java
│   │   │               ├── service
│   │   │               │   ├── PartyDJSpotifyService.java
│   │   │               │   ├── TokenService.java
│   │   │               │   └── VoteService.java
│   │   └── resources
│   │       └── application.properties
```

## Dependências

Inclua a seguinte dependência no arquivo `pom.xml`:

```xml
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20240303</version>
</dependency>
```

## Requisições

As requisições podem ser encontradas na pasta `requests` na raiz do projeto.

## Endpoints

- `GET /party/authInsomnia`: http://localhost:8080/party/authInsomnia
- `GET /party/authRedirect`: http://localhost:8080/party/authRedirect
- `GET /party/callback`: http://localhost:8080/party/callback?code=... (@RequestParam("code"))
- `GET /party/me`: http://localhost:8080/party/me
- `GET /party/current-track`: http://localhost:8080/party/current-track
- `GET /party/album/tracks`: http://localhost:8080/party/album/tracks
- `GET /party/queue`: http://localhost:8080/party/queue
- `POST /party/queue`: http://localhost:8080/party/queue (@RequestParam String trackUri)
- `POST /party/queue/remove`: http://localhost:8080/party/queue/remove
- `POST /party/vote`: http://localhost:8080/party/vote (@RequestParam String trackId)
- `POST /party/next-track`: http://localhost:8080/party/next-track
- `POST /party/playback/stop`: http://localhost:8080/party/playback/stop
- `POST /party/playback/start`: http://localhost:8080/party/playback/start

## Métodos do PartyDJSpotifyService

- `public String getCurrentPlayingTrack()`
- `public String getCurrentUserProfile()`
- `public String getAlbumTracks()`
- `public String skipToNextTrack()`
- `public String voteTrack(String trackId)`
- `public String addTrackToQueue(String trackUri)`
- `public String removeTrackFromQueue()`
- `public String getQueueTracks()`
- `public String stopPlayback()`
- `public String startPlayback()`

## Métodos do TokenService

- `public String getAuthorizationUrl()`
- `public String ensureAccessTokenIsValid()`
- `public void renewAccessToken(String code)`

## Métodos do VoteService

- `public String registerVote(String trackId)`
- `public int getVoteCount(String trackId)`
- `public List<String> listVotes()`

## Informações importantes

- **Client ID** e **Client Secret**: Credenciais do app Spotify necessárias para autenticação.
- **Authorization Url**: URL de autorização fornecida pelo Spotify para autenticação.
- **Token**: Token de acesso para uso das APIs do Spotify, com possibilidade de renovação.
- **Code**: Código de autorização obtido no fluxo OAuth2.

Ao criar um app no Spotify, insira o URI de redirecionamento conforme abaixo:

**Redirect URIs**:
http://localhost:8080/party/callback

## Documentação

- [Documentação oficial da API do Spotify](https://developer.spotify.com/documentation/web-api/tutorials/getting-started)
- [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)

## Licença

Este projeto está licenciado sob a Licença MIT.
