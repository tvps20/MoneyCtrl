package com.santiago.moneyctrl.endpoints.enuns;

public class TipoEndPoint {

	public static final String BANDEIRA = "/bandeiras";
	public static final String CARTAO = "/catoes";
	public static final String COMPRADOR = "/compradores";
	public static final String DIVIDA = "/dividas";
	public static final String FATURA = "/faturas";
	public static final String LANCAMENTO = "/lancamentos";
	public static final String USUARIO = "/usuarios";
	public static final String CREDITO = "/creditos";
	public static final String PAGAMENTO = "/pagamentos";
	public static final String COTA = "/cotas";

	public static final String ID = "/{id}";
	public static final String PAGE = "/page";
	
	public static final String DIVIDA_ID = "/{dividaId}";
	public static final String COMPRADOR_ID = "/{compradorId}";
	public static final String LANCAMENTO_ID = "/{lancamentoId}";

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
