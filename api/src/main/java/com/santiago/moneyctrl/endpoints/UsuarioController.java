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
		List<Usuario> list = service.findAll();
		log.info("Finishing findAll. Tipo: " + this.getClass().getName());
		List<UsuarioDTO> listDTO = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.USUARIO_ID + TipoEndPoint.PERFIl)
	public ResponseEntity<Set<TipoPerfil>> listarRoles(@PathVariable Long usuarioId) {
		Usuario user = this.service.findById(usuarioId);
		return ResponseEntity.ok().body(user.getPerfis());
	}
}
