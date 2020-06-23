package com.santiago.moneyctrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
import com.santiago.moneyctrl.repositories.LancamentoRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.util.MensagemUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LancamentoService extends BaseService<Lancamento, LancamentoDTO> {

	@Autowired
	private FaturaService faturaService;

	@Autowired
	private CompradorService compradorService;

	@Autowired
	private CotaService cotaService;

	public LancamentoService(LancamentoRepository repository) {
		super(repository);
		BaseService.baseLog = LancamentoService.log;
	}

	@Override
	@Transactional
	public Lancamento insert(Lancamento entity) {
		entity.setId(null);
		log.info("[Insert] - Salvando um novo lancamento. Entity: " + entity.toString());

		try {
			log.info("[Insert] - Buscando fatura.");
			this.faturaService.findById(entity.getFatura().getId());
			log.info("[Insert] - Buscando compradores.");
			entity.getCotas().forEach(x -> {
				this.compradorService.findById(x.getComprador().getId());
			});

			log.info("[Insert] - Salvando lancamento.");
			Lancamento lancamentoSalvo = this.repository.save(entity);
			log.info("[Insert] - Salvando compradores.");
			this.cotaService.saveAllCotas(lancamentoSalvo.getCotas());

			log.info("[Insert] - Lacamento salvo no bando de dados.");
			return lancamentoSalvo;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar lancamento.");
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
		} catch (DataIntegrityException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar compradores.");
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			if (ex.getClassTipo().equals(this.faturaService.getClass())) {
				baseLog.error("[Insert] - Erro ao tentar buscar fatura.");
				throw new ObjectNotFoundException(MensagemUtil.erroObjNotFount(entity.getFatura().getId(), "faturaId",
						entity.getFatura().getClass().getName()), FaturaService.class);
			} else {
				baseLog.error("[Insert] - Erro ao tentar comprador fatura.");
				throw new ObjectNotFoundException(ex.getMessage(), CompradorService.class);
			}
		}
	}

	@Override
	public Lancamento fromDTO(LancamentoDTO dto) {
		log.info("[Mapping] - 'LancamentoDTO' to 'Lancamento'. Id: " + dto.getId());
		Lancamento lancamento;
		Fatura fatura = new Fatura(dto.getFaturaId());

		if (dto.isParcelado()) {
			lancamento = new Lancamento(dto.getId(), dto.getDescricao(), dto.getObservacao(), dto.getDataCompra(),
					fatura, dto.isParcelado(), dto.getQtdParcelas(), dto.getParcelaAtual());
		} else {
			lancamento = new Lancamento(dto.getId(), dto.getDescricao(), dto.getObservacao(), dto.getDataCompra(),
					fatura, dto.isParcelado(), null, null);
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

		if (obj.isParcelado()) {
			newObj.setParcelaAtual(obj.getParcelaAtual());
		}

		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
