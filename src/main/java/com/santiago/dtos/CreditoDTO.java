package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.domain.Credito;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
public class CreditoDTO extends BaseDTO {

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
	private Long compradorId;

	// Construtores
	public CreditoDTO() {
	}

	public CreditoDTO(Long id, BigDecimal valor, LocalDate data , Long compradorId) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.compradorId = compradorId;
	}

	public CreditoDTO(Credito credito) {
		super(credito.getId());
		this.valor = credito.getValor();
		this.data = credito.getData();
		this.compradorId = credito.getComprador().getId();
	}
}
