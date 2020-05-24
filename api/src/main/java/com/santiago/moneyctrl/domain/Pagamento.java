package com.santiago.moneyctrl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Pagamento extends BaseEntity {

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
	private String observacao;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "divida_id", nullable = false)
	private Divida divida;

	// Construtores
	public Pagamento() {
	}

	public Pagamento(Long id) {
		super(id);
	}

	public Pagamento(Long id, BigDecimal valor, LocalDate data, String observacao, Divida divida) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
		this.divida = divida;
	}

	@Override
	public String toString() {
		return "Pagamento [" + super.toString() + ", valor=" + valor + ", data=" + data + ", observacao=" + observacao
				+ ", dividaId=" + divida.getId() + "]";
	}
}
