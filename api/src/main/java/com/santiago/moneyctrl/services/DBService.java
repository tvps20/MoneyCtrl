package com.santiago.moneyctrl.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiago.moneyctrl.domain.Bandeira;
import com.santiago.moneyctrl.domain.Cartao;
import com.santiago.moneyctrl.domain.Comprador;
import com.santiago.moneyctrl.domain.Cota;
import com.santiago.moneyctrl.domain.Credito;
import com.santiago.moneyctrl.domain.Divida;
import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.Lancamento;
import com.santiago.moneyctrl.domain.Pagamento;
import com.santiago.moneyctrl.domain.Usuario;
import com.santiago.moneyctrl.domain.enuns.TipoMes;
import com.santiago.moneyctrl.domain.enuns.TipoRoles;
import com.santiago.moneyctrl.repositories.BandeiraRepository;
import com.santiago.moneyctrl.repositories.CartaoRepository;
import com.santiago.moneyctrl.repositories.CotaRepository;
import com.santiago.moneyctrl.repositories.CreditoRepository;
import com.santiago.moneyctrl.repositories.DividaRepository;
import com.santiago.moneyctrl.repositories.FaturaRepository;
import com.santiago.moneyctrl.repositories.LancamentoRepository;
import com.santiago.moneyctrl.repositories.PagamentoRepository;
import com.santiago.moneyctrl.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DBService {

	@Autowired
	private BandeiraRepository bandeiraRepository;

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

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private CotaRepository cotaRepository;

	public void instantiateTestDatabase() {
		log.info("[DbService] - Populando banco de dados.");

		Bandeira bandeira1 = new Bandeira(null, "mastercard");
		Bandeira bandeira2 = new Bandeira(null, "visa");

		Cartao cartao1 = new Cartao(null, "master", bandeira1);
		Cartao cartao2 = new Cartao(null, "nubank", bandeira1);
		Cartao cartao3 = new Cartao(null, "digio", bandeira2);

		Fatura fatura1 = new Fatura(null, LocalDateTime.now(), "fatura de janeiro", TipoMes.JANEIRO, cartao1);

		Usuario user1 = new Usuario(null, "admin", "admin20", "123", TipoRoles.USUARIO, TipoRoles.ADMIN);
		Comprador comprador1 = new Comprador(null, "thiago", "tvps20", "123");
		Comprador comprador2 = new Comprador(null, "filipe", "filipe20", "123");
		Comprador comprador3 = new Comprador(null, "gilson", "gilson20", "123");

		Lancamento lancamento1 = new Lancamento(null, "Itens para o chachorro", "Comprei tambem uma resistencia",
				LocalDate.now(), fatura1, false, null, null);
		Lancamento lancamento2 = new Lancamento(null, "CardsofParadise", "cartas de magic", LocalDate.now(), fatura1,
				false, null, null);
		Lancamento lancamento3 = new Lancamento(null, "Aliexpress", "itens de magic", LocalDate.now(), fatura1, true, 6,
				1);

		Cota cota1 = new Cota(null, BigDecimal.valueOf(23.53), comprador2, lancamento1);
		Cota cota2 = new Cota(null, BigDecimal.valueOf(47.27), comprador3, lancamento2);
		Cota cota3 = new Cota(null, BigDecimal.valueOf(10.27), comprador3, lancamento3);
		Cota cota4 = new Cota(null, BigDecimal.valueOf(6.27), comprador2, lancamento3);

		Divida divida1 = new Divida(null, BigDecimal.valueOf(333.38), "", LocalDate.now(), fatura1, comprador3, false);
		Divida divida2 = new Divida(null, BigDecimal.valueOf(25), "", LocalDate.now(), null, comprador3, true);

		Credito credito = new Credito(null, BigDecimal.valueOf(20), LocalDate.now(), null, comprador2);
		Pagamento pagamento1 = new Pagamento(null, BigDecimal.valueOf(10), LocalDate.now(), "fiquei devendo 5 de troco",
				divida1);
		Pagamento pagamento2 = new Pagamento(null, BigDecimal.valueOf(60), LocalDate.now(), "pagou adiantado", divida1);

		this.bandeiraRepository.saveAll(Arrays.asList(bandeira1, bandeira2));
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
		this.faturaRepository.save(fatura1);
		this.usuarioRepository.saveAll(Arrays.asList(user1, comprador1, comprador2, comprador3));
		this.lancamentoRepository.saveAll(Arrays.asList(lancamento1, lancamento2, lancamento3));
		this.cotaRepository.saveAll(Arrays.asList(cota1, cota2, cota3, cota4));
		this.dividaRepository.saveAll(Arrays.asList(divida1, divida2));
		this.creditoRepository.save(credito);
		this.pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

		log.info("[DbService] - Dados inseridos com sucesso.");
	}
}
