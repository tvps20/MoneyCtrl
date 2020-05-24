package com.santiago.moneyctrl.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Pagamento;
import com.santiago.moneyctrl.dtos.PagamentoDTO;
import com.santiago.moneyctrl.repositories.PagamentoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagamentoService extends BaseService<Pagamento, PagamentoDTO> {

	public PagamentoService(PagamentoRepository repository) {
		super(repository);
		BaseService.baseLog = PagamentoService.log;
	}

	public List<Pagamento> findAllPagamentoByDividaId(Long dividaId) {
		log.info("[FindAllPagamento] - Buscando todas os pagamentos. DividaId: " + dividaId);
		List<Pagamento> pagamentos = ((PagamentoRepository) this.repository).findByDividaId(dividaId);

		log.info("[FindAllPagamento] - Busca finalizada com sucesso.");
		return pagamentos;
	}

	public Page<Pagamento> findPageByDividaId(Long dividaId, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		log.info("[FindPageCota] - Buscando todas os pagamentos paginado: { dividaId: " + dividaId + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + " }");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		Page<Pagamento> pagamentos = ((PagamentoRepository) this.repository).findByDividaId(dividaId, pageRequest);

		log.info("[FindPageCota] - Busca paginada finalizada com sucesso.");
		return pagamentos;
	}

	@Override
	public Pagamento fromDTO(PagamentoDTO dto) {
		log.info("[Mapping] - 'PagamentoDTO' to 'Pagamento'. Id: " + dto.getId());
		Pagamento pagamento = new Pagamento(dto.getId(), dto.getValor(), dto.getData(), dto.getObservacao(),
				new Divida(dto.getDividaId()));

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return pagamento;
	}

	@Override
	public void updateData(Pagamento newObj, Pagamento obj) {
		log.info("[Parse] - 'NewPagamento' from 'Pagamento'. Id: " + newObj.getId());
		newObj.setValor(obj.getValor());
		newObj.setObservacao(obj.getObservacao());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
