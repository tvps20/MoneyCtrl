package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Fatura;
import com.santiago.domain.Lancamento;
import com.santiago.domain.LancamentoAVista;
import com.santiago.domain.LancamentoParcelado;
import com.santiago.dtos.LancamentoDTO;
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
		Fatura fatura = new Fatura();
		fatura.setId(dto.getFaturaId());
		Lancamento lancamento;

		if (dto.isParcelado()) {
			lancamento = new LancamentoParcelado(dto.getId(), dto.getValor(), dto.getDescricao(), dto.getObsrvacao(),
					dto.getDataCompra(), fatura, dto.getQtdParcela(), dto.getParcelaAtual());
		} else {
			lancamento = new LancamentoAVista(dto.getId(), dto.getValor(), dto.getDescricao(), dto.getObsrvacao(),
					dto.getDataCompra(), fatura);
		}

		return lancamento;
	}

	@Override
	public void updateData(Lancamento newObj, Lancamento obj) {
		log.info("Parse lancamento from newLancamento: " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
		newObj.setDescricao(obj.getDescricao());
		newObj.setObsrvacao(obj.getObsrvacao());

		if (obj.isParcelado()) {
			((LancamentoParcelado) newObj).setParcelaAtual(((LancamentoParcelado) obj).getParcelaAtual());
		} else {
			((LancamentoAVista) newObj).setDesconto(((LancamentoAVista) obj).getDesconto());
		}
	}

	@Override
	public Class<Lancamento> getTClass() {
		return Lancamento.class;
	}

}
