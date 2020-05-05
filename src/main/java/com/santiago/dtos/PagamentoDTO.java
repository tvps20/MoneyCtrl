package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.domain.Pagamento;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class PagamentoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDate data = LocalDate.now();

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Long dividaId;

	// Construtores
	public PagamentoDTO() {
	}

	public PagamentoDTO(Long id, BigDecimal valor, LocalDate data, Long dividaId) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.dividaId = dividaId;
	}

	public PagamentoDTO(Pagamento pagamento) {
		super(pagamento.getId());
		this.valor = pagamento.getValor();
		this.data = pagamento.getData();
		this.dividaId = pagamento.getDivida().getId();
		this.createdAt = pagamento.getCreatedAt();
		this.updatedAt = pagamento.getUpdatedAt();
	}
}
