package com.santiago.moneyctrl.services;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.builders.DividaBuilder;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.dtos.DividaDTO;
import com.santiago.moneyctrl.repositories.DividaRepository;
import com.santiago.moneyctrl.services.CompradorService;
import com.santiago.moneyctrl.services.DividaService;
import com.santiago.moneyctrl.services.FaturaService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class DividaServiceTest extends BaseServiceTest<Divida, DividaDTO> {

	@InjectMocks
	private DividaService dividaService;

	@Mock
	private DividaRepository dividaRepository;

	@Mock
	private FaturaService faturaService;
	
	@Mock
	private CompradorService compradorService;

	@Before
	public void setup() {
		super.setup();
	}

	@Override
	public Divida mockEntityBuilder() {
		return DividaBuilder.mockDividaBuilder().getDivida();
	}

	@Override
	public Optional<Divida> mockEntityOptBuilder() {
		return DividaBuilder.mockDividaBuilder().getDividaOpt();
	}

	@Override
	public List<Divida> mockCollectionEntityListBuilder() {
		return DividaBuilder.mockCollectionDividasBuilder().getDividas();
	}

	@Override
	public IServiceCrud<Divida, DividaDTO> getService() {
		return this.dividaService;
	}

	@Override
	public JpaRepository<Divida, Long> getRepository() {
		return this.dividaRepository;
	}

	@Override
	public Divida getEntityUpdate() {
		Divida dividaUp = this.entity;
		dividaUp.setDescricao("Divida Paga");
		return dividaUp;
	}
}
