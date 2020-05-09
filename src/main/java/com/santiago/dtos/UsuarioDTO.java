package com.santiago.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.domain.Usuario;
import com.santiago.services.UsuarioService;
import com.santiago.services.validation.CustomUnique;

import lombok.Getter;
import lombok.Setter;

public class UsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@CustomUnique(classType = UsuarioService.class)
	@Email(message = "{validation.erro.model.email}")
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	private String email;

	@Getter
	@Setter
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 4, max = 80, message = "{validation.erro.model.length.nome}")
	private String nome;

	@Getter
	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	private String password;

	// Construtores
	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Long id, String email, String nome, String password) {
		super(id);
		this.email = email;
		this.nome = nome;
		this.password = password;
	}

	public UsuarioDTO(Usuario usuario) {
		super(usuario.getId());
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.setCreatedAt(usuario.getCreatedAt());
		this.setUpdatedAt(usuario.getUpdatedAt());
	}
}
