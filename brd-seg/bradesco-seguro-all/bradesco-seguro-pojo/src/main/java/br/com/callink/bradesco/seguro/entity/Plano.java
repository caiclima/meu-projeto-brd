/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_PLANO")
@Cacheable
public class Plano implements IdentifiableEntity<BigInteger>, IRemovableEntity, ILog, Comparable<Plano> {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_PLANO")
	private BigInteger id;
	
	@Basic(optional = false)
	@Column(name="SIGLA")
	private String sigla;
	
	@Basic(optional = false)
	@Column(name="NOME_PLANO")
	private String nome;
	
	@Basic(optional = false)
	@Column//(unique=true)
	private String codigo;
	
	@Basic(optional = false)
	@Column(name = "DATA_INICIO_VIGENCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioVigencia;
	
	@Column(name = "DATA_FINAL_VIGENCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinalVigencia;
	
	@Column(name = "CAPITAL_SEGURADO")
	private BigDecimal capitalSegurado;
	
	@Column(name = "VALOR_PREMIO")
	private BigDecimal valorPremio;
	
	@Column(name = "FAIXA_ETARIA_INICIAL")
	private String faixaEtariaInicial;
	
	@Column(name = "FAIXA_ETARIA_FINAL")
	private String faixaEtariaFinal;
	
	@Column(name = "VALOR_TITULAR")
	private BigDecimal valorTitular;
	
	@Column(name = "VALOR_TITULAR_CONJUGE")
	private BigDecimal valorTitularConjuge;
	
	@Column(name = "VALOR_POR_FILHO")
	private BigDecimal valorPorFilho;
	
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
	
	@JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO")
	@ManyToOne
	private Produto produto;
	
	@JoinColumn(name = "ID_TIPO_PLANO", referencedColumnName = "ID_TIPO_PLANO")
	@ManyToOne
	private TipoPlano tipoPlano;
	
	@OneToMany(mappedBy = "plano")
    private Set<BeneficiarioPlano> beneficiarioPlanoList;
	
	// TODO - refatorar para consulta e corrigr imapctos - melhoria
	@OneToMany(mappedBy = "plano", fetch=FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private List<Cobertura> coberturaList;

	public Plano() {
	}

	public Plano(BigInteger id) {
		this.id = id;
	}

	public Plano(BigInteger id, String codigo, Date dataInicioVigencia,
			BigDecimal capitalSegurado) {
		this.id = id;
		this.codigo = codigo;
		this.dataInicioVigencia = dataInicioVigencia;
		this.capitalSegurado = capitalSegurado;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public TipoPlano getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlano tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public BigDecimal getCapitalSegurado() {
		return capitalSegurado;
	}

	public void setCapitalSegurado(BigDecimal capitalSegurado) {
		this.capitalSegurado = capitalSegurado;
	}

	public BigDecimal getValorPremio() {
		return valorPremio;
	}

	public void setValorPremio(BigDecimal valorPremio) {
		this.valorPremio = valorPremio;
	}

	public String getFaixaEtariaInicial() {
		return faixaEtariaInicial;
	}

	public void setFaixaEtariaInicial(String faixaEntariaInicial) {
		this.faixaEtariaInicial = faixaEntariaInicial;
	}

	public String getFaixaEtariaFinal() {
		return faixaEtariaFinal;
	}

	public void setFaixaEtariaFinal(String faixaEtariaFinal) {
		this.faixaEtariaFinal = faixaEtariaFinal;
	}

	public BigDecimal getValorTitular() {
		return valorTitular;
	}

	public void setValorTitular(BigDecimal valorTitular) {
		this.valorTitular = valorTitular;
	}

	public BigDecimal getValorTitularConjuge() {
		return valorTitularConjuge;
	}

	public void setValorTitularConjuge(BigDecimal valorTitularConjuge) {
		this.valorTitularConjuge = valorTitularConjuge;
	}

	public BigDecimal getValorPorFilho() {
		return valorPorFilho;
	}

	public void setValorPorFilho(BigDecimal valorPorFilho) {
		this.valorPorFilho = valorPorFilho;
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Set<BeneficiarioPlano> getBeneficiarioPlanoList() {
		return beneficiarioPlanoList;
	}

	public void setBeneficiarioPlanoList(
			Set<BeneficiarioPlano> beneficiarioPlanoList) {
		this.beneficiarioPlanoList = beneficiarioPlanoList;
	}

	public List<Cobertura> getCoberturaList() {
		return coberturaList;
	}

	public void setCoberturaList(List<Cobertura> coberturaList) {
		this.coberturaList = coberturaList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Plano)) {
			return false;
		}
		Plano other = (Plano) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "br.com.callink.bradesco.seguro.entity.Plano[ id=" + id + " ]";
	}
	
	@Override
	public int compareTo(Plano o) {
		if (this.getNome() == null || o.getNome() == null) {
			return -1;
		}
		return this.getNome().compareTo(o.getNome());
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

	public Boolean getFlagEnabled() {
		return flagEnabled;
	}

	public void setFlagEnabled(Boolean flagEnabled) {
		this.flagEnabled = flagEnabled;
	}

	public Boolean getFlagRemoved() {
		if(flagRemoved == null) {
			flagRemoved = Boolean.FALSE;
		}
		return flagRemoved;
	}

	public void setFlagRemoved(Boolean flagRemoved) {
		this.flagRemoved = flagRemoved;
	}

}
