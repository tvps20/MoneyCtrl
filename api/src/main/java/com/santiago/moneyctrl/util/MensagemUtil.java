package com.santiago.moneyctrl.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class MensagemUtil {

	private static final ResourceBundle resourceBundle;
	private static final Locale ptBR;

	// Bloco de inicializacao
	static {
		ptBR = new Locale("pt", "BR");
		resourceBundle = ResourceBundle.getBundle("messages", ptBR);
	}

	// Construtor
	private MensagemUtil() {
		// System.out.println(Locale.getDefault());
	}

	// Metodos
	public static String erroObjNotFountId(String entity, Long id) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.notFound.id"), entity, id);
	}

	public static String erroObjNotFountCampo(String campo, Long id) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.notFound.campo"), campo, id);
	}

	public static String erroObjDelete(String entity, Long id) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.excluir"), entity, id);
	}

	public static String erroObjInsert(String entity) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.inserir"), entity);
	}

	public static String erroObjUpdate(String entity, Long id) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.obj.update"), entity, id);
	}

	public static String erroEnumMes(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.mes"), tipo);
	}

	public static String erroEnumLancamento(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.lancamento"), tipo);
	}

	public static String erroEnumStatus(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.status"), tipo);
	}

	public static String erroEnumPerfil(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.perfil"), tipo);
	}

	public static String erroEnumAcesso(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.acesso"), tipo);
	}

	public static String erroEnumEntity(String tipo) {
		return MessageFormat.format(resourceBundle.getString("mensagem.erro.enum.entity"), tipo);
	}
}
