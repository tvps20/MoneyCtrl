package com.santiago.moneyctrl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;
import com.santiago.moneyctrl.dtos.CartaoDTO;
import com.santiago.moneyctrl.dtos.CotaCartaoDTO;
import com.santiago.moneyctrl.dtos.CotaFaturaDTO;
import com.santiago.moneyctrl.repositories.CartaoRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.services.interfaces.IServiceValidator;
import com.santiago.moneyctrl.util.MensagemUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartaoService extends BaseService<Cartao, CartaoDTO> implements IServiceValidator {

	@Autowired
	private BandeiraService bandeiraService;
	@Autowired
	private FaturaService faturaService;

	public CartaoService(CartaoRepository repository) {
		super(repository);
		BaseService.baseLog = CartaoService.log;
	}

	@Override
	@Transactional
	public Cartao insert(Cartao entity) {
		entity.setId(null);
		log.info("[Insert] - Salvando um novo cartao. Entity: " + entity.toString());

		try {
			if(entity.getBandeira().getId() != null ) {
				log.info("[Insert] - Buscando bandeira.");
				this.bandeiraService.findById(entity.getBandeira().getId());				
			} else {
				log.info("[Insert] - Salvando Bandeira.");
				this.bandeiraService.insert(entity.getBandeira());
			}
			
			log.info("[Insert] - Salvando Cartao.");
			Cartao cartao = this.repository.save(entity);

			log.info("[Insert] - Cartao salvo no bando de dados.");
			return cartao;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar cartao.");
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Insert] - Erro ao tentar buscar bandeira.");
			throw new ObjectNotFoundException(MensagemUtil.erroObjNotFount(entity.getBandeira().getId(), "bandeiraId",
					BandeiraService.class.getName()));
		}
	}
	
	public List<CotaCartaoDTO> GerarAllCotasCartao(){
		log.info("[GerarCotasCartao] - Buscando faturas.");
		List<Fatura> faturas = this.faturaService.noFindPageByStatus(TipoStatus.ABERTA.toString());
		Map<Cartao, CotaCartaoDTO> mapCotas = new HashMap<>();
		
		log.info("[GerarCotasCartao] - Gerando cotas");
		faturas.forEach(f -> {
			if (!mapCotas.keySet().contains(f.getCartao())) {
				// Se não encontrar criar um item novo.
				CotaCartaoDTO cotaCartao = new CotaCartaoDTO(f.getCartao(), f);
				mapCotas.put(f.getCartao(), cotaCartao);
			}
			List<CotaFaturaDTO> cotasFatura = this.faturaService.GerarCotasByIdFatura(f.getId());
			mapCotas.get(f.getCartao()).setCotas(cotasFatura);
		});
		
		log.info("[GerarCotasCartao] - Cotas geradas com sucesso.");
		return new ArrayList<CotaCartaoDTO>(mapCotas.values());
	}

	@Override
	public Cartao fromDTO(CartaoDTO dto) {
		log.info("[Mapping] - 'CartaoDTO' to 'Cartao'. Id: " + dto.getId());
		Cartao cartao;
		if(dto.getBandeira().getId() != null) {
			cartao = new Cartao(dto.getId(), dto.getNome(), new Bandeira(dto.getBandeira().getId()));			
		} else {
			cartao = new Cartao(dto.getId(), dto.getNome(), new Bandeira(null, dto.getBandeira().getNome()));	
		}

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return cartao;
	}

	@Override
	public void updateData(Cartao newObj, Cartao obj) {
		log.info("[Parse] - 'NewCartao' from 'Cartao'. Id: " + newObj.getId());
		newObj.setNome(obj.getNome());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}

	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("[FindByUnique] - Buscando valor no banco de dados. Value: " + campo);
		boolean retorno = ((CartaoRepository) this.repository).verificarCampoUnico(campo);

		log.info("[FindByUnique] - Busca finalizada. Retorno: " + retorno);
		return retorno;
	}
}
