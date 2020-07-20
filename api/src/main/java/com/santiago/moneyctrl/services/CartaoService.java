package com.santiago.moneyctrl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.santiago.moneyctrl.services.interfaces.IServiceValidator;

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
		this.entityName = "Cartao";
		BaseService.baseLog = CartaoService.log;
	}

	@Override
	@Transactional
	public Cartao insert(Cartao entity) {
		log.info("[InsertCartao] - Salvando um novo cartao. Entity: " + entity.toString());

		if (entity.getBandeira().getId() != null) {
			this.bandeiraService.findById(entity.getBandeira().getId());
		} else {
			this.bandeiraService.insert(entity.getBandeira());
		}

		Cartao cartao = super.insert(entity);

		log.info("[InsertCartao] - Cartao salvo no bando de dados.");
		return cartao;
	}

	public List<CotaCartaoDTO> GerarAllCotasCartao() {
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
			List<CotaFaturaDTO> cotasFatura = this.faturaService.gerarCotasByIdFatura(f.getId());
			mapCotas.get(f.getCartao()).setCotas(cotasFatura);
		});

		log.info("[GerarCotasCartao] - Cotas geradas com sucesso.");
		return new ArrayList<CotaCartaoDTO>(mapCotas.values());
	}

	@Override
	public Cartao fromDTO(CartaoDTO dto) {
		log.info("[Mapping] - 'CartaoDTO' to 'Cartao'. Id: " + dto.getId());
		Cartao cartao;
		if (dto.getBandeira().getId() != null) {
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
