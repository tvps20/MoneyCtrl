package com.santiago.moneyctrl.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Pagamento;
import com.santiago.moneyctrl.dtos.DividaDTO;
import com.santiago.moneyctrl.dtos.PagamentoDTO;
import com.santiago.moneyctrl.repositories.PagamentoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagamentoService extends BaseService<Pagamento, PagamentoDTO> {

	@Autowired
	private DividaService dividaService;
	@Autowired
	private CreditoService creditoService;

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

	public Page<Pagamento> findPageByDividaId(Long dividaId, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageCota] - Buscando todas os pagamentos paginado: { dividaId: " + dividaId + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + " }");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		Page<Pagamento> pagamentos = ((PagamentoRepository) this.repository).findByDividaId(dividaId, pageRequest);

		log.info("[FindPageCota] - Busca paginada finalizada com sucesso.");
		return pagamentos;
	}

	@Override
	@Transactional
	public Pagamento insert(Pagamento entity) {
		log.info("[InsertPagamento] - Salvando um novo Pagamento.");

		Divida divida = this.dividaService.findById(entity.getDivida().getId());

		Pagamento pagamentoSalvo = super.insert(entity);

		Credito novoCredito = this.verificaDividaStatus(divida);
		if (novoCredito.getValor() != null) {
			log.info("[InsertPagamento] - Salvando mudanças na divida.");
			this.dividaService.update(divida);
			if (novoCredito.getValor().doubleValue() != 0) {
				log.info("[InsertPagamento] - Salvando novo crédito");
				this.creditoService.insert(novoCredito);
			}
		}

		log.info("[InsertPagamento] - Pagamento salvo com sucesso.");
		return pagamentoSalvo;
	}

	private Credito verificaDividaStatus(Divida divida) {
		log.info("[Insert] - Verificando status da divida.");
		DividaDTO dividaDto = new DividaDTO(divida);
		BigDecimal saldo = dividaDto.getTotalPagamentos().subtract(dividaDto.getValorDivida());
		log.info("[Insert] - Calculando saldo. Saldo: " + saldo);
		Credito novoCredito = new Credito();
		if (saldo.doubleValue() >= 0) {
			divida.setPaga(true);
			novoCredito = new Credito(null, saldo, LocalDateTime.now(),
					"Credito referente a divida " + divida.getDescricao(), divida.getComprador());
			log.info("[Insert] - Gerando novo credito e mudando status da divida. Credito" + novoCredito.toString());
		}

		return novoCredito;
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
