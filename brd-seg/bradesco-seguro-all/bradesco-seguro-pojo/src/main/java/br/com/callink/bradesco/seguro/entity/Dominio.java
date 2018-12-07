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
import javax.persistence.CascadeType;
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
@Table(name = "TB_DOMINIO")
@XmlRootElement
public class Dominio implements IdentifiableEntity<BigInteger> {
    
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DOMINIO")
    private BigInteger id;
	
	@Basic(optional = false)
	@Column(name = "ID_DOMINIO_CORPORATIVO")
	private BigInteger idDominioCorporativo;
    
	@Basic(optional = false)
    @Column(name = "DES_DOMINIO")
    private String descricaoDominio;
    
	@Basic(optional = false)
    @Column(name = "OBS_DOMINIO")
    private String obsDominio;
    
	@Column(name = "DAT_CADASTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
	@Column(name = "DAT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    
	@Basic(optional = false)
    @Column(name = "IND_ATIVO")
    private Boolean ativo;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Usuario> usuarioList;

    public Dominio() {
    }

    public Dominio(BigInteger id) {
        this.id = id;
    }

	public Dominio(BigInteger id, BigInteger idDominioCorporativo,
			String descricaoDominio, String obsDominio, Date dataCadastro,
			Date dataAtualizacao, Boolean ativo) {
		super();
		this.id = id;
		this.idDominioCorporativo = idDominioCorporativo;
		this.descricaoDominio = descricaoDominio;
		this.obsDominio = obsDominio;
		this.dataCadastro = dataCadastro;
		this.dataAtualizacao = dataAtualizacao;
		this.ativo = ativo;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getIdDominioCorporativo() {
		return idDominioCorporativo;
	}

	public void setIdDominioCorporativo(BigInteger idDominioCorporativo) {
		this.idDominioCorporativo = idDominioCorporativo;
	}

	public String getDescricaoDominio() {
		return descricaoDominio;
	}

	public void setDescricaoDominio(String descricaoDominio) {
		this.descricaoDominio = descricaoDominio;
	}

	public String getObsDominio() {
		return obsDominio;
	}

	public void setObsDominio(String obsDominio) {
		this.obsDominio = obsDominio;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
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
        if (!(object instanceof Dominio)) {
            return false;
        }
        Dominio other = (Dominio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.Dominio[ id=" + id + " ]";
    }
    
}
