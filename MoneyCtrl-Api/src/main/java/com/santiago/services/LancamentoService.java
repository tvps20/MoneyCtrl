package com.santiago.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
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

	public LancamentoService(LancamentoRepository repository) {
		super(repository);
	}

	@Override
	public Lancamento insert(Lancamento entity) {
		log.info("Insert entity: " + this.getTClass().getName());

		try {
			entity.setId(null);
			this.faturaService.findById(entity.getFatura().getId());
			this.compradorService.findById(entity.getComprador().getId());
			return this.repository.save(entity);

		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjDelete(this.getTClass().getName()), ex);
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getTClass().getName()));
		} catch (ObjectNotFoundException ex) {
			if (ex.getClassTipo().equals(this.faturaService.getClass())) {
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "faturaId",
						entity.getFatura().getClass().getName()), this.faturaService.getClass());
			} else {
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "compradorId",
						entity.getComprador().getClass().getName()), this.compradorService.getClass());
			}
		}
	}

	@Override
	public Lancamento fromDTO(LancamentoDTO dto) {
		log.info("Mapping 'LancamentoDTO' to 'Lancamento': " + this.getTClass().getName());
		Lancamento lancamento;
		Fatura fatura = new Fatura(dto.getFaturaId());
		Comprador comprador = new Comprador(dto.getCompradorId());

		if (dto.isParcelado()) {
			lancamento = new Lancamento(dto.getId(), dto.getValor(), dto.getDescricao(), dto.getObsrvacao(),
					dto.getDataCompra(), fatura, comprador, dto.isParcelado(), dto.getQtdParcela(),
					dto.getParcelaAtual());
		} else {
			lancamento = new Lancamento(dto.getId(), dto.getValor(), dto.getDescricao(), dto.getObsrvacao(),
					dto.getDataCompra(), fatura, comprador, dto.isParcelado(), null, null);
		}

		return lancamento;
	}

	public void updateData(Lancamento newObj, Lancamento obj) {
		log.info("Parse 'lancamento' from 'newLancamento': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
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
