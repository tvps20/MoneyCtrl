package com.santiago.builders;

import java.util.ArrayList;
import java.util.Collection;

import com.santiago.domain.Bandeira;
import com.santiago.dtos.BandeiraDTO;

import lombok.Getter;

public class BandeiraBuilder {

	@Getter
	private Bandeira bandeira;

	@Getter
	private BandeiraDTO bandeiraDTO;

	@Getter
	private Collection<Bandeira> bandeiras;

	@Getter
	private Collection<BandeiraDTO> bandeirasDTO;

	// Construtores
	private BandeiraBuilder() {

	}

	// Metodos
	public static BandeiraBuilder mockBandeiraBuilder() {
		BandeiraBuilder builder = new BandeiraBuilder();
		builder.bandeira = new Bandeira(1L, "mastercard");

		return builder;
	}

	public static BandeiraBuilder mockBandeiraDTOBuilder() {
		BandeiraBuilder builder = new BandeiraBuilder();
		builder.bandeiraDTO = new BandeiraDTO(1L, "mastercard");

		return builder;
	}

	public static BandeiraBuilder mockCollectionBandeirasBuilder() {
		BandeiraBuilder builder = new BandeiraBuilder();
		builder.bandeiras = new ArrayList<Bandeira>();

		for (long i = 1; i <= 10; i++) {
			Bandeira bandeira = new Bandeira(i, "bandeira " + i);

			builder.bandeiras.add(bandeira);
		}

		return builder;
	}

	public static BandeiraBuilder mockCollectionBandeirasDTOBuilder() {
		BandeiraBuilder builder = new BandeiraBuilder();
		builder.bandeirasDTO = new ArrayList<BandeiraDTO>();

		for (long i = 1; i <= 10; i++) {
			BandeiraDTO bandeiraDTO = new BandeiraDTO(i, "bandeira " + i);

			builder.bandeirasDTO.add(bandeiraDTO);
		}

		return builder;
	}

	public BandeiraDTO getBandeiraDTOInvalido() {
		this.bandeiraDTO.setNome(null);
		return this.bandeiraDTO;
	}
}
