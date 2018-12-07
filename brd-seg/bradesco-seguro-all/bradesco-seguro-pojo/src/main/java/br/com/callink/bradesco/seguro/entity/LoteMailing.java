package br.com.callink.bradesco.seguro.entity;

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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author swb_mateus
 *
 */
@Entity
@Table(name = "TB_LOTE_MAILING")
public class LoteMailing implements IdentifiableEntity<BigInteger>, ILog {
	
	private static final long serialVersionUID = -4861937345503426662L;

	@Id
    @Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOTE_MAILING", nullable = false)
	private BigInteger id;
	
	@Column(name = "ID_CAMPANHA")
	private BigInteger idCampanha;
	
	@Column(name = "DESIGNACAO", length = 100)
	private String designacao;
	
	@Column(name = "QUANTIDADE_IMPORTADA")
	private Integer quantidadeImportada;
	
	@Column(name = "QUANTIDADE_REJEITADA")
	private Integer quantidadeRejeitada;
	
	@Column(name = "DATA_INICIO_IMPORTACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataImportacao;
	
	@Column(name = "DATA_FIM_IMPORTACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFimImportacao;
	
	@Column(name = "DATA_INICIO_MAILING")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioMailing;
	
	@Column(name = "DATA_FIM_MAILING")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFimMailing;
	
	@Column(name = "NOME_MAILING")
	private String nomeMailing;
	
	@Column(name = "FLAG_EXPORTADO_ALM")
	private Short flagExportadoAlm;
	
	@Column(name = "FLAG_ENABLED")
	private Boolean flagEnabled;
	
	@Column(name = "LOG_UID")
	private String logUid;
	
	@Column(name = "LOG_HOST")
	private String logHost;
	
	@Column(name = "LOG_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logDate;
	
	@Column(name = "LOG_SYSTEM")
	private String logSystem;
	
	@Column(name = "LOG_OBS")
	private String logObs;
	
	@Column(name = "LOG_TRANSACTION")
	private BigInteger logTransaction;
	
	@Column(name = "FLAG_MAILING_FINALIZADO")
	private Boolean flagMailingFinalizado;
	
	@Column(name = "FLAG_EXPORTADO_TIPO_REGISTRO_HEADER")
	private Boolean flagExportadoTipoRegistroHeader;
	
	@Column(name = "EXPORTADO_NUVEM_TIPO_REGISTRO_STATUS")
	private Boolean flagExportadoTipoRegistroStatus;
	
	@JoinColumn(name = "ID_CAMPANHA", referencedColumnName = "ID_CAMPANHA", insertable = false, updatable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Campanha campanha;
	
    @OneToMany(mappedBy = "idLoteMailing")
    private List<ContatoMailing> contatoMailingList;

	public LoteMailing() {
	}
	
	public LoteMailing(String nome) {
		super();
		this.designacao = nome;
	}

	//GETTERS AND SETTERS
	public final BigInteger getId() {
		return id;
	}

	public final void setId(BigInteger id) {
		this.id = id;
	}

	public final BigInteger getIdCampanha() {
		return idCampanha;
	}

	public final void setIdCampanha(BigInteger idCampanha) {
		this.idCampanha = idCampanha;
	}

	public final String getDesignacao() {
		return designacao;
	}

	public final void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public final Integer getQuantidadeImportada() {
		return quantidadeImportada;
	}

	public final void setQuantidadeImportada(Integer quantidadeImportada) {
		this.quantidadeImportada = quantidadeImportada;
	}

	public final Integer getQuantidadeRejeitada() {
		return quantidadeRejeitada;
	}

	public final void setQuantidadeRejeitada(Integer quantidadeRejeitada) {
		this.quantidadeRejeitada = quantidadeRejeitada;
	}

	public final Date getDataImportacao() {
		return dataImportacao;
	}

	public final void setDataImportacao(Date dataImportacao) {
		this.dataImportacao = dataImportacao;
	}

	public final Date getDataInicioMailing() {
		return dataInicioMailing;
	}

	public final void setDataInicioMailing(Date dataInicioMailing) {
		this.dataInicioMailing = dataInicioMailing;
	}

	public final Date getDataFimMailing() {
		return dataFimMailing;
	}

	public final void setDataFimMailing(Date dataFimMailing) {
		this.dataFimMailing = dataFimMailing;
	}

	public final Boolean getFlagEnabled() {
		return flagEnabled;
	}

	public final void setFlagEnabled(Boolean flagEnabled) {
		this.flagEnabled = flagEnabled;
	}

	public final String getLogUid() {
		return logUid;
	}

	public final void setLogUid(String logUid) {
		this.logUid = logUid;
	}

	public final String getLogHost() {
		return logHost;
	}

	public final void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	public final Date getLogDate() {
		return logDate;
	}

	public final void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public final String getLogSystem() {
		return logSystem;
	}

	public final void setLogSystem(String logSystem) {
		this.logSystem = logSystem;
	}

	public final String getLogObs() {
		return logObs;
	}

	public final void setLogObs(String logObs) {
		this.logObs = logObs;
	}

	public final BigInteger getLogTransaction() {
		return logTransaction;
	}

	public final void setLogTransaction(BigInteger logTransaction) {
		this.logTransaction = logTransaction;
	}

	public final Campanha getCampanha() {
		return campanha;
	}

	public final void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Date getDataFimImportacao() {
		return dataFimImportacao;
	}

	public void setDataFimImportacao(Date dataFimImportacao) {
		this.dataFimImportacao = dataFimImportacao;
	}

	public String getNomeMailing() {
		return nomeMailing;
	}

	public void setNomeMailing(String nomeMailing) {
		this.nomeMailing = nomeMailing;
	}

	public Short getFlagExportadoAlm() {
		return flagExportadoAlm;
	}

	public void setFlagExportadoAlm(Short flagExportadoAlm) {
		this.flagExportadoAlm = flagExportadoAlm;
	}

	public List<ContatoMailing> getContatoMailingList() {
		return contatoMailingList;
	}

	public void setContatoMailingList(List<ContatoMailing> contatoMailingList) {
		this.contatoMailingList = contatoMailingList;
	}
	
	public Boolean getFlagMailingFinalizado() {
		return flagMailingFinalizado;
	}

	public void setFlagMailingFinalizado(Boolean flagMailingFinalizado) {
		this.flagMailingFinalizado = flagMailingFinalizado;
	}

	public Boolean getFlagExportadoTipoRegistroStatus() {
		return flagExportadoTipoRegistroStatus;
	}

	public void setFlagExportadoTipoRegistroStatus(
			Boolean flagExportadoTipoRegistroStatus) {
		this.flagExportadoTipoRegistroStatus = flagExportadoTipoRegistroStatus;

	}
	
	public Boolean getFlagExportadoTipoRegistroHeader() {
		return flagExportadoTipoRegistroHeader;
	}

	public void setFlagExportadoTipoRegistroHeader(
			Boolean flagExportadoTipoRegistroHeader) {
		this.flagExportadoTipoRegistroHeader = flagExportadoTipoRegistroHeader;
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
		if (!(obj instanceof LoteMailing)) {
			return false;
		}
		LoteMailing other = (LoteMailing) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
