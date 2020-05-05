package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Credito;
import com.santiago.dtos.CreditoDTO;
import com.santiago.repositories.CreditoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditoService extends BaseService<Credito, CreditoDTO> {

	public CreditoService(CreditoRepository repository) {
		super(repository);
	}

	@Override
	public Credito fromDTO(CreditoDTO dto) {
		log.info("Mapping 'CompradorDTO' to 'Comprador': " + this.getTClass().getName());
		return new Credito(dto.getId(), dto.getValor(), dto.getData(), new Comprador(dto.getCompradorId()));
	}

	@Override
	public void updateData(Credito newObj, Credito obj) {
		log.info("Parse 'comprador' from 'newComprador': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
	}

	@Override
	public Class<Credito> getTClass() {
		return Credito.class;
	}
}
