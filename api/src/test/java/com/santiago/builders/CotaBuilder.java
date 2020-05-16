package com.santiago.builders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.santiago.domain.Comprador;
import com.santiago.domain.Cota;
import com.santiago.domain.Lancamento;
import com.santiago.dtos.CotaDTO;

import lombok.Getter;

public class CotaBuilder {

	@Getter
	private Cota cota;

	@Getter
	private CotaDTO cotaDTO;

	@Getter
	private List<Cota> cotas;

	@Getter
	private List<CotaDTO> cotasDTO;

	private static Comprador comprador;
	private static Lancamento lancamento;

	// Construtores
	private CotaBuilder() {
		comprador = CompradorBuilder.mockCompradorBuilder().getComprador();
		lancamento = LancamentoBuilder.mockLancamentoBuilder().getLancamento();
	}

	// Metodos
	public static CotaBuilder mockCotaBuilder() {
		CotaBuilder builder = new CotaBuilder();
		builder.cota = new Cota(1L, BigDecimal.valueOf(10), comprador, lancamento);

		return builder;
	}

	public static CotaBuilder mockCotaDTOBuilder() {
		CotaBuilder builder = new CotaBuilder();
		builder.cotaDTO = new CotaDTO(1L, BigDecimal.valueOf(10), comprador.getId(), lancamento.getId());

		return builder;
	}

	public static CotaBuilder mockCollectionCotasBuilder() {
		CotaBuilder builder = new CotaBuilder();
		builder.cotas = new ArrayList<Cota>();

		for (long i = 1; i <= 10; i++) {
			Cota cota = new Cota(i, BigDecimal.valueOf(i), comprador, lancamento);

			builder.cotas.add(cota);
		}

		return builder;
	}

	public static CotaBuilder mockCollectionCotasDTOBuilder() {
		CotaBuilder builder = new CotaBuilder();
		builder.cotasDTO = new ArrayList<CotaDTO>();

		for (long i = 1; i <= 10; i++) {
			CotaDTO cotaDTO = new CotaDTO(i, BigDecimal.valueOf(i), comprador.getId(), lancamento.getId());

			builder.cotasDTO.add(cotaDTO);
		}

		return builder;
	}

	public CotaDTO getCotaDTOInvalido() {
		this.cotaDTO.setCompradorId(null);

		return this.cotaDTO;
	}
}
