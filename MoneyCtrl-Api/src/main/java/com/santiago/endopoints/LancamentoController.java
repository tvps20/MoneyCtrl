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

import com.santiago.domain.Lancamento;
import com.santiago.dtos.LancamentoDTO;
import com.santiago.dtos.LancamentoDTOComParcela;
import com.santiago.services.LancamentoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

	@Autowired
	private LancamentoService service;
	
	// Endpoints
	@GetMapping
	public ResponseEntity<List<LancamentoDTO>> listar() {
		List<Lancamento> list = service.findAll();
		List<LancamentoDTO> listDTO = list.stream().map(obj -> this.newClassDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<LancamentoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Lancamento> list = service.findPage(page, linesPerPage, direction, orderBy);
		Page<LancamentoDTO> listDTO = list.map(obj -> this.newClassDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LancamentoDTO> findById(@PathVariable Long id) {
		Lancamento obj = this.service.findById(id);
		LancamentoDTO objDTO = this.newClassDTO(obj);
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody LancamentoDTOComParcela objDTO) {
		Lancamento obj = service.fromDTO(objDTO);
		obj = this.service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody LancamentoDTOComParcela objDTO, @PathVariable Long id) {
		Lancamento obj = service.fromDTO(objDTO);
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
	public LancamentoDTO newClassDTO(Lancamento obj) {
		log.info("Mapping 'Lancamento' to 'LancamentoDTO': " + this.getClass().getName());
		if (obj.isParcelado()) {
			return new LancamentoDTOComParcela(obj);
		} else {
			return new LancamentoDTO(obj);
		}
	}
}
