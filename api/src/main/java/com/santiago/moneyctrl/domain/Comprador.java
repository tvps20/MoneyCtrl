package com.santiago.moneyctrl.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Comprador extends Usuario {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String sobrenome;

	@Getter
	@Setter
	@OneToMany(cascade = {CascadeType.REMOVE }, mappedBy = "comprador")
	@OnDelete(action = OnDeleteAction.CASCADE) // Gera o “cascade delete” na constraint no banco de dados evitando várias querys para delete
	private List<Cota> lancamentos = new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(cascade = {CascadeType.REMOVE }, mappedBy = "comprador")
	@OnDelete(action = OnDeleteAction.CASCADE) // Gera o “cascade delete” na constraint no banco de dados evitando várias querys para delete
	private List<Divida> dividas = new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(cascade = {CascadeType.REMOVE }, mappedBy = "comprador")
	@OnDelete(action = OnDeleteAction.CASCADE) // Gera o “cascade delete” na constraint no banco de dados evitando várias querys para delete
	private List<Credito> creditos = new ArrayList<>();

	// Construtores
	public Comprador() {
	}

	public Comprador(Long id) {
		super(id);
	}

	public Comprador(Long id, String nome, String username, String password) {
		super(id, nome, username, password);
	}
	
	public Comprador(Long id, String nome, String username, String password, String email) {
		super(id, nome, username, password, email);
	}
	
	public Comprador(Long id, String nome, String username, String password, String email, String sobrenome) {
		super(id, nome, username, password, email);
		this.sobrenome = sobrenome;
	}

	@Override
	public String toString() {
		return "Comprador [" + super.toString() + ", sobrenome=" + sobrenome + ", lancamentosEmpty=" + lancamentos.isEmpty()
				+ ", dividasEmpty=" + dividas.isEmpty() + ", creditosEmpty=" + creditos.isEmpty() + "]";
	}
}
