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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_BENEFICIARIO")
@XmlRootElement
public class Beneficiario implements IdentifiableEntity<BigInteger>, ILog {

	private static final long serialVersionUID = -1573312542942672809L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BENEFICIARIO")
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Column(name = "SEXO")
    private String sexo;
    @Basic(optional = false)
    @Column(name = "CPF")
    private String cpf;
    @Basic(optional = false)
    @Column(name = "DATA_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
    @Basic(optional = false)
    @Column(name = "RG")
    private String rg;
    @Basic(optional = false)
    @Column(name = "ORGAO_EXPEDIDOR")
    private String orgaoExpedidor;
    @Basic(optional = false)
    @Column(name = "DATA_EXPEDICAO")
    private Date dataExpedicao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "TIPO_BENEFICIARIO")
    private String tipoBeneficiario;
    @JoinColumn(name = "ID_VENDA", referencedColumnName = "ID_VENDA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Venda venda;
    @JoinColumn(name = "ID_PROFISSAO", referencedColumnName = "ID_PROFISSAO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Profissao profissao;
    @JoinColumn(name = "ID_GRAU_PARENTESCO", referencedColumnName = "ID_GRAU_PARENTESCO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GrauParentesco grauParentesco;
    @JoinColumn(name = "ID_ESPORTE", referencedColumnName = "ID_ESPORTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Esporte esporte;
    @Column(name = "ID_PROFISSAO")
    private BigInteger idProfissao;
    @Column(name = "ID_GRAU_PARENTESCO")
    private BigInteger idGrauParentesco;
    @Column(name = "ID_ESPORTE")
    private BigInteger idEsporte;
    @Column(name = "ID_VENDA")
    private BigInteger idVenda;
    @OneToMany(mappedBy = "beneficiario", fetch=FetchType.EAGER)
    private Set<BeneficiarioPlano> beneficiarioPlanoList;
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
    
    @Column(name = "FLAG_APOSENTADO_INVALIDEZ")
    private Boolean flagAposentadoInvalidez;
    
    @Column(name = "FLAG_HIV")
    private Boolean flagHiv;

    public Beneficiario() {
    }

    public Beneficiario(BigInteger id) {
        this.id = id;
    }
    
    public Beneficiario(Boolean flagHiv, Boolean flagAposentadoInvalidez) {
    	this.flagHiv = flagHiv;
    	this.flagAposentadoInvalidez = flagAposentadoInvalidez;
    }

    public Beneficiario(BigInteger id, String nome, String cpf, Date dataNascimento, String rg, String orgaoExpedidor, Date dataExpedicao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.rg = rg;
        this.orgaoExpedidor = orgaoExpedidor;
        this.dataExpedicao = dataExpedicao;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    public Date getDataExpedicao() {
        return dataExpedicao;
    }

    public void setDataExpedicao(Date dataExpedicao) {
        this.dataExpedicao = dataExpedicao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public GrauParentesco getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(GrauParentesco grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	public Esporte getEsporte() {
		return esporte;
	}

	public void setEsporte(Esporte esporte) {
		this.esporte = esporte;
	}

	public BigInteger getIdProfissao() {
		return idProfissao;
	}

	public void setIdProfissao(BigInteger idProfissao) {
		this.idProfissao = idProfissao;
	}

	public BigInteger getIdGrauParentesco() {
		return idGrauParentesco;
	}

	public void setIdGrauParentesco(BigInteger idGrauParentesco) {
		this.idGrauParentesco = idGrauParentesco;
	}

	public BigInteger getIdEsporte() {
		return idEsporte;
	}

	public void setIdEsporte(BigInteger idEsporte) {
		this.idEsporte = idEsporte;
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

	public Boolean getFlagAposentadoInvalidez() {
		if (flagAposentadoInvalidez == null) {
			return false;
		}
		return flagAposentadoInvalidez;
	}

	public void setFlagAposentadoInvalidez(Boolean flagAposentadoInvalidez) {
		this.flagAposentadoInvalidez = flagAposentadoInvalidez;
	}

	public Boolean getFlagHiv() {
		if (flagHiv == null) {
			return false;
		}
		return flagHiv;
	}

	public void setFlagHiv(Boolean flagHiv) {
		this.flagHiv = flagHiv;
	}
	
	public BigInteger getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(BigInteger idVenda) {
		this.idVenda = idVenda;
	}

	@XmlTransient
    public Set<BeneficiarioPlano> getBeneficiarioPlanoList() {
        return beneficiarioPlanoList;
    }

    public void setBeneficiarioPlanoList(Set<BeneficiarioPlano> beneficiarioPlanoList) {
        this.beneficiarioPlanoList = beneficiarioPlanoList;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Beneficiario)) {
            return false;
        }
        Beneficiario other = (Beneficiario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.Beneficiario[ id=" + id + " ]";
    }
    
}
