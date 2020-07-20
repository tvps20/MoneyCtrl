package com.santiago.moneyctrl.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;
import com.santiago.moneyctrl.services.CartaoService;
import com.santiago.moneyctrl.services.validation.CustomUnique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class CartaoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@CustomUnique(classType = CartaoService.class)
	@NotEmpty(message = "{validation.erro.model.notEmpty.campo}")
	@Length(min = 3, max = 12, message = "{validation.erro.model.length.nome}")
	private String nome;
	
	@Getter
	@Setter
	@NotNull(message = "{validation.erro.model.notEmpty.campo}")
	protected BandeiraDTO bandeira;

	@Getter
	@Setter
	private List<FaturaDTO> faturas = new ArrayList<>();

	// Construtores
	public CartaoDTO() {
	}
	
	public CartaoDTO(Long id) {
		super(id);
	}

	public CartaoDTO(Long id, String nome, BandeiraDTO bandeira) {
		super(id);
		this.nome = nome;
		this.bandeira = bandeira;
	}

	public CartaoDTO(Cartao cartao) {
		super(cartao.getId());
		
		this.nome = cartao.getNome();
		this.bandeira = new BandeiraDTO(cartao.getBandeira());
		this.bandeira.setCartoes(null);
		this.faturas = cartao.getFaturas().stream().map(obj -> new FaturaDTO(obj)).collect(Collectors.toList());
		
		this.createdAt = cartao.getCreatedAt();
		this.updatedAt = cartao.getUpdatedAt();
		this.ativo = cartao.isAtivo();
		this.tipo = TipoEntity.CARTAO;
	}
}
