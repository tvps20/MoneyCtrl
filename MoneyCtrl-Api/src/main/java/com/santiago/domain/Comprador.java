package com.santiago.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Comprador extends Usuario {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private boolean devedor;
	
	@Getter
	@Setter
	@OneToMany(mappedBy = "comprador")
	private List<Divida> dividas = new ArrayList<>();

	// Construtores
	public Comprador() {
	}
	
	public Comprador(Long id) {
		super(id);
	}

	public Comprador(Long id, String email, String nome, String password) {
		super(id, email, nome, password);
	}	
}
