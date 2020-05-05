package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.domain.Divida;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class DividaDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	private Long faturaId;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDate dataDivida = LocalDate.now();

	@Getter
	@Setter
	private boolean paga;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Long compradorId;

	@Getter
	@Setter
	protected boolean parcelada = false;

	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	private Integer qtdParcela;

	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	private Integer parcelaAtual;

	@Getter
	@Setter
	private List<PagamentoDTO> pagamentos = new ArrayList<>();

	// Construtores
	public DividaDTO() {
	}

	public DividaDTO(Long id, Long faturaId, BigDecimal valor, String observacao, LocalDate dataDivida, boolean paga,
			Long compradorId, boolean parcelada, Integer qtdParcela, Integer parcelaAtual) {
		super(id);
		this.faturaId = faturaId;
		this.valor = valor;
		this.observacao = observacao;
		this.dataDivida = dataDivida;
		this.paga = paga;
		this.compradorId = compradorId;
		this.parcelada = parcelada;

		if (parcelada) {
			this.qtdParcela = qtdParcela != null ? qtdParcela : 2;
			this.parcelaAtual = parcelaAtual != null ? parcelaAtual : 1;
		}
	}

	public DividaDTO(Divida divida) {
		super(divida.getId());

		if (divida.getFatura() != null) {
			this.faturaId = divida.getFatura().getId();
		}

		this.valor = divida.getValor();
		this.observacao = divida.getObservacao();
		this.dataDivida = divida.getDataDivida();
		this.paga = divida.isPaga();
		this.parcelada = false;
		this.compradorId = divida.getComprador().getId();
		this.createdAt = divida.getCreatedAt();
		this.updatedAt = divida.getUpdatedAt();
		this.parcelada = divida.isParcelada();

		if (divida.isParcelada()) {
			this.qtdParcela = divida.getQtdParcela();
			this.parcelaAtual = divida.getParcelaAtual();
		}

		this.pagamentos = divida.getPagamentos().stream().map(obj -> new PagamentoDTO(obj))
				.collect(Collectors.toList());
	}

	// Getters and Setters
	public Integer getQtdParcela() {

		if (this.parcelada) {
			this.qtdParcela = qtdParcela != null ? qtdParcela : 2;
		}

		return qtdParcela;
	}

	public Integer getParcelaAtual() {

		if (this.parcelada) {
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
	
	public BigDecimal getPagamentoTotal() {
		double total = this.pagamentos.stream().mapToDouble(x -> x.getValor().doubleValue()).sum();

		return new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
