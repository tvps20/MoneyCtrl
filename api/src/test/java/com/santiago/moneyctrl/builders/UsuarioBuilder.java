package com.santiago.moneyctrl.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.dtos.UsuarioDTO;

import lombok.Getter;

public class UsuarioBuilder {

	@Getter
	private Usuario usuario;

	@Getter
	private UsuarioDTO usuarioDTO;

	@Getter
	private List<Usuario> usuarios;

	@Getter
	private List<UsuarioDTO> usuariosDTO;

	// Construtores
	private UsuarioBuilder() {
	}

	// Metodos
	public static UsuarioBuilder mockUsuarioBuilder() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario(1L, "teste@email.com", "Usuario Teste", "123");

		return builder;
	}

	public static UsuarioBuilder mockUsuarioDTOBuilder() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuarioDTO = new UsuarioDTO(1L, "teste@email.com", "UsuarioDTO Teste", "123");

		return builder;
	}

	public static UsuarioBuilder mockCollectionUsuariosBuilder() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuarios = new ArrayList<Usuario>();

		for (long i = 1; i <= 10; i++) {
			Usuario Usuario = new Usuario(i, "teste" + i + "@email.com", "Usuario Teste " + i, "123" + i);

			builder.usuarios.add(Usuario);
		}

		return builder;
	}

	public static UsuarioBuilder mockCollectionUsuariosDTOBuilder() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuariosDTO = new ArrayList<UsuarioDTO>();

		for (long i = 1; i <= 10; i++) {
			UsuarioDTO UsuarioDTO = new UsuarioDTO(i, "teste" + i + "@email.com", "Usuario Teste " + i, "123" + i);

			builder.usuariosDTO.add(UsuarioDTO);
		}

		return builder;
	}

	public UsuarioDTO getUsuarioDTOInvalido() {
		this.usuarioDTO.setNome(null);
		this.usuarioDTO.setEmail(null);
		this.usuarioDTO.setPassword(null);
		return this.usuarioDTO;
	}

	public Optional<Usuario> getUsuarioOpt() {
		return Optional.of(this.usuario);
	}
}
