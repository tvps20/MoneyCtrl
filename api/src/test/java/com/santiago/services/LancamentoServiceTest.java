package com.santiago.services;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.builders.LancamentoBuilder;
import com.santiago.domain.Lancamento;
import com.santiago.dtos.LancamentoDTO;
import com.santiago.repositories.LancamentoRepository;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class LancamentoServiceTest extends BaseServiceTest<Lancamento, LancamentoDTO> {

	@InjectMocks
	private LancamentoService lancamentoService;

	@Mock
	private LancamentoRepository lancamentoRepository;

	@Mock
	private CotaService cotaService;

	@Mock
	private CompradorService compradorService;

	@Mock
	private FaturaService faturaService;

	@Before
	public void setup() {
		super.setup();
	}

	@Override
	public Lancamento mockEntityBuilder() {
		return LancamentoBuilder.mockLancamentoBuilder().getLancamento();
	}

	@Override
	public Optional<Lancamento> mockEntityOptBuilder() {
		return LancamentoBuilder.mockLancamentoBuilder().getLancamentoOpt();
	}

	@Override
	public List<Lancamento> mockCollectionEntityListBuilder() {
		return LancamentoBuilder.mockCollectionLancamentosBuilder().getLancamentos();
	}

	@Override
	public IServiceCrud<Lancamento, LancamentoDTO> getService() {
		return this.lancamentoService;
	}

	@Override
	public JpaRepository<Lancamento, Long> getRepository() {
		return this.lancamentoRepository;
	}

	@Override
	public Lancamento getEntityUpdate() {
		Lancamento lancamentoUp = this.entity;
		lancamentoUp.setObservacao("Lancamento Atualizada");
		return lancamentoUp;
	}
}
