package com.santiago.builders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.santiago.domain.Comprador;
import com.santiago.domain.Cota;
import com.santiago.domain.Fatura;
import com.santiago.domain.Lancamento;
import com.santiago.dtos.CotaDTO;
import com.santiago.dtos.LancamentoDTO;

import lombok.Getter;

public class LancamentoBuilder {

	@Getter
	private Lancamento lancamento;

	@Getter
	private LancamentoDTO lancamentoDTO;

	@Getter
	private List<Lancamento> lancamentos;

	@Getter
	private List<LancamentoDTO> lancamentosDTO;

	private static Fatura fatura;
	private static Comprador comprador;

	// Construtores
	private LancamentoBuilder() {
		fatura = FaturaBuilder.mockFaturaBuilder().getFatura();
		comprador = CompradorBuilder.mockCompradorBuilder().getComprador();
	}

	// Metodos
	public static LancamentoBuilder mockLancamentoBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamento = new Lancamento(1L, "Lancamento teste", "Compra teste", LocalDate.of(2020, 1, 1), fatura,
				true, 6, 1);

		builder.lancamento.getCompradores().add(new Cota(1L, BigDecimal.valueOf(10), comprador, builder.lancamento));

		return builder;
	}

	public static LancamentoBuilder mockLancamentoDTOBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamentoDTO = new LancamentoDTO(1L, "LancamentoDTO teste", "Compra teste", LocalDate.of(2020, 1, 1),
				fatura.getId(), true, 6, 1);

		builder.lancamentoDTO.getCompradores()
				.add(new CotaDTO(1L, BigDecimal.valueOf(10), comprador.getId(), builder.lancamentoDTO.getId()));

		return builder;
	}

	public static LancamentoBuilder mockCollectionLancamentosBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamentos = new ArrayList<Lancamento>();

		for (long i = 1; i <= 10; i++) {
			Lancamento lancamento = new Lancamento(i, "Lancamento teste " + i, "Compra teste " + i,
					LocalDate.of(2020, 1, 1), fatura, true, 6, 1);

			builder.lancamentos.add(lancamento);
		}

		return builder;
	}

	public static LancamentoBuilder mockCollectionLancamentosDTOBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamentosDTO = new ArrayList<LancamentoDTO>();

		for (long i = 1; i <= 10; i++) {
			LancamentoDTO LancamentoDTO = new LancamentoDTO(i, "LancamentoDTO teste " + i, "Compra teste " + i,
					LocalDate.of(2020, 1, 1), fatura.getId(), true, 6, 1);

			builder.lancamentosDTO.add(LancamentoDTO);
		}

		return builder;
	}

	public LancamentoDTO getLancamentoDTOInvalido() {
		this.lancamentoDTO.setDescricao(null);
		this.lancamentoDTO.setDataCompra(null);
		this.lancamentoDTO.setFaturaId(null);

		return this.lancamentoDTO;
	}
}
