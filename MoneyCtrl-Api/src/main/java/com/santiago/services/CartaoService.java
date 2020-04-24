package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;
import com.santiago.repositories.CartaoRepository;
import com.santiago.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartaoService extends BaseService<Cartao, CartaoDTO> implements IServiceValidator {
	
	public CartaoService(CartaoRepository repository) {
		super(repository);
	}

	@Override
	public Cartao fromDTO(CartaoDTO dto) {
		log.info("Mapping 'CartaoDTO' to 'Cartao': " + this.getTClass().getName());
		return new Cartao(dto.getId(), dto.getNome());
	}

	@Override
	public void updateData(Cartao newObj, Cartao obj) {
		log.info("Parse cartao from newCartao: " + this.getTClass().getName());
		newObj.setNome(obj.getNome());
	}

	@Override
	public Class<Cartao> getTClass() {
		return Cartao.class;
	}
	
	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("Find by unique value: " + campo);
		return ((CartaoRepository) this.repository).verificarCampoUnico(campo);
	}
}
