package com.santiago.moneyctrl.endpoints;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;
import com.santiago.moneyctrl.dtos.UsuarioDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.USUARIO)
public class UsuarioController extends BaseController<Usuario, UsuarioDTO> {

	@Autowired
	private UsuarioService service;

	@Autowired
	public UsuarioController(UsuarioService service) {
		super(service);
		BaseController.baseLog = UsuarioController.log;
	}

	@GetMapping(TipoEndPoint.USUARIO_ID + TipoEndPoint.ROLES)
	public ResponseEntity<Set<TipoRoles>> listarRoles(@PathVariable Long usuarioId) {
		log.info("[GET] - Buscando todas as roles. UsuarioId: " + usuarioId);
		Usuario user = this.service.findById(usuarioId);

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body(user.getRoles());
	}

	@GetMapping(TipoEndPoint.VALIDA + TipoEndPoint.EMAIL + TipoEndPoint.VALOR)
	public ResponseEntity<String> verificaEmailUnico(@PathVariable String valor) {
		log.info("[GET] - Verificando campo único: " + valor);
		boolean result = this.service.verificarEmailUnico(valor);

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body("{ \"alreadySaved\": " + result + " }");
	}

	@GetMapping(TipoEndPoint.VALIDA + TipoEndPoint.UNIQUE + TipoEndPoint.VALOR)
	public ResponseEntity<String> verificaUsernameUnico(@PathVariable String valor) {
		log.info("[GET] - Verificando valor único: " + valor);
		boolean result = this.service.verificarCampoUnico(valor);

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body("{ \"alreadySaved\": " + result + " }");
	}

	@Override
	protected UsuarioDTO newClassDTO(Usuario obj) {
		log.info("[Mapping] - 'Usuario' to 'UsuarioDTO'. Id: " + obj.getId());
		UsuarioDTO dto = new UsuarioDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
