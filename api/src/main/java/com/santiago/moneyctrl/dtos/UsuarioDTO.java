package com.santiago.moneyctrl.dtos;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoAcesso;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;
import com.santiago.moneyctrl.services.UsuarioService;
import com.santiago.moneyctrl.services.validation.CustomUnique;

import lombok.Getter;
import lombok.Setter;

public class UsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 3, max = 12, message = "{validation.erro.model.length.nome}")
	private String nome;
	
	@Getter
	@Setter
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 5, max = 10, message = "{validation.erro.model.length.nome}")
	private String username;
	
	@Getter
	@Setter
	@CustomUnique(classType = UsuarioService.class)
	@Email(message = "{validation.erro.model.email}")
	private String email;
	
	@Getter
	@Setter
	private LocalDate verificaoEmail;
	
	@Getter
	@Setter
	private String tipo;

	@Getter
	@Setter
	@JsonInclude(Include.NON_NULL) // Não faz a serialização se o valor for null
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	private String password;
	
	@Getter
	@Setter
	private Set<TipoAcesso> acesso;
	
	@Getter
	@Setter
	private Set<TipoRoles> roles;

	// Construtores
	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Long id, String nome, String username, String password) {
		super(id);
		this.nome = nome;
		this.username = username;
		this.password = password;
	}

	public UsuarioDTO(Usuario usuario) {
		super(usuario.getId());
		this.nome = usuario.getNome();
		this.username = usuario.getUsername();
		this.email = usuario.getEmail();
		this.verificaoEmail = usuario.getVerificaoEmail();
		this.tipo = "Usuario";
		this.acesso = usuario.getAcesso();
		this.roles = usuario.getRoles();
		this.setCreatedAt(usuario.getCreatedAt());
		this.setUpdatedAt(usuario.getUpdatedAt());
	}
}
