package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
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
	private String compradorNome;
	
	@Getter
	@Setter
	private Long lancamentoId;

	@Getter
	@Setter
	private LancamentoDTO lancamento;

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
		this.lancamentoId = cota.getLancamento().getId();
		
		this.ativo = cota.isAtivo();
		this.tipo = TipoEntity.COTA;
	}
}
