package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.dtos.CompradorDTO;
import com.santiago.repositories.CompradorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompradorService extends BaseService<Comprador, CompradorDTO> {

	public CompradorService(CompradorRepository repository) {
		super(repository);
	}

	@Override
	public Comprador fromDTO(CompradorDTO dto) {
		log.info("Mapping 'CompradorDTO' to 'Comprador': " + this.getTClass().getName());
		return new Comprador(dto.getId(), dto.getEmail(), dto.getNome(), dto.getPassword());
	}

	@Override
	public void updateData(Comprador newObj, Comprador obj) {
		log.info("Parse 'comprador' from 'newComprador': " + this.getTClass().getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPassword(obj.getPassword());
		newObj.setNome(obj.getNome());
		newObj.setDevedor(obj.isDevedor());
	}

	@Override
	public Class<Comprador> getTClass() {
		return Comprador.class;
	}
}
