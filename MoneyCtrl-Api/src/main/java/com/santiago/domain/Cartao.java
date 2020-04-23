package com.santiago.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Cartao extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(unique = true)
	private String nome;

	@Getter
	@Setter
	@OneToMany(mappedBy = "cartao")
	private List<Fatura> faturas = new ArrayList<>();

	// Construtores
	public Cartao() {
	}

	public Cartao(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
}
