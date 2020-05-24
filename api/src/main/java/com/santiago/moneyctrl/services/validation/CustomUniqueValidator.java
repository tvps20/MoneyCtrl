package com.santiago.moneyctrl.services.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.santiago.moneyctrl.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomUniqueValidator implements ConstraintValidator<CustomUnique, String> {

	@Autowired
	private ApplicationContext context;

	private IServiceValidator service;

	@Override
	public void initialize(CustomUnique campoValor) {
		this.service = this.context != null ? context.getBean(campoValor.classType()) : null;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (this.service == null) {
			log.info("[Validator] - O Serviço está null.");
			return false;
		}

		// Teste de validação
		boolean aux = this.service.verificarCampoUnico(value);

		if (aux) {
			log.info("[Validator] - O Valor já foi salvo no bando de dados.");
			return false;
		}

		log.info("[Validator] - O Valor ainda não foi salvo no banco de dados");
		return true;
	}
}
