package com.santiago.moneyctrl.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.dtos.CartaoDTO;
import com.santiago.moneyctrl.endpoints.enuns.TipoEndPoint;
import com.santiago.moneyctrl.services.CartaoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(TipoEndPoint.CARTAO)
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
