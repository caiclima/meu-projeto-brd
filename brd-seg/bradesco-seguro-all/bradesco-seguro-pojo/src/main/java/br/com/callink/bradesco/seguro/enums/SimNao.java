package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

/**
 * Enum generico representando o estado SIM/NAO para qualquer que seja o
 * contexto.
 * 
 * @author swb.thiagohenrique
 * 
 */
public enum SimNao implements Serializable {
	
	SIM(Boolean.TRUE), NAO(Boolean.FALSE);

	private Boolean valor;

	private SimNao(Boolean valor) {
		this.valor = valor;
	}

	public Boolean getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return this.name().equals("SIM") ? "SIM" : "NAO";
	}
}