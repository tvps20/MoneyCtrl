package com.santiago.moneyctrl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoPerfil;
import com.santiago.moneyctrl.repositories.UsuarioRepository;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Bean
	public boolean instantiateDatabase() {
		Usuario userAdmin = new Usuario(null, "admin@email.com", "admin", "123");
		userAdmin.getPerfis().add(TipoPerfil.ADMIN);
		boolean adminSalvo = this.usuarioRepository.verificarCampoUnico(userAdmin.getEmail());

		if (adminSalvo) {
			return false;
		}

		this.usuarioRepository.save(userAdmin);
		return true;
	}
}
