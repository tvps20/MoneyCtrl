package com.santiago.moneyctrl.services;

import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

	public void sendNewPasswordEmail(Usuario user, String newPassword) {
		log.info("[SEND EMAIL] - Enviando email.");
		System.out.println("Enviando email...");
		System.out.println("Email enviado.");
		log.info("[SEND EMAIL] - Email enviado com sucesso.");
	}
}
