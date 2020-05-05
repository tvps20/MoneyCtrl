package com.santiago.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiago.domain.Bandeira;
import com.santiago.domain.Cartao;
import com.santiago.domain.Comprador;
import com.santiago.domain.Credito;
import com.santiago.domain.Divida;
import com.santiago.domain.Fatura;
import com.santiago.domain.Lancamento;
import com.santiago.domain.Usuario;
import com.santiago.domain.enuns.TipoMes;
import com.santiago.domain.enuns.TipoPerfil;
import com.santiago.repositories.BandeiraRepository;
import com.santiago.repositories.CartaoRepository;
import com.santiago.repositories.CreditoRepository;
import com.santiago.repositories.DividaRepository;
import com.santiago.repositories.FaturaRepository;
import com.santiago.repositories.LancamentoRepository;
import com.santiago.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private BandeiraRepository BandeiraRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private FaturaRepository faturaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private DividaRepository dividaRepository;
	
	@Autowired
	private CreditoRepository creditoRepository;

	public void instantiateTestDatabase() {
		Bandeira bandeira1 = new Bandeira(null, "mastercard");
		Bandeira bandeira2 = new Bandeira(null, "visa");
		
		Cartao cartao1 = new Cartao(null, "master", bandeira1);
		Cartao cartao2 = new Cartao(null, "nubank", bandeira1);
		Cartao cartao3 = new Cartao(null, "digio", bandeira2);

		Fatura fatura1 = new Fatura(null, LocalDate.now(), new BigDecimal(100), "fatura de janeiro", TipoMes.JANEIRO,
				cartao1);

		Usuario user1 = new Usuario(null, "thiago@email.com", "thiago", "123", TipoPerfil.ADMIN);
		Comprador comprador2 = new Comprador(null, "filipe@email.com", "filipe", "123");
		Comprador comprador3 = new Comprador(null, "gilson@email.com", "gilson", "123");

		Lancamento lancamento1 = new Lancamento(null, new BigDecimal(23.53), "Itens para o chachorro",
				"Comprei tambem uma resistencia", LocalDate.now(), fatura1, comprador2, false, null, null);
		Lancamento lancamento2 = new Lancamento(null, new BigDecimal(47.27), "CardsofParadise", "cartas de magic",
				LocalDate.now(), fatura1, comprador3, false, null, null);
		Lancamento lancamento3 = new Lancamento(null, new BigDecimal(47.27), "Aliexpress", "itens de magic",
				LocalDate.now(), fatura1, comprador3, true, 6, 1);

		Divida divida1 = new Divida(null, new BigDecimal(333.38), "", LocalDate.now(), fatura1, comprador3, true, false,
				null, null);
		Divida divida2 = new Divida(null, new BigDecimal(25), "", LocalDate.now(), null, comprador3, false, true, 3, 2);

		Credito credito = new Credito(null, new BigDecimal(20), LocalDate.now(), comprador2);
		
		this.BandeiraRepository.saveAll(Arrays.asList(bandeira1, bandeira2));
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
		this.faturaRepository.save(fatura1);
		this.usuarioRepository.saveAll(Arrays.asList(user1, comprador2, comprador3));
		this.lancamentoRepository.saveAll(Arrays.asList(lancamento1, lancamento2, lancamento3));
		this.dividaRepository.saveAll(Arrays.asList(divida1, divida2));
		this.creditoRepository.save(credito);
	}
}
