package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.domain.Fatura;
import com.santiago.domain.enuns.TipoMes;
import com.santiago.domain.enuns.TipoStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(callSuper = true)
public class FaturaDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{validation.erro.model.notEmpty}")
	private Date vencimento;

	@Getter
	@Setter
	private BigDecimal valorTotal;

	@Getter
	@Setter
	private String observacao;

	@Getter
	private TipoStatus status = TipoStatus.ABERTA;

	private TipoMes mesReferente;

	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notNull.id}")
	private Long cartaoId;

	// Construtores
	public FaturaDTO() {
	}

	public FaturaDTO(Long id, Date vencimento, BigDecimal valorTotal, String observacao, Long cartaoId) {
		super(id);
		this.vencimento = vencimento;
		this.valorTotal = valorTotal;
		this.observacao = observacao;
		this.cartaoId = cartaoId;
		this.gerarMesReferente();
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

	// Getters and Setters
	public void setStatus(String status) {
		this.status = TipoStatus.toEnum(status);
	}
	
	public TipoMes getMesReferente() {
		this.gerarMesReferente();
		return this.mesReferente;
	}

	public void setMesReferente(String mesReferente) {
		this.mesReferente = TipoMes.toEnum(mesReferente);
	}
	
	// Metodos
	private void gerarMesReferente() {
		LocalDate vencimento = this.vencimento.toInstant().atZone(ZoneId.systemDefault())
			      .toLocalDate();
		this.mesReferente = TipoMes.toEnum(vencimento.getMonth().getValue());
	}
}
