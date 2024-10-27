# YouTube Video Downloader

Este é um projeto simples para baixar vídeos do YouTube utilizando a biblioteca `yt_dlp`, uma ferramenta poderosa que facilita o download de vídeos na melhor qualidade disponível. Este projeto baixa o vídeo e o salva com o título original do vídeo.

## Estrutura do Projeto
```
application
└── YouTubeVideoDownloaderApplication.java
controller
└── YouTubeVideoDownloaderController.java
service
└── YouTubeVideoDownloaderService.java
```

## Dependências

Este projeto requer a instalação das seguintes dependências:

pom.xml
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-exec</artifactId>
    <version>1.4.0</version>
</dependency>
```

**yt_dlp**: Para gerenciar o download de vídeos.

### youtube-dl

[youtube-dl GitHub Repository](https://github.com/ytdl-org/youtube-dl)

**Descrição**: youtube-dl é uma ferramenta de linha de comando que permite baixar vídeos de YouTube e muitos outros sites de vídeo. É altamente configurável e suporta uma ampla variedade de formatos e opções de download.

**Principais recursos**:
- Suporte para download de vídeos de diversos sites.
- Opções para selecionar formato e qualidade dos vídeos.
- Integração com scripts e automações.

Este projeto requer a instalação da biblioteca `yt_dlp`. Siga as instruções abaixo para instalá-la:

```bash
pip3 install yt-dlp
```

**FFmpeg**: Necessário para combinar áudio e vídeo em alta qualidade. Siga as instruções de instalação para seu sistema operacional abaixo.

### FFmpeg

**Descrição**: O `ffmpeg` é uma ferramenta essencial para manipulação de arquivos de áudio e vídeo e é necessário para que o `yt_dlp` consiga baixar vídeos em alta qualidade (como 720p e 1080p) do YouTube e de outras plataformas. Ele permite que o `yt_dlp` combine vídeo e áudio em um único arquivo, especialmente quando esses são disponibilizados separadamente em resoluções maiores.

Por que o `ffmpeg` é necessário para `yt_dlp`?

Para vídeos em alta resolução, o YouTube geralmente armazena o vídeo e o áudio em arquivos separados (isso é chamado de *DASH format*). O `ffmpeg` permite que o `yt_dlp` combine esses arquivos em um único arquivo completo com áudio e vídeo sincronizados e em alta qualidade.

**Instalação do FFmpeg**

- macOS (usando Homebrew)

  Se você usa macOS e já tem o Homebrew instalado, basta rodar o seguinte comando no terminal:

  ```bash
  brew install ffmpeg
  ```

  Após a instalação, confirme que o `ffmpeg` foi instalado corretamente com o comando:

  ```bash
  ffmpeg -version
  ```

- Ubuntu/Debian (Linux)

  No Ubuntu ou em outras distribuições baseadas no Debian, você pode instalar o `ffmpeg` com o seguinte comando:

  ```bash
  sudo apt update
  sudo apt install ffmpeg
  ```

  Assim como no macOS, verifique a instalação rodando:

  ```bash
  ffmpeg -version
  ```

- Windows

  Para Windows, o processo é um pouco diferente:

  1. Acesse o [site oficial do FFmpeg](https://ffmpeg.org/download.html) e baixe a versão mais recente para Windows.
  2. Extraia o arquivo baixado em uma pasta de fácil acesso.
  3. Adicione o caminho da pasta `bin` (onde está o executável `ffmpeg.exe`) à variável de ambiente **PATH**:
     - Abra o menu Iniciar e procure por **Variáveis de Ambiente**.
     - Edite a variável **Path** e adicione o caminho completo até a pasta `bin` do `ffmpeg`.
  5. Verifique a instalação no terminal (Prompt de Comando ou PowerShell) com:

     ```bash
     ffmpeg -version
     ```

Após a instalação, o `yt_dlp` deverá conseguir usar o `ffmpeg` automaticamente para combinar vídeo e áudio e gerar arquivos de alta qualidade.

## Uso

1. Clone este repositório:
```bash
git clone <URL_DO_REPOSITORIO>
```

2. Navegue até o diretório do projeto:
```bash
cd <NOME_DO_DIRETORIO>
```

3. Execute o aplicativo Spring Boot:
```bash
./mvnw spring-boot:run
```

4. Faça uma requisição POST para o endpoint /api/videos/download

O script está configurado para baixar o vídeo na URL especificada e salvá-lo no diretório videos na raiz do projeto.

O vídeo será baixado com o título original do YouTube e salvo na pasta videos.

## ENDPOINT

POST /api/videos/download

Exemplo: http://localhost:8080/api/videos/download

```json
{
    "url": "https://www.youtube.com/watch?v=a2ujag6SOaU",
    "path": "videos"
}
```

## Código

Abaixo está o código principal do projeto:

```java
package com.example.YouTubeVideoDownloader.service;

