package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

/**
 * Enum generico representando o tipoPlanoEnum INDIVIDUAL/FAMILIAR para qualquer que seja o
 * contexto.
 * 
 * @author neppo.oldamar
 * 
 */
public enum TipoPlanoEnum implements Serializable {
	
	INDIVIDUAL("Individual"), FAMILIAR("Familiar");

	private String valor;

	private TipoPlanoEnum(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
	public static TipoPlanoEnum getTipoPlanoEnumByValor(String valor) {
		TipoPlanoEnum[] tipoPlanoEnums = TipoPlanoEnum.values();
		for (TipoPlanoEnum tipoPlanoEnum : tipoPlanoEnums) {
			if (tipoPlanoEnum.getValor().equalsIgnoreCase(valor)) {
				return tipoPlanoEnum;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}
}