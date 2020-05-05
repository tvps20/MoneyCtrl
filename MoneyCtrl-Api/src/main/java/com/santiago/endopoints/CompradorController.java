package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Comprador;
import com.santiago.dtos.CompradorDTO;
import com.santiago.services.CompradorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comprador")
public class CompradorController extends BaseController<Comprador, CompradorDTO> {

	@Autowired
	public CompradorController(CompradorService service) {
		super(service);
	}

	@Override
	public CompradorDTO newClassDTO(Comprador obj) {
		log.info("Mapping 'Comprador' to 'CompradorDTO': " + this.getClass().getName());
		return new CompradorDTO(obj);
	}
}
