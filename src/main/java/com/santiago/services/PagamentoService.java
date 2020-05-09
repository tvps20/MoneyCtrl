package com.santiago.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public List<Pagamento> findAllPagamentoByDividaId(Long dividaId) {
		log.info("Find All Pagamento: " + this.getTClass().getName());
		return ((PagamentoRepository) this.repository).findByDividaId(dividaId);
	}

	public Page<Pagamento> findPageByDividaId(Long dividaId, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		log.info("Find page credito: " + this.getTClass().getName());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		return ((PagamentoRepository) this.repository).findByDividaId(dividaId, pageRequest);
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
