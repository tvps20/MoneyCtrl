package com.santiago.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.santiago.domain.Cartao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString(callSuper = true)
@Slf4j
public class CartaoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{validation.erro.model.notEmpty}")
	@Length(min = 4, max = 80, message = "{validation.erro.model.length.nome}")
	@Getter
	@Setter
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
		log.info("Mapping 'Cartao' to 'CartaoDTO': " + this.getClass().getName());
		this.nome = cartao.getNome();
		this.createdAt = cartao.getCreatedAt();
		this.updatedAt = cartao.getUpdatedAt();
		this.faturas = cartao.getFaturas().stream().map(obj -> new FaturaDTO(obj)).collect(Collectors.toList());
	}
}
