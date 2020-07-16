package com.santiago.moneyctrl.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.dtos.BandeiraDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.BandeiraService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.BANDEIRA)
public class BandeiraController extends BaseController<Bandeira, BandeiraDTO> {

	@Autowired
	public BandeiraController(BandeiraService service) {
		super(service);
		BaseController.baseLog = BandeiraController.log;
	}

	@GetMapping(TipoEndPoint.VALIDA + TipoEndPoint.UNIQUE + TipoEndPoint.VALOR)
	public ResponseEntity<String> verificaUsernameUnico(@PathVariable String valor) {
		log.info("[GET] - Verificando valor único: " + valor);
		boolean result = ((BandeiraService) this.service).verificarCampoUnico(valor);

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body("{ \"alreadySaved\": " + result + " }");
	}

	@Override
	public BandeiraDTO newClassDTO(Bandeira obj) {
		log.info("[Mapping] - 'Bandeira' to 'BandeiraDTO'. Id: " + obj.getId());
		BandeiraDTO dto = new BandeiraDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
