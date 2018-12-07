/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
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
@Table(name = "TB_USUARIO")
@XmlRootElement
public class Usuario implements IdentifiableEntity<BigInteger> {
   
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private BigInteger id;
	
	@Column(name = "ID_USUARIO_CORPORATIVO")
    private BigInteger idUsuarioCorporativo;
    
	@Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    
	@Column(name = "DAT_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datInicio;
    
	@Column(name = "DAT_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datFim;
    
	@Column(name = "DAT_CADASTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datCadastro;
    
	@Column(name = "DAT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datAtualizacao;
    
	@Column(name = "IND_ATIVO")
    private Boolean indAtivo;
    
	@JoinColumn(name = "ID_PESSOA", referencedColumnName = "ID_PESSOA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pessoa pessoa;
	
	@Column(name = "ID_PESSOA")
	private BigInteger idPessoa;
    
	@JoinColumn(name = "ID_DOMINIO", referencedColumnName = "ID_DOMINIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Dominio dominio;
	
	@Column(name = "ID_DOMINIO")
	private BigInteger idDominio;

    public Usuario() {
    }

    public Usuario(BigInteger id) {
        this.id = id;
    }

    public Usuario(BigInteger id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getDatInicio() {
        return datInicio;
    }

    public void setDatInicio(Date datInicio) {
        this.datInicio = datInicio;
    }

    public Date getDatFim() {
        return datFim;
    }

    public void setDatFim(Date datFim) {
        this.datFim = datFim;
    }

    public Date getDatCadastro() {
        return datCadastro;
    }

    public void setDatCadastro(Date datCadastro) {
        this.datCadastro = datCadastro;
    }

    public Date getDatAtualizacao() {
        return datAtualizacao;
    }

    public void setDatAtualizacao(Date datAtualizacao) {
        this.datAtualizacao = datAtualizacao;
    }

    public Boolean getIndAtivo() {
        return indAtivo;
    }

    public void setIndAtivo(Boolean indAtivo) {
        this.indAtivo = indAtivo;
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

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public BigInteger getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(BigInteger idDominio) {
		this.idDominio = idDominio;
	}

	public BigInteger getIdUsuarioCorporativo() {
		return idUsuarioCorporativo;
	}

	public void setIdUsuarioCorporativo(BigInteger idUsuarioCorporativo) {
		this.idUsuarioCorporativo = idUsuarioCorporativo;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.Usuario[ id=" + id + " ]";
    }
    
}
