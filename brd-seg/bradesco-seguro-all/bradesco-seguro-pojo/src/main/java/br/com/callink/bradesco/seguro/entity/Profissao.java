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
import javax.persistence.Cacheable;
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
import javax.xml.bind.annotation.XmlTransient;

import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_PROFISSAO")
@Cacheable
public class Profissao implements IdentifiableEntity<BigInteger>, IRemovableEntity, Comparable<Profissao>, ILog {
	
	
	private static final long serialVersionUID = -7109977032138440840L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROFISSAO")
    private BigInteger id;
    
	@Basic(optional = false)
	@Column(name = "NOME")
    private String nome;
	
	@Basic(optional = false)
	@Column(name = "DESCRICAO")
    private String descricao;
   
	@Basic(optional = false)
    @Column(name = "FLAG_RISCO")
    private Boolean flagRisco;
   
	@Basic(optional = false)
	@Column(name = "FLAG_ENABLED")
    private Boolean flagEnabled;
	
	@Basic(optional = false)
    @Column(name = "FLAG_REMOVED")
    private Boolean flagRemoved;
    
	@Basic(optional = false)
    private String codigo;
    
	@Basic(optional = false)
    @Column(name = "LOG_UID")
    private String logUid;
   
	@Basic(optional = false)
    @Column(name = "LOG_HOST")
    private String logHost;
    
	@Basic(optional = false)
    @Column(name = "LOG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    
	@Basic(optional = false)
    @Column(name = "LOG_SYSTEM")
    private String logSystem;
    
	@Basic(optional = false)
    @Column(name = "LOG_OBS")
    private String logObs;
    
	@Basic(optional = false)
    @Column(name = "LOG_TRANSACTION")
    private BigInteger logTransaction;
	
	@Column(name = "CODIGO_EXTERNO")
    private String codigoExterno;

	@OneToMany(mappedBy = "id")
    private List<Venda> vendaList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Beneficiario> beneficiarioList;

    public Profissao() {
    }

    public Profissao(BigInteger id) {
        this.id = id;
    }

    public Profissao(BigInteger id, String nome, String descricao, Boolean flagRisco, 
    			String codigo, String logUid, String logHost, Date logDate, String logSystem, 
    			String logObs, BigInteger logTransaction, Boolean flagEnabled) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.flagRisco = flagRisco;
        this.codigo = codigo;
        this.logUid = logUid;
        this.logHost = logHost;
        this.logDate = logDate;
        this.logSystem = logSystem;
        this.logObs = logObs;
        this.logTransaction = logTransaction;
        this.flagEnabled = flagEnabled;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getFlagRisco() {
        return flagRisco;
    }

    public void setFlagRisco(Boolean flagRisco) {
        this.flagRisco = flagRisco;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLogUid() {
        return logUid;
    }

    public void setLogUid(String logUid) {
        this.logUid = logUid;
    }

    public String getLogHost() {
        return logHost;
    }

    public void setLogHost(String logHost) {
        this.logHost = logHost;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogSystem() {
        return logSystem;
    }

    public void setLogSystem(String logSystem) {
        this.logSystem = logSystem;
    }

    public String getLogObs() {
        return logObs;
    }

    public void setLogObs(String logObs) {
        this.logObs = logObs;
    }

    public BigInteger getLogTransaction() {
        return logTransaction;
    }

    public void setLogTransaction(BigInteger logTransaction) {
        this.logTransaction = logTransaction;
    }

    public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	@XmlTransient
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    public Boolean getFlagEnabled() {
		return flagEnabled;
	}

	public void setFlagEnabled(Boolean flagEnabled) {
		this.flagEnabled = flagEnabled;
	}

    public Boolean getFlagRemoved() {
		return flagRemoved;
	}

	public void setFlagRemoved(Boolean flagRemoved) {
		this.flagRemoved = flagRemoved;
	}

	public List<Beneficiario> getBeneficiarioList() {
		return beneficiarioList;
	}

	public void setBeneficiarioList(List<Beneficiario> beneficiarioList) {
		this.beneficiarioList = beneficiarioList;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Profissao)) {
			return false;
		}
		Profissao other = (Profissao) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.Profissao[ id=" + id + " ]";
    }
    
    @Override
	public int compareTo(Profissao o) {
    	if (this.getNome() == null || o.getNome() == null) {
			return -1;
		}
		return this.getNome().compareTo(o.getNome());
	}
    
}
