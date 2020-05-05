package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Lancamento;
import com.santiago.dtos.LancamentoDTO;
import com.santiago.services.LancamentoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/lancamento")
public class LancamentoController extends BaseController<Lancamento, LancamentoDTO> {

	@Autowired
	public LancamentoController(LancamentoService service) {
		super(service);
	}

	@Override
	public LancamentoDTO newClassDTO(Lancamento obj) {
		log.info("Mapping 'Lancamento' to 'LancamentoDTO': " + this.getClass().getName());
		return new LancamentoDTO(obj);
	}
}
