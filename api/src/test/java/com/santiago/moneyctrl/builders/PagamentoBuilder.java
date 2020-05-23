package com.santiago.moneyctrl.builders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Pagamento;
import com.santiago.moneyctrl.dtos.PagamentoDTO;

import lombok.Getter;

public class PagamentoBuilder {

	@Getter
	private Pagamento pagamento;

	@Getter
	private PagamentoDTO pagamentoDTO;

	@Getter
	private List<Pagamento> pagamentos;

	@Getter
	private List<PagamentoDTO> pagamentosDTO;

	private static Divida divida;

	// Construtores
	private PagamentoBuilder() {
		divida = DividaBuilder.mockDividaBuilder().getDivida();
	}

	// Metodos
	public static PagamentoBuilder mockPagamentoBuilder() {
		PagamentoBuilder builder = new PagamentoBuilder();
		builder.pagamento = new Pagamento(1L, BigDecimal.valueOf(10), LocalDate.of(2020, 1, 1), "Pagamento teste",
				divida);

		return builder;
	}

	public static PagamentoBuilder mockPagamentoDTOBuilder() {
		PagamentoBuilder builder = new PagamentoBuilder();
		builder.pagamentoDTO = new PagamentoDTO(1L, BigDecimal.valueOf(10), LocalDate.of(2020, 1, 1),
				"PagamentoDTO teste", divida.getId());

		return builder;
	}

	public static PagamentoBuilder mockCollectionPagamentosBuilder() {
		PagamentoBuilder builder = new PagamentoBuilder();
		builder.pagamentos = new ArrayList<Pagamento>();

		for (long i = 1; i <= 10; i++) {
			Pagamento pagamento = new Pagamento(i, BigDecimal.valueOf(i), LocalDate.of(2020, 1, 1),
					"Pagamento teste " + i, divida);

			builder.pagamentos.add(pagamento);
		}

		return builder;
	}

	public static PagamentoBuilder mockCollectionPagamentosDTOBuilder() {
		PagamentoBuilder builder = new PagamentoBuilder();
		builder.pagamentosDTO = new ArrayList<PagamentoDTO>();

		for (long i = 1; i <= 10; i++) {
			PagamentoDTO pagamentoDTO = new PagamentoDTO(i, BigDecimal.valueOf(i), LocalDate.of(2020, 1, 1),
					"PagamentoDTO teste " + i, divida.getId());

			builder.pagamentosDTO.add(pagamentoDTO);
		}

		return builder;
	}

	public PagamentoDTO getPagamentoDTOInvalido() {
		this.pagamentoDTO.setData(null);

		return this.pagamentoDTO;
	}

	public Optional<Pagamento> getPagamentoOpt() {
		return Optional.of(this.pagamento);
	}
}
