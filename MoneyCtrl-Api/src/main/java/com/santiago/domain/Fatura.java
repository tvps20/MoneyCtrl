package com.santiago.domain;

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

	public Fatura(Long id) {
		super(id);
	}

	public Fatura(Long id, LocalDate vencimento, String observacao, TipoMes mesReferente, Cartao cartao) {
		super(id);
		this.vencimento = vencimento;
		this.observacao = observacao;
		this.mesReferente = mesReferente;
		this.cartao = cartao;
	}
}
