package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class LancamentoComParcela extends Lancamento {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Integer qtdParcela = 1;

	@Getter
	@Setter
	private Integer parcelaAtual = 2;
	
	// Bloco de inicializacao
	{
		this.parcelado = true;
	}

	// Construtores
	public LancamentoComParcela() {
		super();
	}
	
	public LancamentoComParcela(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			boolean parcelado, Fatura fatura, Integer qtdParcela, Integer parcelaAtual) {
		super(id, valor, descricao, obsrvacao, dataCompra, parcelado, fatura);

		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
	}
}
