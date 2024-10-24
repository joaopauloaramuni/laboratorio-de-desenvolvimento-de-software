package com.example.RestAPI.service;

@org.springframework.stereotype.Service
public class Service {
    public String exampleView() {
        return "Quando estiver rodando o projeto localmente, acesse <a href='http://localhost:8080/'>localhost:8080</a> para visualizar o contéudo do arquivo resources/static/index.html. <br />" +
                "Caso já tenha feito o deploy no Fly.io, acesse <a href='https://fly-io-view-restapi.fly.dev/'>fly-io-view-restapi.fly.dev</a> para visualizar o conteúdo do arquivo resources/static/index.html ou <br />" +
                "<a href='https://fly-io-view-restapi.fly.dev/exemplo'>fly-io-view-restapi.fly.dev/exemplo</a> para acessar o endpoint /exemplo.";
    }
}
