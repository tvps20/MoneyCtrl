package com.santiago.builders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.domain.Cartao;
import com.santiago.domain.Fatura;
import com.santiago.domain.enuns.TipoMes;
import com.santiago.dtos.FaturaDTO;

import lombok.Getter;

public class FaturaBuilder {

	@Getter
	private Fatura fatura;

	@Getter
	private FaturaDTO faturaDTO;

	@Getter
	private List<Fatura> faturas;

	@Getter
	private List<FaturaDTO> faturasDTO;

	private static Cartao cartao;

	// Construtores
	private FaturaBuilder() {
		cartao = CartaoBuilder.mockCartaoBuilder().getCartao();
	}

	// Metodos
	public static FaturaBuilder mockFaturaBuilder() {
		FaturaBuilder builder = new FaturaBuilder();
		builder.fatura = new Fatura(1L, LocalDate.of(2020, 1, 1), "Fatura teste", TipoMes.JANEIRO, cartao);

		return builder;
	}

	public static FaturaBuilder mockFaturaDTOBuilder() {
		FaturaBuilder builder = new FaturaBuilder();
		builder.faturaDTO = new FaturaDTO(1L, LocalDate.of(2020, 1, 1), "FaturaDTO teste", cartao.getId());

		return builder;
	}

	public static FaturaBuilder mockCollectionFaturasBuilder() {
		FaturaBuilder builder = new FaturaBuilder();
		builder.faturas = new ArrayList<Fatura>();

		for (long i = 1; i <= 10; i++) {
			Fatura fatura = new Fatura(i, LocalDate.of(2020, 1, 1), "Fatura teste " + i,
					TipoMes.toEnum(Integer.parseInt("" + i)), cartao);

			builder.faturas.add(fatura);
		}

		return builder;
	}

	public static FaturaBuilder mockCollectionFaturasDTOBuilder() {
		FaturaBuilder builder = new FaturaBuilder();
		builder.faturasDTO = new ArrayList<FaturaDTO>();

		for (long i = 1; i <= 10; i++) {
			FaturaDTO faturaDTO = new FaturaDTO(i, LocalDate.of(2020, 1, 1), "FaturaDTO teste " + i, cartao.getId());

			builder.faturasDTO.add(faturaDTO);
		}

		return builder;
	}

	public FaturaDTO getFaturaDTOInvalido() {
		this.faturaDTO.setVencimento(null);
		this.faturaDTO.setCartaoId(null);

		return this.faturaDTO;
	}

	public Optional<Fatura> getFaturaOpt() {
		return Optional.of(this.fatura);
	}
}
