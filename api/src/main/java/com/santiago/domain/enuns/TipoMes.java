package com.santiago.domain.enuns;

import com.santiago.domain.exceptions.IllegalEnumException;
import com.santiago.util.Mensagem;

import lombok.Getter;

public enum TipoMes {
	JANEIRO(1, "Janeiro"), FEVEREIRO(2, "Fevereiro"), MARCO(3, "Março"), ABRIL(4, "Abril"), MAIO(5, "Maio"),
	JUNHO(6, "Junho"), JULHO(7, "Julho"), AGOSTO(8, "Agosto"), SETEMBRO(9, "Setembro"), OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"), DEZEMBRO(12, "Dezembro");

	@Getter
	private int cod;
	
	@Getter
	private String descricao;

	// Construtor
	private TipoMes(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	// Metodos
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

		throw new IllegalEnumException(Mensagem.erroEnumMes(descricao));
	}
}
