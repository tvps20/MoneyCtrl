package com.santiago.moneyctrl.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.santiago.moneyctrl.builders.CotaBuilder;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.repositories.CotaRepository;
import com.santiago.moneyctrl.services.CompradorService;
import com.santiago.moneyctrl.services.CotaService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class CotaServiceTest extends BaseServiceTest<Cota, CotaDTO> {

	@InjectMocks
	private CotaService cotaService;

	@Mock
	private CotaRepository cotaRepository;

	@Mock
	private CompradorService compradorService;

	@Override
	public void setup() {
		super.setup();
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllCotasByLancamento() {
		when(this.cotaRepository.findByLancamentoId(1L)).thenReturn(this.entityList);

		List<Cota> result = this.cotaService.findAllCotaByLancamentoId(1L);

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarAllPageCotasByLancamento() {
		Page<Cota> cotaListPage = new PageImpl<>(this.entityList);
		PageRequest pageRequest = PageRequest.of(0, 24);

		when(this.cotaRepository.findByLancamentoId(1L, pageRequest)).thenReturn(cotaListPage);

		List<Cota> result = this.cotaService.findPageCotaByLancamentoId(1L, 0, 24, "ASC", "nome").getContent();

		assertFalse(result.isEmpty());
		assertEquals(result.size(), 10);
	}

	@Override
	public Cota mockEntityBuilder() {
		return CotaBuilder.mockCotaBuilder().getCota();
	}

	@Override
	public Optional<Cota> mockEntityOptBuilder() {
		return CotaBuilder.mockCotaBuilder().getCotaOpt();
	}

	@Override
	public List<Cota> mockCollectionEntityListBuilder() {
		return CotaBuilder.mockCollectionCotasBuilder().getCotas();
	}

	@Override
	public IServiceCrud<Cota, CotaDTO> getService() {
		return this.cotaService;
	}

	@Override
	public JpaRepository<Cota, Long> getRepository() {
		return this.cotaRepository;
	}

	@Override
	public Cota getEntityUpdate() {
		Cota cotaUp = this.entity;
		cotaUp.setValor(BigDecimal.valueOf(100));
		return cotaUp;
	}
}
