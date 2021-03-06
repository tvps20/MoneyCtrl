package com.santiago.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotNull(message = "{validation.erro.model.notEmpty}")
	protected Long bandeiraId;

	@Getter
	@Setter
	private List<FaturaDTO> faturas = new ArrayList<>();

	// Construtores
	public CartaoDTO() {
	}

	public CartaoDTO(Long id, String nome, Long bandeira) {
		super(id);
		this.nome = nome;
		this.bandeiraId = bandeira;
	}

	public CartaoDTO(Cartao cartao) {
		super(cartao.getId());
		this.nome = cartao.getNome();
		this.bandeiraId = cartao.getBandeira().getId();
		this.createdAt = cartao.getCreatedAt();
		this.updatedAt = cartao.getUpdatedAt();
		this.faturas = cartao.getFaturas().stream().map(obj -> new FaturaDTO(obj)).collect(Collectors.toList());
	}
}
