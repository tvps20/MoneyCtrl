package com.santiago.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.santiago.domain.Bandeira;
import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;
import com.santiago.repositories.CartaoRepository;
import com.santiago.services.exceptions.DataIntegrityException;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.services.interfaces.IServiceValidator;
import com.santiago.util.Mensagem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartaoService extends BaseService<Cartao, CartaoDTO> implements IServiceValidator {

	@Autowired
	private BandeiraService bandeiraService;

	public CartaoService(CartaoRepository repository) {
		super(repository);
	}

	@Override
	public Cartao insert(Cartao entity) {
		log.info("Insert entity: " + this.getTClass().getName());

		try {
			entity.setId(null);
			this.bandeiraService.findById(entity.getBandeira().getId());
			return this.repository.save(entity);

		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjDelete(this.getTClass().getName()), ex);
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getTClass().getName()));
		} catch (ObjectNotFoundException ex) {
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getBandeira().getId(), "bandeiraId",
					entity.getBandeira().getClass().getName()));
		}
	}

	@Override
	public Cartao fromDTO(CartaoDTO dto) {
		log.info("Mapping 'CartaoDTO' to 'Cartao': " + this.getTClass().getName());
		return new Cartao(dto.getId(), dto.getNome(), new Bandeira(dto.getBandeiraId()));
	}

	@Override
	public void updateData(Cartao newObj, Cartao obj) {
		log.info("Parse 'cartao' from 'newCartao': " + this.getTClass().getName());
		newObj.setNome(obj.getNome());
	}

	@Override
	public Class<Cartao> getTClass() {
		return Cartao.class;
	}

	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("Find by unique value: " + campo);
		return ((CartaoRepository) this.repository).verificarCampoUnico(campo);
	}
}
