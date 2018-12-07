package br.com.callink.bradesco.task.dto;

import java.math.BigInteger;
import java.util.Date;

/**
 * 
 * @author michael
 * 
 */
public class UsuarioCoorporativoDTO {
	private String nome;
	private BigInteger codMatricula;
	private String numCpf;
	private BigInteger codCentroCusto;
	private String descricaoCentroCusto;
	private Date dataCadastro;
	private String cargo;
	private String equipe;
	private Long idUsuario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigInteger getCodMatricula() {
		return codMatricula;
	}

	public void setCodMatricula(BigInteger codMatricula) {
		this.codMatricula = codMatricula;
	}

	public String getNumCpf() {
		return numCpf;
	}

	public void setNumCpf(String numCpf) {
		this.numCpf = numCpf;
	}

	public BigInteger getCodCentroCusto() {
		return codCentroCusto;
	}

	public void setCodCentroCusto(BigInteger codCentroCusto) {
		this.codCentroCusto = codCentroCusto;
	}

	public String getDescricaoCentroCusto() {
		return descricaoCentroCusto;
	}

	public void setDescricaoCentroCusto(String descricaoCentroCusto) {
		this.descricaoCentroCusto = descricaoCentroCusto;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return " [nome=" + nome + ", codMatricula=" + codMatricula + ", numCpf=" + numCpf + ", codCentroCusto=" + codCentroCusto + ", descricaoCentroCusto=" + descricaoCentroCusto + ", dataCadastro=" + dataCadastro + ", cargo=" + cargo + ", equipe=" + equipe + ", idUsuario=" + idUsuario + "]";
	}

}