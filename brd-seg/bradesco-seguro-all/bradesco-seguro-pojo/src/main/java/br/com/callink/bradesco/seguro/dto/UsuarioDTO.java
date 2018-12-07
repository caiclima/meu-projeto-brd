package br.com.callink.bradesco.seguro.dto;

import java.io.Serializable;

import br.com.callink.bradesco.seguro.entity.Cargo;

public class UsuarioDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String loginUsuario;
	private Cargo cargo;
	private String codigoMatricula;
	
	public UsuarioDTO(){
		
	}
	
	public String getLoginUsuario() {
		return loginUsuario;
	}
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getCodigoMatricula() {
		return codigoMatricula;
	}

	public void setCodigoMatricula(String codigoMatricula) {
		this.codigoMatricula = codigoMatricula;
	}
	
}
