package com.santiago.moneyctrl.domain.enuns;

import com.santiago.moneyctrl.domain.exceptions.IllegalEnumException;
import com.santiago.moneyctrl.util.MensagemUtil;

import lombok.Getter;

public enum TipoLancamento {
	AVISTA(1, "A Vista"), PARCELADO(2, "Parcelado"), ASSINATURA(3, "Assinatura");

	@Getter
	private int cod;
	
	@Getter
	private String descricao;

	// Construtor
	private TipoLancamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	// Metodos
	public static TipoLancamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoLancamento tm : TipoLancamento.values()) {
			if (cod.equals(tm.getCod())) {
				return tm;
			}
		}

		throw new IllegalEnumException("Id inválido: " + cod);
	}

	public static TipoLancamento toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (TipoLancamento tm : TipoLancamento.values()) {
			if (descricao.equals(tm.toString())) {
				return tm;
			}
		}

		throw new IllegalEnumException(MensagemUtil.erroEnumLancamento(descricao));
	}
}
