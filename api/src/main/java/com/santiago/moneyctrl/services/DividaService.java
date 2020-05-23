package com.santiago.moneyctrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.DividaDTO;
import com.santiago.moneyctrl.repositories.DividaRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.util.Mensagem;

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
			log.info("Finishing findById. Tipo" + FaturaService.class.getName());
			this.compradorService.findById(entity.getComprador().getId());
			log.info("Finishing findById. Tipo" + CompradorService.class.getName());
			return this.repository.save(entity);

		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			if (ex.getClassTipo().equals(FaturaService.class)) {
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "faturaId",
						entity.getFatura().getClass().getName()), FaturaService.class);
			} else {
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "compradorId",
						entity.getComprador().getClass().getName()), CompradorService.class);
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
		newObj.setPaga(obj.isPaga());
	}

	@Override
	public Class<Divida> getTClass() {
		return Divida.class;
	}
}
