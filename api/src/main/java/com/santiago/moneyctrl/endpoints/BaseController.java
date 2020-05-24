package com.santiago.moneyctrl.endpoints;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.santiago.moneyctrl.domain.BaseEntity;
import com.santiago.moneyctrl.dtos.BaseDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.interfaces.IServiceCrud;

public abstract class BaseController<T extends BaseEntity, K extends BaseDTO> {

	protected IServiceCrud<T, K> service;

	protected static Logger baseLog = LoggerFactory.getLogger(BaseController.class);

	public BaseController(IServiceCrud<T, K> service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<K>> listar() {
		baseLog.info("[GET] - Buscando todas as entidades.");
		List<T> list = service.findAll();
		List<K> listDTO = list.stream().map(obj -> this.newClassDTO(obj)).collect(Collectors.toList());

		baseLog.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.PAGE)
	public ResponseEntity<Page<K>> listarPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		baseLog.info("[GET PAGE] - Buscando todas as entidades paginada: { Page: " + page + ", linesPerPage: "
				+ linesPerPage + ", direction: " + direction + ", orderBy: " + orderBy + "}");
		Page<T> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<K> listDTO = list.map(obj -> this.newClassDTO(obj));

		baseLog.info("[GET PAGE] - Busca paginada finalizada com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.ID)
	public ResponseEntity<K> buscarPeloId(@PathVariable Long id) {
		baseLog.info("[GET ID] - Buscando entidade pelo Id: " + id);
		T obj = this.service.findById(id);
		K objDTO = this.newClassDTO(obj);

		baseLog.info("[GET ID] - Entidade encontrada com sucesso.");
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody K objDTO) {
		baseLog.info("[POST] - Salvando uma nova entidade. Entity: " + objDTO.toString());
		T obj = service.fromDTO(objDTO);
		obj = this.service.insert(obj);
		baseLog.info("[POST] - Criando uri.");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		baseLog.info("[POST] - Uri criado com sucesso. Uri: " + uri);

		baseLog.info("[POST] - Entidade salva no bando de dados.");
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(TipoEndPoint.ID)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody K objDTO, @PathVariable Long id) {
		baseLog.info("[PUT] - Atualizando entidade. Entity: " + objDTO.toString());
		T obj = service.fromDTO(objDTO);
		obj.setId(id);
		this.service.update(obj);

		baseLog.info("[PUT] - Entitade atualizada no bando de dados.");
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(TipoEndPoint.ID)
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		baseLog.info("[DELETE] - Apagando entidade de Id: " + id);
		this.service.delete(id);

		baseLog.info("[DELETE] - Entidade apagada com sucesso.");
		return ResponseEntity.noContent().build();
	}

	protected abstract K newClassDTO(T obj);
}
