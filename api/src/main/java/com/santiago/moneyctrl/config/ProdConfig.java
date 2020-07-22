package com.santiago.moneyctrl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;
import com.santiago.moneyctrl.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("prod")
public class ProdConfig {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() {
		log.info("[ProdConfig] - Verificando estratégia. Strategy: " + this.strategy);
		log.info("[ProdConfig] - Atualizando banco de dados.");

		Usuario userAdmin = new Usuario(null, "admin", "admin20", "123", TipoRoles.ADMIN, TipoRoles.USUARIO);
		userAdmin.setEmail("admin@email.com.br");

		log.info("[ProdConfig] - Verificando se ja existe um admin no banco de dados.");
		boolean adminSalvo = this.usuarioRepository.verificarCampoUnico(userAdmin.getEmail());

		if (adminSalvo) {
			log.info("[ProdConfig] - Inserindo um admin no banco de dados. Admin: " + userAdmin.toString());
			this.usuarioRepository.save(userAdmin);
			log.info("[ProdConfig] - Admin inserido com sucesso.");
		} else {
			log.info("[ProdConfig] - Já existe um admin salvo no banco de dados.");
		}

		log.info("[TestConfig] - Finalizando configuração de produção.");
		return adminSalvo;
	}
}
