package com.santiago.moneyctrl.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.santiago.moneyctrl.domain.enuns.TipoLancamento;

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
	@Column(nullable = false)
	private TipoLancamento tipoLancamento = TipoLancamento.AVISTA;

	@Getter
	@Setter
	private Integer qtdParcelas;

	@Getter
	@Setter
	private Integer parcelaAtual;

	@Getter
	@Setter
	@OneToMany(cascade = {CascadeType.REMOVE }, mappedBy = "lancamento") 
	@OnDelete(action = OnDeleteAction.CASCADE) // Gera o “cascade delete” na constraint no banco de dados evitando várias querys para delete
	private List<Cota> cotas = new ArrayList<>();

	// Construtores
	public Lancamento() {
	}

	public Lancamento(Long id) {
		super(id);
	}

	public Lancamento(Long id, String descricao, LocalDateTime dataCompra, Fatura fatura, TipoLancamento tipoLancamento) {
		super(id);
		this.descricao = descricao;
		this.dataCompra = dataCompra;
		this.fatura = fatura;
		this.tipoLancamento = tipoLancamento;
	}
	
	public Lancamento(Long id, String descricao, LocalDateTime dataCompra, Fatura fatura,
			TipoLancamento tipoLancamento, Integer qtdParcelas, Integer parcelaAtual) {
		super(id);
		this.descricao = descricao;
		this.dataCompra = dataCompra;
		this.fatura = fatura;
		this.tipoLancamento = tipoLancamento;
		this.qtdParcelas = qtdParcelas;
		this.parcelaAtual = parcelaAtual;
	}
	
	public Lancamento(Long id, String descricao, String obsrvacao, LocalDateTime dataCompra, Fatura fatura,
			TipoLancamento tipoLancamento, Integer qtdParcelas, Integer parcelaAtual) {
		super(id);
		this.descricao = descricao;
		this.observacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.fatura = fatura;
		this.tipoLancamento = tipoLancamento;
		this.qtdParcelas = qtdParcelas;
		this.parcelaAtual = parcelaAtual;
	}

	@Override
	public String toString() {
		return "Lancamento [" + super.toString() + ", descricao=" + descricao + ", observacao=" + observacao
				+ ", dataCompra=" + dataCompra + ", faturaId=" + fatura.getId() + ", tipoLancamento=" + tipoLancamento
				+ ", qtdParcelas=" + qtdParcelas + ", parcelaAtual=" + parcelaAtual + ", cotassEmpty="
				+ cotas.isEmpty() + "]";
	}
}
