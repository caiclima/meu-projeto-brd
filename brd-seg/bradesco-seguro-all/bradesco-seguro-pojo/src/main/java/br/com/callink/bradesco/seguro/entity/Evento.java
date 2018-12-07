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
 * @author neppo.oldamar
 */
@Entity
@Table(name = "TB_EVENTO")
@Cacheable
public class Evento implements IdentifiableEntity<BigInteger>, IRemovableEntity, Comparable<Evento>, ILog {

	private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EVENTO")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private BigInteger id;
    
    @Basic(optional = false)
    @Column(name = "NOME_EVENTO")
    private String nomeEvento;
    
    @Column(name = "ID_ASPECT")
    private BigInteger idAspect;
    
    @Column(name = "CODIGO_ASPECT")
    private String codigoAspect;
    
    @Column(name = "CODIGO_EXTERNO")
    private String codigoExterno;
    
    @Basic(optional = false)
    @Column(name = "DATA_CADASTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
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
    
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @Column(name = "TIPO_FINALIZACAO")
    private Integer tipoFinalizacao;
    
    @Column(name = "FLAG_GATEWAY")
    private Boolean flagGateway;
    
    @Column(name = "FLAG_VENDA")
    private Boolean flagVenda;    
    
    @JoinColumn(name = "ID_TIPO_EVENTO", referencedColumnName = "ID_TIPO_EVENTO")
    @ManyToOne(optional = false)
    private TipoEvento tipoEvento;
    
	@OneToMany(mappedBy = "evento")
	private List<EventoCampanha> eventoCampanhaList;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<ProdutoEvento> produtoEventoList;
	
    @Column(name = "FLAG_IMPLICITO_USUARIO", insertable = false, updatable = false)
    private Boolean flagImplicitoUsuario;

    public Evento() {
    }
    
    public Evento(BigInteger id) {
    	this.id = id;
	}

    public Evento(BigInteger id, String nome) {
    	this.id = id;
    	this.nomeEvento = nome;
	}

	public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
		this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public BigInteger getIdAspect() {
        return idAspect;
    }

    public void setIdAspect(BigInteger idAspect) {
        this.idAspect = idAspect;
    }

    public String getCodigoAspect() {
        return codigoAspect;
    }

    public void setCodigoAspect(String codigoAspect) {
        this.codigoAspect = codigoAspect;
    }

    public String getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getFlagGateway() {
        return flagGateway;
    }

    public void setFlagGateway(Boolean flagGateway) {
        this.flagGateway = flagGateway;
    }

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<EventoCampanha> getEventoCampanhaList() {
		return eventoCampanhaList;
	}

	public void setEventoCampanhaList(List<EventoCampanha> eventoCampanhaList) {
		this.eventoCampanhaList = eventoCampanhaList;
	}

	public List<ProdutoEvento> getProdutoEventoList() {
		return produtoEventoList;
	}

	public void setProdutoEventoList(List<ProdutoEvento> produtoEventoList) {
		this.produtoEventoList = produtoEventoList;
	}

	public Integer getTipoFinalizacao() {
		return tipoFinalizacao;
	}

	public void setTipoFinalizacao(Integer tipoFinalizacao) {
		this.tipoFinalizacao = tipoFinalizacao;
	}

    public Boolean getFlagVenda() {
        return flagVenda;
    }

    public void setFlagVenda(Boolean flagVenda) {
        this.flagVenda = flagVenda;
    }

	public Boolean getFlagImplicitoUsuario() {
		return flagImplicitoUsuario;
	}

	public void setFlagImplicitoUsuario(Boolean flagImplicitoUsuario) {
		this.flagImplicitoUsuario = flagImplicitoUsuario;
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
		if (!(obj instanceof Evento)) {
			return false;
		}
		Evento other = (Evento) obj;
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
        return "Evento[ idEvento=" + id + " ]";
    }
	
    @Override
	public int compareTo(Evento o) {
    	if (this.getNomeEvento() == null || o.getNomeEvento() == null) {
    		return -1;
    	}
		return this.getNomeEvento().compareTo(o.getNomeEvento());
	}
    
}
