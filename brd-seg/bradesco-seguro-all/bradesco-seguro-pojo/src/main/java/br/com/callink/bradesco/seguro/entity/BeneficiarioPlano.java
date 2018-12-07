/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_BENEFICIARIO_PLANO")
@XmlRootElement
public class BeneficiarioPlano implements IdentifiableEntity<BigInteger> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BENEFICIARIO_PLANO")
    private BigInteger id;
    @JoinColumn(name = "ID_PLANO", referencedColumnName = "ID_PLANO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Plano plano;
    @JoinColumn(name = "ID_BENEFICIARIO", referencedColumnName = "ID_BENEFICIARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Beneficiario beneficiario;
    @Column(name = "ID_PLANO")
    private BigInteger idPlano;
    @Column(name = "ID_BENEFICIARIO")
    private BigInteger idBeneficiario;

    public BeneficiarioPlano() {
    }
    
    public BeneficiarioPlano(Beneficiario beneficiario) {
    	this.beneficiario = beneficiario;
    }

    public BeneficiarioPlano(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

	public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public BigInteger getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(BigInteger idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
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
        if (!(object instanceof BeneficiarioPlano)) {
            return false;
        }
        BeneficiarioPlano other = (BeneficiarioPlano) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.BeneficiarioPlano[ id=" + id + " ]";
    }
    
}
