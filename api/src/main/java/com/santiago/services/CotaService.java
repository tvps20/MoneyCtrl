package com.santiago.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.santiago.domain.Comprador;
import com.santiago.domain.Cota;
import com.santiago.domain.Lancamento;
import com.santiago.dtos.CotaDTO;
import com.santiago.repositories.CotaRepository;
import com.santiago.services.exceptions.DataIntegrityException;
import com.santiago.services.exceptions.ObjectNotFoundException;
import com.santiago.util.Mensagem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CotaService extends BaseService<Cota, CotaDTO> {

	@Autowired
	private CompradorService compradorService;

	public CotaService(CotaRepository repository) {
		super(repository);
	}

	public List<Cota> findAllCotaByLancamentoId(Long lancamentoId) {
		log.info("Find All cota: " + this.getTClass().getName());
		return ((CotaRepository) this.repository).findByLancamentoId(lancamentoId);
	}

	public Page<Cota> findPageByLancamentoId(Long lancamentoId, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		log.info("Find page cota: " + this.getTClass().getName());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		return ((CotaRepository) this.repository).findByLancamentoId(lancamentoId, pageRequest);
	}

	public List<Cota> saveAllCotas(List<Cota> cotas) {
		return this.repository.saveAll(cotas);
	}

	@Override
	public Cota insert(Cota entity) {
		log.info("Insert entity: " + this.getClass().getName());

		try {
			entity.setId(null);
			this.compradorService.findById(entity.getComprador().getId());
			log.info("Finishing findById. Tipo" + CompradorService.class.getName());
			return this.repository.save(entity);

		} catch (DataIntegrityViolationException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			throw new DataIntegrityException(Mensagem.erroObjInserir(this.getClass().getName()));
		} catch (ObjectNotFoundException ex) {
			log.error(Mensagem.erroObjInserir(this.getClass().getName()));
			throw new ObjectNotFoundException(Mensagem.erroObjNotFount(entity.getComprador().getId(), "compradorId",
					CompradorService.class.getName()));
		}
	}

	@Override
	public Cota fromDTO(CotaDTO dto) {
		log.info("Mapping 'CotaDTO' to 'Cota': " + this.getTClass().getName());
		return new Cota(dto.getId(), dto.getValor(), new Comprador(dto.getCompradorId()),
				new Lancamento(dto.getLancamentoId()));
	}

	@Override
	public void updateData(Cota newObj, Cota obj) {
		log.info("Parse 'cota' from 'newCota': " + this.getTClass().getName());
		newObj.setValor(obj.getValor());
	}

	@Override
	public Class<Cota> getTClass() {
		return Cota.class;
	}
}
