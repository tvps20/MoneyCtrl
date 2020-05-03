package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.santiago.domain.Lancamento;

import lombok.Getter;
import lombok.Setter;

public class LancamentoDTOComParcela extends LancamentoDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Integer qtdParcela = 2;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	// Construtores
	public LancamentoDTOComParcela() {
	}

	public LancamentoDTOComParcela(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Long faturaId, Integer qtdParcela, Integer parcelaAtual, Long compradorId) {
		super(id, valor, descricao, obsrvacao, dataCompra, faturaId, compradorId);
		this.parcelado = true;
		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
	}

	public LancamentoDTOComParcela(Lancamento lancamento) {
		super(lancamento);
		this.parcelado = true;
		this.qtdParcela = lancamento.getQtdParcela();
		this.parcelaAtual = lancamento.getParcelaAtual();
	}
}
