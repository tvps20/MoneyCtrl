package com.santiago.moneyctrl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario user;

		log.info("[LoadByLogin] - Buscando login no banco de dados. Login: " + login);
		if (login.contains("@")) {
			log.info("[LoadByLogin] - Buscando por email.");
			user = this.usuarioRepository.findByEmail(login);
		} else {
			log.info("[LoadByLogin] - Buscando por username.");
			user = this.usuarioRepository.findByUsername(login);
		}

		if (user == null) {
			log.error("[LoadByLogin] - Usuario não encontrado.");
			throw new UsernameNotFoundException(login);
		}

		log.info("[LoadByLogin] - Usuario encontrado.");
		log.info("[Parse] - 'Usuario' from 'UserSS'");
		UserSS userSS = new UserSS(user.getId(), login, user.getPassword(), user.getRoles());
		log.info("[Parse] - Parse finalizado com sucesso.");
		log.info("[LoadByLogin] - Busca finalizada com sucesso.");

		return userSS;
	}
}
