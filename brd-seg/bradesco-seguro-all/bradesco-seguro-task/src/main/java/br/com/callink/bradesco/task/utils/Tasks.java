package br.com.callink.bradesco.task.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa as tasks disponíveis para Execução.
 * 
 * @author oldamar
 * 
 */
public enum Tasks {
	SINCRONIZA_PESSOAS_CARGOS_USUARIO;
	

	public static Tasks byName(String name) {
		if (name != null) {
			if (name.equalsIgnoreCase(SINCRONIZA_PESSOAS_CARGOS_USUARIO.toString())) {
				return Tasks.SINCRONIZA_PESSOAS_CARGOS_USUARIO;
			}
		}
		return null;
	}
	
	public static List<String> asList(){
		List<String> out = new ArrayList<String>();
		for(Tasks task : values()){
			out.add(task.toString());
		}
		return out;
	}
}
