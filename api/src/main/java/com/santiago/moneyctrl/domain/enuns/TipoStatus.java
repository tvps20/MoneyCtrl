package com.santiago.moneyctrl.domain.enuns;

import com.santiago.moneyctrl.domain.exceptions.IllegalEnumException;
import com.santiago.moneyctrl.util.Mensagem;

public enum TipoStatus {

	PROXIMA(1, "Próxima"), ABERTA(2, "Aberta"), FECHADA(3, "Fechada"), PAGA(4, "Paga");

	private int cod;
	private String descricao;

	// Construtor
	private TipoStatus(int cod, String descricao) {
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

	public static TipoStatus toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoStatus ts : TipoStatus.values()) {
			if (cod.equals(ts.getCod())) {
				return ts;
			}
		}

		throw new IllegalEnumException("Id inválido: " + cod);
	}

	public static TipoStatus toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (TipoStatus ts : TipoStatus.values()) {
			if (descricao.equals(ts.toString())) {
				return ts;
			}
		}

		throw new IllegalEnumException(Mensagem.erroEnumStatus(descricao)	);
	}
}
