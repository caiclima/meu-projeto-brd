/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_PESSOA_COLABORADOR")
@XmlRootElement
public class PessoaColaborador implements IdentifiableEntity<BigInteger> {
    
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PESSOA_COLABORADOR")
    private BigInteger id;
	
	@Basic(optional = false)
	@Column(name = "ID_PESSOA_COLABORADOR_CORPORATIVO")
	private BigInteger idPessoaColaboradorCorporativo;
    
	@Column(name = "COD_MATRICULA")
    private String matricula;
    
	@Column(name = "IND_ATIVO")
    private Boolean ativo;
    
	@Column(name = "IND_EXPERIENCIA")
    private Boolean experiencia;
    
	@Column(name = "DAT_ADMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAdmissao;
    
	@Column(name = "DAT_AFASTAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAfastamento;
    
	@Column(name = "DAT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", insertable = false, updatable = false)
    @ManyToOne
    private Pessoa pessoa;
	
	@Column(name = "ID_PESSOA")
	private BigInteger idPessoa;
    
	@JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO", insertable = false, updatable = false)
    @ManyToOne
    private Cargo cargo;
	
	@Column(name = "ID_CARGO")
	private BigInteger idCargo;

    public PessoaColaborador() {
    }

    public PessoaColaborador(BigInteger id) {
        this.id = id;
    }

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getIdPessoaColaboradorCorporativo() {
		return idPessoaColaboradorCorporativo;
	}

	public void setIdPessoaColaboradorCorporativo(
			BigInteger idPessoaColaboradorCorporativo) {
		this.idPessoaColaboradorCorporativo = idPessoaColaboradorCorporativo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Boolean experiencia) {
		this.experiencia = experiencia;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataAfastamento() {
		return dataAfastamento;
	}

	public void setDataAfastamento(Date dataAfastamento) {
		this.dataAfastamento = dataAfastamento;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigInteger getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(BigInteger idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public BigInteger getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(BigInteger idCargo) {
		this.idCargo = idCargo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PessoaColaborador)) {
            return false;
        }
        PessoaColaborador other = (PessoaColaborador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.PessoaColaborador[ id=" + id + " ]";
    }
    
}
