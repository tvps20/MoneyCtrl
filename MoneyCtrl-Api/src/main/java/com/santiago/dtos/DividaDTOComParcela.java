package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.santiago.domain.Divida;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class DividaDTOComParcela extends DividaDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Integer qtdParcela = 2;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	// Construtores
	public DividaDTOComParcela() {
	}

	public DividaDTOComParcela(Long id, Long faturaId, BigDecimal valorTotal, String observacao, LocalDate dataDivida,
			boolean paga, boolean parcelada, Long compradorId, Integer qtdParcela, Integer parcelaAtual) {
		super(id, faturaId, valorTotal, observacao, dataDivida, paga, parcelada, compradorId);
		this.parcelada = true;
		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
	}

	public DividaDTOComParcela(Divida divida) {
		super(divida);
		this.parcelada = true;
		this.qtdParcela = divida.getQtdParcela();
		this.parcelaAtual = divida.getParcelaAtual();
	}
}
