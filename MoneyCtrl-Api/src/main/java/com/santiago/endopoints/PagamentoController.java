package com.santiago.endopoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Pagamento;
import com.santiago.dtos.PagamentoDTO;
import com.santiago.services.PagamentoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pagamento")
public class PagamentoController extends BaseController<Pagamento, PagamentoDTO> {

	@Autowired
	public PagamentoController(PagamentoService service) {
		super(service);
	}

	@Override
	public PagamentoDTO newClassDTO(Pagamento obj) {
		log.info("Mapping 'Cartao' to 'CartaoDTO': " + this.getClass().getName());
		return new PagamentoDTO(obj);
	}
}
