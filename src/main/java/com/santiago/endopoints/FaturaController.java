package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Fatura;
import com.santiago.dtos.FaturaDTO;
import com.santiago.services.FaturaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/fatura")
public class FaturaController extends BaseController<Fatura, FaturaDTO> {

	@Autowired
	public FaturaController(FaturaService service) {
		super(service);
	}

	@Override
	public FaturaDTO newClassDTO(Fatura obj) {
		log.info("Mapping 'Fatura' to 'FaturaDTO': " + this.getClass().getName());
		return new FaturaDTO(obj);
	}
}
