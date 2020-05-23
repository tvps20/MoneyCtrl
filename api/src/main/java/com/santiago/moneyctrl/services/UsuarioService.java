package com.santiago.moneyctrl.services;

import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.dtos.UsuarioDTO;
import com.santiago.moneyctrl.repositories.UsuarioRepository;
import com.santiago.moneyctrl.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService extends BaseService<Usuario, UsuarioDTO> implements IServiceValidator {

	public UsuarioService(UsuarioRepository repository) {
		super(repository);
	}

	@Override
	public Usuario fromDTO(UsuarioDTO dto) {
		log.info("Mapping 'UsuarioDTO' to 'Usuario': " + this.getTClass().getName());
		return new Usuario(dto.getId(), dto.getEmail(), dto.getNome(), dto.getPassword());
	}

	@Override
	public void updateData(Usuario newObj, Usuario obj) {
		log.info("Parse 'usuario' from 'newUsuario': " + this.getTClass().getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPassword(obj.getPassword());
		newObj.setNome(obj.getNome());
	}

	@Override
	public Class<Usuario> getTClass() {
		return Usuario.class;
	}

	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("Find by unique value: " + campo);
		return ((UsuarioRepository) this.repository).verificarCampoUnico(campo);
	}
}
