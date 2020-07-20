package com.santiago.moneyctrl.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.repositories.CotaRepository;
import com.santiago.moneyctrl.services.exceptions.DataIntegrityException;
import com.santiago.moneyctrl.util.MensagemUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CotaService extends BaseService<Cota, CotaDTO> {

	public CotaService(CotaRepository repository) {
		super(repository);
		this.entityName = "Cota";
		BaseService.baseLog = CotaService.log;
	}

	public List<Cota> findAllCotaByLancamentoId(Long lancamentoId) {
		log.info("[FindAllCota] - Buscando todas as cotas. LancamentoId: " + lancamentoId);
		List<Cota> cotas = ((CotaRepository) this.repository).findByLancamentoId(lancamentoId);

		log.info("[FindAllCota] - Busca finalizada com sucesso.");
		return cotas;
	}

	public Page<Cota> findPageCotaByLancamentoId(Long lancamentoId, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		log.info("[FindPageCota] - Buscando todas as cotas paginada: { lancamentoId: " + lancamentoId + ", Page: "
				+ page + ", linesPerPage: " + linesPerPage + " }");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		Page<Cota> cotas = ((CotaRepository) this.repository).findByLancamentoId(lancamentoId, pageRequest);

		log.info("[FindPageCota] - Busca paginada finalizada com sucesso.");
		return cotas;
	}

	public List<Cota> saveAllCotas(List<Cota> cotas) {
		log.info("[SaveAll] - Salvando todas as cotas. Cotas: " + cotas);
		try {
			List<Cota> cotasSalvas = this.repository.saveAll(cotas);

			log.info("[SaveAll] - Cotas salvas no bando de dados.");
			return cotasSalvas;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[SaveAll] - Erro ao tentar salvar cotas.");
			throw new DataIntegrityException(MensagemUtil.erroObjInsert("Cotas"));
		}
	}

	public List<Cota> gerarCotasFuturas(List<Cota> cotas, Lancamento novoLancamento) {
		log.info("[GerarCotasFuturas] - Gerando cotas futuas.");
		List<Cota> cotasFuturas = new ArrayList<>();

		for (Cota cota : cotas) {
			Cota novaCota = new Cota(null, cota.getValor(), cota.getComprador(), novoLancamento);
			cotasFuturas.add(novaCota);
		}

		log.info("[GerarCotasFuturas] - Cotas futuras geradas com sucesso.");
		return cotasFuturas;
	}

	public List<Cota> mergeCotasOfLancamentos(List<Lancamento> lancamentos) {
		log.info("[MergeCotasLancamento] - Mesclando todas as cotas dos lancamentos.");
		List<Cota> cotas = new ArrayList<>();

		for (Lancamento lancamento : lancamentos) {
			for (Cota cota : lancamento.getCotas()) {
				cotas.add(cota);
			}
		}

		log.info("[MergeCotasLancamento] - Merge realizado com sucesso.");
		return cotas;
	}

	@Override
	public Cota fromDTO(CotaDTO dto) {
		log.info("[Mapping] - 'CotaDTO' to 'Cota'. Id: " + dto.getId());
		Cota cota = new Cota(dto.getId(), dto.getValor(), new Comprador(dto.getCompradorId()),
				new Lancamento(dto.getLancamentoId()));

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return cota;
	}

	@Override
	public void updateData(Cota newObj, Cota obj) {
		log.info("[Parse] - 'NewCota' from 'Cota'. Id: " + newObj.getId());
		newObj.setValor(obj.getValor());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
