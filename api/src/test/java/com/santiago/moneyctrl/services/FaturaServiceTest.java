package com.santiago.moneyctrl.services;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.builders.FaturaBuilder;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.repositories.FaturaRepository;
import com.santiago.moneyctrl.services.CartaoService;
import com.santiago.moneyctrl.services.FaturaService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class FaturaServiceTest extends BaseServiceTest<Fatura, FaturaDTO> {

	@InjectMocks
	private FaturaService faturaService;

	@Mock
	private FaturaRepository faturaRepository;

	@Mock
	private CartaoService cartaoService;

	@Before
	public void setup() {
		super.setup();
	}

	@Override
	public Fatura mockEntityBuilder() {
		return FaturaBuilder.mockFaturaBuilder().getFatura();
	}

	@Override
	public Optional<Fatura> mockEntityOptBuilder() {
		return FaturaBuilder.mockFaturaBuilder().getFaturaOpt();
	}

	@Override
	public List<Fatura> mockCollectionEntityListBuilder() {
		return FaturaBuilder.mockCollectionFaturasBuilder().getFaturas();
	}

	@Override
	public IServiceCrud<Fatura, FaturaDTO> getService() {
		return this.faturaService;
	}

	@Override
	public JpaRepository<Fatura, Long> getRepository() {
		return this.faturaRepository;
	}

	@Override
	public Fatura getEntityUpdate() {
		Fatura faturaUp = this.entity;
		faturaUp.setObservacao("Fatura Atualizada");
		return faturaUp;
	}
}
