package com.santiago.moneyctrl.builders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.DividaDTO;

import lombok.Getter;

public class DividaBuilder {

	@Getter
	private Divida divida;

	@Getter
	private DividaDTO dividaDTO;

	@Getter
	private List<Divida> dividas;

	@Getter
	private List<DividaDTO> dividasDTO;

	private static Fatura fatura;
	private static Comprador comprador;

	// Construtores
	private DividaBuilder() {
		fatura = FaturaBuilder.mockFaturaBuilder().getFatura();
		comprador = CompradorBuilder.mockCompradorBuilder().getComprador();
	}

	// Metodos
	public static DividaBuilder mockDividaBuilder() {
		DividaBuilder builder = new DividaBuilder();
		builder.divida = new Divida(1L, BigDecimal.valueOf(10), "Divida teste", LocalDate.of(2020, 1, 1), fatura,
				comprador, false);

		return builder;
	}

	public static DividaBuilder mockDividaDTOBuilder() {
		DividaBuilder builder = new DividaBuilder();
		builder.dividaDTO = new DividaDTO(1L, fatura.getId(), BigDecimal.valueOf(10), "DividaDTO teste",
				LocalDate.of(2020, 1, 1), false, comprador.getId());

		return builder;
	}

	public static DividaBuilder mockCollectionDividasBuilder() {
		DividaBuilder builder = new DividaBuilder();
		builder.dividas = new ArrayList<Divida>();

		for (long i = 1; i <= 10; i++) {
			Divida divida = new Divida(i, BigDecimal.valueOf(i), "Divida teste " + i, LocalDate.of(2020, 1, 1), fatura,
					comprador, false);

			builder.dividas.add(divida);
		}

		return builder;
	}

	public static DividaBuilder mockCollectionDividasDTOBuilder() {
		DividaBuilder builder = new DividaBuilder();
		builder.dividasDTO = new ArrayList<DividaDTO>();

		for (long i = 1; i <= 10; i++) {
			DividaDTO dividaDTO = new DividaDTO(i, fatura.getId(), BigDecimal.valueOf(i), "DividaDTO teste " + i,
					LocalDate.of(2020, 1, 1), false, comprador.getId());

			builder.dividasDTO.add(dividaDTO);
		}

		return builder;
	}

	public DividaDTO getDividaDTOInvalido() {
		this.dividaDTO.setDataDivida(null);
		this.dividaDTO.setCompradorId(null);

		return this.dividaDTO;
	}

	public Optional<Divida> getDividaOpt() {
		return Optional.of(this.divida);
	}
}
