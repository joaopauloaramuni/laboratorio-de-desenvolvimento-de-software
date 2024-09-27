package com.example.BD_RestAPI_PostgreSQL.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableJpaRepositories("com.example.BD_RestAPI_PostgreSQL.repository")
@EntityScan("com.example.BD_RestAPI_PostgreSQL.model")
public class BdRestApiPostgreSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(BdRestApiPostgreSqlApplication.class, args);
	}

}
