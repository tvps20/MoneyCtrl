package com.santiago.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Cartao;
import com.santiago.dtos.CartaoDTO;
import com.santiago.services.CartaoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cartao")
public class CartaoController extends BaseController<Cartao, CartaoDTO> {

	@Autowired
	public CartaoController(CartaoService service) {
		super(service);
	}

	@Override
	public CartaoDTO newClassDTO(Cartao obj) {
		log.info("Mapping 'Cartao' to 'CartaoDTO'. Tipo: " + this.getClass().getName());
		return new CartaoDTO(obj);
	}
}
