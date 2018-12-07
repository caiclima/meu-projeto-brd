package br.com.callink.bradesco.seguro.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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

import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

@Entity
@Table(name = "TB_COBERTURA")
@Cacheable
public class Cobertura implements IdentifiableEntity<BigInteger>, IRemovableEntity, ILog, Comparable<Cobertura> {

	private static final long serialVersionUID = 8553839702988542391L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_COBERTURA")
	private BigInteger id;
	
	@Column(name = "VALOR_TRANSPORTE_COLETIVO")
	private BigDecimal valorTransporteColetivo;
	
	@Column(name = "VALOR_VEICULA_PARTICULAR_TAXIS_PEDESTRES")
	private BigDecimal valorVeiculoParticularTaxisPedestres;
	
	@Column(name = "VALOR_DEMAIS_ACIDENTES")
	private BigDecimal valorDemaisAcidentes;
	
	@Column(name = "VALOR_DIH_MOTIVO_ACIDENTES")
	private BigDecimal valorDIHMotivoAcidentes;
	
	@Column(name = "VALOR_SORTEIO")
	private BigDecimal valorSorteio;
	
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
    @Column(name = "FLAG_REMOVED")
    private Boolean flagRemoved;
	
	@JoinColumn(name = "ID_GRAU_PARENTESCO", referencedColumnName = "ID_GRAU_PARENTESCO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GrauParentesco grauParentesco;
    
	@Column(name = "ID_GRAU_PARENTESCO")
    private BigInteger idGrauParentesco;
	
	@JoinColumn(name = "ID_PLANO", referencedColumnName = "ID_PLANO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Plano plano;
    
	@Column(name = "ID_PLANO")
    private BigInteger idPlano;
   
	@Override
	public String getLogUid() {
		return this.logUid;
	}

	@Override
	public void setLogUid(String logUid) {
		this.logUid = logUid;
	}

	@Override
	public String getLogHost() {
		return this.logHost;
	}

	@Override
	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	@Override
	public Date getLogDate() {
		return this.logDate;
	}

	@Override
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Override
	public String getLogSystem() {
		return this.logSystem;
	}

	@Override
	public void setLogSystem(String logSystem) {
		this.logSystem = logSystem;
	}

	@Override
	public String getLogObs() {
		return this.logObs;
	}

	@Override
	public void setLogObs(String logObs) {
		this.logObs = logObs;
	}

	@Override
	public BigInteger getLogTransaction() {
		return this.logTransaction;
	}

	@Override
	public void setLogTransaction(BigInteger logTransaction) {
		this.logTransaction = logTransaction;
	}

	@Override
	public Boolean getFlagRemoved() {
		return this.flagRemoved;
	}

	@Override
	public void setFlagRemoved(Boolean removed) {
		this.flagRemoved = removed;
	}

	@Override
	public BigInteger getId() {
		return this.id;
	}

	@Override
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public BigDecimal getValorTransporteColetivo() {
		return valorTransporteColetivo;
	}

	public void setValorTransporteColetivo(BigDecimal valorTransporteColetivo) {
		this.valorTransporteColetivo = valorTransporteColetivo;
	}

	public BigDecimal getValorVeiculoParticularTaxisPedestres() {
		return valorVeiculoParticularTaxisPedestres;
	}

	public void setValorVeiculoParticularTaxisPedestres(
			BigDecimal valorVeiculoParticularTaxisPedestres) {
		this.valorVeiculoParticularTaxisPedestres = valorVeiculoParticularTaxisPedestres;
	}

	public BigDecimal getValorDemaisAcidentes() {
		return valorDemaisAcidentes;
	}

	public void setValorDemaisAcidentes(BigDecimal valorDemaisAcidentes) {
		this.valorDemaisAcidentes = valorDemaisAcidentes;
	}

	public BigDecimal getValorDIHMotivoAcidentes() {
		return valorDIHMotivoAcidentes;
	}

	public void setValorDIHMotivoAcidentes(BigDecimal valorDIHMotivoAcidentes) {
		this.valorDIHMotivoAcidentes = valorDIHMotivoAcidentes;
	}

	public BigDecimal getValorSorteio() {
		return valorSorteio;
	}

	public void setValorSorteio(BigDecimal valorSorteio) {
		this.valorSorteio = valorSorteio;
	}

	public GrauParentesco getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(GrauParentesco grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	public BigInteger getIdGrauParentesco() {
		return idGrauParentesco;
	}

	public void setIdGrauParentesco(BigInteger idGrauParentesco) {
		this.idGrauParentesco = idGrauParentesco;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public BigInteger getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(BigInteger idPlano) {
		this.idPlano = idPlano;
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
		if (!(obj instanceof Cobertura)) {
			return false;
		}
		Cobertura other = (Cobertura) obj;
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
		return "Cobertura [id=" + id + "]";
	}

	@Override
	public int compareTo(Cobertura o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
