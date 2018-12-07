package br.com.callink.bradesco.seguro.commons.utils;

import java.util.regex.Pattern;

public class CepUtils {
	private static final Pattern patternMask = Pattern.compile("^(\\d{5})\\-(\\d{3})$");
	private static final Pattern pattern = Pattern.compile("^(\\d{5})(\\d{3})$");
	
	public static boolean isValid(String cep) {
		return patternMask.matcher(cep).matches();
	}
	
	public static String unmask(String cep) {
		if (StringUtils.isEmpty(cep)) {
			return null;
		}
		return patternMask.matcher(cep).replaceAll("$1$2");
	}
	
	public static String mask(String cep) {
		return pattern.matcher(cep).replaceAll("$1-$2");
	}
}
