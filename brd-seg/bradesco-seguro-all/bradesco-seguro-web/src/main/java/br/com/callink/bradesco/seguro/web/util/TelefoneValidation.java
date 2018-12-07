package br.com.callink.bradesco.seguro.web.util;

import java.util.regex.Pattern;

import br.com.callink.bradesco.seguro.entity.TelefoneCliente;

public class TelefoneValidation {

	public static TelefoneValidation instance;
	
	private TelefoneValidation() {
		
	}
	
	public static TelefoneValidation getInstance() {
		if (instance == null) {
			instance = new TelefoneValidation();
		}
		return instance;
	}
	
	public TelefoneCliente validarTelefone(String telefone) {
		
		TelefoneCliente telefoneCliente = new TelefoneCliente();
		
		/**
         * Valida se o telefone está em um dos formatos: xxxxxxxxxx(10digitos), xxxxxxxxxxx(11digitos) ou xxxxxxxxxxxx(12digitos)
         * o ddd está representado nos 2 primeiros digitos
         * os demais digitos representam o número do telefone
         */

        //telefone = "(011) 12345-67890";

        String expressaoTelefone = "([1-9][0-9])([0-9]{8}|[0-9]{9}|[0-9]{10})";

        String expressaoTelefoneDDD34 = "([0-9]{8})";


	     //remove da string todos os cqracteres nao numericos
	     String auxTelefone = telefone.replaceAll("[^0-9]", "");
	
	        //caso o primeiro digito seja zero ele é removido
	     auxTelefone = auxTelefone.replaceAll("^[0]", "");
	
	     //caso o telefone tenha mais que 12 digitos recorta apenas os 12 primeiros
	        auxTelefone = auxTelefone.length() > 12 ? auxTelefone.substring(0, 12) : auxTelefone;
	
	     String ddd = "";
	     String numero = "";
	     if (Pattern.matches(expressaoTelefone, auxTelefone)) { // telefone nos formatos: xxxxxxxxxx(10digitos), xxxxxxxxxxx(11digitos) ou xxxxxxxxxxxx(12digitos)
	           
	            //os 2 primeiros digitos são o codigo de area(DDD)
	            ddd = auxTelefone.substring(0, 2);
	            //o numero do telefone
	            numero = auxTelefone.substring(2);
	            
	            telefoneCliente.setDdd(ddd);
	            telefoneCliente.setTelefone(numero);
	            
	            return telefoneCliente;
	
        } else if (Pattern.matches(expressaoTelefoneDDD34, auxTelefone)) { // telefone nos formatos: xxxxxxxx(8digitos) - apenas local (34)
            //havia uma regra previamente implementada que considera o ddd 34 quando recebe apena o numero do telefone(8 digitos)
            ddd = "34";
            numero = auxTelefone;
            
            telefoneCliente.setDdd(ddd);
            telefoneCliente.setTelefone(numero);
            
            return telefoneCliente;
        }
	     
	     return null;
	}
	
}
