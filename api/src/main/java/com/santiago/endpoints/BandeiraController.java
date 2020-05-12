package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Bandeira;
import com.santiago.dtos.BandeiraDTO;
import com.santiago.services.BandeiraService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bandeira")
public class BandeiraController extends BaseController<Bandeira, BandeiraDTO> {

	@Autowired
	public BandeiraController(BandeiraService service) {
		super(service);
	}

	@Override
	public BandeiraDTO newClassDTO(Bandeira obj) {
		log.info("Mapping 'Bandeira' to 'BandeiraDTO'. Tipo: " + this.getClass().getName());
		return new BandeiraDTO(obj);
	}
}
