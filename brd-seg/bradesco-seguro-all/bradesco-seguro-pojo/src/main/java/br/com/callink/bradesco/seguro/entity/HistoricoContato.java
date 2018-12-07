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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author michael
 * 
 */
@Entity
@Table(name = "TB_HISTORICO_CONTATO")
public class HistoricoContato implements IdentifiableEntity<BigInteger>, ILog {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ID_HISTORICO_CONTATO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@Basic(optional = false)
	@Column(name = "DATA_CONTATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataContato;

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

	@Column(name = "DATA_FIM_CONTATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFimContato;

	@Column(name = "DURACAO_SEGUNDOS_CONTATO")
	private BigInteger duracaoSegundosContato;

	@Column(name = "FLAG_CONTATO_ENVIADO")
	private Boolean flagContatoEnviado;
	
	@Column(name = "INFORMACAO_ADICIONAL")
	private String informacaoAdicional;

	@Column(name = "ID_TELEFONE_CLIENTE")
	private BigInteger idTelefoneCliente;
	
	@Column(name = "CHAVE_ATENDIMENTO")
	private String chaveAtendimento;

	@JoinColumn(name = "ID_TELEFONE_CLIENTE", referencedColumnName = "ID_TELEFONE_CLIENTE", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private TelefoneCliente telefoneCliente;
	
	@Column(name = "ID_EVENTO")
	private BigInteger idEvento;

	@JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Evento evento;

	@Column(name = "ID_CLIENTE_CAMPANHA")
	private BigInteger idClienteCampanha;

	@JoinColumn(name = "ID_CLIENTE_CAMPANHA", referencedColumnName = "ID_CLIENTE_CAMPANHA", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private ClienteCampanha clienteCampanha;
	
	@Column(name = "DETALHAMENTO_EVENTO")
	private String detalhamentoEvento;
	
	@JoinColumn(name = "ID_AGENTE", referencedColumnName = "ID_AGENTE")
	@ManyToOne
	private Agente agente;
	
	@Override
	public BigInteger getId() {
		return this.id;
	}

	@Override
	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getDataContato() {
		return dataContato;
	}

	public void setDataContato(Date dataContato) {
		this.dataContato = dataContato;
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

	public void setLogTransaction(BigInteger logTransaction) {
		this.logTransaction = logTransaction;
	}

	public Date getDataFimContato() {
		return dataFimContato;
	}

	public void setDataFimContato(Date dataFimContato) {
		this.dataFimContato = dataFimContato;
	}

	public BigInteger getDuracaoSegundosContato() {
		return duracaoSegundosContato;
	}

	public void setDuracaoSegundosContato(BigInteger duracaoSegundosContato) {
		this.duracaoSegundosContato = duracaoSegundosContato;
	}

	public Boolean getFlagContatoEnviado() {
		return flagContatoEnviado;
	}

	public String getInformacaoAdicional() {
		return informacaoAdicional;
	}

	public void setInformacaoAdicional(String informacaoAdicional) {
		this.informacaoAdicional = informacaoAdicional;
	}

	public void setFlagContatoEnviado(Boolean flagContatoEnviado) {
		this.flagContatoEnviado = flagContatoEnviado;
	}

	public BigInteger getIdTelefoneCliente() {
		return idTelefoneCliente;
	}

	public void setIdTelefoneCliente(BigInteger idTelefoneCliente) {
		this.idTelefoneCliente = idTelefoneCliente;
	}

	public TelefoneCliente getTelefoneCliente() {
		return telefoneCliente;
	}

	public void setTelefoneCliente(TelefoneCliente telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}

	public String getChaveAtendimento() {
		return chaveAtendimento;
	}

	public void setChaveAtendimento(String chaveAtendimento) {
		this.chaveAtendimento = chaveAtendimento;
	}

	public BigInteger getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(BigInteger idEvento) {
		this.idEvento = idEvento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public BigInteger getIdClienteCampanha() {
		return idClienteCampanha;
	}

	public void setIdClienteCampanha(BigInteger idClienteCampanha) {
		this.idClienteCampanha = idClienteCampanha;
	}

	public ClienteCampanha getClienteCampanha() {
		return clienteCampanha;
	}

	public void setClienteCampanha(ClienteCampanha clienteCampanha) {
		this.clienteCampanha = clienteCampanha;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
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
		HistoricoContato other = (HistoricoContato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoricoContato [id=" + id + "]";
	}

	public String getDetalhamentoEvento() {
		return detalhamentoEvento;
	}

	public void setDetalhamentoEvento(String detalhamentoEvento) {
		this.detalhamentoEvento = detalhamentoEvento;
	}
	
}