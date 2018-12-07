package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

public enum ProfissaoEnum implements Serializable {

	APOSENTADO_INVALIDEZ("APOSENTADO POR INVALIDEZ", "395");
	
	private String valor;
	
	private String codigo;

	private ProfissaoEnum(String valor, String codigo) {
		this.valor = valor;
		this.codigo = codigo;
	}

	public String getValor() {
		return this.valor;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
}
