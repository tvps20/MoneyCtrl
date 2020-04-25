package com.santiago.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiago.domain.Cartao;
import com.santiago.domain.Fatura;
import com.santiago.domain.Lancamento;
import com.santiago.domain.LancamentoComParcela;
import com.santiago.domain.enuns.TipoMes;
import com.santiago.repositories.CartaoRepository;
import com.santiago.repositories.FaturaRepository;
import com.santiago.repositories.LancamentoRepository;

@Service
public class DBService {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private FaturaRepository faturaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public void instantiateTestDatabase() {
		Cartao cartao1 = new Cartao(null, "mastercard");
		Cartao cartao2 = new Cartao(null, "nubank");
		Cartao cartao3 = new Cartao(null, "visa");
		
		Fatura fatura1 = new Fatura(null, LocalDate.now(), new BigDecimal(100), "fatura de janeiro", TipoMes.JANEIRO, cartao1);
		
		Lancamento lancamento1 = new Lancamento(null, new BigDecimal(23.53), "Itens para o chachorro", "Comprei tambem uma resistencia", LocalDate.now(), false, fatura1);
		Lancamento lancamento2 = new Lancamento(null, new BigDecimal(47.27), "CardsofParadise", "cartas de magic", LocalDate.now(), false, fatura1);
		Lancamento lancamento3 = new LancamentoComParcela(null, new BigDecimal(47.27), "Aliexpress", "itens de magic", LocalDate.now(), true, fatura1, 6, 1);
		
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
		this.faturaRepository.save(fatura1);
		this.lancamentoRepository.saveAll(Arrays.asList(lancamento1, lancamento2, lancamento3));
	}
}
