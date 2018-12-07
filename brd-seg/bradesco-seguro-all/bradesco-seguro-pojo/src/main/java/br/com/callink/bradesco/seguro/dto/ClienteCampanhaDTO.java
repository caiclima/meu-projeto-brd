package br.com.callink.bradesco.seguro.dto;

import java.math.BigInteger;

/**
 * Dados diversos de cliente campanha
 * @author michael
 *
 */
public class ClienteCampanhaDTO {
	private BigInteger idClienteCampanha;
	private String ddd;
	private String telefone;
	private String cnpj;
	private String nome;

	public BigInteger getIdClienteCampanha() {
		return idClienteCampanha;
	}

	public void setIdClienteCampanha(BigInteger idClienteCampanha) {
		this.idClienteCampanha = idClienteCampanha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public final String getDdd() {
		return ddd;
	}

	public final void setDdd(String ddd) {
		this.ddd = ddd;
	}
}