package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Divida;
import com.santiago.domain.Fatura;
import com.santiago.dtos.DividaDTO;
import com.santiago.dtos.DividaDTOComParcela;
import com.santiago.repositories.DividaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DividaService extends BaseService<Divida, DividaDTO> {

	public DividaService(DividaRepository repository) {
		super(repository);
	}

	@Override
	public Divida fromDTO(DividaDTO dto) {
		log.info("Mapping 'DividaDTO' to 'Divida': " + this.getTClass().getName());
		Divida divida;
		Fatura fatura = new Fatura(dto.getFaturaId());
		Comprador comprador = new Comprador(dto.getCompradorId());

		if (dto.isParcelada()) {
			DividaDTOComParcela dtoComParcela = (DividaDTOComParcela) dto;
			divida = new Divida(dto.getId(), fatura, dto.getValorTotal(), dto.getObservacao(), dto.getDataDivida(),
					dto.isPaga(), dto.isParcelada(), dtoComParcela.getQtdParcela(), dtoComParcela.getParcelaAtual(),
					comprador);
		} else {
			divida = new Divida(dto.getId(), fatura, dto.getValorTotal(), dto.getObservacao(), dto.getDataDivida(),
					dto.isPaga(), dto.isParcelada(), null, null, comprador);
		}

		return divida;
	}

	@Override
	public void updateData(Divida newObj, Divida obj) {
		log.info("Parse 'divida' from 'newDivida': " + this.getTClass().getName());
		newObj.setValorTotal(obj.getValorTotal());
		newObj.setObservacao(obj.getObservacao());
		newObj.setParcelada(obj.isParcelada());

		if (obj.isParcelada()) {
			newObj.setQtdParcela(obj.getQtdParcela());
			newObj.setParcelaAtual(obj.getParcelaAtual());
		}
	}

	@Override
	public Class<Divida> getTClass() {
		return Divida.class;
	}
}
