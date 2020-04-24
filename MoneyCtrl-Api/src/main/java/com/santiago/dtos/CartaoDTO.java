package com.santiago.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.santiago.domain.Cartao;
import com.santiago.services.CartaoService;
import com.santiago.services.validation.CustomUnique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class CartaoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@CustomUnique(classType = CartaoService.class)
	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 4, max = 80, message = "{validation.erro.model.length.nome}")
	private String nome;

	@Getter
	@Setter
	private List<FaturaDTO> faturas = new ArrayList<>();

	// Construtores
	public CartaoDTO() {
	}

	public CartaoDTO(Long id, String nome) {
		super(id);
		this.nome = nome;
	}

	public CartaoDTO(Cartao cartao) {
		super(cartao.getId());
		this.nome = cartao.getNome();
		this.createdAt = cartao.getCreatedAt();
		this.updatedAt = cartao.getUpdatedAt();
		this.faturas = cartao.getFaturas().stream().map(obj -> new FaturaDTO(obj)).collect(Collectors.toList());
	}
}
