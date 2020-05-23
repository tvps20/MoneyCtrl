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
			log.info("Service is null.");
			return false;
		}

		// Teste de validação
		boolean aux = this.service.verificarCampoUnico(value);
		log.info("Finishing find by unique.");

		if (aux) {
			log.info("Value already saved in the database.");
			return false;
		}

		log.info("Value not saved in the database");
		return true;
	}
}
