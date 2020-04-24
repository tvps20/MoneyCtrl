package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Lancamento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	@Column(nullable = false)
	private String descricao;

	@Getter
	@Setter
	private String obsrvacao;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDate dataCompra;
	
	@Getter
	@Setter
	protected boolean parcelado;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "fatura_id", nullable = false)
	private Fatura fatura;

	// Construtores
	public Lancamento() {
	}

	public Lancamento(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Fatura fatura) {
		super(id);
		this.valor = valor;
		this.descricao = descricao;
		this.obsrvacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.fatura = fatura;
	}
}
