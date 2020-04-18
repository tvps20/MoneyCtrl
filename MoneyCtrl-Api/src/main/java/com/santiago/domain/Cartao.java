package com.santiago.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Cartao extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String nome;

	// Construtores
	public Cartao() {
	}

	public Cartao(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
}
