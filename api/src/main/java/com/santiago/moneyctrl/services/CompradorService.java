package com.santiago.moneyctrl.services;

import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.dtos.CompradorDTO;
import com.santiago.moneyctrl.repositories.CompradorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompradorService extends BaseService<Comprador, CompradorDTO> {

	public CompradorService(CompradorRepository repository) {
		super(repository);
		BaseService.baseLog = CompradorService.log;
	}

	@Override
	public Comprador fromDTO(CompradorDTO dto) {
		log.info("[Mapping] - 'CompradorDTO' to 'Comprador'. Id: " + dto.getId());
		Comprador comprador = new Comprador(dto.getId(), dto.getEmail(), dto.getNome(), dto.getPassword());

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return comprador;
	}

	@Override
	public void updateData(Comprador newObj, Comprador obj) {
		log.info("[Parse] - 'comprador' from 'newComprador'. Id: " + newObj.getId());
		newObj.setEmail(obj.getEmail());
		newObj.setPassword(obj.getPassword());
		newObj.setNome(obj.getNome());
		newObj.setDevedor(obj.isDevedor());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
