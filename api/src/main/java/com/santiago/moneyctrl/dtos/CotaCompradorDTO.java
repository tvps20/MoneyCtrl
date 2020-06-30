package com.santiago.moneyctrl.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.santiago.moneyctrl.domain.Cartao;

import lombok.Getter;
import lombok.Setter;

public class CotaCompradorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long cartaoId;

	@Getter
	@Setter
	private String cartaoNome;

	@Getter
	@Setter
	private List<CotaDTO> cotas = new ArrayList<>();

	// Construtores
	public CotaCompradorDTO() {
	}

	public CotaCompradorDTO(Cartao cartao) {
		this.cartaoId = cartao.getId();
		this.cartaoNome = cartao.getNome();
	}
	
	// Getters and Setters
	public BigDecimal getValorTotal() {
		double total = this.cotas.stream().mapToDouble(x -> x.getValor().doubleValue()).sum();

		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	// Metodos de Comparação por valor
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartaoId == null) ? 0 : cartaoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CotaCompradorDTO other = (CotaCompradorDTO) obj;
		if (cartaoId == null) {
			if (other.cartaoId != null)
				return false;
		} else if (!cartaoId.equals(other.cartaoId))
			return false;
		return true;
	}
}
