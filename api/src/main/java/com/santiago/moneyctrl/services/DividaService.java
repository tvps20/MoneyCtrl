package com.santiago.moneyctrl.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.CotaFaturaDTO;
import com.santiago.moneyctrl.dtos.DividaDTO;
import com.santiago.moneyctrl.repositories.DividaRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.util.MensagemUtil;

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
		this.entityName = "Divida";
		BaseService.baseLog = DividaService.log;
	}

	@Override
	public Divida insert(Divida entity) {
		log.info("[InsertDivida] - Salvando uma nova divida.");

		if (entity.getFatura() != null) {
			this.faturaService.findById(entity.getFatura().getId());
		}
		this.compradorService.findById(entity.getComprador().getId());
		Divida divida = super.insert(entity);

		log.info("[InsertDivida] - Divida salva no bando de dados.");
		return divida;
	}

	public Page<Divida> findPageByStatus(boolean paga, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageStatus] - Buscando paginado todas as dividas por status: { paga: " + paga + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + " }");

		Direction directionParse = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, directionParse, orderBy);
		Page<Divida> dividas = ((DividaRepository) this.repository).findByPaga(paga, pageRequest);

		log.info("[FindPageStatus] - Busca paginada finalizada com sucesso.");
		return dividas;
	}

	public List<Divida> findByStatus(boolean paga) {
		log.info("[FindStatus] - Buscando todas as dividas por status: " + paga);

		List<Divida> dividas = ((DividaRepository) this.repository).findByPaga(paga);

		log.info("[FindStatus] - Busca finalizada com sucesso.");
		return dividas;
	}

	public List<Divida> saveAllDividas(List<Divida> dividas) {
		log.info("[SaveAll] - Salvando todas as dividas. Dividas: " + dividas);
		try {
			List<Divida> dividasSalvas = this.repository.saveAll(dividas);

			log.info("[SaveAll] - Dividas salvas no bando de dados.");
			return dividasSalvas;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[SaveAll] - Erro ao tentar salvar dividas.");
			throw new DataIntegrityException(MensagemUtil.erroObjInsert("Dividas"));
		}
	}

	public List<Divida> gerarDividas(List<CotaFaturaDTO> cotasFatura, Fatura fatura) {
		log.info("[GerarDividas] - Gerando dividas.");
		List<Divida> dividas = new ArrayList<>();

		for (CotaFaturaDTO cotaFatura : cotasFatura) {
			Comprador comprador = new Comprador(cotaFatura.getCompradorId());
			String descricao = "Fatura de " + fatura.getMesReferente() + "/" + fatura.getVencimento().getYear();
			Divida novaDivida = new Divida(null, cotaFatura.getValorTotal(), descricao, LocalDateTime.now(), comprador,
					fatura);
			dividas.add(novaDivida);
		}

		log.info("[GerarDividas] - Dividas geradas com sucesso.");
		return dividas;
	}

	@Override
	public Divida fromDTO(DividaDTO dto) {
		log.info("[Mapping] - 'DividaDTO' to 'Divida'. Id: " + dto.getId());
		Fatura fatura = dto.getFaturaId() != null ? new Fatura(dto.getFaturaId()) : null;
		Comprador comprador = new Comprador(dto.getCompradorId());
		Divida divida = new Divida(dto.getId(), dto.getValorDivida(), dto.getDescricao(), dto.getDataDivida(), fatura,
				comprador, dto.isPaga());

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return divida;
	}

	@Override
	public void updateData(Divida newObj, Divida obj) {
		log.info("[Parse] - 'NewDivida' from 'Divida'. Id: " + newObj.getId());
		newObj.setValorDivida(obj.getValorDivida());
		newObj.setDescricao(obj.getDescricao());
		newObj.setPaga(obj.isPaga());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
