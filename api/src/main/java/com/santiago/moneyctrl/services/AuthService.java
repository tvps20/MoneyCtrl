package com.santiago.moneyctrl.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.dtos.NewPasswordDTO;
import com.santiago.moneyctrl.repositories.UsuarioRepository;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder crypt;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		log.info("[SendNewPassword] - Enviando um email com a nova senha.");
		Usuario user = this.usuarioRepository.findByEmail(email);

		if (user == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPassword = this.newPassword();
		user.setPassword(this.crypt.encode(newPassword));

		this.usuarioRepository.save(user);
		this.emailService.sendNewPasswordEmail(user, newPassword);
		log.info("[SendNewPassword] - Email enviado com sucesso.");
	}

	public void changePassword(NewPasswordDTO objDto) {
		log.info("[ChangePassword] - Trocando a senha do usuario.");
		Usuario user;

		if (objDto.getLogin().contains("@")) {
			user = this.usuarioRepository.findByEmail(objDto.getLogin());
		} else {
			user = this.usuarioRepository.findByUsername(objDto.getLogin());
		}

		if (user == null) {
			throw new ObjectNotFoundException("Usuario não encontrado");
		}

		user.setPassword(this.crypt.encode(objDto.getNewPassword()));
		this.usuarioRepository.save(user);
		log.info("[ChangePassword] - Senha trocada com sucesso.");
	}

	private String newPassword() {
		log.info("[NewPassword] - Gerando nova senha.");
		char[] vetor = new char[10];

		for (int i = 0; i < 10; i++) {
			vetor[i] = this.randomChar();
		}
		
		log.info("[NewPassword] - Senha gerada com sucesso.");
		return new String(vetor);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);

		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 2) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera uma letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
