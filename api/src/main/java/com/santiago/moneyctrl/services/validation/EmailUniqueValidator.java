package com.santiago.moneyctrl.services.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santiago.moneyctrl.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

	@Autowired
	private UsuarioService service;

	@Override
	public void initialize(EmailUnique campoValor) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		// Teste de validação
		boolean aux = this.service.verificarEmailUnico(value);

		if (aux) {
			log.info("[Validator] - O Email já foi salvo no bando de dados.");
			return false;
		}

		log.info("[Validator] - O Email ainda não foi salvo no banco de dados");
		return true;
	}
}
