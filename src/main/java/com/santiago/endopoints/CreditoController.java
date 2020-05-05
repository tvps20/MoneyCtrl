package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Credito;
import com.santiago.dtos.CreditoDTO;
import com.santiago.services.CreditoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/credito")
public class CreditoController extends BaseController<Credito, CreditoDTO> {

	@Autowired
	public CreditoController(CreditoService service) {
		super(service);
	}

	@Override
	public CreditoDTO newClassDTO(Credito obj) {
		log.info("Mapping 'Cartao' to 'CartaoDTO': " + this.getClass().getName());
		return new CreditoDTO(obj);
	}
}
