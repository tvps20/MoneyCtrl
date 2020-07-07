package com.santiago.moneyctrl.domain;

import java.math.BigDecimal;
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
public class Divida extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(nullable = false)
	private BigDecimal valorDivida;

	@Getter
	@Setter
	@Column(nullable = false)
	private String descricao;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDateTime dataDivida;

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

	public Divida(Long id, BigDecimal valorDivida, String descricao, LocalDateTime dataDivida, Comprador comprador) {
		super(id);
		this.valorDivida = valorDivida;
		this.descricao = descricao;
		this.dataDivida = dataDivida;
		this.comprador = comprador;
	}

	public Divida(Long id, BigDecimal valorDivida, String descricao, LocalDateTime dataDivida, Comprador comprador,
			Fatura fatura) {
		super(id);
		this.valorDivida = valorDivida;
		this.descricao = descricao;
		this.dataDivida = dataDivida;
		this.comprador = comprador;
		this.fatura = fatura;
	}

	public Divida(Long id, BigDecimal valorDivida, String descricao, LocalDateTime dataDivida, Fatura fatura,
			Comprador comprador, boolean paga) {
		super(id);
		this.valorDivida = valorDivida;
		this.descricao = descricao;
		this.dataDivida = dataDivida;
		this.fatura = fatura;
		this.comprador = comprador;
		this.paga = paga;
	}

	@Override
	public String toString() {
		return "Divida [" + super.toString() + ", valor=" + valorDivida + ", descricao=" + descricao + ", dataDivida="
				+ dataDivida + ", faturaId=" + (fatura != null ? fatura.getId() : "null") + ", compradorId="
				+ comprador.getId() + ", paga=" + paga + ", pagamentosEmpty=" + pagamentos.isEmpty() + "]";
	}
}
