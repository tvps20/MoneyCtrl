package com.santiago.services;

import java.util.List;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.builders.CartaoBuilder;
import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;
import com.santiago.repositories.CartaoRepository;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class CartaoServiceTest extends BaseServiceTest<Cartao, CartaoDTO> {

	@InjectMocks
	private CartaoService cartaoService;

	@Mock
	private CartaoRepository cartaoRepository;

	@Mock
	private BandeiraService bandeiraService;

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public Cartao mockEntityBuilder() {
		return CartaoBuilder.mockCartaoBuilder().getCartao();
	}

	@Override
	public Optional<Cartao> mockEntityOptBuilder() {
		return CartaoBuilder.mockCartaoBuilder().getCartaoOpt();
	}

	@Override
	public List<Cartao> mockCollectionEntityListBuilder() {
		return CartaoBuilder.mockCollectionCartoesBuilder().getCartoes();
	}

	@Override
	public IServiceCrud<Cartao, CartaoDTO> getService() {
		return this.cartaoService;
	}

	@Override
	public JpaRepository<Cartao, Long> getRepository() {
		return this.cartaoRepository;
	}

	@Override
	public Cartao getEntityUpdate() {
		Cartao cartaoUp = this.entity;
		cartaoUp.setNome("Cartao Atualizado");
		return cartaoUp;
	}
}
