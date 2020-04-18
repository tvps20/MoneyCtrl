package com.santiago.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.repositories.CartaoRepository;

@Service
public class DBService {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	public void instantiateTestDatabase() {
		Cartao cartao1 = new Cartao(null, "mastercard");
		Cartao cartao2 = new Cartao(null, "nubank");
		Cartao cartao3 = new Cartao(null, "visa");
		
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
	}
}
