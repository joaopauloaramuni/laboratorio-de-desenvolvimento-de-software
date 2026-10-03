package com.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe de inicializacao da aplicacao TaskFlow.
 *
 * A aplicacao sobe um servidor web embutido (Tomcat) que expoe:
 *  - Paginas HTML via Thymeleaf (front-end simples), em "/"
 *  - Uma API REST para o recurso "tasks", em "/api/tasks"
 */
@SpringBootApplication
public class TaskFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskFlowApplication.class, args);
    }
}
