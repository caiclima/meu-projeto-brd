package br.com.callink.bradesco.seguro.enums;

import java.io.Serializable;

/**
 * Enum generico representando os estados brasileiro para qualquer que seja o
 * contexto.
 * 
 * @author neppo.oldamar
 * 
 */
public enum UF implements Serializable {
	

	AC ("AC", "Acre"),
	AL ("AL", "Alagoas"),
	AP ("AP", "Amapá"),
	AM ("AM", "Amazonas"),
	BA ("BA", "Bahia"),
	CE ("CE", "Ceará"),
	DF ("DF", "Distrito Federal"),
	ES ("ES", "Espírito Santo"),
	GO ("GO", "Goiás"),
	MA ("MA", "Maranhão"),
	MT ("MT", "Mato Grosso"),
	MS ("MS", "Mato Grosso do Sul"),
	MG ("MG", "Minas Gerais"),
	PA ("PA", "Pará"),
	PB ("PB", "Paraíba"),
	PR ("PR", "Paraná"),
	PE ("PE", "Pernambuco"),
	PI ("PI", "Piauí"),
	RJ ("RJ", "Rio de Janeiro"),
	RN ("RN", "Rio Grande do Norte"),
	RS ("RS", "Rio Grande do Sul"),
	RO ("RO", "Rondonia"),
	RR ("RR", "Roraima"),
	SC ("SC", "Santa Catarina"),
	SP ("SP", "São Paulo"),
	SE ("SE", "Sergipe"),
	TO ("TO", "Tocantins");

	private String uf;
	private String nome;

	private UF(String uf, String nome) {
		this.uf = uf;
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public String getNome() {
		return nome;
	}

	public static UF getUFByValor(String valor) {
		UF[] UFs = UF.values();
		for (UF uf : UFs) {
			if (uf.getUf().equalsIgnoreCase(valor)) {
				return uf;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}
	
	public static UF getInstance(Object value) {
		
		UF[] values = UF.values();
		
		for (UF uf : values) {
			if(value instanceof String){
				if(uf.nome.equals(value)){
					return uf;
				}
			}else if(value instanceof String){
				if(uf.nome.equals(value)){
					return uf;
				}
			}
		}
		return null;
	}
	
}