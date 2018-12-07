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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_GRAU_PARENTESCO")
@Cacheable
public class GrauParentesco implements IdentifiableEntity<BigInteger>, Comparable<GrauParentesco> {
	
    private static final long serialVersionUID = 1L;
    
    //TODO - lembrar que as verificacoes em cima dessa Entidade precisam ser inseridas em producao nessa ordem
    public static final GrauParentesco CONJUGE = new GrauParentesco(new BigInteger("1"));
   
    public static final GrauParentesco FILHO = new GrauParentesco(new BigInteger("2"));
    
    public static final GrauParentesco ENTEADO = new GrauParentesco(new BigInteger("3"));
    
    public static final GrauParentesco TITULAR = new GrauParentesco(new BigInteger("4"));
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GRAU_PARENTESCO")
    private BigInteger id;
   
    @Basic(optional = false)
    private String descricao;
   
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Beneficiario> beneficiarioList;

    public GrauParentesco() {
    }

    public GrauParentesco(BigInteger id) {
        this.id = id;
    }

    public GrauParentesco(BigInteger id, String descricao, String logUid, String logHost, Date logDate, String logSystem, String logObs, BigInteger logTransaction) {
        this.id = id;
        this.descricao = descricao;
        this.logUid = logUid;
        this.logHost = logHost;
        this.logDate = logDate;
        this.logSystem = logSystem;
        this.logObs = logObs;
        this.logTransaction = logTransaction;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public List<Beneficiario> getBeneficiarioList() {
		return beneficiarioList;
	}

	public void setBeneficiarioList(List<Beneficiario> beneficiarioList) {
		this.beneficiarioList = beneficiarioList;
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
        if (!(object instanceof GrauParentesco)) {
            return false;
        }
        GrauParentesco other = (GrauParentesco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.GrauParentesco[ id=" + id + " ]";
    }
    
    @Override
	public int compareTo(GrauParentesco o) {
    	if (this.getDescricao() == null || o.getDescricao() == null) {
			return -1;
		}
		return this.getDescricao().compareTo(o.getDescricao());
	}
    
}
