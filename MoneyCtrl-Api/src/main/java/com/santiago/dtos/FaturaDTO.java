package com.santiago.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private LocalDate vencimento;

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
	
	@Getter
	@Setter
	private List<LancamentoDTO> lancamentos = new ArrayList<>();

	// Construtores
	public FaturaDTO() {
	}

	public FaturaDTO(Long id, LocalDate vencimento, BigDecimal valorTotal, String observacao, Long cartaoId) {
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
		this.lancamentos = fatura.getLancamentos().stream().map(obj -> new LancamentoDTO(obj)).collect(Collectors.toList());
	}

	// Getters and Setters
	public void setStatus(String status) {
		this.status = TipoStatus.toEnum(status);
	}

	public TipoMes getMesReferente() {

		if (this.mesReferente == null) {
			this.gerarMesReferente();
		}

		return this.mesReferente;
	}

	public void setMesReferente(String mesReferente) {
		this.mesReferente = TipoMes.toEnum(mesReferente);
	}

	// Metodos
	private void gerarMesReferente() {
		this.mesReferente = TipoMes.toEnum(this.vencimento.getMonth().getValue());
	}
}
