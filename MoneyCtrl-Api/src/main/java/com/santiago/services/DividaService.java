package com.santiago.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Divida;
import com.santiago.domain.Fatura;
import com.santiago.dtos.DividaDTO;
import com.santiago.repositories.DividaRepository;
import com.santiago.services.exceptions.DataIntegrityException;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.util.Mensagem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DividaService extends BaseService<Divida, DividaDTO> {

	@Autowired
	private FaturaService faturaService;

	@Autowired
	private CompradorService compradorService;

	public DividaService(DividaRepository repository) {
		super(repository);
	}

	@Override
	public Divida insert(Divida entity) {
		log.info("Insert entity: " + this.getTClass().getName());

		try {
			entity.setId(null);
			if (entity.getFatura() != null) {
				this.faturaService.findById(entity.getFatura().getId());
			}
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
	public Divida fromDTO(DividaDTO dto) {
		log.info("Mapping 'DividaDTO' to 'Divida': " + this.getTClass().getName());
		Fatura fatura = dto.getFaturaId() != null ? new Fatura(dto.getFaturaId()) : null;
		Comprador comprador = new Comprador(dto.getCompradorId());
		Divida divida = new Divida(dto.getId(), dto.getValor(), dto.getObservacao(), dto.getDataDivida(), fatura,
				comprador, dto.isPaga());

		return divida;
	}

	@Override
	public void updateData(Divida newObj, Divida obj) {
		log.info("Parse 'divida' from 'newDivida': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
		newObj.setObservacao(obj.getObservacao());
	}

	@Override
	public Class<Divida> getTClass() {
		return Divida.class;
	}
}
