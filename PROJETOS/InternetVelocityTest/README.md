# Projeto InternetVelocityTest

## Descrição
O projeto InternetVelocityTest é uma aplicação que permite realizar testes de velocidade de download e upload utilizando a biblioteca `jspeedtest`. A aplicação monitora o progresso do teste e fornece relatórios detalhados sobre o desempenho da conexão à internet.

## Dependências
O projeto utiliza a seguinte dependência no arquivo `pom.xml`:

```xml
<dependency>
    <groupId>fr.bmartel</groupId>
    <artifactId>jspeedtest</artifactId>
    <version>1.32.1</version>
</dependency>
```

## Estrutura do projeto
- **application**: `InternetVelocityTestApplication.java`
- **controller**: `SpeedTestController.java`
- **service**: `SpeedTestService.java`

## Sobre a jspeedtest
A biblioteca jspeedtest é um cliente de teste de velocidade para Java/Android com suporte a HTTP e FTP. As funcionalidades incluem:
- Teste de velocidade de download
- Teste de velocidade de upload
- Monitoramento do progresso de download/upload
- Hostname/port/uri configurável (usuário e senha para FTP)
- Timeout de socket e tamanho de chunk configuráveis
- Armazenamento de arquivo de upload configurável

### Bibliotecas externas da jspeedtest
- [http-endec](https://github.com/bertrandmartel/http-endec)
- [http-endec - mvnrepository](https://mvnrepository.com/artifact/fr.bmartel/http-endec/1.04)
- [Apache Commons Net](https://commons.apache.org/proper/commons-net/)

## Links úteis
- [Central de artefatos Sonatype](https://central.sonatype.com/artifact/fr.bmartel/jspeedtest)
- [Maven Repository](https://mvnrepository.com/artifact/fr.bmartel/jspeedtest/1.32.1)
- [Repositório GitHub](https://github.com/bertrandmartel/speed-test-lib)

## Arquivo de teste
Para realizar os testes, foi escolhido um arquivo de vídeo WMV de 25 MB.

Link do arquivo de teste: [25MB-WMV.wmv](https://sampletestfile.com/wp-content/uploads/2023/07/25MB-WMV.wmv)

## Como testar a aplicação
Para testar a aplicação, utilize os seguintes endpoints:

- **Download Speed Test**: http://localhost:8080/speed-test/download
- **Upload Speed Test**: http://localhost:8080/speed-test/upload

## Endpoints
- `GET /speed-test/download`
- `GET /speed-test/upload`

## Métodos do SpeedTestService
Os métodos disponíveis no `SpeedTestService` são:
- `public void startDownloadSpeedTest()`: Inicia o teste de velocidade de download.
- `public void startUploadSpeedTest()`: Inicia o teste de velocidade de upload.
- `startSpeedTest(boolean isDownload)`: Método auxiliar para iniciar o teste de velocidade, determinando se é download ou upload.
- `logCompletion(SpeedTestReport report, boolean isDownload)`: Registra a conclusão do teste, salvando o relatório.
- `logProgress(SpeedTestReport report, float percent, boolean isDownload)`: Registra o progresso do teste, atualizando o relatório.
- `String formatDuration(double seconds)`: Formata a duração do teste em um formato legível.

## Licença

Este projeto é de código aberto e está licenciado sob a MIT License. Sinta-se livre para usá-lo e modificá-lo conforme necessário.
