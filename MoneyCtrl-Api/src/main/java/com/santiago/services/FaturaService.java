package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.domain.Fatura;
import com.santiago.dtos.FaturaDTO;
import com.santiago.repositories.FaturaRepository;
import com.santiago.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FaturaService extends BaseService<Fatura, FaturaDTO> implements IServiceValidator {

	public FaturaService(FaturaRepository repository) {
		super(repository);
	}

	@Override
	public Fatura fromDTO(FaturaDTO dto) {
		log.info("Mapping 'CartaoDTO' to 'Cartao': " + this.getTClass().getName());
		Fatura fatura = new Fatura(dto.getId(), dto.getVencimento(), dto.getValorTotal(), dto.getObservacao(),
				dto.getMesReferente());
		fatura.setCartao(new Cartao(dto.getCartaoId(), null));
		return fatura;
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

	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("Find by unique value: " + campo);
		return ((FaturaRepository) this.repository).verificarCampoUnico(campo);
	}
}
