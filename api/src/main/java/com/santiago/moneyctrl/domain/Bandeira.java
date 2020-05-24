package com.santiago.moneyctrl.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Bandeira extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(unique = true, nullable = false)
	private String nome;

	@Getter
	@Setter
	@OneToMany(mappedBy = "bandeira")
	private List<Cartao> cartoes = new ArrayList<>();

	// Construtores
	public Bandeira() {
	}

	public Bandeira(Long id) {
		super(id);
	}

	public Bandeira(Long id, String nome) {
		super(id);
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Bandeira [" + super.toString() + ", nome=" + nome + ", cartoesEmpty=" + cartoes.isEmpty() + "]";
	}

}
