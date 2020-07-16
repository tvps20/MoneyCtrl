package com.santiago.moneyctrl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.santiago.moneyctrl.services.DBService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() {
		log.info("[DevConfig] - Verificando estratégia. Strategy: " + this.strategy);

		boolean aux = !"create".equals(strategy);
		if (aux) {
			log.info("[DevConfig] - Atualizando banco de dados.");
			aux = false;
		} else {
			log.info("[DevConfig] - O banco de dados foi recriado.");
			this.dbService.instantiateTestDatabase();
			aux = true;
		}

		log.info("[DevConfig] - Finalizando configuração de desenvolvimento.");
		return aux;
	}
}
