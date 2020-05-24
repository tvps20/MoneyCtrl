package com.santiago.moneyctrl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Credito extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDate data;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "comprador_id", nullable = false)
	private Comprador comprador;

	// Construtores
	public Credito() {
	}

	public Credito(Long id) {
		super(id);
	}

	public Credito(Long id, BigDecimal valor, LocalDate data, String observacao, Comprador comprador) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
		this.comprador = comprador;
	}

	@Override
	public String toString() {
		return "Credito [" + super.toString() + ", valor=" + valor + ", data=" + data + ", observacao=" + observacao
				+ ", compradorId=" + comprador.getId() + "]";
	}
}
