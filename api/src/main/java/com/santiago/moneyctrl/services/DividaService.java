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
		BaseService.baseLog = DividaService.log;
	}

	@Override
	public Divida insert(Divida entity) {
		entity.setId(null);
		log.info("[Insert] - Salvando uma nova divida. Entity: " + entity.toString());

		try {
			if (entity.getFatura() != null) {
				log.info("[Insert] - Buscando fatura.");
				this.faturaService.findById(entity.getFatura().getId());
			}
			log.info("[Insert] - Buscando comprador.");
			this.compradorService.findById(entity.getComprador().getId());
			Divida divida = this.repository.save(entity);

			log.info("[Insert] - Divida salva no bando de dados.");
			return divida;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar divida.");
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			if (ex.getClassTipo().equals(FaturaService.class)) {
				baseLog.error("[Insert] - Erro ao tentar buscar fatura.");
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "faturaId",
						entity.getFatura().getClass().getName()), FaturaService.class);
			} else {
				baseLog.error("[Insert] - Erro ao tentar buscar comprador.");
				throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getFatura().getId(), "compradorId",
						entity.getComprador().getClass().getName()), CompradorService.class);
			}
		}
	}

	@Override
	public Divida fromDTO(DividaDTO dto) {
		log.info("[Mapping] - 'DividaDTO' to 'Divida'. Id: " + dto.getId());
		Fatura fatura = dto.getFaturaId() != null ? new Fatura(dto.getFaturaId()) : null;
		Comprador comprador = new Comprador(dto.getCompradorId());
		Divida divida = new Divida(dto.getId(), dto.getValor(), dto.getObservacao(), dto.getDataDivida(), fatura,
				comprador, dto.isPaga());

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return divida;
	}

	@Override
	public void updateData(Divida newObj, Divida obj) {
		log.info("[Parse] - 'NewDivida' from 'Divida'. Id: " + newObj.getId());
		newObj.setValor(obj.getValor());
		newObj.setObservacao(obj.getObservacao());
		newObj.setPaga(obj.isPaga());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
