package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.domain.Lancamento;

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
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDate dataCompra;

	@Getter
	@Setter
	protected boolean parcelado = false;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	protected Long faturaId;

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
		this.parcelado = false;
		this.faturaId = faturaId;
	}

	public LancamentoDTO(Lancamento lancamento) {
		super(lancamento.getId());
		this.valor = lancamento.getValor();
		this.descricao = lancamento.getDescricao();
		this.obsrvacao = lancamento.getObsrvacao();
		this.dataCompra = lancamento.getDataCompra();
		this.parcelado = lancamento.isParcelado();
		this.createdAt = lancamento.getCreatedAt();
		this.updatedAt = lancamento.getUpdatedAt();
	}
}
