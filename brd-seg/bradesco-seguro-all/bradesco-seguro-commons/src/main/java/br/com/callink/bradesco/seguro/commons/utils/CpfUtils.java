package br.com.callink.bradesco.seguro.commons.utils;

public class CpfUtils {
	
	public static String removeMask(String cpf) {
		if(StringUtils.isEmpty(cpf)) {
			return "";
		}
		
		return cpf.replaceAll("[\\.-]+", "");
	}
	
	public static boolean validateCPF(String stringCPF) {
		stringCPF = removeMask(stringCPF);
	    int i, soma1, soma2, digito1, digito2;
		
	    if (stringCPF.length() != 11) {
			return false;
		}
	    
		if ((stringCPF.equals("00000000000")) || 
			(stringCPF.equals("11111111111")) || 
			(stringCPF.equals("22222222222")) || 
			(stringCPF.equals("33333333333")) || 
			(stringCPF.equals("44444444444")) || 
			(stringCPF.equals("55555555555")) || 
			(stringCPF.equals("66666666666")) || 
			(stringCPF.equals("77777777777")) || 
			(stringCPF.equals("88888888888")) || 
			(stringCPF.equals("99999999999"))) {
			return false;
		}

	      //Calcula o primeiro dígito
	    soma1 = 0;
		
	    for (i = 0; i <= 8; i++) {
			soma1 = soma1 + Integer.parseInt(stringCPF.substring(i, i + 1))	* (10 - i);
		}
		
		if (soma1 % 11 < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - (soma1 % 11);
		}
		
		soma2 = 0;
		
		for (i = 0; i <= 9; i++) {
			soma2 = soma2 + Integer.parseInt(stringCPF.substring(i, i + 1))	* (11 - i);
		}
		
		if (soma2 % 11 < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - (soma2 % 11);
		}

		if ((digito1 == Integer.parseInt(stringCPF.substring(9, 10))) && 
			(digito2 == Integer.parseInt(stringCPF.substring(10)))) {
			return true;
		}
		
		return false;
	  }
}
