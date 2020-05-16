package com.santiago.builders;

import java.util.ArrayList;
import java.util.List;

import com.santiago.domain.Bandeira;
import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;

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
		builder.cartaoDTO = new CartaoDTO(1L, "nubank", bandeira.getId());

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
			CartaoDTO cartaoDTO = new CartaoDTO(i, "nubank" + i, bandeira.getId());

			builder.cartoesDTO.add(cartaoDTO);
		}

		return builder;
	}

	public CartaoDTO getCartaoDTOInvalido() {
		this.cartaoDTO.setNome(null);
		this.cartaoDTO.setBandeiraId(null);
		
		return this.cartaoDTO;
	}
}
