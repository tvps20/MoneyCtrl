package com.santiago.moneyctrl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.util.Mensagem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CotaService extends BaseService<Cota, CotaDTO> {

	@Autowired
	private CompradorService compradorService;

	public CotaService(CotaRepository repository) {
		super(repository);
		BaseService.baseLog = CotaService.log;
	}

	public List<Cota> findAllCotaByLancamentoId(Long lancamentoId) {
		log.info("[FindAllCota] - Buscando todas as cotas. LancamentoId: " + lancamentoId);
		List<Cota> cotas = ((CotaRepository) this.repository).findByLancamentoId(lancamentoId);

		log.info("[FindAllCota] - Busca finalizada com sucesso.");
		return cotas;
	}

	public Page<Cota> findPageByLancamentoId(Long lancamentoId, Integer page, Integer linesPerPage, String orderBy,
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
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		}
	}

	@Override
	public Cota insert(Cota entity) {
		entity.setId(null);
		log.info("[Insert] - Salvando uma nova cota. Entity: " + entity.toString());

		try {
			log.info("[Insert] - Buscando comprador.");
			this.compradorService.findById(entity.getComprador().getId());
			Cota cota = this.repository.save(entity);

			log.info("[Insert] - Cota salva no bando de dados.");
			return cota;

		} catch (DataIntegrityViolationException ex) {
			baseLog.error("[Insert] - Erro ao tentar salvar cota.");
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			baseLog.error("[Insert] - Erro ao tentar buscar comprador.");
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getComprador().getId(), "compradorId",
					CompradorService.class.getName()));
		}
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
