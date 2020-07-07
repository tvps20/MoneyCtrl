package com.santiago.moneyctrl.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.santiago.moneyctrl.domain.Comprador;

import lombok.Getter;
import lombok.Setter;

public class CotaFaturaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long compradorId;

	@Getter
	@Setter
	private String compradorNome;

	@Getter
	@Setter
	private List<CotaDTO> cotas = new ArrayList<>();

	// Construtores
	public CotaFaturaDTO() {
	}

	public CotaFaturaDTO(Comprador comprador) {
		this.compradorId = comprador.getId();
		this.compradorNome = comprador.getNome();
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
		result = prime * result + ((compradorId == null) ? 0 : compradorId.hashCode());
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
		CotaFaturaDTO other = (CotaFaturaDTO) obj;
		if (compradorId == null) {
			if (other.compradorId != null)
				return false;
		} else if (!compradorId.equals(other.compradorId))
			return false;
		return true;
	}
}
