package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDateTime data = LocalDateTime.now();
	
	@Getter
	@Setter
	private boolean disponivel = true;

	@Getter
	@Setter
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 5, max = 20, message = "{validation.erro.model.length.nome}")
	private String descricao;

	@Getter
	@Setter
	private Long compradorId;

	// Construtores
	public CreditoDTO() {
	}

	public CreditoDTO(Long id, BigDecimal valor, LocalDateTime data, String descricao) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.descricao = descricao;
	}

	public CreditoDTO(Long id, BigDecimal valor, LocalDateTime data, String descricao, Long compradorId) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.descricao = descricao;
		this.compradorId = compradorId;
	}

	public CreditoDTO(Credito credito) {
		super(credito.getId());
		
		this.valor = credito.getValor();
		this.data = credito.getData();
		this.descricao = credito.getDescricao();
		this.disponivel = credito.isDisponivel();
		this.tipo = TipoEntity.CREDITO;
	}
}
