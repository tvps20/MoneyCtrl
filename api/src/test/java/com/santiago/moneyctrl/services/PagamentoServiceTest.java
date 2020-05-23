package com.santiago.moneyctrl.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.builders.PagamentoBuilder;
import com.santiago.moneyctrl.domain.Pagamento;
import com.santiago.moneyctrl.dtos.PagamentoDTO;
import com.santiago.moneyctrl.repositories.PagamentoRepository;
import com.santiago.moneyctrl.services.PagamentoService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class PagamentoServiceTest extends BaseServiceTest<Pagamento, PagamentoDTO> {

	@InjectMocks
	private PagamentoService pagamentoService;

	@Mock
	private PagamentoRepository pagamentoRepository;

	@Override
	public void setup() {
		super.setup();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllCreditosByLancamento() {
		when(this.pagamentoRepository.findByDividaId(1L)).thenReturn(this.entityList);

		List<Pagamento> result = this.pagamentoService.findAllPagamentoByDividaId(1L);

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPageCreditosByLancamento() {
		Page<Pagamento> cotaListPage = new PageImpl<>(this.entityList);
		PageRequest pageRequest = PageRequest.of(0, 24);

		when(this.pagamentoRepository.findByDividaId(1L, pageRequest)).thenReturn(cotaListPage);

		List<Pagamento> result = this.pagamentoService.findPageByDividaId(1L, 0, 24, "ASC", "nome").getContent();

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Override
	public Pagamento mockEntityBuilder() {
		return PagamentoBuilder.mockPagamentoBuilder().getPagamento();
	}

	@Override
	public Optional<Pagamento> mockEntityOptBuilder() {
		return PagamentoBuilder.mockPagamentoBuilder().getPagamentoOpt();
	}

	@Override
	public List<Pagamento> mockCollectionEntityListBuilder() {
		return PagamentoBuilder.mockCollectionPagamentosBuilder().getPagamentos();
	}

	@Override
	public IServiceCrud<Pagamento, PagamentoDTO> getService() {
		return this.pagamentoService;
	}

	@Override
	public JpaRepository<Pagamento, Long> getRepository() {
		return this.pagamentoRepository;
	}

	@Override
	public Pagamento getEntityUpdate() {
		Pagamento pagamentoUp = this.entity;
		pagamentoUp.setObservacao("Pagamento Atualizado");
		return pagamentoUp;
	}
}
