package br.com.callink.bradesco.task.dto;

import java.util.Date;

/**
 * 
 * @author michael
 * 
 */
public class LoginCoorporativoDTO {
	private Integer matricula;
	private Date dataInicio;
	private Short codigoDominio;
	private Date dataFim;
	private String login;
	private Date dataCadastro;

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Short getCodigoDominio() {
		return codigoDominio;
	}

	public void setCodigoDominio(Short codigoDominio) {
		this.codigoDominio = codigoDominio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public String toString() {
		return " [matricula=" + matricula + ", dataInicio=" + dataInicio + ", codigoDominio=" + codigoDominio + ", dataFim=" + dataFim + ", login=" + login + ", dataCadastro=" + dataCadastro + "]";
	}
}