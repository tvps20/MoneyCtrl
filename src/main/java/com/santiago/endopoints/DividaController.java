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
	public ResponseEntity<List<PagamentoDTO>> listarCotas(@PathVariable Long dividaId) {
		List<Pagamento> list = pagamentoService.findAllPagamentoByDividaId(dividaId);
		List<PagamentoDTO> listDTO = list.stream().map(obj -> new PagamentoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{dividaId}/pagamento/page")
	public ResponseEntity<Page<PagamentoDTO>> findPage(@PathVariable Long dividaId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Pagamento> list = pagamentoService.findPageByDividaId(dividaId, page, linesPerPage, direction, orderBy);
		Page<PagamentoDTO> listDTO = list.map(obj -> new PagamentoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/pagamento/{id}")
	public ResponseEntity<PagamentoDTO> findCotaById(@PathVariable Long id) {
		Pagamento obj = this.pagamentoService.findById(id);
		PagamentoDTO objDTO = new PagamentoDTO(obj);
		objDTO.setDividaId(obj.getDivida().getId());
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping("/{dividaId}/pagamento")
	public ResponseEntity<PagamentoDTO> insert(@PathVariable Long dividaId, @Valid @RequestBody PagamentoDTO objDTO) {
		this.service.findById(dividaId);
		objDTO.setDividaId(dividaId);
		Pagamento obj = this.pagamentoService.fromDTO(objDTO);
		obj = this.pagamentoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("divida/pagamento/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("pagamento/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.pagamentoService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public DividaDTO newClassDTO(Divida obj) {
		log.info("Mapping 'Divida' to 'DividaDTO': " + this.getClass().getName());
		return new DividaDTO(obj);
	}
}
