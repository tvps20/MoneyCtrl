package com.santiago.moneyctrl.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

public class NewPasswordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@NotEmpty(message = "Preenchimento obrigatório")
	private String login;

	@Getter
	@Setter
	@NotEmpty(message = "Preenchimento obrigatório")
	private String newPassword;

	// Construtores
	public NewPasswordDTO() {
	}

	public NewPasswordDTO(String login, String newPassword) {
		this.login = login;
		this.newPassword = newPassword;
	}
}
