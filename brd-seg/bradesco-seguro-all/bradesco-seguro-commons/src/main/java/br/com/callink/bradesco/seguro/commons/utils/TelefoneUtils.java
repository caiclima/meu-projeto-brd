package br.com.callink.bradesco.seguro.commons.utils;

import java.util.regex.Pattern;

public class TelefoneUtils {
	
	private static final String VALIDACAO_CELULAR = "((10)|\\([1-9][1-9]\\)) [5-9][0-9]{3,4}-[0-9]{4}";
	private static final String VALIDACAO_TELEFONE = "((10)|\\([1-9][1-9]\\)) [1-9][0-9]{3,4}-[0-9]{4}";
	
	public static Boolean validarCelular(String numero) {
		if (numero == null) {
			return Boolean.FALSE;
		}
		
		return Pattern.compile(VALIDACAO_CELULAR).matcher(numero).matches() || Pattern.compile(VALIDACAO_CELULAR).matcher(insertCharactersPhone(numero)).matches();
	}
	
	public static Boolean validarFixo(String numero) {
		if (numero == null) {
			return Boolean.FALSE;
		}
		
		return Pattern.compile(VALIDACAO_TELEFONE).matcher(numero).matches() || Pattern.compile(VALIDACAO_TELEFONE).matcher(insertCharactersPhone(numero)).matches();
	}
	
	public static String removeCharactersPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		
		return phone.replaceAll("(", "").replaceAll(")", "").replaceAll("-", "").replaceAll(" ", "");
	}
	
	public static String insertCharactersPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return phone;
		}
		
		if (phone.length() == 10) {
			return "(" + phone.substring(0, 2) + ")" +  " " + phone.substring(2, 6) + "-" + phone.substring(6, 10);
		} else if (phone.length() == 11) {
			return "(" + phone.substring(0, 2) + ")" +  " " + phone.substring(2, 7) + "-" + phone.substring(7, 11);
		} else {
			return phone;
		}
	}

}
