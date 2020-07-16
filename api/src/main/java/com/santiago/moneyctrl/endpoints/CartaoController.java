package com.santiago.moneyctrl.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.dtos.CartaoDTO;
import com.santiago.moneyctrl.dtos.CotaCartaoDTO;
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
		BaseController.baseLog = CartaoController.log;
	}

	@GetMapping(TipoEndPoint.COTA)
	public ResponseEntity<List<CotaCartaoDTO>> gerarCotas() {
		baseLog.info("[GET COTAS] - Buscando todas as cotas.");
		List<CotaCartaoDTO> list = ((CartaoService) this.service).GerarAllCotasCartao();

		baseLog.info("[GET COTAS] - Get cotas realizado com sucesso.");
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(TipoEndPoint.VALIDA + TipoEndPoint.UNIQUE + TipoEndPoint.VALOR)
	public ResponseEntity<String> verificaUsernameUnico(@PathVariable String valor) {
		log.info("[GET] - Verificando valor único: " + valor);
		boolean result = ((CartaoService) this.service).verificarCampoUnico(valor);

		log.info("[GET] - Busca finalizada com sucesso.");
		return ResponseEntity.ok().body("{ \"alreadySaved\": " + result + " }");
	}

	@Override
	public CartaoDTO newClassDTO(Cartao obj) {
		log.info("[Mapping] - 'Cartao' to 'CartaoDTO'. Id: " + obj.getId());
		CartaoDTO dto = new CartaoDTO(obj);

		log.info("[Mapping] - Mapping finalizado com sucesso.");
		return dto;
	}
}
