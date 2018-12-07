package br.com.callink.bradesco.seguro.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author swb.thiagohenrique
 */
@Entity
@Table(name = "TB_CAMPANHA")
public class Campanha implements IdentifiableEntity<BigInteger>, IRemovableEntity, Comparable<Campanha>, ILog, Cloneable {
	
    private static final long serialVersionUID = 1L;
    
	@Id
    @Basic(optional = false)
    @Column(name = "ID_CAMPANHA")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private BigInteger id;
    
    @Basic(optional = false)
    @Column(name = "NOME_CAMPANHA")
    private String nomeCampanha;
    
    @Column(name = "SERVICEID_ASPECT")
    private BigInteger serviceidAspect;
    
    @Basic(optional = false)
    @Column(name = "FLAG_ENABLED")
    private Boolean flagEnabled;
    
    @Basic(optional = false)
    @Column(name = "FLAG_REMOVED")
    private Boolean flagRemoved;
    
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
    
    @Column(name = "QTD_AGENDAMENTO_ATENDENTE")
    private BigInteger qtdAgendamentoAtendente;
    
    @Column(name = "QTD_AGENDAMENTO_CLIENTE")
    private BigInteger qtdAgendamentoCliente;
    
    @Column(name = "MIN_VALOR_EMISSAO_PROPOSTA")
    private BigDecimal minValorEmissaoProposta;
    
    @Column(name = "MAX_VALOR_EMISSAO_PROPOSTA")
    private BigDecimal maxValorEmissaoProposta;
    
    @Column(name = "NUM_MAXIMO_DIAS_AGENDAMENTO")
    private BigInteger numMaximoDiasAgendamento;
    
    @Column(name = "CODIGO_EXTERNO")
    private String codigoExterno;
    
    @Column(name = "ID_DATABASE_ALM")
    private BigInteger idDataBaseAlm;
    
    @Column(name = "SIGLA")
    private String sigla;
    
    @JoinColumn(name = "ID_DATABASE_ALM", referencedColumnName = "ID_DATABASE_ALM", insertable = false, updatable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private DataBaseAlm dataBaseAlm;
    
	@OneToMany(mappedBy="campanha")
	private List<EventoCampanha> eventoCampanhaList;

    public Campanha() {
    }

    public Campanha(BigInteger id) {
        this.id = id;
    }

    public Campanha(BigInteger id, String nomeCampanha) {
		super();
		this.id = id;
		this.nomeCampanha = nomeCampanha;
	}

	public Campanha(BigInteger id, String nomeCampanha, boolean flagEnabled, String logUid, String logHost, Date logDate, String logSystem, String logObs, BigInteger logTransaction) {
        this.id = id;
        this.nomeCampanha = nomeCampanha;
        this.flagEnabled = flagEnabled;
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

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public BigInteger getServiceidAspect() {
        return serviceidAspect;
    }

    public void setServiceidAspect(BigInteger serviceidAspect) {
        this.serviceidAspect = serviceidAspect;
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

    public BigInteger getQtdAgendamentoAtendente() {
        return qtdAgendamentoAtendente;
    }

    public void setQtdAgendamentoAtendente(BigInteger qtdAgendamentoAtendente) {
        this.qtdAgendamentoAtendente = qtdAgendamentoAtendente;
    }

    public BigInteger getQtdAgendamentoCliente() {
        return qtdAgendamentoCliente;
    }

    public void setQtdAgendamentoCliente(BigInteger qtdAgendamentoCliente) {
        this.qtdAgendamentoCliente = qtdAgendamentoCliente;
    }

    public BigInteger getNumMaximoDiasAgendamento() {
        return numMaximoDiasAgendamento;
    }

    public void setNumMaximoDiasAgendamento(BigInteger numMaximoDiasAgendamento) {
        this.numMaximoDiasAgendamento = numMaximoDiasAgendamento;
    }

    public List<EventoCampanha> getEventoCampanhaList() {
		return eventoCampanhaList;
	}

	public void setEventoCampanhaList(List<EventoCampanha> eventoCampanhaList) {
		this.eventoCampanhaList = eventoCampanhaList;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	/**
	 * @return the idDataBaseAlm
	 */
	public BigInteger getIdDataBaseAlm() {
		return idDataBaseAlm;
	}

	/**
	 * @param idDataBaseAlm the idDataBaseAlm to set
	 */
	public void setIdDataBaseAlm(BigInteger idDataBaseAlm) {
		this.idDataBaseAlm = idDataBaseAlm;
	}

	/**
	 * @return the dataBaseAlm
	 */
	public DataBaseAlm getDataBaseAlm() {
		return dataBaseAlm;
	}

	/**
	 * @param dataBaseAlm the dataBaseAlm to set
	 */
	public void setDataBaseAlm(DataBaseAlm dataBaseAlm) {
		this.dataBaseAlm = dataBaseAlm;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the minValorEmissaoProposta
	 */
	public BigDecimal getMinValorEmissaoProposta() {
		return minValorEmissaoProposta;
	}

	/**
	 * @param minValorEmissaoProposta the minValorEmissaoProposta to set
	 */
	public void setMinValorEmissaoProposta(BigDecimal minValorEmissaoProposta) {
		this.minValorEmissaoProposta = minValorEmissaoProposta;
	}

	/**
	 * @return the maxValorEmissaoProposta
	 */
	public BigDecimal getMaxValorEmissaoProposta() {
		return maxValorEmissaoProposta;
	}

	/**
	 * @param maxValorEmissaoProposta the maxValorEmissaoProposta to set
	 */
	public void setMaxValorEmissaoProposta(BigDecimal maxValorEmissaoProposta) {
		this.maxValorEmissaoProposta = maxValorEmissaoProposta;
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
		if (!(obj instanceof Campanha)) {
			return false;
		}
		Campanha other = (Campanha) obj;
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
        return "Campanha[ id=" + id + " ]";
    }

	@Override
	public int compareTo(Campanha o) {
		if (getNomeCampanha() != null) {
			return this.getNomeCampanha().compareTo(o.getNomeCampanha());
		} else {
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Campanha clone() throws CloneNotSupportedException {
		Campanha clone = new Campanha();
		clone.nomeCampanha = this.nomeCampanha;
		clone.serviceidAspect = this.serviceidAspect;//inutil
		clone.flagEnabled = this.flagEnabled;
		clone.logUid = this.logUid;
		clone.logHost = this.logHost;
		clone.logDate = this.logDate;
		clone.logSystem = this.logSystem;
		clone.logObs = this.logObs;
		clone.logTransaction = this.logTransaction;
		clone.qtdAgendamentoAtendente = this.qtdAgendamentoAtendente;//inutil
		clone.qtdAgendamentoCliente = this.qtdAgendamentoCliente;//inutil
		clone.minValorEmissaoProposta = this.minValorEmissaoProposta;
		clone.maxValorEmissaoProposta = this.maxValorEmissaoProposta;
		clone.numMaximoDiasAgendamento = this.numMaximoDiasAgendamento;//inutil
		clone.codigoExterno = this.codigoExterno;
		clone.idDataBaseAlm = this.idDataBaseAlm;
		clone.dataBaseAlm = this.dataBaseAlm;
		return clone;
	}
    
	
}
