package com.santiago.moneyctrl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santiago.moneyctrl.domain.enuns.TipoPerfil;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(unique = true, nullable = false)
	private String email;

	@Getter
	@Setter
	private String nome;

	@Getter
	@Setter
	@JsonIgnore
	private String password;

	@Getter
	@Setter
	@CollectionTable(name = "PERFIS")
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<TipoPerfil> perfis = new HashSet<>();

	// Construtores
	public Usuario() {
		this.perfis.add(TipoPerfil.USUARIO);
	}

	public Usuario(Long id) {
		super(id);
	}

	public Usuario(Long id, String email, String nome, String password) {
		super(id);
		this.email = email;
		this.nome = nome;
		this.password = password;
		this.perfis.add(TipoPerfil.USUARIO);
	}

	public Usuario(Long id, String email, String nome, String password, TipoPerfil... perfis) {
		super(id);
		this.email = email;
		this.nome = nome;
		this.password = password;

		for (TipoPerfil tipoPerfil : perfis) {
			if (!this.perfis.contains(tipoPerfil)) {
				this.perfis.add(tipoPerfil);
			}
		}
	}

	@Override
	public String toString() {
		return "Usuario [" + super.toString() + ", email=" + email + ", nome=" + nome + ", password=" + password + ", perfis=" + perfis + "]";
	}
}
