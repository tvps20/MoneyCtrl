package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;

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
	private String observacao;

	@Getter
	@Setter
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDateTime dataCompra = LocalDateTime.now();

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	protected Long faturaId;

	@Getter
	@Setter
	protected boolean parcelado;

	@Setter
	private Integer qtdParcelas;

	@Setter
	private Integer parcelaAtual;

	@Getter
	@Setter
	@Valid
	@NotEmpty(message = "{validation.erro.model.notEmpty.list}")
	private List<CotaDTO> cotas = new ArrayList<>();

	// Construtores
	public LancamentoDTO() {
	}

	public LancamentoDTO(Long id) {
		super(id);
	}

	public LancamentoDTO(Long id, String descricao, String obsrvacao, LocalDateTime dataCompra, Long faturaId,
			boolean parcelado, Integer qtdParcelas, Integer parcelaAtual) {
		super(id);
		this.descricao = descricao;
		this.observacao = obsrvacao;
		this.dataCompra = dataCompra;
		this.faturaId = faturaId;
		this.parcelado = parcelado;

		if (parcelado) {
			this.qtdParcelas = qtdParcelas != null ? qtdParcelas : 2;
			this.parcelaAtual = parcelaAtual != null ? parcelaAtual : 1;
		}
	}

	public LancamentoDTO(Lancamento lancamento) {
		super(lancamento.getId());

		this.descricao = lancamento.getDescricao();
		this.observacao = lancamento.getObservacao();
		this.dataCompra = lancamento.getDataCompra();
		this.faturaId = lancamento.getFatura().getId();
		this.parcelado = lancamento.isParcelado();
		this.cotas = lancamento.getCotas().stream().map(obj -> {
			CotaDTO dto = new CotaDTO(obj);
			dto.setLancamentoId(null);
			return dto;
		}).collect(Collectors.toList());
		if (lancamento.isParcelado()) {
			this.qtdParcelas = lancamento.getQtdParcelas();
			this.parcelaAtual = lancamento.getParcelaAtual();
		}

		this.createdAt = lancamento.getCreatedAt();
		this.updatedAt = lancamento.getUpdatedAt();
		this.ativo = lancamento.isAtivo();
		this.tipo = TipoEntity.LANCAMENTO;
	}

	// Getters and Setters
	public Integer getQtdParcelas() {

		if (this.parcelado) {
			this.qtdParcelas = qtdParcelas != null ? qtdParcelas : 2;
		}

		return qtdParcelas;
	}

	public Integer getParcelaAtual() {

		if (this.parcelado) {
			if (this.parcelaAtual != null) {
				if (this.parcelaAtual > this.qtdParcelas) {
					this.parcelaAtual = this.qtdParcelas;
				}
			} else {
				this.parcelaAtual = 1;
			}
		}

		return parcelaAtual;
	}

	public BigDecimal getValorTotal() {
		double total = this.cotas.stream().mapToDouble(x -> x.getValor().doubleValue()).sum();

		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
