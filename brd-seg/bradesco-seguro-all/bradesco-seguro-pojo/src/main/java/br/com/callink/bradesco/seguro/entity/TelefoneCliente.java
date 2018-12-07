package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author swb.thiagohenrique
 */
@Entity
@Table(name = "TB_TELEFONE_CLIENTE")
public class TelefoneCliente implements IdentifiableEntity<BigInteger>, ILog, Cloneable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TELEFONE_CLIENTE")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private BigInteger id;
    
    @Basic(optional = false)
    @Column(name = "DDD")
    private String ddd;
    
    @Basic(optional = false)
    @Column(name = "TELEFONE")
    private String telefone;
    
    @Basic(optional = false)
    @Column(name = "SEQUENCIA")
    private BigInteger sequencia;
    
    @Basic(optional = false)
    @Column(name = "DATA_CADASTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
    @Basic(optional = false)
    @Column(name = "FLAG_NOVO")
    private boolean flagNovo;
    
    @Basic(optional = false)
    @Column(name = "FLAG_PREFERENCIAL")
    private boolean flagPreferencial;
    
    @Basic(optional = false)
    @Column(name = "FLAG_INVALIDO")
    private boolean flagInvalido;
    
    @Basic(optional = false)
    @Column(name = "FLAG_BLOQUEADO_PROCON")
    private boolean flagBloqueadoProcon;
    
    @Basic(optional = false)
    @Column(name = "FLAG_ENABLED")
    private boolean flagEnabled;
    
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
    
    @Column(name = "ID_CLIENTE_CAMPANHA")
    private BigInteger idClienteCampanha;
    
    @Column(name = "FLAG_RECEBE_OFERTAS_SMS")
    private Boolean flagRecebeSms;
    
    @JoinColumn(name = "ID_CLIENTE_CAMPANHA", referencedColumnName = "ID_CLIENTE_CAMPANHA", insertable=false, updatable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private ClienteCampanha clienteCampanha;

    public TelefoneCliente() {
    	this.telefone = "";
    	this.ddd = "";
    }

    public TelefoneCliente(BigInteger idTelefoneCliente) {
        this.id = idTelefoneCliente;
    }

    public TelefoneCliente(BigInteger idTelefoneCliente, String ddd, String telefone, BigInteger sequencia, Date dataCadastro, boolean flagNovo, boolean flagPreferencial, boolean flagInvalido, boolean flagBloqueadoProcon, boolean flagEnabled, String logUid, String logHost, Date logDate, String logSystem, String logObs, BigInteger logTransaction) {
        this.id = idTelefoneCliente;
        this.ddd = ddd;
        this.telefone = telefone;
        this.sequencia = sequencia;
        this.dataCadastro = dataCadastro;
        this.flagNovo = flagNovo;
        this.flagPreferencial = flagPreferencial;
        this.flagInvalido = flagInvalido;
        this.flagBloqueadoProcon = flagBloqueadoProcon;
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

    public ClienteCampanha getClienteCampanha() {
        return clienteCampanha;
    }

    public void setClienteCampanha(ClienteCampanha clienteCampanha) {
        this.clienteCampanha = clienteCampanha;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public BigInteger getSequencia() {
        return sequencia;
    }

    public void setSequencia(BigInteger sequencia) {
        this.sequencia = sequencia;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean getFlagNovo() {
        return flagNovo;
    }

    public void setFlagNovo(boolean flagNovo) {
        this.flagNovo = flagNovo;
    }

    public boolean getFlagPreferencial() {
        return flagPreferencial;
    }

    public void setFlagPreferencial(boolean flagPreferencial) {
        this.flagPreferencial = flagPreferencial;
    }

    public boolean getFlagInvalido() {
        return flagInvalido;
    }

    public void setFlagInvalido(boolean flagInvalido) {
        this.flagInvalido = flagInvalido;
    }

    public boolean getFlagBloqueadoProcon() {
        return flagBloqueadoProcon;
    }

    public void setFlagBloqueadoProcon(boolean flagBloqueadoProcon) {
        this.flagBloqueadoProcon = flagBloqueadoProcon;
    }

    public boolean getFlagEnabled() {
        return flagEnabled;
    }

    public void setFlagEnabled(boolean flagEnabled) {
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

    public void setLogTransaction(BigInteger logTransaction) {
        this.logTransaction = logTransaction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof TelefoneCliente)) {
            return false;
        }
        TelefoneCliente other = (TelefoneCliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TelefoneCliente[ idTelefoneCliente=" + id + " ]";
    }

	public BigInteger getIdClienteCampanha() {
		return idClienteCampanha;
	}

	public void setIdClienteCampanha(BigInteger idClienteCampanha) {
		this.idClienteCampanha = idClienteCampanha;
	}

	public Boolean getFlagRecebeSms() {
		return flagRecebeSms;
	}

	public void setFlagRecebeSms(Boolean flagRecebeSms) {
		this.flagRecebeSms = flagRecebeSms;
	}

	@Override
	public TelefoneCliente clone() throws CloneNotSupportedException {
		TelefoneCliente clone = new TelefoneCliente();
		clone.ddd = this.ddd;
		clone.telefone = this.telefone;
		clone.sequencia = this.sequencia;
		clone.dataCadastro = this.dataCadastro;
		clone.flagNovo = this.flagNovo;
		clone.flagPreferencial = this.flagPreferencial;
		clone.flagInvalido = this.flagInvalido;
		clone.flagBloqueadoProcon = this.flagBloqueadoProcon;
		clone.flagEnabled = this.flagEnabled;
		clone.logUid = this.logUid;
		clone.logHost = this.logHost;
		clone.logDate = this.logDate;
		clone.logSystem = this.logSystem;
		clone.logObs = this.logObs;
		clone.logTransaction = this.logTransaction;
		clone.idClienteCampanha = this.idClienteCampanha;
		
		return clone;
	}
    
}
