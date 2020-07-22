package com.santiago.moneyctrl.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String login;

	@Getter
	@Setter
	private String password;

	// Construtores
	public CredenciaisDTO() {
	}
}
