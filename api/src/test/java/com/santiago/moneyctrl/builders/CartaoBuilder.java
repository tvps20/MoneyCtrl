package com.santiago.moneyctrl.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.dtos.BandeiraDTO;
import com.santiago.moneyctrl.dtos.CartaoDTO;

import lombok.Getter;

public class CartaoBuilder {

	@Getter
	private Cartao cartao;

	@Getter
	private CartaoDTO cartaoDTO;

	@Getter
	private List<Cartao> cartoes;

	@Getter
	private List<CartaoDTO> cartoesDTO;

	private static Bandeira bandeira;

	// Construtores
	private CartaoBuilder() {
		bandeira = BandeiraBuilder.mockBandeiraBuilder().getBandeira();
	}

	// Metodos
	public static CartaoBuilder mockCartaoBuilder() {
		CartaoBuilder builder = new CartaoBuilder();
		builder.cartao = new Cartao(1L, "nubank", bandeira);

		return builder;
	}

	public static CartaoBuilder mockCartaoDTOBuilder() {
		CartaoBuilder builder = new CartaoBuilder();
		builder.cartaoDTO = new CartaoDTO(1L, "nubank", new BandeiraDTO(bandeira));

		return builder;
	}

	public static CartaoBuilder mockCollectionCartoesBuilder() {
		CartaoBuilder builder = new CartaoBuilder();
		builder.cartoes = new ArrayList<Cartao>();

		for (long i = 1; i <= 10; i++) {
			Cartao cartao = new Cartao(i, "nubank" + i, bandeira);

			builder.cartoes.add(cartao);
		}

		return builder;
	}

	public static CartaoBuilder mockCollectionCartoesDTOBuilder() {
		CartaoBuilder builder = new CartaoBuilder();
		builder.cartoesDTO = new ArrayList<CartaoDTO>();

		for (long i = 1; i <= 10; i++) {
			CartaoDTO cartaoDTO = new CartaoDTO(i, "nubank" + i, new BandeiraDTO(bandeira));

			builder.cartoesDTO.add(cartaoDTO);
		}

		return builder;
	}

	public CartaoDTO getCartaoDTOInvalido() {
		this.cartaoDTO.setNome(null);
		this.cartaoDTO.setBandeira(null);

		return this.cartaoDTO;
	}

	public Optional<Cartao> getCartaoOpt() {
		return Optional.of(this.cartao);
	}
}
