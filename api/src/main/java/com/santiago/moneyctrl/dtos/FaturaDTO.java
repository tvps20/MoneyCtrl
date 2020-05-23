package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.enuns.TipoMes;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

	public FaturaDTO(Long id, LocalDate vencimento, String observacao, Long cartaoId) {
		super(id);
		this.vencimento = vencimento;
		this.observacao = observacao;
		this.cartaoId = cartaoId;
		this.gerarMesReferente();
	}

	public FaturaDTO(Fatura fatura) {
		super(fatura.getId());
		this.vencimento = fatura.getVencimento();
		this.observacao = fatura.getObservacao();
		this.mesReferente = fatura.getMesReferente();
		this.cartaoId = fatura.getCartao().getId();
		this.createdAt = fatura.getCreatedAt();
		this.updatedAt = fatura.getUpdatedAt();
		this.lancamentos = fatura.getLancamentos().stream().map(obj -> new LancamentoDTO(obj))
				.collect(Collectors.toList());
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

	public BigDecimal getValorTotal() {
		double total = this.lancamentos.stream().mapToDouble(x -> x.getValorTotal().doubleValue()).sum();

		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	// Metodos
	private void gerarMesReferente() {
		this.mesReferente = TipoMes.toEnum(this.vencimento.getMonth().getValue());
	}
}
