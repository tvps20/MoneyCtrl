package com.santiago.moneyctrl.services;

import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.dtos.BandeiraDTO;
import com.santiago.moneyctrl.repositories.BandeiraRepository;
import com.santiago.moneyctrl.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BandeiraService extends BaseService<Bandeira, BandeiraDTO> implements IServiceValidator {

	public BandeiraService(BandeiraRepository repository) {
		super(repository);
		this.entityName = "Bandeira";
		BaseService.baseLog = BandeiraService.log;
	}

	@Override
	public Bandeira fromDTO(BandeiraDTO dto) {
		log.info("[Mapping] - 'BandeiraDTO' to 'Bandeira'. Id: " + dto.getId());
		Bandeira bandeira = new Bandeira(dto.getId(), dto.getNome());

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return bandeira;
	}

	@Override
	public void updateData(Bandeira newObj, Bandeira obj) {
		log.info("[Parse] - 'NewBandeira' from 'Bandeira'. Id: " + newObj.getId());
		newObj.setNome(obj.getNome());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}

	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("[FindByUnique] - Buscando valor no banco de dados. Value: " + campo);
		boolean retorno = ((BandeiraRepository) this.repository).verificarCampoUnico(campo);

		log.info("[FindByUnique] - Busca finalizada. Retorno: " + retorno);
		return retorno;
	}
}
