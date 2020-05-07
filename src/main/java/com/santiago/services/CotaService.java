package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Cota;
import com.santiago.dtos.CotaDTO;
import com.santiago.repositories.CotaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CotaService extends BaseService<Cota, CotaDTO> {
	

	public CotaService(CotaRepository repository) {
		super(repository);
	}

	@Override
	public Cota fromDTO(CotaDTO dto) {
		log.info("Mapping 'CotaDTO' to 'Cota': " + this.getTClass().getName());
		return new Cota(dto.getId(), dto.getValor(), new Comprador(dto.getCompradorId()), null);
	}

	@Override
	public void updateData(Cota newObj, Cota obj) {
		log.info("Parse 'cota' from 'newCota': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
	}

	@Override
	public Class<Cota> getTClass() {
		return Cota.class;
	}
}
