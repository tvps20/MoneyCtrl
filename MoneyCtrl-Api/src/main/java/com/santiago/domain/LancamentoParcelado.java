package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class LancamentoParcelado extends Lancamento {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Integer qtdParcela = 1;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;
	
	// Bloco de inicializacao
	{
		this.parcelado = true;
	}

	// Construtores
	public LancamentoParcelado() {
		super();
	}

	public LancamentoParcelado(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Fatura fatura, Integer qtdParcela, Integer parcelaAtual) {
		super(id, valor, descricao, obsrvacao, dataCompra, fatura);

		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
	}
}
