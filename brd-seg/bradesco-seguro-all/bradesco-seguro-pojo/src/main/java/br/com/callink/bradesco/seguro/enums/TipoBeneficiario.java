package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

/**
 * Enum generico representando o tiposBeneficiario TITULAR/DEPENDENTE para qualquer que seja o
 * contexto.
 * 
 * @author neppo.oldamar
 * 
 */
public enum TipoBeneficiario implements Serializable {
	
	TITULAR("Titular"), DEPENDENTE("Dependente");

	private String valor;

	private TipoBeneficiario(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
	public static TipoBeneficiario getTipoBeneficiarioByValor(String valor) {
		TipoBeneficiario[] tiposBeneficiarios = TipoBeneficiario.values();
		for (TipoBeneficiario tiposBeneficiario : tiposBeneficiarios) {
			if (tiposBeneficiario.getValor().equalsIgnoreCase(valor)) {
				return tiposBeneficiario;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name().equals("TITULAR") ? "TITULAR" : "DEPENDENTE";
	}
}