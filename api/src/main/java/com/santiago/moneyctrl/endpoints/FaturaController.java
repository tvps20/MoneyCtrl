package com.santiago.moneyctrl.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.CotaFaturaDTO;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.FaturaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.FATURA)
public class FaturaController extends BaseController<Fatura, FaturaDTO> {
	
	@Autowired
	public FaturaController(FaturaService service) {
		super(service);
		BaseController.baseLog = FaturaController.log;
	}
	
	@GetMapping(TipoEndPoint.PAGE + TipoEndPoint.STATUS)
	public ResponseEntity<Page<FaturaDTO>> listarPageFaturasStatus(@RequestParam String status,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		log.info("[GET PAGE] - Buscando paginado todos as faturas por status: {status: " + status + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + ", direction: " + direction + ", orderBy: " + orderBy + "}");
		Page<Fatura> list = ((FaturaService) this.service).findPageByStatus(status, page, linesPerPage, direction,
				orderBy);
		Page<FaturaDTO> listDTO = list.map(obj -> new FaturaDTO(obj));

		log.info("[GET PAGE] - GetPage realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(TipoEndPoint.PAGE + TipoEndPoint.NO_STATUS)
	public ResponseEntity<Page<FaturaDTO>> listarPageFaturasNoStatus(@RequestParam String status,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		log.info("[GET PAGE] - Buscando paginado todos as faturas sem status: {status: " + status + ", Page: " + page
				+ ", linesPerPage: " + linesPerPage + ", direction: " + direction + ", orderBy: " + orderBy + "}");
		Page<Fatura> list = ((FaturaService) this.service).noFindPageByStatus(status, page, linesPerPage, direction,
				orderBy);
		Page<FaturaDTO> listDTO = list.map(obj -> new FaturaDTO(obj));

		log.info("[GET PAGE] - GetPage realizado com sucesso.");
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(TipoEndPoint.ID + TipoEndPoint.COTA)
	public ResponseEntity<List<CotaFaturaDTO>> gerarCotas(@PathVariable Long id){
		baseLog.info("[GET COTAS] - Buscando todas as cotas.");
		List<CotaFaturaDTO> list = ((FaturaService) this.service).GerarCotasByIdFatura(id);

		baseLog.info("[GET COTAS] - Get cotas realizado com sucesso.");
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(TipoEndPoint.FECHARFATURA + TipoEndPoint.ID)
	public ResponseEntity<String> fecharFatura(@PathVariable Long id){
		log.info("[FECHAR-FATURA] - Fechar fatura. id: " + id);
		boolean result = ((FaturaService) this.service).fecharFatura(id) != null ? true : false;
		
		log.info("[FECHAR-FATURA] - Fatura fechada com sucesso. id: " + id);
		return ResponseEntity.ok().body("{ \"faturaFechada\": " + result + " }");
	}
	
	@GetMapping(TipoEndPoint.PAGARFATURA + TipoEndPoint.ID)
	public ResponseEntity<String> pagarFatura(@PathVariable Long id){
		log.info("[PAGAR-FATURA] - Pagar fatura. id: " + id);
		boolean result = ((FaturaService) this.service).pagarFatura(id) != null ? true : false;
		
		log.info("[PAGAR-FATURA] - Fatura paga com sucesso. id: " + id);
		return ResponseEntity.ok().body("{ \"faturaPaga\": " + result + " }");
	}

	@Override
	public FaturaDTO newClassDTO(Fatura obj) {
		log.info("[Mapping] - 'Fatura' to 'FaturaDTO'. Id: " + obj.getId());
		FaturaDTO dto = new FaturaDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
