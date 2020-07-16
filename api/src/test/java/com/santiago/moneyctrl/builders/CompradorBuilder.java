package com.santiago.moneyctrl.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.dtos.CompradorDTO;

import lombok.Getter;

public class CompradorBuilder {

	@Getter
	private Comprador comprador;

	@Getter
	private CompradorDTO compradorDTO;

	@Getter
	private List<Comprador> compradores;

	@Getter
	private List<CompradorDTO> compradoresDTO;

	// Construtores
	private CompradorBuilder() {

	}

	// Metodos
	public static CompradorBuilder mockCompradorBuilder() {
		CompradorBuilder builder = new CompradorBuilder();
		builder.comprador = new Comprador(1L, "teste@email.com", "Comprador teste", "123");

		return builder;
	}

	public static CompradorBuilder mockCompradorDTOBuilder() {
		CompradorBuilder builder = new CompradorBuilder();
		builder.compradorDTO = new CompradorDTO(1L, "teste@email.com", "Comprador teste", "123");

		return builder;
	}

	public static CompradorBuilder mockCollectionCompradoresBuilder() {
		CompradorBuilder builder = new CompradorBuilder();
		builder.compradores = new ArrayList<Comprador>();

		for (long i = 1; i <= 10; i++) {
			Comprador comprador = new Comprador(i, "teste" + i + "@email.com", "Comprador teste " + i, "123" + i);

			builder.compradores.add(comprador);
		}

		return builder;
	}

	public static CompradorBuilder mockCollectionCompradoresDTOBuilder() {
		CompradorBuilder builder = new CompradorBuilder();
		builder.compradoresDTO = new ArrayList<CompradorDTO>();

		for (long i = 1; i <= 10; i++) {
			CompradorDTO compradorDTO = new CompradorDTO(i, "teste" + i + "@email.com", "Comprador teste " + i,
					"123" + i);

			builder.compradoresDTO.add(compradorDTO);
		}

		return builder;
	}

	public CompradorDTO getCompradorDTOInvalido() {
		this.compradorDTO.setEmail(null);

		return this.compradorDTO;
	}

	public Optional<Comprador> getCompradorOpt() {
		return Optional.of(this.comprador);
	}
}
