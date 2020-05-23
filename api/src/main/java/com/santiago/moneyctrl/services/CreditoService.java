package com.santiago.moneyctrl.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.dtos.CreditoDTO;
import com.santiago.moneyctrl.repositories.CreditoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditoService extends BaseService<Credito, CreditoDTO> {

	public CreditoService(CreditoRepository repository) {
		super(repository);
	}

	public List<Credito> findAllCreditoByCompradorId(Long lancamentoId) {
		log.info("Find All credito: " + this.getTClass().getName());
		return ((CreditoRepository) this.repository).findByCompradorId(lancamentoId);
	}

	public Page<Credito> findPageByCompradorId(Long compradorId, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		log.info("Find page credito: " + this.getTClass().getName());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		return ((CreditoRepository) this.repository).findByCompradorId(compradorId, pageRequest);
	}

	@Override
	public Credito fromDTO(CreditoDTO dto) {
		log.info("Mapping 'CreditoDTO' to 'Credito': " + this.getTClass().getName());
		return new Credito(dto.getId(), dto.getValor(), dto.getData(), dto.getObservacao(),
				new Comprador(dto.getCompradorId()));
	}

	@Override
	public void updateData(Credito newObj, Credito obj) {
		log.info("Parse 'credito' from 'newCredito': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
		newObj.setObservacao(obj.getObservacao());
	}

	@Override
	public Class<Credito> getTClass() {
		return Credito.class;
	}
}
