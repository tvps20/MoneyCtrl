package com.santiago.moneyctrl.domain.exceptions;

public class IllegalEnumException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public IllegalEnumException(String msg) {
		super(msg);
	}
	
	public IllegalEnumException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
