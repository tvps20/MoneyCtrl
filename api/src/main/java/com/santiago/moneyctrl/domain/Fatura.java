package com.santiago.moneyctrl.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.santiago.moneyctrl.domain.enuns.TipoMes;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Fatura extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(nullable = false)
	private LocalDateTime vencimento;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	private TipoStatus status = TipoStatus.ABERTA;

	@Getter
	@Setter
	@Column(nullable = false)
	private TipoMes mesReferente;

	@Getter
	@Setter
	private boolean via2 = false;
	
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

	public Fatura(Long id, LocalDateTime vencimento, TipoMes mesReferente, Cartao cartao) {
		super(id);
		this.vencimento = vencimento;
		this.mesReferente = mesReferente;
		this.cartao = cartao;
	}
	
	public Fatura(Long id, LocalDateTime vencimento, String observacao, TipoMes mesReferente, Cartao cartao) {
		super(id);
		this.vencimento = vencimento;
		this.observacao = observacao;
		this.mesReferente = mesReferente;
		this.cartao = cartao;
	}

	@Override
	public String toString() {
		return "Fatura [" + super.toString() + ", vencimento=" + vencimento + ", observacao=" + observacao + ", status="
				+ status + ", mesReferente=" + mesReferente + ", cartaoId=" + cartao.getId() + ", lancamentosEmpty="
				+ lancamentos.isEmpty() + "]";
	}
}
