package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Divida;
import com.santiago.dtos.DividaDTO;
import com.santiago.services.DividaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/divida")
public class DividaController extends BaseController<Divida, DividaDTO> {

	@Autowired
	public DividaController(DividaService service) {
		super(service);
	}

	@Override
	public DividaDTO newClassDTO(Divida obj) {
		log.info("Mapping 'Divida' to 'DividaDTO': " + this.getClass().getName());
		return new DividaDTO(obj);
	}
}
