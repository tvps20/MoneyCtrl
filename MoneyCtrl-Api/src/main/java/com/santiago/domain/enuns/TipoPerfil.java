package com.santiago.domain.enuns;

import com.santiago.domain.exceptions.IllegalEnumException;
import com.santiago.util.Mensagem;

public enum TipoPerfil {

	ADMIN(1, "ROLE_ADMIN"), USUARIO(2, "ROLE_USER");

	private int cod;
	private String descricao;

	private TipoPerfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return this.cod;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoMes toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoMes tm : TipoMes.values()) {
			if (cod.equals(tm.getCod())) {
				return tm;
			}
		}

		throw new IllegalEnumException("Id inválido: " + cod);
	}

	public static TipoMes toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (TipoMes tm : TipoMes.values()) {
			if (descricao.equals(tm.toString())) {
				return tm;
			}
		}

		throw new IllegalEnumException(Mensagem.erroEnumPerfil(descricao));
	}
}
