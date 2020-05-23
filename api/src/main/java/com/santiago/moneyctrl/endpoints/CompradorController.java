package com.santiago.moneyctrl.endpoints;

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

import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.dtos.CompradorDTO;
import com.santiago.moneyctrl.dtos.CreditoDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.CompradorService;
import com.santiago.moneyctrl.services.CreditoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.COMPRADOR)
public class CompradorController extends BaseController<Comprador, CompradorDTO> {

	@Autowired
	private CreditoService creditoService;

	@Autowired
	public CompradorController(CompradorService service) {
		super(service);
	}

	@GetMapping(TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO)
	public ResponseEntity<List<CreditoDTO>> listarCreditos(@PathVariable Long compradorId) {
		List<Credito> list = creditoService.findAllCreditoByCompradorId(compradorId);
		log.info("Finishing findAll. Tipo: " + this.getClass().getName());
		List<CreditoDTO> listDTO = list.stream().map(obj -> new CreditoDTO(obj)).collect(Collectors.toList());
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO + TipoEndPoint.PAGE)
	public ResponseEntity<Page<CreditoDTO>> findPage(@PathVariable Long compradorId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Credito> list = creditoService.findPageByCompradorId(compradorId, page, linesPerPage, direction, orderBy);
		log.info("Finishing findPage. Tipo: " + this.getClass().getName());
		Page<CreditoDTO> listDTO = list.map(obj -> new CreditoDTO(obj));
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.CREDITO + TipoEndPoint.ID)
	public ResponseEntity<CreditoDTO> findCreditoById(@PathVariable Long id) {
		Credito obj = this.creditoService.findById(id);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		CreditoDTO objDTO = new CreditoDTO(obj);
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		objDTO.setCompradorId(obj.getComprador().getId());
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping(TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO)
	public ResponseEntity<CreditoDTO> insertCredito(@PathVariable Long compradorId,
			@Valid @RequestBody CreditoDTO objDTO) {
		this.service.findById(compradorId);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		objDTO.setCompradorId(compradorId);
		Credito obj = this.creditoService.fromDTO(objDTO);
		log.info("Finishing fromDTO. Tipo: " + this.getClass().getName());
		obj = this.creditoService.insert(obj);
		log.info("Finishing insert. Tipo: " + this.getClass().getName());
		log.info("Create uri. " + this.getClass().getName());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("comprador/credito/{id}")
				.buildAndExpand(obj.getId()).toUri();
		log.info("Finishing create uri. Tipo: " + this.getClass().getName());
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(TipoEndPoint.CREDITO + TipoEndPoint.ID)
	public ResponseEntity<Void> deleteCredito(@PathVariable Long id) {
		this.creditoService.delete(id);
		log.info("Finishing delete. Tipo: " + this.getClass().getName());
		return ResponseEntity.noContent().build();
	}

	@Override
	public CompradorDTO newClassDTO(Comprador obj) {
		log.info("Mapping 'Comprador' to 'CompradorDTO': " + this.getClass().getName());
		return new CompradorDTO(obj);
	}
}
