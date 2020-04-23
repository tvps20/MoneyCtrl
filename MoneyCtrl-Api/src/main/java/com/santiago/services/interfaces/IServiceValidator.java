package com.santiago.services.interfaces;

public interface IServiceValidator {
	
	default boolean verificarCampoUnico(String campo) {
		return false;
	};
}
