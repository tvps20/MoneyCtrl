package com.santiago.moneyctrl.domain.enuns;

import com.santiago.moneyctrl.domain.exceptions.IllegalEnumException;
import com.santiago.moneyctrl.util.MensagemUtil;

public enum TipoAcesso {
	
	USERNAME(1, "Username"), EMAIL(2, "Email");

	private int cod;
	private String descricao;

	private TipoAcesso(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return this.cod;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoAcesso toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoAcesso tm : TipoAcesso.values()) {
			if (cod.equals(tm.getCod())) {
				return tm;
			}
		}

		throw new IllegalEnumException("Id inválido: " + cod);
	}

	public static TipoAcesso toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (TipoAcesso tm : TipoAcesso.values()) {
			if (descricao.equals(tm.toString())) {
				return tm;
			}
		}

		throw new IllegalEnumException(MensagemUtil.erroEnumAcesso(descricao));
	}
}
