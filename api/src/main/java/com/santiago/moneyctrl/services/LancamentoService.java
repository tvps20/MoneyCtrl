package com.santiago.moneyctrl.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.domain.enuns.TipoLancamento;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
import com.santiago.moneyctrl.repositories.LancamentoRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.util.MensagemUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LancamentoService extends BaseService<Lancamento, LancamentoDTO> {

	@Autowired
	private CotaService cotaService;

	@Autowired
	private FaturaService faturaService;

	@Autowired
	private CompradorService compradorService;

	public LancamentoService(LancamentoRepository repository) {
		super(repository);
		BaseService.baseLog = LancamentoService.log;
	}

	@Override
	@Transactional
	public Lancamento insert(Lancamento entity) {
		log.info("[InsertLancamento] - Salvando um novo lancamento.");

		this.faturaService.findById(entity.getFatura().getId());
		log.info("[InsertLancamento] - Buscando compradores.");
		entity.getCotas().forEach(x -> {
			this.compradorService.findById(x.getComprador().getId());
		});

		Lancamento lancamentoSalvo = super.insert(entity);
		this.cotaService.saveAllCotas(lancamentoSalvo.getCotas());

		log.info("[InsertLancamento] - Lacamento salvo no bando de dados.");
		return lancamentoSalvo;
	}

	public List<Lancamento> saveAllLancamentos(List<Lancamento> lancamentos) {
		log.info("[SaveAll] - Salvando todas os lancamentos. Lancamentos: " + lancamentos);
		try {
			List<Lancamento> lancametnosSalvos = this.repository.saveAll(lancamentos);
			List<Cota> cotas = this.cotaService.mergeCotasOfLancamentos(lancamentos);
			this.cotaService.saveAllCotas(cotas);
			
			log.info("[SaveAll] - Lancamentos salvos no bando de dados.");
			return lancametnosSalvos;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[SaveAll] - Erro ao tentar salvar lancamentos.");
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
		}
	}

	public List<Lancamento> gerarLancamentosFuturos(List<Lancamento> lancamentos, Fatura novaFatura) {
		log.info("[GerarLancamentosFuturos] - Gerando lancamentos futuros.");
		List<Lancamento> lancamentosFuturos = new ArrayList<>();

		for (Lancamento lancamento : lancamentos) {
			if (lancamento.getTipoLancamento() == TipoLancamento.PARCELADO) {
				if (lancamento.getParcelaAtual() < lancamento.getQtdParcelas()) {
					lancamentosFuturos.add(this.gerarProximoLancamento(lancamento, novaFatura));
				}
			} else if (lancamento.getTipoLancamento() == TipoLancamento.ASSINATURA) {
				lancamentosFuturos.add(this.gerarProximoLancamento(lancamento, novaFatura));
			}
		}

		log.info("[GerarLancamentosFuturos] - Lancamentos futuros gerados com sucesso.");
		return lancamentosFuturos;
	}

	private Lancamento gerarProximoLancamento(Lancamento lancamento, Fatura novaFatura) {
		log.info("[GerarProximoLancamento] - Gerando proximo lancamento.");
		Lancamento novoLancamento = new Lancamento(null, lancamento.getDescricao(), lancamento.getObservacao(),
				lancamento.getDataCompra(), novaFatura, lancamento.getTipoLancamento(),
				lancamento.getQtdParcelas(), lancamento.getParcelaAtual());
		
		if(lancamento.getTipoLancamento() == TipoLancamento.PARCELADO) {
			novoLancamento.setParcelaAtual(lancamento.getParcelaAtual() + 1);
		}

		novoLancamento.setCotas(this.cotaService.gerarCotasFuturas(lancamento.getCotas(), novoLancamento));
		log.error("[GerarProximoLancamento] - Lancamento gerado com sucesso.");
		return novoLancamento;
	}

	@Override
	public Lancamento fromDTO(LancamentoDTO dto) {
		log.info("[Mapping] - 'LancamentoDTO' to 'Lancamento'. Id: " + dto.getId());
		Lancamento lancamento;
		Fatura fatura = new Fatura(dto.getFaturaId());

		if (dto.getTipoLancamento() == TipoLancamento.PARCELADO) {
			lancamento = new Lancamento(dto.getId(), dto.getDescricao(), dto.getObservacao(), dto.getDataCompra(),
					fatura, TipoLancamento.PARCELADO, dto.getQtdParcelas(), dto.getParcelaAtual());
		} else {
			lancamento = new Lancamento(dto.getId(), dto.getDescricao(), dto.getObservacao(), dto.getDataCompra(),
					fatura, TipoLancamento.AVISTA, null, null);
		}

		dto.getCotas().forEach(x -> {
			Cota cota = new Cota(null, x.getValor(), new Comprador(x.getCompradorId()), lancamento);
			lancamento.getCotas().add(cota);
		});

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return lancamento;
	}

	public void updateData(Lancamento newObj, Lancamento obj) {
		log.info("[Parse] - 'NewLancamento' from 'Lancamento'. Id: " + newObj.getId());
		newObj.setDescricao(obj.getDescricao());
		newObj.setObservacao(obj.getObservacao());

		if (obj.getTipoLancamento() == TipoLancamento.PARCELADO) {
			newObj.setParcelaAtual(obj.getParcelaAtual());
		}

		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
