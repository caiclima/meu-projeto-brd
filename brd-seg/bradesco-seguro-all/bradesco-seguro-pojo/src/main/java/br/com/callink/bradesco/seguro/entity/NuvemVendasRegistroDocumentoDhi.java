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
@Table(name = "TB_NUVEM_VENDAS_REGISTRO_DOCUMENTO_DHI")
public class NuvemVendasRegistroDocumentoDhi implements IdentifiableEntity<Long> {
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
    @Column(name = "DATA_EMISSAO")
    private String dataEmissao;
    @Column(name = "INICIO_VIGENCIA")
    private String inicioVigencia;
    @Column(name = "FIM_VIGENCIA")
    private String fimVigencia;
    @Column(name = "PREMIO_SEGURADO")
    private String premioSegurado;
    @Column(name = "FORMA_PARCELAMENTO")
    private String formaParcelamento;
    @Column(name = "ESTIPULANTE")
    private String estipulante;
    @Column(name = "PERCENTUAL_DESCONTO")
    private String percentualDesconto;
    @Column(name = "CAMPO_LIVRE_1")
    private String campoLivre1;
    @Column(name = "CAMPO_LIVRE_2")
    private String campoLivre2;
    @Column(name = "FILLER")
    private String filler;
    @JoinColumn(name = "ID_HEADER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private NuvemVendasRegistroHeaderDhi idHeader;

    public NuvemVendasRegistroDocumentoDhi() {
    }

    public NuvemVendasRegistroDocumentoDhi(Long id) {
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

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(String inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public String getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(String fimVigencia) {
        this.fimVigencia = fimVigencia;
    }

    public String getPremioSegurado() {
        return premioSegurado;
    }

    public void setPremioSegurado(String premioSegurado) {
        this.premioSegurado = premioSegurado;
    }

    public String getFormaParcelamento() {
        return formaParcelamento;
    }

    public void setFormaParcelamento(String formaParcelamento) {
        this.formaParcelamento = formaParcelamento;
    }

    public String getEstipulante() {
        return estipulante;
    }

    public void setEstipulante(String estipulante) {
        this.estipulante = estipulante;
    }

    public String getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(String percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public String getCampoLivre1() {
        return campoLivre1;
    }

    public void setCampoLivre1(String campoLivre1) {
        this.campoLivre1 = campoLivre1;
    }

    public String getCampoLivre2() {
        return campoLivre2;
    }

    public void setCampoLivre2(String campoLivre2) {
        this.campoLivre2 = campoLivre2;
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
        if (!(object instanceof NuvemVendasRegistroDocumentoDhi)) {
            return false;
        }
        NuvemVendasRegistroDocumentoDhi other = (NuvemVendasRegistroDocumentoDhi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroDocumentoDhi[ id=" + id + " ]";
    }
    
}
