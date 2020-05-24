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

import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Pagamento;
import com.santiago.moneyctrl.dtos.DividaDTO;
import com.santiago.moneyctrl.dtos.PagamentoDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.DividaService;
import com.santiago.moneyctrl.services.PagamentoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.DIVIDA)
public class DividaController extends BaseController<Divida, DividaDTO> {

	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	public DividaController(DividaService service) {
		super(service);
		BaseController.baseLog = DividaController.log;
	}

	@GetMapping(TipoEndPoint.DIVIDA_ID + TipoEndPoint.PAGAMENTO)
	public ResponseEntity<List<PagamentoDTO>> listarPagamentos(@PathVariable Long dividaId) {
		log.info("[GET] - Buscando todas as dividas. DividaId: " + dividaId);
		List<Pagamento> list = pagamentoService.findAllPagamentoByDividaId(dividaId);
		List<PagamentoDTO> listDTO = list.stream().map(obj -> new PagamentoDTO(obj)).collect(Collectors.toList());

		log.info("[GET] - Get realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.DIVIDA_ID + TipoEndPoint.PAGAMENTO + TipoEndPoint.PAGE)
	public ResponseEntity<Page<PagamentoDTO>> listarPagePagamentos(@PathVariable Long dividaId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		log.info("[GET PAGE] - Buscando todos os pagamentos paginado: {dividaId: " + dividaId + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + ", direction: " + direction + ", orderBy: " + orderBy + "}");
		Page<Pagamento> list = pagamentoService.findPageByDividaId(dividaId, page, linesPerPage, direction, orderBy);
		Page<PagamentoDTO> listDTO = list.map(obj -> new PagamentoDTO(obj));

		log.info("[GET PAGE] - GetPage realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.PAGAMENTO + TipoEndPoint.ID)
	public ResponseEntity<PagamentoDTO> buscarPagamentoPeloId(@PathVariable Long id) {
		log.info("[GET ID] - Buscando pagamento pelo Id: " + id);
		Pagamento obj = this.pagamentoService.findById(id);
		PagamentoDTO objDTO = new PagamentoDTO(obj);
		objDTO.setDividaId(obj.getDivida().getId());

		log.info("[GET ID] - GetById realizado com sucesso.");
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping(TipoEndPoint.DIVIDA_ID + TipoEndPoint.PAGAMENTO)
	public ResponseEntity<PagamentoDTO> insertPagamento(@PathVariable Long dividaId,
			@Valid @RequestBody PagamentoDTO objDTO) {
		log.info("[POST] - Salvando um novo Pagamento. Dto: " + objDTO.toString());
		this.service.findById(dividaId);
		objDTO.setDividaId(dividaId);
		Pagamento obj = this.pagamentoService.fromDTO(objDTO);
		obj = this.pagamentoService.insert(obj);
		log.info("[POST] - Criando uri.");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("divida/pagamento/{id}")
				.buildAndExpand(obj.getId()).toUri();
		log.info("[POST] - Uri criado com sucesso. Uri: " + uri);

		log.info("[POST] - Post realizado com sucesso.");
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(TipoEndPoint.PAGAMENTO + TipoEndPoint.ID)
	public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
		log.info("[DELETE] - Apagando Pagamento de Id: " + id);
		this.pagamentoService.delete(id);

		log.info("[DELETE] - Delete realizado com sucesso.");
		return ResponseEntity.noContent().build();
	}

	@Override
	public DividaDTO newClassDTO(Divida obj) {
		log.info("[Mapping] - 'Divida' to 'DividaDTO'. Id: " + obj.getId());
		DividaDTO dto = new DividaDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
