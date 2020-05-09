package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.domain.Pagamento;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
public class PagamentoDTO extends BaseDTO {

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
	private String observacao;

	@Getter
	@Setter
	private Long dividaId;

	// Construtores
	public PagamentoDTO() {
	}

	public PagamentoDTO(Long id, BigDecimal valor, LocalDate data, String observacao) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
	}

	public PagamentoDTO(Long id, BigDecimal valor, LocalDate data, String observacao, Long dividaId) {
		super(id);
		this.valor = valor;
		this.data = data;
		this.observacao = observacao;
		this.dividaId = dividaId;
	}

	public PagamentoDTO(Pagamento pagamento) {
		super(pagamento.getId());
		this.valor = pagamento.getValor();
		this.data = pagamento.getData();
		this.observacao = pagamento.getObservacao();
	}
}
