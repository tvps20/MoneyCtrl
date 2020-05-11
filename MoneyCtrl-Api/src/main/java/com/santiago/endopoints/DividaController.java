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

import com.santiago.domain.Divida;
import com.santiago.domain.Pagamento;
import com.santiago.dtos.DividaDTO;
import com.santiago.dtos.PagamentoDTO;
import com.santiago.services.DividaService;
import com.santiago.services.PagamentoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/divida")
public class DividaController extends BaseController<Divida, DividaDTO> {

	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	public DividaController(DividaService service) {
		super(service);
	}

	@GetMapping("/{dividaId}/pagamento")
	public ResponseEntity<List<PagamentoDTO>> listarPagamentos(@PathVariable Long dividaId) {
		List<Pagamento> list = pagamentoService.findAllPagamentoByDividaId(dividaId);
		log.info("Finishing findAll. Tipo: " + this.getClass().getName());
		List<PagamentoDTO> listDTO = list.stream().map(obj -> new PagamentoDTO(obj)).collect(Collectors.toList());
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{dividaId}/pagamento/page")
	public ResponseEntity<Page<PagamentoDTO>> findPagePagamento(@PathVariable Long dividaId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Pagamento> list = pagamentoService.findPageByDividaId(dividaId, page, linesPerPage, direction, orderBy);
		log.info("Finishing findPage. Tipo: " + this.getClass().getName());
		Page<PagamentoDTO> listDTO = list.map(obj -> new PagamentoDTO(obj));
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/pagamento/{id}")
	public ResponseEntity<PagamentoDTO> findPagamentoById(@PathVariable Long id) {
		Pagamento obj = this.pagamentoService.findById(id);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		PagamentoDTO objDTO = new PagamentoDTO(obj);
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		objDTO.setDividaId(obj.getDivida().getId());
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping("/{dividaId}/pagamento")
	public ResponseEntity<PagamentoDTO> insertPagamento(@PathVariable Long dividaId,
			@Valid @RequestBody PagamentoDTO objDTO) {
		this.service.findById(dividaId);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		objDTO.setDividaId(dividaId);
		Pagamento obj = this.pagamentoService.fromDTO(objDTO);
		log.info("Finishing fromDTO. Tipo: " + this.getClass().getName());
		obj = this.pagamentoService.insert(obj);
		log.info("Finishing insert. Tipo: " + this.getClass().getName());
		log.info("Create uri. " + this.getClass().getName());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("divida/pagamento/{id}")
				.buildAndExpand(obj.getId()).toUri();
		log.info("Finishing create uri. Tipo: " + this.getClass().getName());
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("pagamento/{id}")
	public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
		this.pagamentoService.delete(id);
		log.info("Finishing delete. Tipo: " + this.getClass().getName());
		return ResponseEntity.noContent().build();
	}

	@Override
	public DividaDTO newClassDTO(Divida obj) {
		log.info("Mapping 'Divida' to 'DividaDTO': " + this.getClass().getName());
		return new DividaDTO(obj);
	}
}