import org.springframework.stereotype.Service;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import java.io.File;

@Service
public class YouTubeVideoDownloaderService {

    public void downloadVideo(String videoUrl, String outputDirectory) {
        try {
            // Criar um objeto File para o caminho de saída
            File outputFile = new File(outputDirectory);

            // Verificar se o diretório existe, se não existir, tentar criá-lo
            if (!outputFile.exists()) {
                boolean dirCreated = outputFile.getParentFile().mkdirs();
                if (!dirCreated) {
                    System.err.println("Erro ao criar diretório: " + outputFile.getParent());
                    return;
                }
            }

            // Caminho do executável yt-dlp
            String ytDlpPath = "/Users/joaopauloaramuni/Downloads/YouTubeVideoDownloader/path/to/venv/bin/yt-dlp";

            // Comando para executar yt-dlp
            CommandLine cmdLine = new CommandLine(ytDlpPath);

            cmdLine.addArgument("--format");
            cmdLine.addArgument("bestvideo[height<=1080]+bestaudio/best"); // Prioriza 1080p

            // Usando o título do vídeo e a extensão correta para o arquivo
            cmdLine.addArgument("-o");
            cmdLine.addArgument(outputDirectory + "/%(title)s.%(ext)s"); // Salva com título original e extensão correta

            cmdLine.addArgument(videoUrl);
            cmdLine.addArgument("--merge-output-format");
            cmdLine.addArgument("mp4"); // Garante que o vídeo e áudio estejam em MP4

            DefaultExecutor executor = new DefaultExecutor();
            int exitValue = executor.execute(cmdLine);

            System.out.println("Download concluído com código de saída: " + exitValue);
        } catch (ExecuteException e) {
            System.err.println("Erro ao executar o yt-dlp: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

# Configurações de download

A configuração `ydl_opts` é um dicionário que define como o `yt_dlp` deve baixar o vídeo. Veja cada opção:

- **'format': 'bestvideo[height<=1080]+bestaudio/best'**: Esta opção define o formato do vídeo a ser baixado. A configuração prioriza o download da melhor qualidade de vídeo que não exceda 1080p, combinando isso com a melhor qualidade de áudio disponível. Se não houver um vídeo disponível na qualidade especificada, o `yt_dlp` tentará baixar o melhor vídeo disponível.

- **'outtmpl': '%(title)s.%(ext)s'**: Esta opção define o template de saída para os arquivos baixados. O vídeo será salvo com o título original do vídeo do YouTube, seguido da extensão apropriada (como .mp4).

- **'merge_output_format': 'mp4'**: Esta opção garante que, ao combinar o áudio e o vídeo, o formato final do arquivo será MP4. Isso é especialmente útil quando os arquivos de áudio e vídeo são baixados separadamente e precisam ser mesclados em um único arquivo.

Essas configurações ajudam a garantir que o vídeo seja baixado na melhor qualidade possível, enquanto mantém a compatibilidade com a maioria dos dispositivos e reprodutores de mídia, usando o formato MP4.

## Ambiente virtual

É recomendável usar um ambiente virtual para gerenciar suas dependências. Siga os passos abaixo para configurar um ambiente virtual:

1. Crie um ambiente virtual usando o seguinte comando:

    ```bash
    python3 -m venv .venv
    ```

2. Ative o ambiente virtual:
   - No macOS e Linux:

    ```bash
    source .venv/bin/activate
    ```
   - No Windows:

    ```bash
    .venv\Scripts\activate
    ```

Após ativar o ambiente virtual, você pode instalar a dependência do yt-dlp conforme mencionado anteriormente.

## Observação

Para baixar vídeos de URLs diferentes, altere o valor da variável `url` no código. 

## Licença

Este projeto é de código aberto e está licenciado sob a MIT License. Sinta-se livre para usá-lo e modificá-lo conforme necessário.
