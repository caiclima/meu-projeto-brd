/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_CARGO")
@XmlRootElement
public class Cargo implements IdentifiableEntity<BigInteger>, Cloneable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CARGO")
    private BigInteger id;
	
	@Column(name = "ID_CARGO_CORPORATIVO")
    private BigInteger idCargoCorporativo;
    
	@Column(name = "NOM_CARGO")
    private String nomeCargo;
    
	@Column(name = "IND_ATIVO")
    private Boolean ativo;
    
	@Column(name = "DAT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datAtualizacao;
    
	@OneToMany(mappedBy = "id")
    private List<PessoaColaborador> pessoaColaboradorList;

    public Cargo() {
    }

    public Cargo(BigInteger id) {
        this.id = id;
    }
    
    public Cargo(BigInteger id, String nomeCargo){
    	this.id = id;
    	this.nomeCargo = nomeCargo;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getIdCargoCorporativo() {
		return idCargoCorporativo;
	}

	public void setIdCargoCorporativo(BigInteger idCargoCorporativo) {
		this.idCargoCorporativo = idCargoCorporativo;
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

    public Date getDatAtualizacao() {
        return datAtualizacao;
    }

    public void setDatAtualizacao(Date datAtualizacao) {
        this.datAtualizacao = datAtualizacao;
    }

    @XmlTransient
    public List<PessoaColaborador> getPessoaColaboradorList() {
        return pessoaColaboradorList;
    }

    public void setPessoaColaboradorList(List<PessoaColaborador> pessoaColaboradorList) {
        this.pessoaColaboradorList = pessoaColaboradorList;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cargo other = (Cargo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return nomeCargo;
    }
	
}
