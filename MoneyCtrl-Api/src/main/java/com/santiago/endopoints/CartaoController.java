package com.santiago.endopoints;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santiago.domain.Cartao;
import com.santiago.util.Mensagem;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

	@GetMapping
	public ResponseEntity<List<Cartao>> listar(){
		Cartao cartao = new Cartao(1L, "mastercard");
		System.out.println(cartao);
		System.out.println(Locale.getDefault());
		System.out.println(Mensagem.erroNotFount("a", "b"));
		System.out.println(Mensagem.erroDelete("b"));
		return ResponseEntity.ok().body(Arrays.asList(cartao));
	}
}
