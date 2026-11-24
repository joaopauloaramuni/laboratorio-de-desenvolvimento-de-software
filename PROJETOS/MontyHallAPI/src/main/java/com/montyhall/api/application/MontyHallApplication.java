package com.montyhall.api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Ponto de entrada da MontyHallAPI.
 *
 * Como esta classe fica no pacote "application", avisamos ao Spring onde
 * procurar os outros componentes (scanBasePackages) e os repositórios do
 * MongoDB (@EnableMongoRepositories).
 */
@SpringBootApplication(scanBasePackages = {"com.montyhall.api"})
@EnableMongoRepositories("com.montyhall.api.repository")
public class MontyHallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MontyHallApplication.class, args);
	}
}
