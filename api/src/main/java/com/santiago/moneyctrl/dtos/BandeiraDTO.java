package com.santiago.moneyctrl.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;
import com.santiago.moneyctrl.services.BandeiraService;
import com.santiago.moneyctrl.services.validation.CustomUnique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class BandeiraDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@CustomUnique(classType = BandeiraService.class)
	@NotEmpty(message = "{validation.erro.model.notEmpty.campo}")
	@Length(min = 3, max = 12, message = "{validation.erro.model.length.nome}")
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
		this.cartoes = bandeira.getCartoes().stream().map(obj -> new CartaoDTO(obj.getId()))
				.collect(Collectors.toList());
		
		this.createdAt = bandeira.getCreatedAt();
		this.updatedAt = bandeira.getUpdatedAt();
		this.ativo = bandeira.isAtivo();
		this.tipo = TipoEntity.BANDEIRA;
	}
}
