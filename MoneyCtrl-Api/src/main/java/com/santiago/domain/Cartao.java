package com.santiago.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Cartao extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String nome;

	@Getter
	@Setter
	@OneToMany(mappedBy = "cartao")
	@JsonIgnore
	private List<Fatura> faturas = new ArrayList<>();

	// Construtores
	public Cartao() {
	}

	public Cartao(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
}
