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
	}

	@GetMapping(TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA)
	public ResponseEntity<List<CotaDTO>> listarCotas(@PathVariable Long lancamentoId) {
		List<Cota> list = cotaService.findAllCotaByLancamentoId(lancamentoId);
		log.info("Finishing findAll. Tipo: " + this.getClass().getName());
		List<CotaDTO> listDTO = list.stream().map(obj -> new CotaDTO(obj)).collect(Collectors.toList());
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA + TipoEndPoint.PAGE)
	public ResponseEntity<Page<CotaDTO>> findPageCotas(@PathVariable Long lancamentoId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cota> list = cotaService.findPageByLancamentoId(lancamentoId, page, linesPerPage, direction, orderBy);
		log.info("Finishing findPage. Tipo: " + this.getClass().getName());
		Page<CotaDTO> listDTO = list.map(obj -> new CotaDTO(obj));
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(TipoEndPoint.COTA + TipoEndPoint.ID)
	public ResponseEntity<CotaDTO> findCotaById(@PathVariable Long id) {
		Cota obj = this.cotaService.findById(id);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		CotaDTO objDTO = new CotaDTO(obj);
		log.info("Finishing mapping. Tipo: " + this.getClass().getName());
		objDTO.setLancamentoId(obj.getLancamento().getId());
		return ResponseEntity.ok().body(objDTO);
	}

	@PostMapping(TipoEndPoint.LANCAMENTO_ID + TipoEndPoint.COTA)
	public ResponseEntity<CotaDTO> insertCota(@PathVariable Long lancamentoId, @Valid @RequestBody CotaDTO objDTO) {
		this.service.findById(lancamentoId);
		log.info("Finishing findById. Tipo: " + this.getClass().getName());
		objDTO.setLancamentoId(lancamentoId);
		Cota obj = this.cotaService.fromDTO(objDTO);
		log.info("Finishing fromDTO. Tipo: " + this.getClass().getName());
		obj = this.cotaService.insert(obj);
		log.info("Finishing insert. Tipo: " + this.getClass().getName());
		log.info("Create uri. " + this.getClass().getName());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("lancamento/cota/{id}")
				.buildAndExpand(obj.getId()).toUri();
		log.info("Finishing create uri. Tipo: " + this.getClass().getName());
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(TipoEndPoint.COTA + TipoEndPoint.ID)
	public ResponseEntity<Void> deleteCota(@PathVariable Long id) {
		this.cotaService.delete(id);
		log.info("Finishing delete. Tipo: " + this.getClass().getName());
		return ResponseEntity.noContent().build();
	}

	@Override
	public LancamentoDTO newClassDTO(Lancamento obj) {
		log.info("Mapping 'Lancamento' to 'LancamentoDTO': " + this.getClass().getName());
		return new LancamentoDTO(obj);
	}
}
