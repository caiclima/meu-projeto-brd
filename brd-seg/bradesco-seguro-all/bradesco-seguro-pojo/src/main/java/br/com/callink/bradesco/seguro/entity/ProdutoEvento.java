/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TB_PRODUTO_EVENTO")
@XmlRootElement
public class ProdutoEvento implements IdentifiableEntity<BigInteger>, ILog, Cloneable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUTO_EVENTO")
    private BigInteger id;
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
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO")
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    private Produto produto;
    @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO")
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    private Evento evento;

    public ProdutoEvento() {
    }

    public ProdutoEvento(BigInteger id) {
        this.id = id;
    }

    public ProdutoEvento(Produto produto, Evento evento) {
		this.produto = produto;
		this.evento = evento;
	}

	public ProdutoEvento(BigInteger id, String logUid, String logHost, Date logDate, String logSystem, String logObs, BigInteger logTransaction) {
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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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
		ProdutoEvento other = (ProdutoEvento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ProdutoEvento clone(Evento evento, Produto produto) {
		ProdutoEvento clone = new ProdutoEvento();
		clone.evento = this.evento;
		clone.produto = this.produto;
		return clone;
	}
    
}
