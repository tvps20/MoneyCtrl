package com.santiago.services;

import org.springframework.stereotype.Service;

import com.santiago.domain.Bandeira;
import com.santiago.dtos.BandeiraDTO;
import com.santiago.repositories.BandeiraRepository;
import com.santiago.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BandeiraService extends BaseService<Bandeira, BandeiraDTO> implements IServiceValidator{

	public BandeiraService(BandeiraRepository repository) {
		super(repository);
	}

	@Override
	public Bandeira fromDTO(BandeiraDTO dto) {
		log.info("Mapping 'BandeiraDTO' to 'Bandeira': " + this.getTClass().getName());
		return new Bandeira(dto.getId(), dto.getNome());
	}

	@Override
	public void updateData(Bandeira newObj, Bandeira obj) {
		log.info("Parse 'bandeira' from 'newBandeira': " + this.getTClass().getName());
		newObj.setNome(obj.getNome());
	}

	@Override
	public Class<Bandeira> getTClass() {
		return Bandeira.class;
	}
	
	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("Find by unique value: " + campo);
		return ((BandeiraRepository) this.repository).verificarCampoUnico(campo);
	}
}
