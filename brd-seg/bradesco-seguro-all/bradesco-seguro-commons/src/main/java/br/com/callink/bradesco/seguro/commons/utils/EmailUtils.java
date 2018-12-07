package br.com.callink.bradesco.seguro.commons.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailUtils {

	private static final String EMAIL_VALIDACAO_REGEX = "((?:^[\\.]{0})[a-zA-Z0-9\\#\\$\\%\\&\\*\\+\\-\\/\\=\\^\\_\\{\\|\\}\\~\\!\\?]+(?:\\.[a-zA-Z0-9\\#\\$\\%\\&\\*\\+\\-\\/\\=\\^\\_\\{\\|\\}\\~\\!\\?]+)*@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]+)*(?:\\.[A-Za-z]{2,})$(?:[\\.]{0}$))";

	public static boolean isValido(String email) {

		boolean isValido = Boolean.TRUE;

		if (!StringUtils.isEmpty(email)) {
			isValido = Boolean.FALSE;

			Pattern pattern = Pattern.compile(EMAIL_VALIDACAO_REGEX);
			Matcher matcher = pattern.matcher(email);

			while (matcher.find()) {
				isValido = Boolean.TRUE;
			}
		}
		return isValido;
	}

	public static Map<String, Boolean> validarLista(List<String> emails) {

		Map<String, Boolean> emailsValidados = new HashMap<String, Boolean>();
		for (String email : emails) {
			emailsValidados.put(email, isValido(email));
		}
		return emailsValidados;
	}
}
