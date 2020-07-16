package com.santiago.moneyctrl.services.interfaces;

public interface IServiceValidator {

	/**
	 * Verifica se o campo especifico ja possui o valor passado no banco de dados.
	 * 
	 * @param campo
	 * @return
	 */
	default boolean verificarCampoUnico(String campo) {
		return false;
	};
}
