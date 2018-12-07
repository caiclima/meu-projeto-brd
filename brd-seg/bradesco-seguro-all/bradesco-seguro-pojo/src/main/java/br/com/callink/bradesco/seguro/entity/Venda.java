/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_VENDA")
@XmlRootElement
public class Venda implements IdentifiableEntity<BigInteger>, ILog {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_VENDA")
	private BigInteger id;
	@Column(name = "FLAG_HIV")
	private Boolean flagHiv;
	@Column(name = "FLAG_VENDA_FECHADA")
	private Boolean flagVendaFechada;
	private String sexo;
	@Column(name = "VALOR_TOTAL")
	private BigDecimal valorTotal;
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
	@Column(name = "DATA_NASCIMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	@Column(name = "FLAG_APOSENTADO_INVALIDEZ")
	private Boolean flagAposentadoInvalidez;
	@JoinColumn(name = "ID_PROFISSAO", referencedColumnName = "ID_PROFISSAO", insertable = false, updatable = false)
	@ManyToOne
	private Profissao profissao;
	@JoinColumn(name = "ID_ESTADO_CIVIL", referencedColumnName = "ID_ESTADO_CIVIL", insertable = false, updatable = false)
	@ManyToOne
	private EstadoCivil estadoCivil;
	@JoinColumn(name = "ID_ESPORTE", referencedColumnName = "ID_ESPORTE", insertable = false, updatable = false)
	@ManyToOne
	private Esporte esporte;
	@JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", insertable = false, updatable = false)
	@ManyToOne
	private Evento evento;
	@JoinColumn(name = "ID_CLIENTE_CAMPANHA", referencedColumnName = "ID_CLIENTE_CAMPANHA", insertable = false, updatable = false)
	@ManyToOne
	private ClienteCampanha clienteCampanha;
	@Column(name = "ID_CLIENTE_CAMPANHA")
	private BigInteger idClienteCampanha;
	@Column(name = "ID_ESPORTE")
	private BigInteger idEsporte;
	@Column(name = "ID_ESTADO_CIVIL")
	private BigInteger idEstadoCivil;
	@Column(name = "ID_PROFISSAO")
	private BigInteger idProfissao;
	@Column(name = "SCRIPT_AUDITORIA")
	private String scriptAuditoria;
	@Column(name = "ID_EVENTO")
	private BigInteger idEvento;
	@OneToMany(mappedBy = "venda")
	private Set<Beneficiario> beneficiarioList;
	@Column(name = "CODIGO_APOLICE")
	private String codigoApolice;
	@Column(name = "DATA_VENDA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVenda;
	@Column(name = "DATA_ENVIO")
	@Temporal(TemporalType.DATE)
	private Date dataEnvio;
	
	public Venda() {
	}

	public Venda(BigInteger id) {
		this.id = id;
	}
	
	public Venda(Boolean flagVendaFechada, 
			Boolean flagAposentadoInvalidez, 
			Boolean flagHiv) {
		this.flagVendaFechada = flagVendaFechada;
		this.flagAposentadoInvalidez = flagAposentadoInvalidez;
		this.flagHiv = flagHiv;
				
	}

	public Venda(BigInteger id, String logUid, String logHost, Date logDate,
			String logSystem, String logObs, BigInteger logTransaction) {
		this.id = id;
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

	public BigInteger getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(BigInteger idEvento) {
		this.idEvento = idEvento;
	}

	public Boolean getFlagHiv() {
		return flagHiv;
	}

	public void setFlagHiv(Boolean flagHiv) {
		this.flagHiv = flagHiv;
	}

	public Boolean getFlagVendaFechada() {
		return flagVendaFechada;
	}

	public void setFlagVendaFechada(Boolean flagVendaFechada) {
		this.flagVendaFechada = flagVendaFechada;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
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

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public ClienteCampanha getClienteCampanha() {
		return clienteCampanha;
	}

	public void setClienteCampanha(ClienteCampanha clienteCampanha) {
		this.clienteCampanha = clienteCampanha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Esporte getEsporte() {
		return esporte;
	}

	public void setEsporte(Esporte esporte) {
		this.esporte = esporte;
	}

	public Boolean getFlagAposentadoInvalidez() {
		return flagAposentadoInvalidez;
	}

	public void setFlagAposentadoInvalidez(Boolean flagAposentadoInvalidez) {
		this.flagAposentadoInvalidez = flagAposentadoInvalidez;
	}

	public BigInteger getIdClienteCampanha() {
		return idClienteCampanha;
	}

	public void setIdClienteCampanha(BigInteger idClienteCampanha) {
		this.idClienteCampanha = idClienteCampanha;
	}

	public BigInteger getIdEsporte() {
		return idEsporte;
	}

	public void setIdEsporte(BigInteger idEsporte) {
		this.idEsporte = idEsporte;
	}

	public BigInteger getIdEstadoCivil() {
		return idEstadoCivil;
	}

	public void setIdEstadoCivil(BigInteger idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public BigInteger getIdProfissao() {
		return idProfissao;
	}

	public void setIdProfissao(BigInteger idProfissao) {
		this.idProfissao = idProfissao;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Set<Beneficiario> getBeneficiarioList() {
		return beneficiarioList;
	}

	public void setBeneficiarioList(Set<Beneficiario> beneficiarioList) {
		this.beneficiarioList = beneficiarioList;
	}

	public String getCodigoApolice() {
		return codigoApolice;
	}

	public void setCodigoApolice(String codigoApolice) {
		this.codigoApolice = codigoApolice;
	}

    public String getScriptAuditoria() {
        return scriptAuditoria;
    }

    public void setScriptAuditoria(String scriptAuditoria) {
        this.scriptAuditoria = scriptAuditoria;
    }

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Venda)) {
			return false;
		}
		Venda other = (Venda) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.callink.bradesco.seguro.entity.Venda[ id=" + id + " ]";
	}

}
