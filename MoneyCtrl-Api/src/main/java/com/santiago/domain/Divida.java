package com.santiago.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Divida extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "fatura_id")
	private Fatura fatura;

	@Getter
	@Setter
	private BigDecimal valorTotal;

	@Getter
	@Setter
	private boolean paga;

	@Getter
	@Setter
	private boolean parcelada;

	@Getter
	@Setter
	private Integer qtdParcela = 2;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "comprador_id", nullable = false)
	private Comprador comprador;

	// Construtores
	public Divida() {
	}

	public Divida(Long id, Fatura fatura, BigDecimal valorTotal, boolean paga, boolean parcelada, Integer qtdParcela,
			Integer parcelaAtual, Comprador comprador) {
		super(id);
		this.fatura = fatura;
		this.valorTotal = valorTotal;
		this.paga = paga;
		this.parcelada = parcelada;
		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
		this.comprador = comprador;
	}
}
