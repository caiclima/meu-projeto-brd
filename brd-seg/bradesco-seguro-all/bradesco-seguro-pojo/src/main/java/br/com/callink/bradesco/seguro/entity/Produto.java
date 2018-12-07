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

/**
 * 
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_PRODUTO")
@Cacheable
public class Produto extends DefaultEntity implements Comparable<Produto>, IRemovableEntity {

	private static final long serialVersionUID = 7144977637436480099L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_PRODUTO")
	private BigInteger id;
	
	@Basic(optional = false)
	@Column(name="NOME_PRODUTO")
	private String nome;
	
	@Basic(optional = false)
	@Column(name="SIGLA")
	private String sigla;
	
	private String descricao;
	
	@Basic(optional = false)
	private String codigo;
	
	@Basic(optional = false)
	@Column(name = "DATA_INICIO_VIGENCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioVigencia;
	
	@Column(name = "DATA_FINAL_VIGENCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinalVigencia;
	
	@Basic(optional=false)
	@Column(name="VALIDACAO_VENDA_PRODUTO")
	private String codigoValidacaoProduto;
	
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
	
	@Basic(optional = false)
	@Column(name = "FLAG_ENABLED")
	private Boolean flagEnabled;
	
	@Basic(optional = false)
    @Column(name = "FLAG_REMOVED")
    private Boolean flagRemoved;
	
	@OneToMany(mappedBy = "id")
	private List<Plano> planoList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private List<ProdutoEvento> produtoEventoList;

	public Produto() {
	}

	public Produto(BigInteger id) {
		this.id = id;
	}

	public Produto(BigInteger id, String descricao, Date dataInicioVigencia,
			String logUid, String logHost, Date logDate, String logSystem,
			String logObs, BigInteger logTransaction, Boolean flagEnabled,
			String codigo) {
		this.id = id;
		this.descricao = descricao;
		this.dataInicioVigencia = dataInicioVigencia;
		this.logUid = logUid;
		this.logHost = logHost;
		this.logDate = logDate;
		this.logSystem = logSystem;
		this.logObs = logObs;
		this.logTransaction = logTransaction;
		this.flagEnabled = flagEnabled;
		this.codigo = codigo;
	}

	public Produto(BigInteger id, String descricao) {
		this.id = id;
		this.descricao = descricao;
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

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Date getDataFinalVigencia() {
		return dataFinalVigencia;
	}

	public void setDataFinalVigencia(Date dataFinalVigencia) {
		this.dataFinalVigencia = dataFinalVigencia;
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

	@XmlTransient
	public List<Plano> getPlanoList() {
		return planoList;
	}

	public void setPlanoList(List<Plano> planoList) {
		this.planoList = planoList;
	}

	@XmlTransient
	public List<ProdutoEvento> getProdutoEventoList() {
		return produtoEventoList;
	}

	public void setProdutoEventoList(List<ProdutoEvento> produtoEventoList) {
		this.produtoEventoList = produtoEventoList;
	}

	public Boolean getFlagEnabled() {
		return flagEnabled;
	}

	public void setFlagEnabled(Boolean flagEnabled) {
		this.flagEnabled = flagEnabled;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigoValidacaoProduto() {
		return codigoValidacaoProduto;
	}

	public void setCodigoValidacaoProduto(String codigoValidacaoProduto) {
		this.codigoValidacaoProduto = codigoValidacaoProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public Boolean getFlagRemoved() {
		if(flagRemoved == null) {
			flagRemoved = Boolean.FALSE;
		}
		return flagRemoved;
	}

	@Override
	public void setFlagRemoved(Boolean flagRemoved) {
		this.flagRemoved = flagRemoved;
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
		if (!(object instanceof Produto)) {
			return false;
		}
		Produto other = (Produto) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.callink.bradesco.seguro.entity.Produto[ id=" + id + " ]";
	}

	@Override
	public int compareTo(Produto o) {
		if (this.getNome() == null || o.getNome() == null) {
			return -1;
		}
		return this.getNome().compareTo(o.getNome());
	}

}
