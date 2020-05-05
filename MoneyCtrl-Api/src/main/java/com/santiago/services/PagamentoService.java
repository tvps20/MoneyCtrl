package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Divida;
import com.santiago.domain.Pagamento;
import com.santiago.dtos.PagamentoDTO;
import com.santiago.repositories.PagamentoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagamentoService extends BaseService<Pagamento, PagamentoDTO> {

	public PagamentoService(PagamentoRepository repository) {
		super(repository);
	}

	@Override
	public Pagamento fromDTO(PagamentoDTO dto) {
		log.info("Mapping 'PagamentoDTO' to 'Pagamento': " + this.getTClass().getName());
		return new Pagamento(dto.getId(), dto.getValor(), dto.getData(), new Divida(dto.getDividaId()));
	}

	@Override
	public void updateData(Pagamento newObj, Pagamento obj) {
		log.info("Parse 'pagamento' from 'newPagamento': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
	}

	@Override
	public Class<Pagamento> getTClass() {
		return Pagamento.class;
	}
}
