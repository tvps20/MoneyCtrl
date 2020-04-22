package com.santiago.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.santiago.domain.enuns.TipoStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Fatura extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Date vencimento;

	@Getter
	@Setter
	private BigDecimal valorTotal;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	private TipoStatus status = TipoStatus.ABERTA;

	@Getter
	@Setter
	private String mesReferente;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "cartao_id")
	private Cartao cartao;

	// Construtores
	public Fatura() {
	}

	public Fatura(Long id, Date vencimento, BigDecimal valorTotal, String observacao, String mesReferente) {
		super(id);
		this.vencimento = vencimento;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.mesReferente = mesReferente;
	}

	public Fatura(Long id, Date vencimento, BigDecimal valorTotal, String observacao, String mesReferente,
			Cartao cartao) {
		this(id, vencimento, valorTotal, observacao, mesReferente);
		this.cartao = cartao;
	}	
}
