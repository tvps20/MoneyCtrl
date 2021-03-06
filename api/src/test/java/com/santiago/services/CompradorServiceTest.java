package com.santiago.services;

import java.util.List;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.builders.CompradorBuilder;
import com.santiago.domain.Comprador;
import com.santiago.dtos.CompradorDTO;
import com.santiago.repositories.CompradorRepository;
import com.santiago.services.interfaces.IServiceCrud;

@RunWith(MockitoJUnitRunner.class)
public class CompradorServiceTest extends BaseServiceTest<Comprador, CompradorDTO> {

	@InjectMocks
	private CompradorService compradorService;

	@Mock
	private CompradorRepository compradorRepository;

	@Override
	public Comprador mockEntityBuilder() {
		return CompradorBuilder.mockCompradorBuilder().getComprador();
	}

	@Override
	public Optional<Comprador> mockEntityOptBuilder() {
		return CompradorBuilder.mockCompradorBuilder().getCompradorOpt();
	}

	@Override
	public List<Comprador> mockCollectionEntityListBuilder() {
		return CompradorBuilder.mockCollectionCompradoresBuilder().getCompradores();
	}

	@Override
	public IServiceCrud<Comprador, CompradorDTO> getService() {
		return this.compradorService;
	}

	@Override
	public JpaRepository<Comprador, Long> getRepository() {
		return this.compradorRepository;
	}

	@Override
	public Comprador getEntityUpdate() {
		Comprador compradorUp = this.entity;
		compradorUp.setNome("Comprador Atualizado");
		return compradorUp;
	}
}
