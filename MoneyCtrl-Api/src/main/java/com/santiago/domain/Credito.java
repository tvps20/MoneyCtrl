package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
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
	@ManyToOne
	@JoinColumn(name = "comprador_id", nullable = false)
	private Comprador comprador;

	// Construtores
	public Credito() {
	}

	public Credito(Long id) {
		super(id);
	}

	public Credito(Long id, BigDecimal valor, LocalDate data, Comprador comprador) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.comprador = comprador;
	}
}
