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
		this.entityName = "Usuário";
		BaseService.baseLog = UsuarioService.log;
	}

	@Override
	public Usuario fromDTO(UsuarioDTO dto) {
		log.info("[Mapping] - 'UsuarioDTO' to 'Usuario'. Id: " + dto.getId());
		Usuario usuario = new Usuario(dto.getId(), dto.getNome(), dto.getUsername(), dto.getPassword());
		usuario.setEmail(dto.getEmail());
		
		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return usuario;
	}

	@Override
	public void updateData(Usuario newObj, Usuario obj) {
		log.info("[Parse] - 'NewUsuario' from 'Usuario'. Id: " + newObj.getId());
		newObj.setNome(obj.getNome());
		newObj.setPassword(obj.getPassword());
		newObj.setEmail(obj.getEmail());
		log.info("[Parse] - Parse finalizado com sucesso.");
	}

	@Override
	public boolean verificarCampoUnico(String campo) {
		log.info("[FindByUnique] - Buscando valor no banco de dados. Value: " + campo);
		boolean retorno = ((UsuarioRepository) this.repository).verificarCampoUnico(campo);

		log.info("[FindByUnique] - Busca finalizada. Retorno: " + retorno);
		return retorno;
	}
	
	public boolean verificarEmailUnico(String email) {
		log.info("[FindByUnique] - Buscando email no banco de dados. Email: " + email);
		boolean retorno = ((UsuarioRepository) this.repository).verificarEmailUnico(email);

		log.info("[FindByUnique] - Busca finalizada. Retorno: " + retorno);
		return retorno;
	}
}
