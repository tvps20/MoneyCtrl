package com.santiago.moneyctrl.endpoints.enuns;

public class TipoEndPoint {

	public static final String BANDEIRA = "/bandeiras";
	public static final String CARTAO = "/cartoes";
	public static final String COMPRADOR = "/compradores";
	public static final String DIVIDA = "/dividas";
	public static final String FATURA = "/faturas";
	public static final String LANCAMENTO = "/lancamentos";
	public static final String USUARIO = "/usuarios";
	public static final String CREDITO = "/creditos";
	public static final String PAGAMENTO = "/pagamentos";
	public static final String COTA = "/cotas";
	public static final String ROLES = "/perfis";

	public static final String ID = "/{id}";
	public static final String PAGE = "/page";
	public static final String FIELD = "/{field}";
	public static final String VALIDA = "/valida";
	public static final String VALOR = "/{valor}";
	public static final String UNIQUE = "/valor-unico";
	public static final String EMAIL = "/email-unico";
	public static final String FECHARFATURA = "/fechar-fatura";
	public static final String PAGARFATURA = "/pagar-fatura";

	public static final String DIVIDA_ID = "/{dividaId}";
	public static final String COMPRADOR_ID = "/{compradorId}";
	public static final String LANCAMENTO_ID = "/{lancamentoId}";
	public static final String USUARIO_ID = "/{usuarioId}";
	

	// Construtores
	private TipoEndPoint() {

	}

	// Metodos
	public static String makeRoute(String... endpoints) {
		StringBuilder routa = new StringBuilder();

		for (String endPoint : endpoints) {
			routa.append(endPoint);
		}

		return routa.toString();
	}
}
