package com.santiago.moneyctrl.endpoints;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoPerfil;
import com.santiago.moneyctrl.dtos.UsuarioDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.USUARIO)
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		log.info("[GET] - Buscando todos os Usuarios.");
		List<Usuario> list = service.findAll();
		List<UsuarioDTO> listDTO = list.stream().map(obj -> this.newClassDTO(obj)).collect(Collectors.toList());

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.USUARIO_ID + TipoEndPoint.PERFIl)
	public ResponseEntity<Set<TipoPerfil>> listarRoles(@PathVariable Long usuarioId) {
		log.info("[GET] - Buscando todas os perfis. UsuarioId: " + usuarioId);
		Usuario user = this.service.findById(usuarioId);

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body(user.getPerfis());
	}

	// Metodos
	private UsuarioDTO newClassDTO(Usuario obj) {
		log.info("[Mapping] - 'Usuario' to 'UsuarioDTO'. Id: " + obj.getId());
		UsuarioDTO dto = new UsuarioDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
