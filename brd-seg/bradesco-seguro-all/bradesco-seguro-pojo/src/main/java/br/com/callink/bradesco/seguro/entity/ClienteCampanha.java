package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author swb.thiagohenrique
 */
@Entity
@Table(name = "TB_CLIENTE_CAMPANHA")
public class ClienteCampanha implements IdentifiableEntity<BigInteger>,
		Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ID_CLIENTE_CAMPANHA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	@Column(name = "ID_TELEFONE_CLIENTE")
	private BigInteger idTelefoneCliente;

	@Column(name = "DATA_EXPORTACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExportacao;

	@Column(name = "INFORMACAO_ADICIONAL")
	private String informacaoAdicional;

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
	private long logTransaction;

	@Column(name = "DATA_FIM_CONTATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFimContato;

	@Column(name = "ID_EVENTO")
	private BigInteger idEvento;

	@Column(name = "DATA_CONTATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataContato;

	@Column(name = "DURACAO_SEGUNDOS_CONTATO")
	private BigInteger duracaoSegundosContato;

	@Column(name = "ID_CONTATO_MAILING")
	private BigInteger idContatoMailing;

	@JoinColumn(name = "ID_CONTATO_MAILING", referencedColumnName = "ID_CONTATO_MAILING", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private ContatoMailing contatoMailing;

	@Column(name = "ID_CAMPANHA")
	private BigInteger idCampanha;

	@JoinColumn(name = "ID_CAMPANHA", referencedColumnName = "ID_CAMPANHA", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Campanha campanha;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteCampanha")
	private Set<TelefoneCliente> telefoneClienteList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteCampanha")
	private List<HistoricoContato> historicoContatoList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteCampanha")
	private Set<Venda> vendaList;

	/*@Column(name = "FLAG_FINALIZADO_ATENDIMENTO")
	private Boolean flagFinalizadoAtendimento;
*/
	public ClienteCampanha() {
	}

	public ClienteCampanha(BigInteger idClienteCampanha) {
		this.id = idClienteCampanha;
	}

	public ClienteCampanha(BigInteger idClienteCampanha, boolean flagEnabled,
			String logUid, String logHost, Date logDate, String logSystem,
			String logObs, long logTransaction) {
		this.id = idClienteCampanha;
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

	public void setId(BigInteger obj) {
		this.id = obj;
	}

	public final BigInteger getIdTelefoneCliente() {
		return idTelefoneCliente;
	}

	public final void setIdTelefoneCliente(BigInteger idTelefoneCliente) {
		this.idTelefoneCliente = idTelefoneCliente;
	}

	public List<HistoricoContato> getHistoricoContatoList() {
		return historicoContatoList;
	}

	public void setHistoricoContatoList(
			List<HistoricoContato> historicoContatoList) {
		this.historicoContatoList = historicoContatoList;
	}

	public Date getDataExportacao() {
		return dataExportacao;
	}

	public void setDataExportacao(Date dataExportacao) {
		this.dataExportacao = dataExportacao;
	}

	public String getInformacaoAdicional() {
		return informacaoAdicional;
	}

	public void setInformacaoAdicional(String informacaoAdicional) {
		this.informacaoAdicional = informacaoAdicional;
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

	public long getLogTransaction() {
		return logTransaction;
	}

	public void setLogTransaction(long logTransaction) {
		this.logTransaction = logTransaction;
	}

	public Date getDataFimContato() {
		return dataFimContato;
	}

	public void setDataFimContato(Date dataFimContato) {
		this.dataFimContato = dataFimContato;
	}

	public BigInteger getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(BigInteger idEvento) {
		this.idEvento = idEvento;
	}

	public Set<TelefoneCliente> getTelefoneClienteList() {
		return telefoneClienteList;
	}

	public void setTelefoneClienteList(Set<TelefoneCliente> telefoneClienteList) {
		this.telefoneClienteList = telefoneClienteList;
	}

	/**
	 * @return the flagFinalizadoAtendimento
	 */
/*	public Boolean getFlagFinalizadoAtendimento() {
		return flagFinalizadoAtendimento;
	}*/

	/**
	 * @param flagFinalizadoAtendimento
	 *            the flagFinalizadoAtendimento to set
	 */
	/*public void setFlagFinalizadoAtendimento(Boolean flagFinalizadoAtendimento) {
		this.flagFinalizadoAtendimento = flagFinalizadoAtendimento;
	}*/

	public Date getDataContato() {
		return dataContato;
	}

	public void setDataContato(Date dataContato) {
		this.dataContato = dataContato;
	}

	public BigInteger getDuracaoSegundosContato() {
		return duracaoSegundosContato;
	}

	public void setDuracaoSegundosContato(BigInteger duracaoSegundosContato) {
		this.duracaoSegundosContato = duracaoSegundosContato;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof ClienteCampanha)) {
			return false;
		}
		ClienteCampanha other = (ClienteCampanha) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ClienteCampanha[ id=" + id + " ]";
	}

	public ContatoMailing getContatoMailing() {
		return contatoMailing;
	}

	public void setContatoMailing(ContatoMailing contatoMailing) {
		this.contatoMailing = contatoMailing;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public BigInteger getIdContatoMailing() {
		return idContatoMailing;
	}

	public void setIdContatoMailing(BigInteger idContatoMailing) {
		this.idContatoMailing = idContatoMailing;
	}

	public BigInteger getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(BigInteger idCampanha) {
		this.idCampanha = idCampanha;
	}
	

	public Set<Venda> getVendaList() {
		return vendaList;
	}

	public void setVendaList(Set<Venda> vendaList) {
		this.vendaList = vendaList;
	}

	@Override
	public ClienteCampanha clone() throws CloneNotSupportedException {
		ClienteCampanha clone = new ClienteCampanha();
		clone.idTelefoneCliente = this.idTelefoneCliente;
		clone.dataExportacao = this.dataExportacao;
		clone.informacaoAdicional = this.informacaoAdicional;
		clone.flagEnabled = this.flagEnabled;
		clone.logUid = this.logUid;
		clone.logHost = this.logHost;
		clone.logDate = this.logDate;
		clone.logSystem = this.logSystem;
		clone.logObs = this.logObs;
		clone.logTransaction = this.logTransaction;
		clone.dataFimContato = this.dataFimContato;
		clone.idEvento = this.idEvento;
		clone.dataContato = this.dataContato;
		clone.duracaoSegundosContato = this.duracaoSegundosContato;
		clone.idContatoMailing = this.idContatoMailing;
		clone.contatoMailing = this.contatoMailing;
		clone.idCampanha = this.idCampanha;
		clone.campanha = this.campanha;
		//clone.flagFinalizadoAtendimento = this.flagFinalizadoAtendimento;

		return clone;
	}

}
