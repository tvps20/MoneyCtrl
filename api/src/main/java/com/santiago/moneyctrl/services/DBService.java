package com.santiago.moneyctrl.services;

import java.math.BigDecimal;
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
import com.santiago.moneyctrl.domain.enuns.TipoLancamento;
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

		Bandeira bandeira1 = new Bandeira(null, "Mastercard");
		Bandeira bandeira2 = new Bandeira(null, "Visa");

		Cartao cartao1 = new Cartao(null, "Master", bandeira1);
		Cartao cartao2 = new Cartao(null, "Nubank", bandeira1);
		Cartao cartao3 = new Cartao(null, "Digio", bandeira2);

		Fatura fatura1 = new Fatura(null, LocalDateTime.of(2020, 2, 1, 0, 0), TipoMes.JANEIRO, cartao1);
		Fatura fatura2 = new Fatura(null, LocalDateTime.of(2020, 2, 1, 0, 0), TipoMes.JANEIRO, cartao2);
		Fatura fatura3 = new Fatura(null, LocalDateTime.of(2020, 2, 1, 0, 0), "Fatura de janeiro", TipoMes.JANEIRO,
				cartao3);

		Usuario user1 = new Usuario(null, "admin", "admin20", "123", TipoRoles.USUARIO, TipoRoles.ADMIN);
		Comprador comprador1 = new Comprador(null, "thiago", "tvps20", "123", "thiago.vps20@gmail.com");
		Comprador comprador2 = new Comprador(null, "filipe", "filipe20", "123");
		Comprador comprador3 = new Comprador(null, "gilson", "gilson20", "123");
		Comprador comprador4 = new Comprador(null, "joseilton", "joseilton20", "123");
		Comprador comprador5 = new Comprador(null, "amanda", "amanda20", "123");
		Comprador comprador6 = new Comprador(null, "sidney", "sidney20", "123");
		Comprador comprador7 = new Comprador(null, "harryson", "harryson20", "123");
		Comprador comprador8 = new Comprador(null, "evin", "evin20", "123");

		Lancamento lancamento1 = new Lancamento(null, "Celular", LocalDateTime.now(), fatura1, TipoLancamento.PARCELADO,
				12, 8);
		Lancamento lancamento2 = new Lancamento(null, "Notbook", LocalDateTime.now(), fatura1, TipoLancamento.PARCELADO,
				12, 1);
		Lancamento lancamento3 = new Lancamento(null, "Casa Alves", LocalDateTime.now(), fatura3,
				TipoLancamento.PARCELADO, 2, 1);
		Lancamento lancamento4 = new Lancamento(null, "Nagem", LocalDateTime.now(), fatura3, TipoLancamento.PARCELADO,
				2, 1);
		Lancamento lancamento5 = new Lancamento(null, "Super Esperança", LocalDateTime.now(), fatura3,
				TipoLancamento.AVISTA);
		Lancamento lancamento6 = new Lancamento(null, "Casa Alves", LocalDateTime.now(), fatura3,
				TipoLancamento.PARCELADO, 2, 2);
		Lancamento lancamento7 = new Lancamento(null, "Netflix", LocalDateTime.now(), fatura2,
				TipoLancamento.ASSINATURA);
		Lancamento lancamento8 = new Lancamento(null, "Oculos", LocalDateTime.now(), fatura2, TipoLancamento.PARCELADO,
				10, 1);
		Lancamento lancamento9 = new Lancamento(null, "Credito", LocalDateTime.now(), fatura2, TipoLancamento.AVISTA);
		Lancamento lancamento10 = new Lancamento(null, "Barbeador", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 10, 9);
		Lancamento lancamento11 = new Lancamento(null, "SSD", LocalDateTime.now(), fatura2, TipoLancamento.PARCELADO,
				10, 8);
		Lancamento lancamento12 = new Lancamento(null, "TV Fire", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 10, 2);

		Cota cota1 = new Cota(null, BigDecimal.valueOf(78.87), comprador3, lancamento1);
		Cota cota2 = new Cota(null, BigDecimal.valueOf(228.85), comprador4, lancamento2);
		Cota cota3 = new Cota(null, BigDecimal.valueOf(157.57), comprador4, lancamento3);
		Cota cota4 = new Cota(null, BigDecimal.valueOf(14.95), comprador4, lancamento4);
		Cota cota5 = new Cota(null, BigDecimal.valueOf(70.10), comprador4, lancamento5);
		Cota cota6 = new Cota(null, BigDecimal.valueOf(104.84), comprador4, lancamento6);
		Cota cota7 = new Cota(null, BigDecimal.valueOf(20.00), comprador6, lancamento7);
		Cota cota8 = new Cota(null, BigDecimal.valueOf(10.00), comprador3, lancamento7);
		Cota cota9 = new Cota(null, BigDecimal.valueOf(10.00), comprador7, lancamento7);
		Cota cota10 = new Cota(null, BigDecimal.valueOf(05.90), comprador1, lancamento7);
		Cota cota11 = new Cota(null, BigDecimal.valueOf(115.90), comprador5, lancamento8);
		Cota cota12 = new Cota(null, BigDecimal.valueOf(40.00), comprador5, lancamento9);
		Cota cota13 = new Cota(null, BigDecimal.valueOf(14.17), comprador1, lancamento10);
		Cota cota14 = new Cota(null, BigDecimal.valueOf(14.17), comprador8, lancamento10);
		Cota cota15 = new Cota(null, BigDecimal.valueOf(14.17), comprador3, lancamento11);
		Cota cota16 = new Cota(null, BigDecimal.valueOf(23.90), comprador1, lancamento12);
		Cota cota17 = new Cota(null, BigDecimal.valueOf(23.90), comprador3, lancamento12);

		Divida divida1 = new Divida(null, BigDecimal.valueOf(333.38), "Divida fatura teste", LocalDateTime.now(),
				comprador3);
		Divida divida2 = new Divida(null, BigDecimal.valueOf(25.00), "Emprestado teste", LocalDateTime.now(),
				comprador3, true);

		Credito credito = new Credito(null, BigDecimal.valueOf(01.00), LocalDateTime.now(), "fatura de maio teste",
				comprador2);
		Pagamento pagamento1 = new Pagamento(null, BigDecimal.valueOf(10.00), LocalDateTime.now(), divida1);
		Pagamento pagamento2 = new Pagamento(null, BigDecimal.valueOf(60.00), LocalDateTime.now(), divida1);
		Pagamento pagamento3 = new Pagamento(null, BigDecimal.valueOf(25.00), LocalDateTime.now(), divida2);

		this.bandeiraRepository.saveAll(Arrays.asList(bandeira1, bandeira2));
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
		this.faturaRepository.saveAll(Arrays.asList(fatura1, fatura2, fatura3));
		this.usuarioRepository.saveAll(Arrays.asList(user1, comprador1, comprador2, comprador3, comprador4, comprador5,
				comprador6, comprador7, comprador8));
		this.lancamentoRepository.saveAll(Arrays.asList(lancamento1, lancamento2, lancamento3, lancamento4, lancamento5,
				lancamento6, lancamento7, lancamento8, lancamento9, lancamento10, lancamento11, lancamento12));
		this.cotaRepository.saveAll(Arrays.asList(cota1, cota2, cota3, cota4, cota5, cota6, cota7, cota8, cota9, cota10,
				cota11, cota12, cota13, cota14, cota15, cota16, cota17));
		this.dividaRepository.saveAll(Arrays.asList(divida1, divida2));
		this.creditoRepository.save(credito);
		this.pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2, pagamento3));

		log.info("[DbService] - Dados inseridos com sucesso.");
	}
}
