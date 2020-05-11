package com.santiago.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.domain.Cota;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
public class CotaDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private BigDecimal valor = new BigDecimal(0);

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Long compradorId;

	@Getter
	@Setter
	private Long lancamentoId;

	// Construtores
	public CotaDTO() {
	}

	public CotaDTO(Long id, BigDecimal valor, Long compradorId) {
		super(id);
		this.valor = valor;
		this.compradorId = compradorId;
	}

	public CotaDTO(Long id, BigDecimal valor, Long compradorId, Long lancamentoId) {
		super(id);
		this.valor = valor;
		this.compradorId = compradorId;
		this.lancamentoId = lancamentoId;
	}

	public CotaDTO(Cota cota) {
		super(cota.getId());
		this.valor = cota.getValor();
		this.compradorId = cota.getComprador().getId();
	}
}
