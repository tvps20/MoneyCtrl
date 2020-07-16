package com.santiago.moneyctrl.domain.enuns;

import com.santiago.moneyctrl.domain.exceptions.IllegalEnumException;
import com.santiago.moneyctrl.util.MensagemUtil;

public enum TipoRoles {

	ADMIN(1, "ROLE_ADMIN"), USUARIO(2, "ROLE_USER");

	private int cod;
	private String descricao;

	private TipoRoles(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return this.cod;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoRoles toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoRoles tm : TipoRoles.values()) {
			if (cod.equals(tm.getCod())) {
				return tm;
			}
		}

		throw new IllegalEnumException("Id inválido: " + cod);
	}

	public static TipoRoles toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (TipoRoles tm : TipoRoles.values()) {
			if (descricao.equals(tm.toString())) {
				return tm;
			}
		}

		throw new IllegalEnumException(MensagemUtil.erroEnumPerfil(descricao));
	}
}
