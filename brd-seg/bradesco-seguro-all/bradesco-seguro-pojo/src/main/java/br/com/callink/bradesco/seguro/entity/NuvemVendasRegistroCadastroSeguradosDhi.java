/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.util.Date;

import javax.persistence.Basic;
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

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_NUVEM_VENDAS_REGISTRO_CADASTRO_SEGURADOS_DHI")
public class NuvemVendasRegistroCadastroSeguradosDhi implements IdentifiableEntity<Long> {
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
    @Column(name = "NOME_SEGURADO")
    private String nomeSegurado;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "RG_SEGURADO")
    private String rgSegurado;
    @Column(name = "ORGAO_EMISSOR")
    private String orgaoEmissor;
    @Column(name = "DATA_EMISSAO")
    private String dataEmissao;
    @Column(name = "CPF_SEGURADO")
    private String cpfSegurado;
    @Column(name = "SEXO_SEGURADO")
    private String sexoSegurado;
    @Column(name = "DATA_NASC_SEGURADO")
    private String dataNascSegurado;
    @Column(name = "NUMERO_ESTADO_CIVIL")
    private String numeroEstadoCivil;
    @Column(name = "ENDERECO_SEGURADO")
    private String enderecoSegurado;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "BAIRRO_SEGURADO")
    private String bairroSegurado;
    @Column(name = "NUM_ENDERECO_SEGURADO")
    private String numEnderecoSegurado;
    @Column(name = "CIDADE_SEGURADO")
    private String cidadeSegurado;
    @Column(name = "ESTADO_SEGURADO")
    private String estadoSegurado;
    @Column(name = "CEP_SEGURADO")
    private String cepSegurado;
    @Column(name = "PROFISSAO_SEGURADO")
    private String profissaoSegurado;
    @Column(name = "DDD_TELEFONE_RES")
    private String dddTelefoneRes;
    @Column(name = "NUM_TELEFONE_RES")
    private String numTelefoneRes;
    @Column(name = "RAMAL_TELEFONE_RES")
    private String ramalTelefoneRes;
    @Column(name = "DDD_TELEFONE_COM")
    private String dddTelefoneCom;
    @Column(name = "NUM_TELEFONE_COM")
    private String numTelefoneCom;
    @Column(name = "RAMAL_TELEFONE_COM")
    private String ramalTelefoneCom;
    @Column(name = "DDD_TELEFONE_FAX_CELULAR")
    private String dddTelefoneFaxCelular;
    @Column(name = "NUM_TELEFONE_FAX_CELULAR")
    private String numTelefoneFaxCelular;
    @Column(name = "RAMAL_TELEFONE_FAX_CELULAR")
    private String ramalTelefoneFaxCelular;
    @Column(name = "TIPO_SEGURADO")
    private String tipoSegurado;
    @Column(name = "PREMIOS_SEGURADOS")
    private String premiosSegurados;
    @Column(name = "PLANO")
    private String plano;
    @Column(name = "INICIO_VIGENCIA_SEGURADO")
    private String inicioVigenciaSegurado;
    @Column(name = "CAMPO_LIVRE_1")
    private String campoLivre1;
    @Column(name = "CAMPO_LIVRE_2")
    private String campoLivre2;
    @Column(name = "CAMPO_LIVRE_3")
    private String campoLivre3;
    @Column(name = "CAMPO_LIVRE_4")
    private String campoLivre4;
    @Column(name = "CAMPO_LIVRE")
    private String campoLivre;
    @Column(name = "NUMERO_SEQUENCIAL_SEGURADO")
    private String numeroSequencialSegurado;
    @Column(name = "NUMERO_UNICO_SEGURADO_SITE")
    private String numeroUnicoSeguradoSite;
    @Column(name = "OPCAO_RECEBER_EMAIL_CERTIFICADO")
    private String opcaoReceberEmailCertificado;
    @Column(name = "PERMITE_RECEBIMENTO_EMAIL_MARKETING")
    private String permiteRecebimentoEmailMarketing;
    @Column(name = "FILLER")
    private String filler;
    @Column(name = "DATA_OPCAO_RECEBIMENTO_EMAIL_MARKETING")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOpcaoRecebimentoEmailMarketing;
    @Column(name = "DATA_OPCAO_RECEBIMENTO_EMAIL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOpcaoRecebimentoEmail;
    @JoinColumn(name = "ID_HEADER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private NuvemVendasRegistroHeaderDhi idHeader;

    public NuvemVendasRegistroCadastroSeguradosDhi() {
    }

    public NuvemVendasRegistroCadastroSeguradosDhi(Long id) {
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

    public String getNomeSegurado() {
        return nomeSegurado;
    }

    public void setNomeSegurado(String nomeSegurado) {
        this.nomeSegurado = nomeSegurado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRgSegurado() {
        return rgSegurado;
    }

    public void setRgSegurado(String rgSegurado) {
        this.rgSegurado = rgSegurado;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getCpfSegurado() {
        return cpfSegurado;
    }

    public void setCpfSegurado(String cpfSegurado) {
        this.cpfSegurado = cpfSegurado;
    }

    public String getSexoSegurado() {
        return sexoSegurado;
    }

    public void setSexoSegurado(String sexoSegurado) {
        this.sexoSegurado = sexoSegurado;
    }

    public String getDataNascSegurado() {
        return dataNascSegurado;
    }

    public void setDataNascSegurado(String dataNascSegurado) {
        this.dataNascSegurado = dataNascSegurado;
    }

    public String getNumeroEstadoCivil() {
        return numeroEstadoCivil;
    }

    public void setNumeroEstadoCivil(String numeroEstadoCivil) {
        this.numeroEstadoCivil = numeroEstadoCivil;
    }

    public String getEnderecoSegurado() {
        return enderecoSegurado;
    }

    public void setEnderecoSegurado(String enderecoSegurado) {
        this.enderecoSegurado = enderecoSegurado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairroSegurado() {
        return bairroSegurado;
    }

    public void setBairroSegurado(String bairroSegurado) {
        this.bairroSegurado = bairroSegurado;
    }

    public String getNumEnderecoSegurado() {
        return numEnderecoSegurado;
    }

    public void setNumEnderecoSegurado(String numEnderecoSegurado) {
        this.numEnderecoSegurado = numEnderecoSegurado;
    }

    public String getCidadeSegurado() {
        return cidadeSegurado;
    }

    public void setCidadeSegurado(String cidadeSegurado) {
        this.cidadeSegurado = cidadeSegurado;
    }

    public String getEstadoSegurado() {
        return estadoSegurado;
    }

    public void setEstadoSegurado(String estadoSegurado) {
        this.estadoSegurado = estadoSegurado;
    }

    public String getCepSegurado() {
        return cepSegurado;
    }

    public void setCepSegurado(String cepSegurado) {
        this.cepSegurado = cepSegurado;
    }

    public String getProfissaoSegurado() {
        return profissaoSegurado;
    }

    public void setProfissaoSegurado(String profissaoSegurado) {
        this.profissaoSegurado = profissaoSegurado;
    }

    public String getDddTelefoneRes() {
        return dddTelefoneRes;
    }

    public void setDddTelefoneRes(String dddTelefoneRes) {
        this.dddTelefoneRes = dddTelefoneRes;
    }

    public String getNumTelefoneRes() {
        return numTelefoneRes;
    }

    public void setNumTelefoneRes(String numTelefoneRes) {
        this.numTelefoneRes = numTelefoneRes;
    }

    public String getRamalTelefoneRes() {
        return ramalTelefoneRes;
    }

    public void setRamalTelefoneRes(String ramalTelefoneRes) {
        this.ramalTelefoneRes = ramalTelefoneRes;
    }

    public String getDddTelefoneCom() {
        return dddTelefoneCom;
    }

    public void setDddTelefoneCom(String dddTelefoneCom) {
        this.dddTelefoneCom = dddTelefoneCom;
    }

    public String getNumTelefoneCom() {
        return numTelefoneCom;
    }

    public void setNumTelefoneCom(String numTelefoneCom) {
        this.numTelefoneCom = numTelefoneCom;
    }

    public String getRamalTelefoneCom() {
        return ramalTelefoneCom;
    }

    public void setRamalTelefoneCom(String ramalTelefoneCom) {
        this.ramalTelefoneCom = ramalTelefoneCom;
    }

    public String getDddTelefoneFaxCelular() {
        return dddTelefoneFaxCelular;
    }

    public void setDddTelefoneFaxCelular(String dddTelefoneFaxCelular) {
        this.dddTelefoneFaxCelular = dddTelefoneFaxCelular;
    }

    public String getNumTelefoneFaxCelular() {
        return numTelefoneFaxCelular;
    }

    public void setNumTelefoneFaxCelular(String numTelefoneFaxCelular) {
        this.numTelefoneFaxCelular = numTelefoneFaxCelular;
    }

    public String getRamalTelefoneFaxCelular() {
        return ramalTelefoneFaxCelular;
    }

    public void setRamalTelefoneFaxCelular(String ramalTelefoneFaxCelular) {
        this.ramalTelefoneFaxCelular = ramalTelefoneFaxCelular;
    }

    public String getTipoSegurado() {
        return tipoSegurado;
    }

    public void setTipoSegurado(String tipoSegurado) {
        this.tipoSegurado = tipoSegurado;
    }

    public String getPremiosSegurados() {
        return premiosSegurados;
    }

    public void setPremiosSegurados(String premiosSegurados) {
        this.premiosSegurados = premiosSegurados;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getInicioVigenciaSegurado() {
        return inicioVigenciaSegurado;
    }

    public void setInicioVigenciaSegurado(String inicioVigenciaSegurado) {
        this.inicioVigenciaSegurado = inicioVigenciaSegurado;
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

    public String getCampoLivre3() {
        return campoLivre3;
    }

    public void setCampoLivre3(String campoLivre3) {
        this.campoLivre3 = campoLivre3;
    }

    public String getCampoLivre4() {
        return campoLivre4;
    }

    public void setCampoLivre4(String campoLivre4) {
        this.campoLivre4 = campoLivre4;
    }

    public String getCampoLivre() {
        return campoLivre;
    }

    public void setCampoLivre(String campoLivre) {
        this.campoLivre = campoLivre;
    }

    public String getNumeroSequencialSegurado() {
        return numeroSequencialSegurado;
    }

    public void setNumeroSequencialSegurado(String numeroSequencialSegurado) {
        this.numeroSequencialSegurado = numeroSequencialSegurado;
    }

    public String getNumeroUnicoSeguradoSite() {
        return numeroUnicoSeguradoSite;
    }

    public void setNumeroUnicoSeguradoSite(String numeroUnicoSeguradoSite) {
        this.numeroUnicoSeguradoSite = numeroUnicoSeguradoSite;
    }

    public String getOpcaoReceberEmailCertificado() {
        return opcaoReceberEmailCertificado;
    }

    public void setOpcaoReceberEmailCertificado(String opcaoReceberEmailCertificado) {
        this.opcaoReceberEmailCertificado = opcaoReceberEmailCertificado;
    }

    public String getPermiteRecebimentoEmailMarketing() {
        return permiteRecebimentoEmailMarketing;
    }

    public void setPermiteRecebimentoEmailMarketing(String permiteRecebimentoEmailMarketing) {
        this.permiteRecebimentoEmailMarketing = permiteRecebimentoEmailMarketing;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public Date getDataOpcaoRecebimentoEmailMarketing() {
        return dataOpcaoRecebimentoEmailMarketing;
    }

    public void setDataOpcaoRecebimentoEmailMarketing(Date dataOpcaoRecebimentoEmailMarketing) {
        this.dataOpcaoRecebimentoEmailMarketing = dataOpcaoRecebimentoEmailMarketing;
    }

    public Date getDataOpcaoRecebimentoEmail() {
        return dataOpcaoRecebimentoEmail;
    }

    public void setDataOpcaoRecebimentoEmail(Date dataOpcaoRecebimentoEmail) {
        this.dataOpcaoRecebimentoEmail = dataOpcaoRecebimentoEmail;
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
        if (!(object instanceof NuvemVendasRegistroCadastroSeguradosDhi)) {
            return false;
        }
        NuvemVendasRegistroCadastroSeguradosDhi other = (NuvemVendasRegistroCadastroSeguradosDhi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroCadastroSeguradosDhi[ id=" + id + " ]";
    }
    
}
