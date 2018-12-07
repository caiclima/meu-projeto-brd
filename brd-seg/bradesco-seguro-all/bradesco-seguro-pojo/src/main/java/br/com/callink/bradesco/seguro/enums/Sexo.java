package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

/**
 * Enum generico representando o sexo MASCULINO/FEMININO para qualquer que seja o
 * contexto.
 * 
 * @author neppo.oldamar
 * 
 */
public enum Sexo implements Serializable {
	
	MASCULINO("M"), FEMININO("F"), INDETERMINADO("I");

	private String valor;

	private Sexo(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
	public static Sexo getSexoByValor(String valor) {
		Sexo[] sexos = Sexo.values();
		for (Sexo sexo : sexos) {
			if (sexo.getValor().equalsIgnoreCase(valor)) {
				return sexo;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}
	
	public static Sexo getInstance(Object value) {
		
		Sexo[] values = Sexo.values();
		
		for (Sexo sexo : values) {
			if(value instanceof String){
				if(sexo.valor.equals(value)){
					return sexo;
				}
			}else if(value instanceof String){
				if(sexo.valor.equals(value)){
					return sexo;
				}
			}
		}
		return null;
	}
}

