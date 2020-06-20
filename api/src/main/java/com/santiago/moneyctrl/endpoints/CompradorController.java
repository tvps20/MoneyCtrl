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
		BaseController.baseLog = CompradorController.log;
	}

	@GetMapping(TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO)
	public ResponseEntity<List<CreditoDTO>> listarCreditos(@PathVariable Long compradorId) {
		log.info("[GET] - Buscando todos os creditos. CompradorId: " + compradorId);
		List<Credito> list = creditoService.findAllCreditoByCompradorId(compradorId);
		List<CreditoDTO> listDTO = list.stream().map(obj -> new CreditoDTO(obj)).collect(Collectors.toList());

		log.info("[GET] - Get realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO + TipoEndPoint.PAGE)
	public ResponseEntity<Page<CreditoDTO>> listarPageCreditos(@PathVariable Long compradorId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		log.info("[GET PAGE] - Buscando todas os creditos paginado: {compradorId: " + compradorId + " Page: " + page
				+ ", linesPerPage: " + linesPerPage + ", direction: " + direction + ", orderBy: " + orderBy + "}");
		Page<Credito> list = creditoService.findPageByCompradorId(compradorId, page, linesPerPage, direction, orderBy);
		Page<CreditoDTO> listDTO = list.map(obj -> new CreditoDTO(obj));

		log.info("[GET PAGE] - GetPage realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.CREDITO + TipoEndPoint.ID)
	public ResponseEntity<CreditoDTO> buscarCreditoPeloId(@PathVariable Long id) {
		log.info("[GET ID] - Buscando credito pelo Id: " + id);
		Credito obj = this.creditoService.findById(id);
		CreditoDTO objDTO = new CreditoDTO(obj);
		objDTO.setCompradorId(obj.getComprador().getId());

		log.info("[GET ID] - GetById realizado com sucesso.");
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping(TipoEndPoint.COMPRADOR_ID + TipoEndPoint.CREDITO)
	public ResponseEntity<CreditoDTO> inserirCredito(@PathVariable Long compradorId,
			@Valid @RequestBody CreditoDTO objDTO) {
		log.info("[POST] - Salvando um novo credito. Dto: " + objDTO.toString());
		this.service.findById(compradorId);
		objDTO.setCompradorId(compradorId);
		Credito obj = this.creditoService.fromDTO(objDTO);
		obj = this.creditoService.insert(obj);
		log.info("[POST] - Criando uri.");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("comprador/credito/{id}")
				.buildAndExpand(obj.getId()).toUri();
		log.info("[POST] - Uri criado com sucesso. Uri: " + uri);

		log.info("[POST] - Post realizado com sucesso.");
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(TipoEndPoint.CREDITO + TipoEndPoint.ID)
	public ResponseEntity<Void> deletarCredito(@PathVariable Long id) {
		log.info("[DELETE] - Apagando credito de Id: " + id);
		this.creditoService.delete(id);

		log.info("[DELETE] - Delete realizado com sucesso.");
		return ResponseEntity.noContent().build();
	}

	@Override
	public CompradorDTO newClassDTO(Comprador obj) {
		log.info("[Mapping] - 'Comprador' to 'CompradorDTO'. Id: " + obj.getId());
		CompradorDTO dto = new CompradorDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
