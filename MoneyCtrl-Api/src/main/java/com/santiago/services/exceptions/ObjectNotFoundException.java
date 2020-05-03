package com.santiago.services.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ObjectNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Class<?> classTipo;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Class<?> claString) {
		super(msg);
		this.classTipo = claString;
	}

	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
