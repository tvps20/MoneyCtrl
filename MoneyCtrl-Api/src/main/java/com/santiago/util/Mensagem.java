package com.santiago.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Mensagem {

	private static final  ResourceBundle resourceBundle;
	private static final  Locale ptBR;

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
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.notFound"), id, claseNome);
	}

	public static String erroDelete(String claseNome) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.excluir"), claseNome);
	}
}
