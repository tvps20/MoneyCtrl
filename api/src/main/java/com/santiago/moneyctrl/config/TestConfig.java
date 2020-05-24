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
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() {
		log.info("[TestConfig] - Verificando estratégia. Strategy: " + this.strategy);
		log.info("[TestConfig] - Apagando e criando o banco de dados.");
		this.dbService.instantiateTestDatabase();

		log.info("[TestConfig] - Finalizando configuração de teste.");
		return true;
	}
}
