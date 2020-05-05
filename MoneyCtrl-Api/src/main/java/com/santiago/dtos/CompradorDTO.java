package com.santiago.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.santiago.domain.Comprador;

import lombok.Getter;
import lombok.Setter;

public class CompradorDTO extends UsuarioDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private boolean devedor;

	@Getter
	@Setter
	private List<DividaDTO> dividas = new ArrayList<>();

	@Getter
	@Setter
	private List<CreditoDTO> creditos = new ArrayList<>();

	public CompradorDTO() {
	}

	public CompradorDTO(Long id, String email, String nome, String password) {
		super(id, email, nome, password);
		this.devedor = false;
	}

	public CompradorDTO(Comprador comprador) {
		super(comprador);
		this.devedor = false;
		this.dividas = comprador.getDividas().stream().map(obj -> new DividaDTO(obj)).collect(Collectors.toList());
		this.creditos = comprador.getCreditos().stream().map(obj -> new CreditoDTO(obj)).collect(Collectors.toList());
	}
}
