package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.domain.Fatura;
import com.santiago.dtos.FaturaDTO;
import com.santiago.repositories.FaturaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FaturaService extends BaseService<Fatura, FaturaDTO> {

	public FaturaService(FaturaRepository repository) {
		super(repository);
	}

	@Override
	public Fatura fromDTO(FaturaDTO dto) {
		log.info("Mapping 'CartaoDTO' to 'Cartao': " + this.getTClass().getName());
		Fatura fatura = new Fatura(dto.getId(), dto.getVencimento(), dto.getValorTotal(), dto.getObservacao(),
				dto.getMesReferente(), new Cartao(dto.getCartaoId()));
		
		return fatura;
	}

	@Override
	public void updateData(Fatura newObj, Fatura obj) {
		log.info("Parse 'fatura' from 'newFatura': " + this.getTClass().getName());
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
