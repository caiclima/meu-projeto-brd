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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author swb.alisson
 */
@Entity
@Table(name = "TB_TIPO_PLANO")
@Cacheable
public class TipoPlano implements IdentifiableEntity<BigInteger>,Comparable<TipoPlano>, ILog {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TIPO_PLANO")
    private BigInteger id;
    
    @Basic(optional = false)
    @Column(name = "NOME_TIPO_PLANO")
    private String nomeTipoPlano;
    
    @Basic(optional = false)
    @Column(name = "FLAG_ENABLED")
    private Boolean flagEnabled;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPlano")
    private List<Plano> planoList;
    
    public TipoPlano() {
	}

    public TipoPlano(BigInteger id, String nomeTipoPlano) {
		this.id = id;
		this.nomeTipoPlano = nomeTipoPlano;
	}

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger obj) {
        this.id = obj;
    }

    public String getNomeTipoPlano() {
        return nomeTipoPlano;
    }

    public void setNomeTipoPlano(String nomeTipoPlano) {
        this.nomeTipoPlano = nomeTipoPlano;
    }

    public Boolean getFlagEnabled() {
        return flagEnabled;
    }

    public void setFlagEnabled(Boolean flagEnabled) {
        this.flagEnabled = flagEnabled;
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

    public void setLogTransaction(BigInteger obj) {
        this.logTransaction = obj;
    }

    @XmlTransient
    public List<Plano> getPlanoList() {
        return planoList;
    }

    public void setPlanoList(List<Plano> planoList) {
        this.planoList = planoList;
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
		TipoPlano other = (TipoPlano) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(TipoPlano o) {
		return this.getNomeTipoPlano().compareTo(o.getNomeTipoPlano());
	}
    
}
