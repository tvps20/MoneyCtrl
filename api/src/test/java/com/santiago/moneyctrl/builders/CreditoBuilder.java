package com.santiago.moneyctrl.builders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.dtos.CreditoDTO;

import lombok.Getter;

public class CreditoBuilder {

	@Getter
	private Credito credito;

	@Getter
	private CreditoDTO creditoDTO;

	@Getter
	private List<Credito> creditos;

	@Getter
	private List<CreditoDTO> creditosDTO;

	private static Comprador comprador;

	// Construtores
	private CreditoBuilder() {
		comprador = CompradorBuilder.mockCompradorBuilder().getComprador();
	}

	// Metodos
	public static CreditoBuilder mockCreditoBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.credito = new Credito(1L, BigDecimal.valueOf(10), LocalDateTime.of(2020, 1, 1, 0, 0), "Credito teste", comprador);

		return builder;
	}

	public static CreditoBuilder mockCreditoDTOBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.creditoDTO = new CreditoDTO(1L, BigDecimal.valueOf(10), LocalDateTime.of(2020, 1, 1, 0, 0), "CreditoDTO teste",
				comprador.getId());

		return builder;
	}

	public static CreditoBuilder mockCollectionCreditosBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.creditos = new ArrayList<Credito>();

		for (long i = 1; i <= 10; i++) {
			Credito credito = new Credito(i, BigDecimal.valueOf(i), LocalDateTime.of(2020, 1, 1, 0, 0), "Credito teste " + i,
					comprador);

			builder.creditos.add(credito);
		}

		return builder;
	}

	public static CreditoBuilder mockCollectionCreditosDTOBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.creditosDTO = new ArrayList<CreditoDTO>();

		for (long i = 1; i <= 10; i++) {
			CreditoDTO creditoDTO = new CreditoDTO(i, BigDecimal.valueOf(i),LocalDateTime.of(2020, 1, 1, 0, 0),
					"CreditoDTO teste " + i, comprador.getId());

			builder.creditosDTO.add(creditoDTO);
		}

		return builder;
	}

	public CreditoDTO getCreditoDTOInvalido() {
		this.creditoDTO.setData(null);

		return this.creditoDTO;
	}

	public Optional<Credito> getCreditoOpt() {
		return Optional.of(this.credito);
	}
}
