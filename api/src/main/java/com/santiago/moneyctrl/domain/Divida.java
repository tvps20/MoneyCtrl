package com.santiago.moneyctrl.domain;

import java.math.BigDecimal;
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

@Entity
public class Divida extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDate dataDivida;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "fatura_id")
	private Fatura fatura;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "comprador_id", nullable = false)
	private Comprador comprador;

	@Getter
	@Setter
	private boolean paga;

	@Getter
	@Setter
	@OneToMany(mappedBy = "divida")
	private List<Pagamento> pagamentos = new ArrayList<>();

	// Construtores
	public Divida() {
	}

	public Divida(Long id) {
		super(id);
	}

	public Divida(Long id, BigDecimal valor, String observacao, LocalDate dataDivida, Fatura fatura,
			Comprador comprador, boolean paga) {
		super(id);
		this.valor = valor;
		this.observacao = observacao;
		this.dataDivida = dataDivida;
		this.fatura = fatura;
		this.comprador = comprador;
		this.paga = paga;
	}

	@Override
	public String toString() {
		return "Divida [" + super.toString() + ", valor=" + valor + ", observacao=" + observacao + ", dataDivida="
				+ dataDivida + ", faturaId=" + fatura.getId() + ", compradorId=" + comprador.getId() + ", paga=" + paga
				+ ", pagamentosEmpty=" + pagamentos.isEmpty() + "]";
	}
}
