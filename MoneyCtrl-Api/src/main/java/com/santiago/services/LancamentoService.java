package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Fatura;
import com.santiago.domain.Lancamento;
import com.santiago.dtos.LancamentoDTO;
import com.santiago.dtos.LancamentoDTOComParcela;
import com.santiago.repositories.LancamentoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LancamentoService extends BaseService<Lancamento, LancamentoDTO> {

	public LancamentoService(LancamentoRepository repository) {
		super(repository);
	}

	@Override
	public Lancamento fromDTO(LancamentoDTO dto) {
		log.info("Mapping 'LancamentoDTO' to 'Lancamento': " + this.getTClass().getName());
		Fatura fatura = new Fatura(dto.getFaturaId());
		Comprador comprador = new Comprador(dto.getCompradorId());
		Lancamento lancamento;

		if (dto.isParcelado()) {
			LancamentoDTOComParcela dtoComParcela = (LancamentoDTOComParcela) dto;
			lancamento = new Lancamento(dto.getId(), dto.getValor(), dto.getDescricao(), dto.getObsrvacao(),
					dto.getDataCompra(), dto.isParcelado(), fatura, dtoComParcela.getQtdParcela(),
					dtoComParcela.getParcelaAtual(), comprador);
		} else {
			lancamento = new Lancamento(dto.getId(), dto.getValor(), dto.getDescricao(), dto.getObsrvacao(),
					dto.getDataCompra(), dto.isParcelado(), fatura, null, null, comprador);
		}

		return lancamento;
	}

	public void updateData(Lancamento newObj, Lancamento obj) {
		log.info("Parse lancamento from newLancamento: " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
		newObj.setDescricao(obj.getDescricao());
		newObj.setObsrvacao(obj.getObsrvacao());
		newObj.setParcelado(obj.isParcelado());

		if (obj.isParcelado()) {
			newObj.setQtdParcela(obj.getQtdParcela());
			newObj.setParcelaAtual(obj.getParcelaAtual());
		}
	}

	@Override
	public Class<Lancamento> getTClass() {
		return Lancamento.class;
	}
}
