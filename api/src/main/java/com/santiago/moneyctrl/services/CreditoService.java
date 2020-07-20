package com.santiago.moneyctrl.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.dtos.CreditoDTO;
import com.santiago.moneyctrl.repositories.CreditoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditoService extends BaseService<Credito, CreditoDTO> {

	public CreditoService(CreditoRepository repository) {
		super(repository);
		this.entityName = "Credito";
		BaseService.baseLog = CreditoService.log;
	}

	public List<Credito> findAllCreditoByCompradorId(Long compradorId) {
		log.info("[FindAllCredito] - Buscando todas as creditos. CompradorId: " + compradorId);
		List<Credito> creditos = ((CreditoRepository) this.repository).findByCompradorId(compradorId);

		log.info("[FindAllCredito] - Busca finalizada com sucesso.");
		return creditos;
	}

	public Page<Credito> findPageByCompradorId(Long compradorId, Integer page, Integer linesPerPage, String direction,
			String orderBy) {
		log.info("[FindPageCota] - Buscando todas os creditos paginado: { compradorId: " + compradorId + ", Page: "
				+ page + ", linesPerPage: " + linesPerPage + " }");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage);
		Page<Credito> creditos = ((CreditoRepository) this.repository).findByCompradorId(compradorId, pageRequest);

		log.info("[FindPageCota] - Busca paginada finalizada com sucesso.");
		return creditos;
	}

	@Override
	public Credito fromDTO(CreditoDTO dto) {
		log.info("[Mapping] - 'CreditoDTO' to 'Credito'. Id: " + dto.getId());
		Credito credito = new Credito(dto.getId(), dto.getValor(), dto.getData(), dto.getDescricao(),
				new Comprador(dto.getCompradorId()));

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return credito;
	}

	@Override
	public void updateData(Credito newObj, Credito obj) {
		log.info("[Parse] - 'NewCredito' from 'Credito'. Id: " + newObj.getId());
		newObj.setValor(obj.getValor());
		newObj.setDescricao(obj.getDescricao());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}
}
