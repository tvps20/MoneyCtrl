package com.santiago.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Mensagem {

	private static ResourceBundle resourceBundle;
	private static Locale ptBR;

	// Bloco de inicializacao
	static {
		ptBR = new Locale("pt", "BR");
		resourceBundle = ResourceBundle.getBundle("messages", ptBR);
	}
	
	// Construtor
	private Mensagem() {
		//System.out.println(Locale.getDefault());
	}

	// Metodos
	public static String erroNotFount(String id, String claseNome) {
		return MessageFormat.format(resourceBundle.getString("mensagem-erro-notfound"), id, claseNome);
	}

	public static String erroDelete(String claseNome) {
		return MessageFormat.format(resourceBundle.getString("mensagem-erro-excluir"), claseNome);
	}
}
