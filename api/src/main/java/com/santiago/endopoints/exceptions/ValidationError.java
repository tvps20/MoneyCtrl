package com.santiago.endopoints.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	@Getter
	private List<FieldMessage> erros = new ArrayList<>();
	
	// Construtores
	public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}

	// Metodos
	public void addErro(String fieldName, String message) {
		this.erros.add(new FieldMessage(fieldName, message));
	}
}
