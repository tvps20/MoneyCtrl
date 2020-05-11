package com.santiago.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.santiago.domain.Bandeira;
import com.santiago.services.BandeiraService;
import com.santiago.services.validation.CustomUnique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class BandeiraDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@CustomUnique(classType = BandeiraService.class)
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 4, max = 80, message = "{validation.erro.model.length.nome}")
	private String nome;

	@Getter
	@Setter
	private List<CartaoDTO> cartoes = new ArrayList<>();

	// Construtores
	public BandeiraDTO() {
	}

	public BandeiraDTO(Long id, String nome) {
		super(id);
		this.nome = nome;
	}

	public BandeiraDTO(Bandeira bandeira) {
		super(bandeira.getId());
		this.nome = bandeira.getNome();
		this.createdAt = bandeira.getCreatedAt();
		this.updatedAt = bandeira.getUpdatedAt();
		this.cartoes = bandeira.getCartoes().stream().map(obj -> new CartaoDTO(obj)).collect(Collectors.toList());
	}
}
