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

import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.dtos.CotaDTO;
import com.santiago.moneyctrl.dtos.LancamentoDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.CotaService;
import com.santiago.moneyctrl.services.LancamentoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.LANCAMENTO)
public class LancamentoController extends BaseController<Lancamento, LancamentoDTO> {

	@Autowired
	private CotaService cotaService;

	@Autowired
	public LancamentoController(LancamentoService service) {
		super(service);
		BaseController.baseLog = LancamentoController.log;
	}

	@GetMapping(TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA)
	public ResponseEntity<List<CotaDTO>> listarCotas(@PathVariable Long lancamentoId) {
		log.info("[GET] - Buscando todas as cotas. LancamentoId: " + lancamentoId);
		List<Cota> list = cotaService.findAllCotaByLancamentoId(lancamentoId);
		List<CotaDTO> listDTO = list.stream().map(obj -> new CotaDTO(obj)).collect(Collectors.toList());

		log.info("[GET] - Get realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA + TipoEndPoint.PAGE)
	public ResponseEntity<Page<CotaDTO>> listarPageCotas(@PathVariable Long lancamentoId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		log.info("[GET PAGE] - Buscando todos as cotas paginado: {lancamentoId: " + lancamentoId + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + ", direction: " + direction + ", orderBy: " + orderBy + "}");
		Page<Cota> list = cotaService.findPageCotaByLancamentoId(lancamentoId, page, linesPerPage, direction, orderBy);
		Page<CotaDTO> listDTO = list.map(obj -> new CotaDTO(obj));

		log.info("[GET PAGE] - GetPage realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.COTA + TipoEndPoint.ID)
	public ResponseEntity<CotaDTO> buscarCotaPeloId(@PathVariable Long id) {
		log.info("[GET ID] - Buscando cota pelo Id: " + id);
		Cota obj = this.cotaService.findById(id);
		CotaDTO objDTO = new CotaDTO(obj);
		objDTO.setLancamento(new LancamentoDTO(obj.getLancamento()));

		log.info("[GET ID] - GetById realizado com sucesso.");
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping(TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA)
	public ResponseEntity<CotaDTO> inserirCota(@PathVariable Long lancamentoId, @Valid @RequestBody CotaDTO objDTO) {
		log.info("[POST] - Salvando uma nova Cota. Dto: " + objDTO.toString());
		this.service.findById(lancamentoId);
		objDTO.setLancamento(new LancamentoDTO(lancamentoId));
		Cota obj = this.cotaService.fromDTO(objDTO);
		obj = this.cotaService.insert(obj);

		log.info("[POST] - Criando uri.");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("lancamento/cota/{id}")
				.buildAndExpand(obj.getId()).toUri();
		log.info("[POST] - Uri criado com sucesso. Uri: " + uri);

		log.info("[POST] - Put realizado com sucesso.");
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(TipoEndPoint.COTA + TipoEndPoint.ID)
	public ResponseEntity<Void> deletarCota(@PathVariable Long id) {
		log.info("[DELETE] - Apagando Cota de Id: " + id);
		this.cotaService.delete(id);

		log.info("[DELETE] - Delete realizado com sucesso.");
		return ResponseEntity.noContent().build();
	}

	@Override
	public LancamentoDTO newClassDTO(Lancamento obj) {
		log.info("[Mapping] - 'Lancamento' to 'LancamentoDTO'. Id: " + obj.getId());
		LancamentoDTO dto = new LancamentoDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
