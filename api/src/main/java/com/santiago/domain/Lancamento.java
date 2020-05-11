package com.santiago.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Lancamento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(nullable = false)
	private String descricao;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDate dataCompra;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "fatura_id", nullable = false)
	private Fatura fatura;

	@Getter
	@Setter
	protected boolean parcelado;

	@Getter
	@Setter
	private Integer qtdParcela = 2;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	@Getter
	@Setter
	@OneToMany(mappedBy = "lancamento")
	private List<Cota> compradores = new ArrayList<>();

	// Construtores
	public Lancamento() {
	}

	public Lancamento(Long id) {
		super(id);
	}

	public Lancamento(Long id, String descricao, String obsrvacao, LocalDate dataCompra, Fatura fatura,
			boolean parcelado, Integer qtdParcela, Integer parcelaAtual) {
		super(id);
		this.descricao = descricao;
		this.observacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.fatura = fatura;
		this.parcelado = parcelado;
		this.qtdParcela = qtdParcela;
		this.parcelaAtual = parcelaAtual;
	}
}
