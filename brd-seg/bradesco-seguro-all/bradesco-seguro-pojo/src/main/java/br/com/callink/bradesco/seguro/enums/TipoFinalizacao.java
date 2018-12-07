package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

public enum TipoFinalizacao implements Serializable {

	SUCESSO(1), CALLBACK(3);

	private Integer id;

	private TipoFinalizacao(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.name();
	}

	public static TipoFinalizacao getInstance(Object value) {
		TipoFinalizacao[] values = TipoFinalizacao.values();
		
		for (TipoFinalizacao tipoFinalizacao : values) {
			if(value instanceof String){
				if(tipoFinalizacao.name().equals(value)){
					return tipoFinalizacao;
				}
			}else if(value instanceof Integer){
				if(tipoFinalizacao.getId().equals(value)){
					return tipoFinalizacao;
				}
			}
		}
		
		return null;
	}
}
