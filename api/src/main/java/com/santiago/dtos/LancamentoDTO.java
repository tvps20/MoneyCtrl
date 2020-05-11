package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.domain.Lancamento;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class LancamentoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

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
	private LocalDate dataCompra = LocalDate.now();

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	protected Long faturaId;

	@Getter
	@Setter
	protected boolean parcelado = false;

	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	private Integer qtdParcela;

	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	private Integer parcelaAtual;

	@Getter
	@Setter
	@Valid
	@NotEmpty(message = "{validation.erro.model.notEmpty.list}")
	private List<CotaDTO> compradores = new ArrayList<>();

	// Construtores
	public LancamentoDTO() {
	}

	public LancamentoDTO(Long id, BigDecimal valor, String descricao, String obsrvacao, LocalDate dataCompra,
			Long faturaId, Long compradorId, boolean parcelado, Integer qtdParcela, Integer parcelaAtual) {
		super(id);
		this.descricao = descricao;
		this.obsrvacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.faturaId = faturaId;
		this.parcelado = parcelado;

		if (parcelado) {
			this.qtdParcela = qtdParcela != null ? qtdParcela : 2;
			this.parcelaAtual = parcelaAtual != null ? parcelaAtual : 1;
		}
	}

	public LancamentoDTO(Lancamento lancamento) {
		super(lancamento.getId());
		this.descricao = lancamento.getDescricao();
		this.obsrvacao = lancamento.getObservacao();
		this.dataCompra = lancamento.getDataCompra();
		this.faturaId = lancamento.getFatura().getId();
		this.createdAt = lancamento.getCreatedAt();
		this.updatedAt = lancamento.getUpdatedAt();
		this.parcelado = lancamento.isParcelado();
		this.compradores = lancamento.getCompradores().stream().map(obj -> new CotaDTO(obj))
				.collect(Collectors.toList());

		if (lancamento.isParcelado()) {
			this.qtdParcela = lancamento.getQtdParcela();
			this.parcelaAtual = lancamento.getParcelaAtual();
		}
	}

	// Getters and Setters
	public Integer getQtdParcela() {

		if (this.parcelado) {
			this.qtdParcela = qtdParcela != null ? qtdParcela : 2;
		}

		return qtdParcela;
	}

	public Integer getParcelaAtual() {

		if (this.parcelado) {
			if (this.parcelaAtual != null) {
				if (this.parcelaAtual > this.qtdParcela) {
					this.parcelaAtual = this.qtdParcela;
				}
			} else {
				this.parcelaAtual = 1;
			}
		}

		return parcelaAtual;
	}

	public BigDecimal getValorTotal() {
		double total = this.compradores.stream().mapToDouble(x -> x.getValor().doubleValue()).sum();

		return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
