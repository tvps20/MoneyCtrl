package com.santiago.moneyctrl.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.dtos.FaturaDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.FaturaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.FATURA)
public class FaturaController extends BaseController<Fatura, FaturaDTO> {

	@Autowired
	private FaturaService faturaService;
	
	@Autowired
	public FaturaController(FaturaService service) {
		super(service);
		BaseController.baseLog = FaturaController.log;
	}
	
	@GetMapping(TipoEndPoint.FECHARFATURA + TipoEndPoint.ID)
	public ResponseEntity<String> fecharFatura(@PathVariable Long id){
		log.info("[FECHAR-FATURA] - Fechar fatura. id: " + id);
		boolean result = this.faturaService.fecharFatura(id) != null ? true : false;
		
		log.info("[FECHAR-FATURA] - Fatura fechada com sucesso. id: " + id);
		return ResponseEntity.ok().body("{ \"faturaFechada\": " + result + " }");
	}
	
	@GetMapping(TipoEndPoint.PAGARFATURA + TipoEndPoint.ID)
	public ResponseEntity<String> pagarFatura(@PathVariable Long id){
		log.info("[PAGAR-FATURA] - Pagar fatura. id: " + id);
		boolean result = this.faturaService.pagarFatura(id) != null ? true : false;
		
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
