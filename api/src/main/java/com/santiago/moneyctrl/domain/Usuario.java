package com.santiago.moneyctrl.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santiago.moneyctrl.domain.enuns.TipoAcesso;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	@Column(unique = true)
	private String username;

	@Getter
	@Setter
	@Column(unique = true)
	private String email;

	@Getter
	@Setter
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate verificaoEmail;
	
	@Getter
	@Setter
	@JsonIgnore
	private String password;

	@Getter
	@Setter
	@CollectionTable(name = "ACESSO")
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<TipoAcesso> acesso = new HashSet<>();
	
	@Getter
	@Setter
	@CollectionTable(name = "ROLES")
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<TipoRoles> roles = new HashSet<>();

	// Construtores
	public Usuario() {
		this.roles.add(TipoRoles.USUARIO);
	}

	public Usuario(Long id) {
		super(id);
	}

	public Usuario(Long id, String nome, String username, String password) {
		super(id);
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.roles.add(TipoRoles.USUARIO);
		this.acesso.add(TipoAcesso.USERNAME);
	}

	public Usuario(Long id, String nome, String username, String password, TipoRoles... perfis) {
		super(id);
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.acesso.add(TipoAcesso.USERNAME);

		for (TipoRoles tipoPerfil : perfis) {
			if (!this.roles.contains(tipoPerfil)) {
				this.roles.add(tipoPerfil);
			}
		}
	}

	@Override
	public String toString() {
		return "Usuario [" + super.toString() + ", nome=" + nome + ", username=" + username + ", password=" + password + ", roles=" + roles + ", acesso=" + acesso +"]";
	}
}
