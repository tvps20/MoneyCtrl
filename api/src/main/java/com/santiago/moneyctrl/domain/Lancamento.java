package com.santiago.moneyctrl.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
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
	private LocalDateTime dataCompra;

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
	private Integer qtdParcelas = 2;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	@Getter
	@Setter
	@OneToMany(mappedBy = "lancamento")
	private List<Cota> cotas = new ArrayList<>();

	// Construtores
	public Lancamento() {
	}

	public Lancamento(Long id) {
		super(id);
	}

	public Lancamento(Long id, String descricao, String obsrvacao, LocalDateTime dataCompra, Fatura fatura,
			boolean parcelado, Integer qtdParcelas, Integer parcelaAtual) {
		super(id);
		this.descricao = descricao;
		this.observacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.fatura = fatura;
		this.parcelado = parcelado;
		this.qtdParcelas = qtdParcelas;
		this.parcelaAtual = parcelaAtual;
	}

	@Override
	public String toString() {
		return "Lancamento [" + super.toString() + ", descricao=" + descricao + ", observacao=" + observacao
				+ ", dataCompra=" + dataCompra + ", faturaId=" + fatura.getId() + ", parcelado=" + parcelado
				+ ", qtdParcelas=" + qtdParcelas + ", parcelaAtual=" + parcelaAtual + ", cotassEmpty="
				+ cotas.isEmpty() + "]";
	}
}
