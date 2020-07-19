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
		Fatura fatura3 = new Fatura(null, LocalDateTime.of(2020, 2, 1, 0, 0), TipoMes.JANEIRO, cartao3);

		Usuario user1 = new Usuario(null, "admin", "admin20", "123", TipoRoles.USUARIO, TipoRoles.ADMIN);
		Comprador comprador1 = new Comprador(null, "Thiago", "tvps20", "123", "thiago.vps20@gmail.com");
		Comprador comprador2 = new Comprador(null, "Filipe", "filipe20", "123", "filipe@email.com", "Santiago");
		Comprador comprador3 = new Comprador(null, "Gilson", "gilson20", "123", "gilson@email.com", "Santiago");
		Comprador comprador4 = new Comprador(null, "Joseilton", "joseilton20", "123");
		Comprador comprador5 = new Comprador(null, "Amanda", "amanda20", "123");
		Comprador comprador6 = new Comprador(null, "Sidney", "sidney20", "123");
		Comprador comprador7 = new Comprador(null, "Harryson", "harryson20", "123");
		Comprador comprador8 = new Comprador(null, "Evin", "evin20", "123");
		Comprador comprador9 = new Comprador(null, "Jocelio", "jocelio20", "123");
		Comprador comprador10 = new Comprador(null, "Gazo", "gazo20", "123");

		Lancamento lancamento1 = new Lancamento(null, "pc-pai", LocalDateTime.now(), fatura1, TipoLancamento.PARCELADO,
				10, 5);
		Cota cota1 = new Cota(null, BigDecimal.valueOf(45.00), comprador1, lancamento1);
		Cota cota2 = new Cota(null, BigDecimal.valueOf(45.00), comprador2, lancamento1);
		Cota cota3 = new Cota(null, BigDecimal.valueOf(205.06), comprador3, lancamento1);

		Lancamento lancamento2 = new Lancamento(null, "Teclado lks250", LocalDateTime.now(), fatura1,
				TipoLancamento.PARCELADO, 10, 2);
		Cota cota4 = new Cota(null, BigDecimal.valueOf(58.48), comprador1, lancamento2);
		Cota cota5 = new Cota(null, BigDecimal.valueOf(58.48), comprador2, lancamento2);

		Lancamento lancamento3 = new Lancamento(null, "carregador", LocalDateTime.now(), fatura1,
				TipoLancamento.PARCELADO, 6, 3);
		Cota cota6 = new Cota(null, BigDecimal.valueOf(20.00), comprador3, lancamento3);

		Lancamento lancamento4 = new Lancamento(null, "Notbook", LocalDateTime.now(), fatura1, TipoLancamento.PARCELADO,
				12, 6);
		Cota cota7 = new Cota(null, BigDecimal.valueOf(228.85), comprador4, lancamento4);

		Lancamento lancamento5 = new Lancamento(null, "Amazon Prime", LocalDateTime.now(), fatura2,
				TipoLancamento.ASSINATURA);
		Cota cota8 = new Cota(null, BigDecimal.valueOf(9.90), comprador1, lancamento5);

		Lancamento lancamento6 = new Lancamento(null, "Spotify", LocalDateTime.now(), fatura2,
				TipoLancamento.ASSINATURA);
		Cota cota9 = new Cota(null, BigDecimal.valueOf(8.50), comprador1, lancamento6);

		Lancamento lancamento7 = new Lancamento(null, "Netflix", LocalDateTime.now(), fatura2,
				TipoLancamento.ASSINATURA);
		Cota cota10 = new Cota(null, BigDecimal.valueOf(5.90), comprador1, lancamento7);
		Cota cota11 = new Cota(null, BigDecimal.valueOf(10.00), comprador3, lancamento7);
		Cota cota12 = new Cota(null, BigDecimal.valueOf(20.00), comprador6, lancamento7);
		Cota cota13 = new Cota(null, BigDecimal.valueOf(10.00), comprador7, lancamento7);

		Lancamento lancamento8 = new Lancamento(null, "aliexpress", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 6, 4);
		Cota cota14 = new Cota(null, BigDecimal.valueOf(25.29), comprador1, lancamento8);

		Lancamento lancamento9 = new Lancamento(null, "Ventilador", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 10, 6);
		Cota cota15 = new Cota(null, BigDecimal.valueOf(13.90), comprador1, lancamento9);

		Lancamento lancamento10 = new Lancamento(null, "Teclado", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 10, 9);
		Cota cota16 = new Cota(null, BigDecimal.valueOf(38.00), comprador1, lancamento10);

		Lancamento lancamento11 = new Lancamento(null, "CardParadise", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 6, 3);
		Cota cota17 = new Cota(null, BigDecimal.valueOf(14.39), comprador1, lancamento11);

		Lancamento lancamento12 = new Lancamento(null, "Jambo", LocalDateTime.now(), fatura2, TipoLancamento.PARCELADO,
				3, 3);
		Cota cota18 = new Cota(null, BigDecimal.valueOf(25.50), comprador1, lancamento12);

		Lancamento lancamento13 = new Lancamento(null, "Miniaturas", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 6, 6);
		Cota cota19 = new Cota(null, BigDecimal.valueOf(26.35), comprador1, lancamento13);
		Cota cota20 = new Cota(null, BigDecimal.valueOf(26.35), comprador2, lancamento13);

		Lancamento lancamento14 = new Lancamento(null, "tv fire", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 10, 7);
		Cota cota21 = new Cota(null, BigDecimal.valueOf(23.90), comprador1, lancamento14);
		Cota cota22 = new Cota(null, BigDecimal.valueOf(23.90), comprador3, lancamento14);

		Lancamento lancamento15 = new Lancamento(null, "box magic", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 6, 3);
		Cota cota23 = new Cota(null, BigDecimal.valueOf(20.09), comprador1, lancamento15);

		Lancamento lancamento16 = new Lancamento(null, "Livros RPG", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 12, 5);
		Cota cota24 = new Cota(null, BigDecimal.valueOf(32.48), comprador1, lancamento16);

		Lancamento lancamento17 = new Lancamento(null, "Vitamina D", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 4, 2);
		Cota cota25 = new Cota(null, BigDecimal.valueOf(17.25), comprador1, lancamento17);

		Lancamento lancamento18 = new Lancamento(null, "Moldura", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 2, 2);
		Cota cota26 = new Cota(null, BigDecimal.valueOf(44.00), comprador1, lancamento18);

		Lancamento lancamento19 = new Lancamento(null, "MusicDot", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 12, 1);
		Cota cota27 = new Cota(null, BigDecimal.valueOf(50.00), comprador1, lancamento19);

		Lancamento lancamento20 = new Lancamento(null, "Universo Magic", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 4, 1);
		Cota cota28 = new Cota(null, BigDecimal.valueOf(9.10), comprador1, lancamento20);
		Cota cota29 = new Cota(null, BigDecimal.valueOf(14.10), comprador9, lancamento20);

		Lancamento lancamento21 = new Lancamento(null, "Presente Daniel", LocalDateTime.now(), fatura2,
				TipoLancamento.AVISTA);
		Cota cota30 = new Cota(null, BigDecimal.valueOf(25.00), comprador1, lancamento21);

		Lancamento lancamento22 = new Lancamento(null, "dexa", LocalDateTime.now(), fatura2, TipoLancamento.AVISTA);
		Cota cota31 = new Cota(null, BigDecimal.valueOf(22.47), comprador1, lancamento22);

		Lancamento lancamento23 = new Lancamento(null, "Óculos", LocalDateTime.now(), fatura2, TipoLancamento.PARCELADO,
				12, 1);
		Cota cota32 = new Cota(null, BigDecimal.valueOf(115.90), comprador5, lancamento23);

		Lancamento lancamento24 = new Lancamento(null, "Mundo Verde", LocalDateTime.now(), fatura2,
				TipoLancamento.AVISTA);
		Cota cota33 = new Cota(null, BigDecimal.valueOf(29.33), comprador5, lancamento24);

		Lancamento lancamento25 = new Lancamento(null, "Cadeira", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 10, 3);
		Cota cota34 = new Cota(null, BigDecimal.valueOf(64.99), comprador5, lancamento25);

		Lancamento lancamento26 = new Lancamento(null, "Caixa de som", LocalDateTime.now(), fatura2,
				TipoLancamento.PARCELADO, 8, 6);
		Cota cota35 = new Cota(null, BigDecimal.valueOf(72.11), comprador10, lancamento26);

		Lancamento lancamento27 = new Lancamento(null, "Todas as compras", LocalDateTime.now(), fatura3,
				TipoLancamento.AVISTA);
		Cota cota36 = new Cota(null, BigDecimal.valueOf(651.90), comprador4, lancamento27);

		Divida divida1 = new Divida(null, BigDecimal.valueOf(100.00), "Bicicleta", LocalDateTime.now(), comprador8);
		Pagamento pagamento1 = new Pagamento(null, BigDecimal.valueOf(25.00), LocalDateTime.now(), divida1);

		Divida divida2 = new Divida(null, BigDecimal.valueOf(100.00), "Emprestado", LocalDateTime.now(), comprador4);
		Pagamento pagamento2 = new Pagamento(null, BigDecimal.valueOf(50.00), LocalDateTime.now(), divida2);
		Pagamento pagamento3 = new Pagamento(null, BigDecimal.valueOf(50.00), LocalDateTime.now(), divida2);
		divida2.setPaga(true);

		Divida divida3 = new Divida(null, BigDecimal.valueOf(72.11), "Mês de maio", LocalDateTime.now(), comprador10);

		Credito credito = new Credito(null, BigDecimal.valueOf(01.00), LocalDateTime.now(), "fatura de maio",
				comprador5);

		this.bandeiraRepository.saveAll(Arrays.asList(bandeira1, bandeira2));
		this.cartaoRepository.saveAll(Arrays.asList(cartao1, cartao2, cartao3));
		this.faturaRepository.saveAll(Arrays.asList(fatura1, fatura2, fatura3));
		this.usuarioRepository.saveAll(Arrays.asList(user1, comprador1, comprador2, comprador3, comprador4, comprador5,
				comprador6, comprador7, comprador8, comprador9, comprador10));
		this.lancamentoRepository.saveAll(Arrays.asList(lancamento1, lancamento2, lancamento3, lancamento4, lancamento5,
				lancamento6, lancamento7, lancamento8, lancamento9, lancamento10, lancamento11, lancamento12,
				lancamento13, lancamento14, lancamento15, lancamento16, lancamento17, lancamento18, lancamento19,
				lancamento20, lancamento21, lancamento22, lancamento23, lancamento24, lancamento25, lancamento26,
				lancamento27));
		this.cotaRepository.saveAll(Arrays.asList(cota1, cota2, cota3, cota4, cota5, cota6, cota7, cota8, cota9, cota10,
				cota11, cota12, cota13, cota14, cota15, cota16, cota17, cota18, cota19, cota20, cota21, cota22, cota23,
				cota24, cota25, cota26, cota27, cota28, cota29, cota30, cota31, cota32, cota33, cota34, cota35,
				cota36));
		this.dividaRepository.saveAll(Arrays.asList(divida1, divida2, divida3));
		this.creditoRepository.save(credito);
		this.pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2, pagamento3));

		log.info("[DbService] - Dados inseridos com sucesso.");
	}
}
