package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class LancamentoAVista extends Lancamento {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal desconto;
	
	// Bloco de inicializacao
	{
		this.parcelado = false;
	}

	// Construtores
	public LancamentoAVista() {
	}

	public LancamentoAVista(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Fatura fatura) {
		super(id, valor, descricao, obsrvacao, dataCompra, fatura);
	}
}
