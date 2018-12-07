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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "TB_REJEICAO")
@XmlRootElement
public class Rejeicao implements IdentifiableEntity<BigInteger> {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_REJEICAO")
    private BigInteger id;
    @Column(name = "TIPO_MENSAGEM")
    private String tipoMensagem;
    @Column(name = "NUMERO_LOTE")
    private String numeroLote;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "ID_VENDA")
    private BigInteger idVenda;
    @Column(name = "FLAG_TRATADO")
    private Boolean flagTratado;
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
    private Long logTransaction;

    public Rejeicao() {
    }

    public Rejeicao(BigInteger id) {
        this.id = id;
    }

    public Rejeicao(BigInteger id, String logUid, String logHost, Date logDate, String logSystem, String logObs, Long logTransaction) {
        this.id = id;
        this.logUid = logUid;
        this.logHost = logHost;
        this.logDate = logDate;
        this.logSystem = logSystem;
        this.logObs = logObs;
        this.logTransaction = logTransaction;
    }

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTipoMensagem() {
        return tipoMensagem;
    }

    public void setTipoMensagem(String tipoMensagem) {
        this.tipoMensagem = tipoMensagem;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Long getLogTransaction() {
        return logTransaction;
    }

    public void setLogTransaction(Long logTransaction) {
        this.logTransaction = logTransaction;
    }

    public BigInteger getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(BigInteger idVenda) {
        this.idVenda = idVenda;
    }

    public Boolean isFlagTratado() {
        return flagTratado;
    }

    public void setFlagTratado(Boolean flagTratado) {
        this.flagTratado = flagTratado;
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
        if (!(object instanceof Rejeicao)) {
            return false;
        }
        Rejeicao other = (Rejeicao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.Rejeicao[ id=" + id + " ]";
    }
    
}
