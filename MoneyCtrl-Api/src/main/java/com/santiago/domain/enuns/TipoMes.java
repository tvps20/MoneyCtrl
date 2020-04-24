package com.santiago.domain.enuns;

import com.santiago.domain.exceptions.IllegalEnumException;

public enum TipoMes {
	JANEIRO(1, "Janeiro"), FEVEREIRO(2, "Fevereiro"), MARCO(3, "Março"), ABRIL(4, "Abril"), MAIO(5, "Maio"),
	JUNHO(6, "Junho"), JULHO(7, "Julho"), AGOSTO(8, "Agosto"), SETEMBRO(9, "Setembro"), OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"), DEZEMBRO(12, "Dezembro");

	private int cod;
	private String descricao;

	// Construtor
	private TipoMes(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	// Metodos
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

		throw new IllegalEnumException(
				"Tipo [" + descricao + "] inválido. Não atende aos tipos [JANEIRO, FEREVEIRO ... DEZEMBRO]");
	}
}
