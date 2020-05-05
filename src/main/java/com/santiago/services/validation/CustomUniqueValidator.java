package com.santiago.services.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.santiago.services.interfaces.IServiceValidator;

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
		
		if (aux) {
			// Valor já cadastrado no banco.
			return false;
		}

		return true;
	}
}
