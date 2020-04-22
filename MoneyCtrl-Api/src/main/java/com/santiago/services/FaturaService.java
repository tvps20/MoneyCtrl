package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Fatura;
import com.santiago.dtos.FaturaDTO;
import com.santiago.repositories.FaturaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FaturaService extends BaseService<Fatura, FaturaDTO> {

	public FaturaService(FaturaRepository repository) {
		super(repository);
	}

	@Override
	public Fatura fromDTO(FaturaDTO dto) {
		log.info("Mapping 'CartaoDTO' to 'Cartao': " + this.getTClass().getName());
		return new Fatura(dto.getId(), dto.getVencimento(), dto.getValorTotal(), dto.getObservacao(),
				dto.getMesReferente());
	}

	@Override
	public void updateData(Fatura newObj, Fatura obj) {
		log.info("Parse obj from newObj: " + this.getTClass().getName());
		newObj.setVencimento(obj.getVencimento());
		newObj.setValorTotal(obj.getValorTotal());
		newObj.setObservacao(obj.getObservacao());
		newObj.setMesReferente(obj.getMesReferente());
	}

	@Override
	public Class<Fatura> getTClass() {
		return Fatura.class;
	}
}
