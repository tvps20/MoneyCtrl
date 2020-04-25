package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.santiago.domain.Lancamento;
import com.santiago.domain.LancamentoComParcela;

import lombok.Getter;
import lombok.Setter;

public class LancamentoDTOComParcela extends LancamentoDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Integer qtdParcela = 1;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	// Construtores
	public LancamentoDTOComParcela() {
	}

	public LancamentoDTOComParcela(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Long faturaId, Integer qtdParcela, Integer parcelaAtual) {
		super(id, valor, descricao, obsrvacao, dataCompra, faturaId);
		this.parcelado = true;
		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
	}

	public LancamentoDTOComParcela(Lancamento lancamento) {
		super(lancamento.getId(), lancamento.getValor(), lancamento.getDescricao(), lancamento.getObsrvacao(),
				lancamento.getDataCompra(), lancamento.getFatura().getId());
		this.qtdParcela = ((LancamentoComParcela) lancamento).getQtdParcela();
		this.parcelaAtual = ((LancamentoComParcela) lancamento).getParcelaAtual();
		this.parcelado = true;
		this.createdAt = lancamento.getCreatedAt();
		this.updatedAt = lancamento.getUpdatedAt();
	}
}
