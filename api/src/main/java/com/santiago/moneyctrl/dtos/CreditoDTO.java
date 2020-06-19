package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.moneyctrl.domain.Credito;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class CreditoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor = new BigDecimal(0);

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDate data = LocalDate.now();
	
	@Getter
	@Setter
	private boolean disponivel = true;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	private Long compradorId;

	// Construtores
	public CreditoDTO() {
	}

	public CreditoDTO(Long id, BigDecimal valor, LocalDate data, String observacao) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
	}

	public CreditoDTO(Long id, BigDecimal valor, LocalDate data, String observacao, Long compradorId) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
		this.compradorId = compradorId;
	}

	public CreditoDTO(Credito credito) {
		super(credito.getId());
		
		this.valor = credito.getValor();
		this.data = credito.getData();
		this.observacao = credito.getObservacao();
		this.disponivel = credito.isDisponivel();
	}
}
