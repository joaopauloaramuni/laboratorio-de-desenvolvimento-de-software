# Boleto Generator

Este projeto é um gerador de boletos bancários utilizando a biblioteca Caelum Stella. Ele permite criar boletos no formato PDF e PNG para o Banco do Brasil, facilitando a emissão e gestão de pagamentos.

## Dependências

O projeto utiliza as seguintes dependências, que estão configuradas no arquivo `pom.xml`:

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
        <groupId>com.lowagie</groupId>
        <artifactId>itext</artifactId>
        <version>2.1.7.js2</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/itext-2.1.7.js2.jar</systemPath>
    </dependency>

    <dependency>
        <groupId>br.com.caelum.stella</groupId>
        <artifactId>caelum-stella-boleto</artifactId>
        <version>2.1.6</version>
    </dependency>

    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>net.sf.jasperreports</groupId>
        <artifactId>jasperreports</artifactId>
        <version>5.5.0</version>
    </dependency>
</dependencies>
```

## Stella

O **Stella** é um projeto que visa suprir as necessidades do programador Java em seu dia a dia ao trabalhar com o domínio brasileiro. A biblioteca oferece funcionalidades como validadores para **CEP**, **CPF**, **CNPJ**, inscrição estadual e um gerador de boletos. Além disso, proporciona integração com **JPA**, **JSF**, **Bean Validation** e outras tecnologias. A versão mais recente é a **2.0**, lançada em **2013**. Saiba como fazer o download e usar o Maven.

### Biblioteca itext

A biblioteca `itext-2.1.7.js2.jar` não está disponível no repositório Maven Central, por isso deve ser armazenada localmente na pasta `lib`. Para adicionar a dependência no Maven, utilize o comando abaixo:

```bash
mvn install:install-file -Dfile=lib/itext-2.1.7.js2.jar -DgroupId=com.lowagie -DartifactId=itext -Dversion=2.1.7.js2 -Dpackaging=jar
```

## Estrutura do projeto

A estrutura das pastas do projeto é a seguinte:

```
com.example.BoletoGenerator
│
├── application
│   └── BoletoGeneratorApplication.java  # Classe principal para inicializar a aplicação
│
├── controller
│   └── BoletoGeneratorController.java  # Controlador que gerencia as requisições para geração do boleto
│
└── service
    └── BoletoGeneratorService.java  # Serviço que gera o boleto
```

## Como rodar o projeto

Para rodar o projeto, siga os passos abaixo:

1. Certifique-se de ter o Maven instalado.
2. Navegue até a raiz do projeto no terminal.
3. Execute o comando para instalar as dependências do Maven:

```bash
mvn clean install
```

4. Para iniciar a aplicação, execute:

```bash
mvn spring-boot:run
```

## Endpoint

O endpoint para gerar o boleto é:

@GetMapping("/gerar-boleto")

Você pode testá-lo acessando o seguinte link no seu navegador:

http://localhost:8080/gerar-boleto

## Função gerarBoleto

A função `gerarBoleto` dentro do `BoletoGeneratorService` é responsável pela criação do boleto. Ela define as datas, informações do beneficiário e pagador, além de gerar os arquivos PDF e PNG correspondentes ao boleto. O código da função é apresentado a seguir:

```java
package com.example.BoletoGenerator.service;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import org.springframework.stereotype.Service;
import br.com.caelum.stella.boleto.bancos.GeradorDeLinhaDigitavel;
import br.com.caelum.stella.boleto.Modalidade;

@Service
public class BoletoGeneratorService {

    public void gerarBoleto() {
        Datas datas = Datas.novasDatas()
                .comDocumento(1, 5, 2008)
                .comProcessamento(1, 5, 2008)
                .comVencimento(2, 5, 2008);

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro("Av das Empresas, 555")
                .comBairro("Bairro Grande")
                .comCep("01234-555")
                .comCidade("São Paulo")
                .comUf("SP");

        // Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("Fulano de Tal")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("000000000018")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro("Av dos testes, 111 apto 333")
                .comBairro("Bairro Teste")
                .comCep("01234-111")
                .comCidade("São Paulo")
                .comUf("SP");

        // Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome("Fulano da Silva")
                .comDocumento("111.222.333-12")
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto("200.00")
                .comNumeroDoDocumento("1234")
                .comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")
                .comLocaisDePagamento("local 1", "local 2");

        // Geração da linha digitável usando GeradorDeLinhaDigitavel
        GeradorDeLinhaDigitavel geradorDeLinhaDigitavel = new GeradorDeLinhaDigitavel();
        String codigoDeBarras = banco.geraCodigoDeBarrasPara(boleto);
        String linhaDigitavel = geradorDeLinhaDigitavel.geraLinhaDigitavelPara(codigoDeBarras, banco);
        // Exibir ou retornar a linha digitável conforme necessário
        System.out.println("Linha Digitável: " + linhaDigitavel);

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

        // Para gerar um boleto em PDF
        gerador.geraPDF("BancoDoBrasil.pdf");

        // Para gerar um boleto em PNG
        gerador.geraPNG("BancoDoBrasil.png");

        // Para gerar um array de bytes a partir de um PDF
        byte[] bPDF = gerador.geraPDF();

        // Para gerar um array de bytes a partir de um PNG
        byte[] bPNG = gerador.geraPNG();
    }
}
```

Os arquivos `BancoDoBrasil.pdf` e `BancoDoBrasil.png` são criados na pasta raiz do projeto.

## Documentação

Para mais informações, você pode acessar a documentação da biblioteca utilizada:

- [Caelum Stella](https://github.com/caelum/caelum-stella/wiki)
- [Gerando Boleto](https://github.com/caelum/caelum-stella/wiki/Gerando-boleto)
- [Download](https://github.com/caelum/caelum-stella/wiki/Download)
- [Banco do Brasil - Classe](https://github.com/caelum/caelum-stella/blob/master/stella-boleto/src/main/java/br/com/caelum/stella/boleto/bancos/BancoDoBrasil.java)
- [Banco do Brasil - Teste](https://github.com/caelum/caelum-stella/blob/master/stella-boleto/src/test/java/br/com/caelum/stella/boleto/bancos/BancoDoBrasilTest.java)
- [Gerador de Linha Digitável](https://github.com/caelum/caelum-stella/blob/master/stella-boleto/src/main/java/br/com/caelum/stella/boleto/bancos/GeradorDeLinhaDigitavel.java)
- [Gerador de Boleto](https://github.com/caelum/caelum-stella/blob/master/stella-boleto/src/main/java/br/com/caelum/stella/boleto/transformer/GeradorDeBoleto.java)

## Licença

Este projeto está licenciado sob a Licença MIT.
