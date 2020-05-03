package com.santiago.endopoints;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.santiago.domain.Divida;
import com.santiago.dtos.DividaDTO;
import com.santiago.dtos.DividaDTOComParcela;
import com.santiago.services.DividaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/divida")
public class DividaController {

	@Autowired
	private DividaService service;

	// Endpoints
	@GetMapping
	public ResponseEntity<List<DividaDTO>> listar() {
		List<Divida> list = service.findAll();
		List<DividaDTO> listDTO = list.stream().map(obj -> this.newClassDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<DividaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Divida> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<DividaDTO> listDTO = list.map(obj -> this.newClassDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DividaDTO> findById(@PathVariable Long id) {
		Divida obj = this.service.findById(id);
		DividaDTO objDTO = this.newClassDTO(obj);
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody DividaDTOComParcela objDTO) {
		Divida obj = service.fromDTO(objDTO);
		obj = this.service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody DividaDTOComParcela objDTO, @PathVariable Long id) {
		Divida obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = this.service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.service.delete(id);
		return ResponseEntity.noContent().build();
	}

	// Metodos
	public DividaDTO newClassDTO(Divida obj) {
		log.info("Mapping 'Divida' to 'DividaDTO': " + this.getClass().getName());
		if (obj.isParcelada()) {
			return new DividaDTOComParcela(obj);
		} else {
			return new DividaDTO(obj);
		}
	}
}
