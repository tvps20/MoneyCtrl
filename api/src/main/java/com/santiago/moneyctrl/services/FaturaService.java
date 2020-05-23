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
import com.santiago.moneyctrl.util.Mensagem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FaturaService extends BaseService<Fatura, FaturaDTO> {

	@Autowired
	private CartaoService cartaoService;

	public FaturaService(FaturaRepository repository) {
		super(repository);
	}

	@Override
	public Fatura insert(Fatura entity) {
		log.info("Insert entity: " + this.getTClass().getName());

		try {
			entity.setId(null);
			this.cartaoService.findById(entity.getCartao().getId());
			log.info("Finishing findById. Tipo" + CartaoService.class.getName());
			return this.repository.save(entity);

		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			throw new ObjectNotFoundException(
					Mensagem.erroObjNotFount(entity.getCartao().getId(), "cartaoId", CartaoService.class.getName()));
		}
	}

	@Override
	public Fatura fromDTO(FaturaDTO dto) {
		log.info("Mapping 'CartaoDTO' to 'Cartao': " + this.getTClass().getName());

		return new Fatura(dto.getId(), dto.getVencimento(), dto.getObservacao(), dto.getMesReferente(),
				new Cartao(dto.getCartaoId()));
	}

	@Override
	public void updateData(Fatura newObj, Fatura obj) {
		log.info("Parse 'fatura' from 'newFatura': " + this.getTClass().getName());
		newObj.setVencimento(obj.getVencimento());
		newObj.setObservacao(obj.getObservacao());
		newObj.setStatus(obj.getStatus());
	}

	@Override
	public Class<Fatura> getTClass() {
		return Fatura.class;
	}
}
