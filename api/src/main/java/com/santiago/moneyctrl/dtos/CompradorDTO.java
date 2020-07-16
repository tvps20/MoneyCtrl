package com.santiago.moneyctrl.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.dtos.enuns.TipoEntity;

import lombok.Getter;
import lombok.Setter;

public class CompradorDTO extends UsuarioDTO {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private String sobrenome;

	@Getter
	@Setter
	private List<CotaDTO> lancamentos = new ArrayList<>();

	@Getter
	@Setter
	private List<DividaDTO> dividas = new ArrayList<>();

	@Getter
	@Setter
	private List<CreditoDTO> creditos = new ArrayList<>();

	public CompradorDTO() {
	}

	public CompradorDTO(Long id, String nome, String username, String password) {
		super(id, nome, username, password);
	}

	public CompradorDTO(Comprador comprador) {
		super(comprador);
		
		this.sobrenome = comprador.getSobrenome();
		this.dividas = comprador.getDividas().stream().map(obj -> new DividaDTO(obj)).collect(Collectors.toList());
		this.creditos = comprador.getCreditos().stream().map(obj -> new CreditoDTO(obj)).collect(Collectors.toList());
		this.lancamentos = comprador.getLancamentos().stream().map(obj -> new CotaDTO(obj))
				.collect(Collectors.toList());
		
		this.createdAt = comprador.getCreatedAt();
		this.updatedAt = comprador.getUpdatedAt();
		this.ativo = comprador.isAtivo();
		this.tipo = TipoEntity.COMPRADOR;
	}

	// Getters and Setters
	public BigDecimal getTotalDividas() {
		double total = this.dividas.stream().filter(x -> !x.isPaga()).mapToDouble(x -> x.getValorDivida().doubleValue()).sum();

		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal getTotalPagamentos() {
		double total = this.dividas.stream().filter(x -> !x.isPaga()).mapToDouble(x -> x.getTotalPagamentos().doubleValue()).sum();
		
		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getTotalCreditos() {
		double total = this.creditos.stream().filter(x -> x.isDisponivel()).mapToDouble(x -> x.getValor().doubleValue()).sum();

		return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public boolean getDevedor() {
		List<DividaDTO> dividas = this.dividas.stream().filter(x -> !x.isPaga()).collect(Collectors.toList());
		
		if(dividas.size() > 0) {
			return true;
		}
		
		return false;
	}
}
