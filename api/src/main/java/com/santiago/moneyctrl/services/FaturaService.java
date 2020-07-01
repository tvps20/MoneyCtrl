package com.santiago.moneyctrl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.dtos.CotaFaturaDTO;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
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
			throw new ObjectNotFoundException(MensagemUtil.erroObjNotFount(entity.getCartao().getId(), "cartaoId",
					CartaoService.class.getName()));
		}
	}

	public Page<Fatura> findPageByStatus(String status, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageStatus] - Buscando paginado todas as faturas por status: { status: " + status + ", Page: "
				+ page + ", linesPerPage: " + linesPerPage + " }");

		Direction directionParse = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, directionParse, orderBy);
		TipoStatus statusType = TipoStatus.toEnum(status);
		Page<Fatura> faturas = ((FaturaRepository) this.repository).findByStatus(statusType, pageRequest);

		log.info("[FindPageStatus] - Busca paginada finalizada com sucesso.");
		return faturas;
	}
	
	public Page<Fatura> noFindPageByStatus(String status, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageStatus] - Buscando paginado todas as faturas sem status: { status: " + status + ", Page: "
				+ page + ", linesPerPage: " + linesPerPage + " }");

		Direction directionParse = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, directionParse, orderBy);
		TipoStatus statusType = TipoStatus.toEnum(status);
		Page<Fatura> faturas = ((FaturaRepository) this.repository).noFindByStatus(statusType, pageRequest);

		log.info("[FindPageStatus] - Busca paginada finalizada com sucesso.");
		return faturas;
	}
	
	public List<Fatura> noFindPageByStatus(String status){
		log.info("[FindAllStatus] - Buscando paginado todas as faturas sem status. Status: " + status);
		TipoStatus statusType = TipoStatus.toEnum(status);
		List<Fatura> list = ((FaturaRepository) this.repository).noFindByStatus(statusType);

		log.info("[FindAllStatus] - Busca finalizada com sucesso.");
		return list;
	}
	
	public List<Fatura> findPageByStatus(String status){
		log.info("[FindAllStatus] - Buscando paginado todas as faturas por status. Status: " + status);
		TipoStatus statusType = TipoStatus.toEnum(status);
		List<Fatura> list = ((FaturaRepository) this.repository).findByStatus(statusType);

		log.info("[FindAllStatus] - Busca finalizada com sucesso.");
		return list;
	}

	public List<CotaFaturaDTO> GerarCotasByIdFatura(Long faturaId) {
		log.info("[GerarCotasById] - Buscando fatura. FaturaId: " + faturaId);
		Fatura fatura = this.findById(faturaId);
		Map<Comprador, CotaFaturaDTO> mapCotas = new HashMap<>();

		log.info("[GerarCotasById] - Gerando cotas");
		for (Lancamento lancamento : fatura.getLancamentos()) {
			lancamento.getCotas().stream().forEach(c -> {
				if (!mapCotas.keySet().contains(c.getComprador())) {
					// Se não encontrar criar um item novo.
					CotaFaturaDTO cotaFatura = new CotaFaturaDTO(c.getComprador());
					mapCotas.put(c.getComprador(), cotaFatura);
				}
				CotaDTO dto = new CotaDTO(c.getId(), c.getValor(), null);
				dto.setLancamento(new LancamentoDTO(c.getLancamento()));
				
				mapCotas.get(c.getComprador()).getCotas().add(dto);
			});
		}

		log.info("[GerarCotasById] - Cotas geradas com sucesso.");
		return new ArrayList<CotaFaturaDTO>(mapCotas.values());
	}

	public Fatura fecharFatura(Long id) {
		log.info("[Fechar-Fatura] - Fechando fatura. Id: " + id);
		try {
			Fatura fatura = this.findById(id);
			fatura.setStatus(TipoStatus.FECHADA);
			return this.update(fatura);
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Fechar-Fatura] - Erro ao tentar buscar fatura. Id: " + id);
			throw new ObjectNotFoundException(MensagemUtil.erroObjNotFount(id, this.getClass().getName()),
					this.getClass());
		} catch (DataIntegrityException ex) {
			baseLog.error("[Fechar-Fatura] - Erro ao tentar atualizar status. Id: " + id);
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
		}
	}

	public Fatura pagarFatura(Long id) {
		log.info("[Pagar-Fatura] - Pagando fatura. Id: " + id);
		try {
			Fatura fatura = this.findById(id);
			fatura.setStatus(TipoStatus.PAGA);
			return this.update(fatura);
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Pagar-Fatura] - Erro ao tentar buscar fatura. Id: " + id);
			throw new ObjectNotFoundException(MensagemUtil.erroObjNotFount(id, this.getClass().getName()),
					this.getClass());
		} catch (DataIntegrityException ex) {
			baseLog.error("[Pagar-Fatura] - Erro ao tentar atualizar status. Id: " + id);
			throw new DataIntegrityException(MensagemUtil.erroObjInserir(this.getClass().getName()));
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
