package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;
import com.santiago.repositories.CartaoRepository;

@Service
public class CartaoService extends BaseService<Cartao, CartaoDTO> {

	public CartaoService(CartaoRepository repository) {
		super(repository);
	}

	@Override
	public Cartao fromDTO(CartaoDTO dto) {
		return new Cartao(dto.getId(), dto.getNome());
	}

	@Override
	public void updateData(Cartao newObj, Cartao obj) {
		newObj.setNome(obj.getNome());
	}

	@Override
	public Class<Cartao> getTClass() {
		return Cartao.class;
	}
}
