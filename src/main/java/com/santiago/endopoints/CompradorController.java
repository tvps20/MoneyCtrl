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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.santiago.domain.Comprador;
import com.santiago.domain.Credito;
import com.santiago.dtos.CompradorDTO;
import com.santiago.dtos.CreditoDTO;
import com.santiago.services.CompradorService;
import com.santiago.services.CreditoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comprador")
public class CompradorController extends BaseController<Comprador, CompradorDTO> {

	@Autowired
	private CreditoService creditoService;

	@Autowired
	public CompradorController(CompradorService service) {
		super(service);
	}

	@GetMapping("/{compradorId}/credito")
	public ResponseEntity<List<CreditoDTO>> listarCotas(@PathVariable Long compradorId) {
		List<Credito> list = creditoService.findAllCotaByLancamentoId(compradorId);
		List<CreditoDTO> listDTO = list.stream().map(obj -> new CreditoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{lancamentoId}/credito/page")
	public ResponseEntity<Page<CreditoDTO>> findPage(@PathVariable Long compradorId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Credito> list = creditoService.findPageByLancamentoId(compradorId, page, linesPerPage, direction, orderBy);
		Page<CreditoDTO> listDTO = list.map(obj -> new CreditoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/credito/{id}")
	public ResponseEntity<CreditoDTO> findCotaById(@PathVariable Long id) {
		Credito obj = this.creditoService.findById(id);
		CreditoDTO objDTO = new CreditoDTO(obj);
		objDTO.setCompradorId(obj.getComprador().getId());
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping("/{compradorId}/credito")
	public ResponseEntity<CreditoDTO> insert(@PathVariable Long compradorId, @Valid @RequestBody CreditoDTO objDTO) {
		this.service.findById(compradorId);
		objDTO.setCompradorId(compradorId);
		Credito obj = this.creditoService.fromDTO(objDTO);
		obj = this.creditoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("comprador/cota/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("credito/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.creditoService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public CompradorDTO newClassDTO(Comprador obj) {
		log.info("Mapping 'Comprador' to 'CompradorDTO': " + this.getClass().getName());
		return new CompradorDTO(obj);
	}
}
