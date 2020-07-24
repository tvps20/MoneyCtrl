package com.santiago.moneyctrl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;
import com.santiago.moneyctrl.dtos.UsuarioDTO;
import com.santiago.moneyctrl.repositories.UsuarioRepository;
import com.santiago.moneyctrl.security.UserSS;
import com.santiago.moneyctrl.services.exceptions.AuthorizationException;
import com.santiago.moneyctrl.services.exceptions.ObjectNotFoundException;
import com.santiago.moneyctrl.services.interfaces.IServiceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService extends BaseService<Usuario, UsuarioDTO> implements IServiceValidator {

	@Autowired
	private BCryptPasswordEncoder crypt;

	public UsuarioService(UsuarioRepository repository) {
		super(repository);
		this.entityName = "Usuário";
		BaseService.baseLog = UsuarioService.log;
	}

	@Override
	public Usuario findById(Long id) throws ObjectNotFoundException {
		log.info("[FindByIdUser] - Buscando usuario");

		UserSS userSS = authenticated();

		if (userSS == null || !userSS.hasRole(TipoRoles.ADMIN) && !id.equals(userSS.getId())) {
			log.error("[FindByIdUser] - Usuario não autorizado.");
			throw new AuthorizationException("Acesso negado");
		}

		Usuario user = super.findById(id);
		log.info("[FindByIdUser] - Busca finalizada com sucesso.");
		return user;
	}

	@Override
	public Usuario fromDTO(UsuarioDTO dto) {
		log.info("[Mapping] - 'UsuarioDTO' to 'Usuario'. Id: " + dto.getId());
		Usuario usuario = new Usuario(dto.getId(), dto.getNome(), dto.getUsername(), crypt.encode(dto.getPassword()));
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

	public static UserSS authenticated() {
		try {
			log.info("[Authenticated] - Buscando usuario logado.");
			UserSS userLogado = (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("[Authenticated] - UserSS: " + userLogado.toString());
			return userLogado;
		} catch (Exception ex) {
			log.error("[Authenticated] - Usuario não encontrado.");
			return null;
		}
	}
}
