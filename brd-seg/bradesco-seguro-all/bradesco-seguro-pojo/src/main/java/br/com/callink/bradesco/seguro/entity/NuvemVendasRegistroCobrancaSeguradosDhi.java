/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_NUVEM_VENDAS_REGISTRO_COBRANCA_SEGURADOS_DHI")
public class NuvemVendasRegistroCobrancaSeguradosDhi implements IdentifiableEntity<Long> {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TIPO_REGISTRO")
    private String tipoRegistro;
    @Column(name = "LOTE_TRANSACAO")
    private String loteTransacao;
    @Column(name = "TRANSACAO")
    private String transacao;
    @Column(name = "CHAVE_SEGURADO")
    private String chaveSegurado;
    @Column(name = "NUMERO_MEIO_COBRANCA")
    private String numeroMeioCobranca;
    @Column(name = "NUMERO_ADM_COBRANCA")
    private String numeroAdmCobranca;
    @Column(name = "NUMERO_CICLO_ADM_COBRANCA")
    private String numeroCicloAdmCobranca;
    @Column(name = "NUMERO_CARTAO_CREDITO")
    private String numeroCartaoCredito;
    @Column(name = "DATA_ANUIDADE_CARTAO_CREDITO")
    private String dataAnuidadeCartaoCredito;
    @Column(name = "NUMERO_CONTA_CARTAO_CREDITO")
    private String numeroContaCartaoCredito;
    @Column(name = "NUMERO_BANCO")
    private String numeroBanco;
    @Column(name = "NUMERO_AGENCIA")
    private String numeroAgencia;
    @Column(name = "NUMERO_CONTA")
    private String numeroConta;
    @Column(name = "NUMERO_FUNCIONARIO")
    private String numeroFuncionario;
    @Column(name = "NOME_FUNCIONARIO")
    private String nomeFuncionario;
    @Column(name = "NUMERO_TIPO_MEIO_COBRANCA")
    private String numeroTipoMeioCobranca;
    @Column(name = "CAMPO_RESERVADO_MEIO_COBRANCA")
    private String campoReservadoMeioCobranca;
    @Column(name = "FILLER")
    private String filler;
    @JoinColumn(name = "ID_HEADER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private NuvemVendasRegistroHeaderDhi idHeader;

    public NuvemVendasRegistroCobrancaSeguradosDhi() {
    }

    public NuvemVendasRegistroCobrancaSeguradosDhi(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getLoteTransacao() {
        return loteTransacao;
    }

    public void setLoteTransacao(String loteTransacao) {
        this.loteTransacao = loteTransacao;
    }

    public String getTransacao() {
        return transacao;
    }

    public void setTransacao(String transacao) {
        this.transacao = transacao;
    }

    public String getChaveSegurado() {
        return chaveSegurado;
    }

    public void setChaveSegurado(String chaveSegurado) {
        this.chaveSegurado = chaveSegurado;
    }

    public String getNumeroMeioCobranca() {
        return numeroMeioCobranca;
    }

    public void setNumeroMeioCobranca(String numeroMeioCobranca) {
        this.numeroMeioCobranca = numeroMeioCobranca;
    }

    public String getNumeroAdmCobranca() {
        return numeroAdmCobranca;
    }

    public void setNumeroAdmCobranca(String numeroAdmCobranca) {
        this.numeroAdmCobranca = numeroAdmCobranca;
    }

    public String getNumeroCicloAdmCobranca() {
        return numeroCicloAdmCobranca;
    }

    public void setNumeroCicloAdmCobranca(String numeroCicloAdmCobranca) {
        this.numeroCicloAdmCobranca = numeroCicloAdmCobranca;
    }

    public String getNumeroCartaoCredito() {
        return numeroCartaoCredito;
    }

    public void setNumeroCartaoCredito(String numeroCartaoCredito) {
        this.numeroCartaoCredito = numeroCartaoCredito;
    }

    public String getDataAnuidadeCartaoCredito() {
        return dataAnuidadeCartaoCredito;
    }

    public void setDataAnuidadeCartaoCredito(String dataAnuidadeCartaoCredito) {
        this.dataAnuidadeCartaoCredito = dataAnuidadeCartaoCredito;
    }

    public String getNumeroContaCartaoCredito() {
        return numeroContaCartaoCredito;
    }

    public void setNumeroContaCartaoCredito(String numeroContaCartaoCredito) {
        this.numeroContaCartaoCredito = numeroContaCartaoCredito;
    }

    public String getNumeroBanco() {
        return numeroBanco;
    }

    public void setNumeroBanco(String numeroBanco) {
        this.numeroBanco = numeroBanco;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroFuncionario() {
        return numeroFuncionario;
    }

    public void setNumeroFuncionario(String numeroFuncionario) {
        this.numeroFuncionario = numeroFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNumeroTipoMeioCobranca() {
        return numeroTipoMeioCobranca;
    }

    public void setNumeroTipoMeioCobranca(String numeroTipoMeioCobranca) {
        this.numeroTipoMeioCobranca = numeroTipoMeioCobranca;
    }

    public String getCampoReservadoMeioCobranca() {
        return campoReservadoMeioCobranca;
    }

    public void setCampoReservadoMeioCobranca(String campoReservadoMeioCobranca) {
        this.campoReservadoMeioCobranca = campoReservadoMeioCobranca;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public NuvemVendasRegistroHeaderDhi getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(NuvemVendasRegistroHeaderDhi idHeader) {
        this.idHeader = idHeader;
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
        if (!(object instanceof NuvemVendasRegistroCobrancaSeguradosDhi)) {
            return false;
        }
        NuvemVendasRegistroCobrancaSeguradosDhi other = (NuvemVendasRegistroCobrancaSeguradosDhi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroCobrancaSeguradosDhi[ id=" + id + " ]";
    }
    
}
