package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.santiago.domain.Lancamento;
import com.santiago.domain.LancamentoAVista;
import com.santiago.domain.LancamentoParcelado;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class LancamentoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	private String descricao;

	@Getter
	@Setter
	private String obsrvacao;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDate dataCompra;

	@Getter
	@Setter
	private boolean parcelado = false;

	@Getter
	@Setter
	private BigDecimal desconto;

	@Getter
	@Setter
	private Integer qtdParcela = 1;

	@Getter
	@Setter
	private Integer parcelaAtual = 1;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Long faturaId;

	// Construtores
	public LancamentoDTO() {
	}

	public LancamentoDTO(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Long faturaId) {
		super(id);
		this.valor = valor;
		this.descricao = descricao;
		this.obsrvacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.faturaId = faturaId;
	}

	public LancamentoDTO(Lancamento lancamento) {
		super(lancamento.getId());
		this.valor = lancamento.getValor();
		this.descricao = lancamento.getDescricao();
		this.obsrvacao = lancamento.getObsrvacao();
		this.dataCompra = lancamento.getDataCompra();
		this.faturaId = lancamento.getFatura().getId();
		this.createdAt = lancamento.getCreatedAt();
		this.updatedAt = lancamento.getUpdatedAt();

		if (lancamento.isParcelado()) {
			this.qtdParcela = ((LancamentoParcelado) lancamento).getQtdParcela();
			this.parcelaAtual = ((LancamentoParcelado) lancamento).getParcelaAtual();
		} else {
			this.desconto = ((LancamentoAVista) lancamento).getDesconto();
		}
	}
}
