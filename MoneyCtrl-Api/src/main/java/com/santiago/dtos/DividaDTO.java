package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.domain.Divida;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class DividaDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long faturaId;

	@Getter
	@Setter
	private BigDecimal valorTotal;

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
	protected boolean parcelada = false;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Long compradorId;

	// Construtores
	public DividaDTO() {
	}

	public DividaDTO(Long id, Long faturaId, BigDecimal valorTotal, String observacao, LocalDate dataDivida,
			boolean paga, boolean parcelada, Long compradorId) {
		super(id);
		this.faturaId = faturaId;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.dataDivida = dataDivida;
		this.paga = paga;
		this.parcelada = parcelada;
		this.compradorId = compradorId;
	}

	public DividaDTO(Divida divida) {
		super(divida.getId());

		if (divida.getFatura() != null) {
			this.faturaId = divida.getFatura().getId();
		}

		this.valorTotal = divida.getValorTotal();
		this.observacao = divida.getObservacao();
		this.dataDivida = divida.getDataDivida();
		this.paga = divida.isPaga();
		this.parcelada = false;
		this.compradorId = divida.getComprador().getId();
		this.createdAt = divida.getCreatedAt();
		this.updatedAt = divida.getUpdatedAt();
	}
}
