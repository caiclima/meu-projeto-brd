package br.com.callink.bradesco.task.dto;

import java.math.BigInteger;

public class CargoDTO {
	
	private BigInteger idCargo;
	private String nomeCargo;
	private Boolean ativo;
	
	public BigInteger getIdCargo() {
		return idCargo;
	}
	
	public void setIdCargo(BigInteger idCargo) {
		this.idCargo = idCargo;
	}
	
	public String getNomeCargo() {
		return nomeCargo;
	}
	
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
