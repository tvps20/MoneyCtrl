package com.santiago.moneyctrl.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
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
	public FaturaController(FaturaService service) {
		super(service);
		BaseController.baseLog = FaturaController.log;
	}

	@Override
	public FaturaDTO newClassDTO(Fatura obj) {
		log.info("[Mapping] - 'Fatura' to 'FaturaDTO'. Id: " + obj.getId());
		FaturaDTO dto = new FaturaDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
