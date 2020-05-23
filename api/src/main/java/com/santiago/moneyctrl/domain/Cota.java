package com.santiago.moneyctrl.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Cota extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "comprador_id", nullable = false)
	private Comprador comprador;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "lancamento_id", nullable = false)
	private Lancamento lancamento;

	// Construtores
	public Cota() {
	}

	public Cota(Long id) {
		super(id);
	}

	public Cota(Long id, BigDecimal valor, Comprador comprador, Lancamento lancamento) {
		super(id);
		this.valor = valor;
		this.comprador = comprador;
		this.lancamento = lancamento;
	}
}
