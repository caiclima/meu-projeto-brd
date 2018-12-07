package br.com.callink.bradesco.seguro.web.util;

public class StringUtil {

	private StringUtil() {
	}

	public static boolean isNotEmpty(String str) {
		return str != null && !str.trim().isEmpty();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static boolean hasAtLeast(String str, int qtdChars) {
		return isNotEmpty(str) && str.trim().length() >= qtdChars;
	}

	public static Integer convertStringToInteger(String valor) {
		return StringUtil.isNotEmpty(valor) ? Integer.parseInt(valor) : null;
	}
}
