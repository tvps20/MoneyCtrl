package com.santiago.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Cota;
import com.santiago.domain.Fatura;
import com.santiago.domain.Lancamento;
import com.santiago.dtos.LancamentoDTO;
import com.santiago.repositories.LancamentoRepository;
import com.santiago.services.exceptions.DataIntegrityException;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.util.Mensagem;

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
	}

	@Override
	@Transactional
	public Lancamento insert(Lancamento entity) {
		log.info("Insert entity: " + this.getClass().getName());

		try {
			entity.setId(null);
			this.faturaService.findById(entity.getFatura().getId());
			log.info("Finishing findById. Tipo" + FaturaService.class.getName());
			entity.getCompradores().forEach(x -> {
				this.compradorService.findById(x.getComprador().getId());
			});
			log.info("Finishing findById. Tipo" + CompradorService.class.getName());
			Lancamento lancamentoSalvo = this.repository.save(entity);
			this.cotaService.saveAllCotas(lancamentoSalvo.getCompradores());
			log.info("Finishing saveAll. Tipo" + CotaService.class.getName());

			return lancamentoSalvo;

		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			if (ex.getClassTipo().equals(this.faturaService.getClass())) {
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "faturaId",
						entity.getFatura().getClass().getName()), FaturaService.class);
			} else {
				throw new ObjectNotFoundException(ex.getMessage(), CompradorService.class);
			}
		}
	}

	@Override
	public Lancamento fromDTO(LancamentoDTO dto) {
		log.info("Mapping 'LancamentoDTO' to 'Lancamento': " + this.getTClass().getName());
		Lancamento lancamento;
		Fatura fatura = new Fatura(dto.getFaturaId());

		if (dto.isParcelado()) {
			lancamento = new Lancamento(dto.getId(), dto.getDescricao(), dto.getObservacao(), dto.getDataCompra(),
					fatura, dto.isParcelado(), dto.getQtdParcela(), dto.getParcelaAtual());
		} else {
			lancamento = new Lancamento(dto.getId(), dto.getDescricao(), dto.getObservacao(), dto.getDataCompra(),
					fatura, dto.isParcelado(), null, null);
		}

		dto.getCompradores().forEach(x -> {
			Cota cota = new Cota(null, x.getValor(), new Comprador(x.getCompradorId()), lancamento);
			lancamento.getCompradores().add(cota);
		});

		return lancamento;
	}

	public void updateData(Lancamento newObj, Lancamento obj) {
		log.info("Parse 'lancamento' from 'newLancamento': " + this.getTClass().getName());
		newObj.setDescricao(obj.getDescricao());
		newObj.setObservacao(obj.getObservacao());

		if (obj.isParcelado()) {
			newObj.setParcelaAtual(obj.getParcelaAtual());
		}
	}

	@Override
	public Class<Lancamento> getTClass() {
		return Lancamento.class;
	}
}
