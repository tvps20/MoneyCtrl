package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;

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
	private BigDecimal valorDivida;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	@Length(min = 5, max = 20, message = "{validation.erro.model.length.nome}")
	private String descricao;

	@Getter
	@Setter
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private LocalDateTime dataDivida = LocalDateTime.now();

	@Getter
	@Setter
	private boolean paga;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Long compradorId;
	
	@Getter
	@Setter
	private String compradorNome;

	@Getter
	@Setter
	private List<PagamentoDTO> pagamentos = new ArrayList<>();

	// Construtores
	public DividaDTO() {
	}

	public DividaDTO(Long id, Long faturaId, BigDecimal valorDivida, String descricao, LocalDateTime dataDivida, boolean paga,
			Long compradorId) {
		super(id);
		this.faturaId = faturaId;
		this.valorDivida = valorDivida;
		this.descricao = descricao;
		this.dataDivida = dataDivida;
		this.paga = paga;
		this.compradorId = compradorId;
	}

	public DividaDTO(Divida divida) {
		super(divida.getId());

		if (divida.getFatura() != null) {
			this.faturaId = divida.getFatura().getId();
		}
		this.valorDivida = divida.getValorDivida();
		this.descricao = divida.getDescricao();
		this.dataDivida = divida.getDataDivida();
		this.paga = divida.isPaga();
		this.compradorId = divida.getComprador().getId();
		this.compradorNome = divida.getComprador().getNome();
		this.pagamentos = divida.getPagamentos().stream().map(obj -> new PagamentoDTO(obj))
				.collect(Collectors.toList());
		
		this.createdAt = divida.getCreatedAt();
		this.updatedAt = divida.getUpdatedAt();
		this.ativo = divida.isAtivo();
		this.tipo = TipoEntity.DIVIDA;
	}

	// Getters and Setters
	public BigDecimal getTotalPagamentos() {
		double total = this.pagamentos.stream().mapToDouble(x -> x.getValor().doubleValue()).sum();

		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
