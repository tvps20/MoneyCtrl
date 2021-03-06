package com.santiago.endpoints;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.santiago.domain.BaseEntity;
import com.santiago.dtos.BaseDTO;
import com.santiago.endpoints.enuns.TipoEndPoint;
import com.santiago.services.interfaces.IServiceCrud;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController<T extends BaseEntity, K extends BaseDTO> {

	protected IServiceCrud<T, K> service;

	public BaseController(IServiceCrud<T, K> service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<K>> listar() {
		List<T> list = service.findAll();
		log.info("Finishing findAll. Tipo: " + this.getClass().getName());
		List<K> listDTO = list.stream().map(obj -> this.newClassDTO(obj)).collect(Collectors.toList());
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.PAGE)
	public ResponseEntity<Page<K>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<T> list = service.findPage(page, linesPerPage, direction, orderBy);
		log.info("Finishing findPage. Tipo: " + this.getClass().getName());
		Page<K> listDTO = list.map(obj -> this.newClassDTO(obj));
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.ID)
	public ResponseEntity<K> findById(@PathVariable Long id) {
		T obj = this.service.findById(id);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		K objDTO = this.newClassDTO(obj);
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody K objDTO) {
		T obj = service.fromDTO(objDTO);
		log.info("Finishing fromDTO. Tipo: " + this.getClass().getName());
		obj = this.service.insert(obj);
		log.info("Finishing insert. Tipo: " + this.getClass().getName());
		log.info("Create uri. " + this.getClass().getName());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		log.info("Finishing create uri. Tipo: " + this.getClass().getName());
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(TipoEndPoint.ID)
	public ResponseEntity<Void> update(@Valid @RequestBody K objDTO, @PathVariable Long id) {
		T obj = service.fromDTO(objDTO);
		log.info("Finishing fromDTO. Tipo: " + this.getClass().getName());
		obj.setId(id);
		this.service.update(obj);
		log.info("Finishing update. Tipo: " + this.getClass().getName());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(TipoEndPoint.ID)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.service.delete(id);
		log.info("Finishing delete. Tipo: " + this.getClass().getName());
		return ResponseEntity.noContent().build();
	}

	protected abstract K newClassDTO(T obj);
}
