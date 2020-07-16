package com.santiago.moneyctrl.services;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.moneyctrl.builders.BandeiraBuilder;
import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.dtos.BandeiraDTO;
import com.santiago.moneyctrl.repositories.BandeiraRepository;
import com.santiago.moneyctrl.services.BandeiraService;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class BandeiraServiceTest extends BaseServiceTest<Bandeira, BandeiraDTO> {

	@InjectMocks
	private BandeiraService bandeiraService;

	@Mock
	private BandeiraRepository bandeiraRepository;

	@Before
	public void setup() {
		super.setup();
	}

	@Override
	public void deveRetornarException_QuandoInserirEntity() {
		// TODO Auto-generated method stub
		super.deveRetornarException_QuandoInserirEntity();
	}

	@Override
	public Bandeira mockEntityBuilder() {
		return BandeiraBuilder.mockBandeiraBuilder().getBandeira();
	}

	@Override
	public Optional<Bandeira> mockEntityOptBuilder() {
		return BandeiraBuilder.mockBandeiraBuilder().getBandeiraOpt();
	}

	@Override
	public List<Bandeira> mockCollectionEntityListBuilder() {
		return BandeiraBuilder.mockCollectionBandeirasBuilder().getBandeiras();
	}

	@Override
	public IServiceCrud<Bandeira, BandeiraDTO> getService() {
		return this.bandeiraService;
	}

	@Override
	public JpaRepository<Bandeira, Long> getRepository() {
		return this.bandeiraRepository;
	}

	@Override
	public Bandeira getEntityUpdate() {
		Bandeira bandeiraUp = this.entity;
		bandeiraUp.setNome("Bandeira Atualizada");
		return bandeiraUp;
	}
}
