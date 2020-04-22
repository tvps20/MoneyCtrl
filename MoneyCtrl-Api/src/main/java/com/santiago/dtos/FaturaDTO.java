package com.santiago.dtos;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.domain.Fatura;
import com.santiago.domain.enuns.TipoStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString(callSuper = true)
@Slf4j
public class FaturaDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date vencimento;

	@Getter
	@Setter
	private BigDecimal valorTotal;

	@Getter
	@Setter
	private String observacao;

	@Getter
	@Setter
	private TipoStatus status = TipoStatus.ABERTA;

	@Getter
	@Setter
	private String mesReferente;

	@Getter
	@Setter
	@NotNull(message = "Deve ser passando o id de um obj existente")
	private Long cartaoId;

	// Construtores
	public FaturaDTO() {
	}

	public FaturaDTO(Long id, Date vencimento, BigDecimal valorTotal, String observacao, String mesReferente,
			Long cartaoId) {
		super(id);
		this.vencimento = vencimento;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.mesReferente = mesReferente;
		this.cartaoId = cartaoId;
	}

	public FaturaDTO(Fatura fatura) {
		super(fatura.getId());
		log.info("Mapping 'Fatura' to 'FaturaDTO': " + this.getClass().getName());
		this.vencimento = fatura.getVencimento();
		this.valorTotal = fatura.getValorTotal();
		this.observacao = fatura.getObservacao();
		this.mesReferente = fatura.getMesReferente();
		this.cartaoId = fatura.getCartao().getId();
		this.createdAt = fatura.getCreatedAt();
		this.updatedAt = fatura.getUpdatedAt();
	}
}
