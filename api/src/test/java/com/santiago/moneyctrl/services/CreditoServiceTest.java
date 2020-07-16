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

import com.santiago.moneyctrl.builders.CreditoBuilder;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.dtos.CreditoDTO;
import com.santiago.moneyctrl.repositories.CreditoRepository;
import com.santiago.moneyctrl.services.CreditoService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class CreditoServiceTest extends BaseServiceTest<Credito, CreditoDTO> {

	@InjectMocks
	private CreditoService creditoService;

	@Mock
	private CreditoRepository creditoRepository;

	@Override
	public void setup() {
		super.setup();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllCreditosByLancamento() {
		when(this.creditoRepository.findByCompradorId(1L)).thenReturn(this.entityList);

		List<Credito> result = this.creditoService.findAllCreditoByCompradorId(1L);

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPageCreditosByLancamento() {
		Page<Credito> cotaListPage = new PageImpl<>(this.entityList);
		PageRequest pageRequest = PageRequest.of(0, 24);

		when(this.creditoRepository.findByCompradorId(1L, pageRequest)).thenReturn(cotaListPage);

		List<Credito> result = this.creditoService.findPageByCompradorId(1L, 0, 24, "ASC", "nome").getContent();

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Override
	public Credito mockEntityBuilder() {
		return CreditoBuilder.mockCreditoBuilder().getCredito();
	}

	@Override
	public Optional<Credito> mockEntityOptBuilder() {
		return CreditoBuilder.mockCreditoBuilder().getCreditoOpt();
	}

	@Override
	public List<Credito> mockCollectionEntityListBuilder() {
		return CreditoBuilder.mockCollectionCreditosBuilder().getCreditos();
	}

	@Override
	public IServiceCrud<Credito, CreditoDTO> getService() {
		return this.creditoService;
	}

	@Override
	public JpaRepository<Credito, Long> getRepository() {
		return this.creditoRepository;
	}

	@Override
	public Credito getEntityUpdate() {
		Credito creditoUp = this.entity;
		creditoUp.setDescricao("Credito Atualizado");
		return creditoUp;
	}
}
