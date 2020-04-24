package com.santiago.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.santiago.domain.enuns.TipoMes;
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
	@Column(nullable = false)
	private LocalDate vencimento;

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
	private TipoMes mesReferente;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "cartao_id", nullable = false)
	private Cartao cartao;

	@Getter
	@Setter
	@OneToMany(mappedBy = "fatura")
	private List<Lancamento> lancamentos = new ArrayList<>();

	// Construtores
	public Fatura() {
	}

	public Fatura(Long id, LocalDate vencimento, BigDecimal valorTotal, String observacao, TipoMes mesReferente) {
		super(id);
		this.vencimento = vencimento;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.mesReferente = mesReferente;
	}

	public Fatura(Long id, LocalDate vencimento, BigDecimal valorTotal, String observacao, TipoMes mesReferente,
			Cartao cartao) {
		this(id, vencimento, valorTotal, observacao, mesReferente);
		this.cartao = cartao;
	}
}
