package com.santiago.services.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.santiago.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUniqueValidator implements ConstraintValidator<CustomUnique, String> {

	@Autowired
	private ApplicationContext context;
	
	private IServiceValidator service;

	@Override
	public void initialize(CustomUnique campoValor) {
			this.service = context.getBean(campoValor.classType());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

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
