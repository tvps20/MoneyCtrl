package com.santiago.builders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.santiago.domain.Comprador;
import com.santiago.domain.Credito;
import com.santiago.dtos.CreditoDTO;

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
		builder.credito = new Credito(1L, BigDecimal.valueOf(10), LocalDate.of(2020, 1, 1), "Credito teste", comprador);

		return builder;
	}

	public static CreditoBuilder mockCreditoDTOBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.creditoDTO = new CreditoDTO(1L, BigDecimal.valueOf(10), LocalDate.of(2020, 1, 1), "CreditoDTO teste",
				comprador.getId());

		return builder;
	}

	public static CreditoBuilder mockCollectionCreditosBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.creditos = new ArrayList<Credito>();

		for (long i = 1; i <= 10; i++) {
			Credito credito = new Credito(i, BigDecimal.valueOf(i), LocalDate.of(2020, 1, 1), "Credito teste " + i,
					comprador);

			builder.creditos.add(credito);
		}

		return builder;
	}

	public static CreditoBuilder mockCollectionCreditosDTOBuilder() {
		CreditoBuilder builder = new CreditoBuilder();
		builder.creditosDTO = new ArrayList<CreditoDTO>();

		for (long i = 1; i <= 10; i++) {
			CreditoDTO creditoDTO = new CreditoDTO(i, BigDecimal.valueOf(i), LocalDate.of(2020, 1, 1),
					"CreditoDTO teste " + i, comprador.getId());

			builder.creditosDTO.add(creditoDTO);
		}

		return builder;
	}

	public CreditoDTO getCreditoDTOInvalido() {
		this.creditoDTO.setData(null);

		return this.creditoDTO;
	}
}
