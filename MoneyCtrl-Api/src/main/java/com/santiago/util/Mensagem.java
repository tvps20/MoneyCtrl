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
	public static String erroObjNotFount(Long id, String claseNome) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.notFound"), id, claseNome);
	}

	public static String erroObjDelete(String claseNome) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.excluir"), claseNome);
	}
	
	public static String erroObjInserir(String claseNome) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.inserir"), claseNome);
	}
	
	public static String erroEnumMes(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.mes"), tipo);
	}
	
	public static String erroEnumStatus(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.status"), tipo);
	}
	
	public static String erroEnumPerfil(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.perfil"), tipo);
	}
}
