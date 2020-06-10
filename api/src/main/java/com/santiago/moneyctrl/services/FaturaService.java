package com.santiago.moneyctrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.repositories.FaturaRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.util.MensagemUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FaturaService extends BaseService<Fatura, FaturaDTO> {

	@Autowired
	private CartaoService cartaoService;

	public FaturaService(FaturaRepository repository) {
		super(repository);
		BaseService.baseLog = FaturaService.log;
	}

	@Override
	public Fatura insert(Fatura entity) {
		entity.setId(null);
		log.info("[Insert] - Salvando uma nova fatura. Entity: " + entity.toString());

		try {
			log.info("[Insert] - Buscando cartao.");
			this.cartaoService.findById(entity.getCartao().getId());
			Fatura fatura = this.repository.save(entity);

			log.info("[Insert] - Fatura salva no bando de dados.");
			return fatura;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar fatura.");
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Insert] - Erro ao tentar buscar cartao.");
			throw new ObjectNotFoundException(
					MensagemUtil.erroObjNotFount(entity.getCartao().getId(), "cartaoId", CartaoService.class.getName()));
		}
	}

	@Override
	public Fatura fromDTO(FaturaDTO dto) {
		log.info("[Mapping] - 'FaturaDTO' to 'Fatura'. Id: " + dto.getId());
		Fatura fatura = new Fatura(dto.getId(), dto.getVencimento(), dto.getObservacao(), dto.getMesReferente(),
				new Cartao(dto.getCartaoId()));

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return fatura;
	}

	@Override
	public void updateData(Fatura newObj, Fatura obj) {
		log.info("[Parse] - 'NewFatura' from 'Fatura'. Id: " + newObj.getId());
		newObj.setVencimento(obj.getVencimento());
		newObj.setObservacao(obj.getObservacao());
		newObj.setStatus(obj.getStatus());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
