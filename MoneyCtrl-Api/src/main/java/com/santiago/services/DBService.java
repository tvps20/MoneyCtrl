package com.santiago.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.domain.Fatura;
import com.santiago.repositories.CartaoRepository;
import com.santiago.repositories.FaturaRepository;

@Service
public class DBService {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private FaturaRepository faturaRepository;
	
	public void instantiateTestDatabase() {
		Cartao cartao1 = new Cartao(null, "mastercard");
		Cartao cartao2 = new Cartao(null, "nubank");
		Cartao cartao3 = new Cartao(null, "visa");
		
		Fatura fatura1 = new Fatura(null, new Date(), new BigDecimal(100), "fatura de janeiro", "janeiro", cartao1);
		
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
		this.faturaRepository.save(fatura1);
	}
}
