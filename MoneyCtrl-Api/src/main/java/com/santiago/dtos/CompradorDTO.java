package com.santiago.dtos;

import com.santiago.domain.Usuario;

import lombok.Getter;
import lombok.Setter;

public class CompradorDTO extends UsuarioDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private boolean devedor;

	public CompradorDTO() {
	}

	public CompradorDTO(Long id, String email, String nome, String password) {
		super(id, email, nome, password);
		this.devedor = false;
	}

	public CompradorDTO(Usuario obj) {
		super(obj);
		this.devedor = false;
	}
}
