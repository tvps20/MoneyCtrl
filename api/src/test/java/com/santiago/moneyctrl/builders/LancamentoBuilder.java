package com.santiago.moneyctrl.builders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.dtos.LancamentoDTO;

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
		builder.lancamento = new Lancamento(1L, "Lancamento teste", "Compra teste", LocalDateTime.of(2020, 1, 1, 0, 0), fatura,
				true, 6, 1);

		builder.lancamento.getCotas().add(new Cota(1L, BigDecimal.valueOf(10), comprador, builder.lancamento));

		return builder;
	}

	public static LancamentoBuilder mockLancamentoDTOBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamentoDTO = new LancamentoDTO(1L, "LancamentoDTO teste", "Compra teste", LocalDateTime.of(2020, 1, 1, 0, 0),
				fatura.getId(), true, 6, 1);

		builder.lancamentoDTO.getCotas()
				.add(new CotaDTO(1L, BigDecimal.valueOf(10), comprador.getId()));

		return builder;
	}

	public static LancamentoBuilder mockCollectionLancamentosBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamentos = new ArrayList<Lancamento>();

		for (long i = 1; i <= 10; i++) {
			Lancamento lancamento = new Lancamento(i, "Lancamento teste " + i, "Compra teste " + i,
					LocalDateTime.of(2020, 1, 1, 0, 0), fatura, true, 6, 1);

			builder.lancamentos.add(lancamento);
		}

		return builder;
	}

	public static LancamentoBuilder mockCollectionLancamentosDTOBuilder() {
		LancamentoBuilder builder = new LancamentoBuilder();
		builder.lancamentosDTO = new ArrayList<LancamentoDTO>();

		for (long i = 1; i <= 10; i++) {
			LancamentoDTO LancamentoDTO = new LancamentoDTO(i, "LancamentoDTO teste " + i, "Compra teste " + i,
					LocalDateTime.of(2020, 1, 1, 0, 0), fatura.getId(), true, 6, 1);

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

	public Optional<Lancamento> getLancamentoOpt() {
		return Optional.of(this.lancamento);
	}
}
