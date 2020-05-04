package com.santiago.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Bandeira extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(unique = true, nullable = false)
	private String nome;

	// Construtores
	public Bandeira() {
	}

	public Bandeira(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
}
