package com.santiago.moneyctrl.dtos.enuns;

import com.santiago.moneyctrl.domain.exceptions.IllegalEnumException;
import com.santiago.moneyctrl.util.MensagemUtil;

public enum TipoEntity {
	
	USUARIO(1, "Usuario"), COMPRADOR(2, "Comprador"), CARTAO(3, "Cartao"), BANDEIRA(4, "Bandeira"), FATURA(5, "Fatura");

	private int cod;
	private String descricao;

	private TipoEntity(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return this.cod;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoEntity toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoEntity tm : TipoEntity.values()) {
			if (cod.equals(tm.getCod())) {
				return tm;
			}
		}

		throw new IllegalEnumException("Id inválido: " + cod);
	}

	public static TipoEntity toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (TipoEntity tm : TipoEntity.values()) {
			if (descricao.equals(tm.toString())) {
				return tm;
			}
		}

		throw new IllegalEnumException(MensagemUtil.erroEnumEntity(descricao));
	}
}
