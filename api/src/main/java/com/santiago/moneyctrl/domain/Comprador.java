package com.santiago.moneyctrl.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Comprador extends Usuario {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private boolean devedor;

	@Getter
	@Setter
	@OneToMany(mappedBy = "comprador")
	private List<Cota> lancamentos = new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "comprador")
	private List<Divida> dividas = new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "comprador")
	private List<Credito> creditos = new ArrayList<>();

	// Construtores
	public Comprador() {
	}

	public Comprador(Long id) {
		super(id);
	}

	public Comprador(Long id, String email, String nome, String password) {
		super(id, email, nome, password);
	}

	@Override
	public String toString() {
		return "Comprador [" + super.toString() + ", devedor=" + devedor + ", lancamentosEmpty=" + lancamentos.isEmpty()
				+ ", dividasEmpty=" + dividas.isEmpty() + ", creditosEmpty=" + creditos.isEmpty() + "]";
	}
}