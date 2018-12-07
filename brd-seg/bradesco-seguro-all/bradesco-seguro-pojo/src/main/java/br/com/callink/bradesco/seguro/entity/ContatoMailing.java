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
@Table(name = "TB_CONTATO_MAILING")
@XmlRootElement
public class ContatoMailing implements IdentifiableEntity<BigInteger>, Cloneable {
	
	public static final String DATA_FORMATO = "dd/MM/yyyy";
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CONTATO_MAILING")
    private BigInteger id;
   
    @Column(name = "FLAG_ENABLED")
    private Short flagEnabled;
    
    @Column(name = "CARTAO")
    private String cartao;
    
    @Column(name = "NOME")
    private String nome;
    
    @Column(name = "ENDERECO")
    private String endereco;
    
    @Column(name = "BAIRRO")
    private String bairro;
    
    @Column(name = "CIDADE")
    private String cidade;
    
    @Column(name = "UF")
    private String uf;
    
    @Column(name = "CEP")
    private String cep;
    
    @Column(name = "FONE_COMERCIAL")
    private String foneComercial;
    
    @Column(name = "FONE_RESIDENCIAL")
    private String foneResidencial;
    
    @Column(name = "FONE_CELULAR")
    private String foneCelular;
    
    @Column(name = "SEXO")
    private String sexo;
    
    @Column(name = "CPF")
    private String cpf;
    
    @Column(name = "DIA_VENCECIMENTO_FATURA")
    private String diaVencecimentoFatura;
    
    @Column(name = "DATA_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
    
    @Column(name = "PRODUTO")
    private String produto;
    
    @Column(name = "VALIDADE_CARTAO")
    private String validadeCartao;
    
    @Column(name = "TIPO_CARTAO")
    private String tipoCartao;
    
    @JoinColumn(name = "ID_LOTE_MAILING", referencedColumnName = "ID_LOTE_MAILING", insertable = false, updatable = false)
    @ManyToOne
    private LoteMailing loteMailing;
    
    @Column(name = "ID_LOTE_MAILING")
    private BigInteger idLoteMailing;
    
    @Column(name = "NUMERO_ENDERECO")
    private String numeroEndereco;
    
    @Column(name = "COMPLEMENTO_ENDERECO")
    private String complementoEndereco;
    
    @Column(name = "RG")
    private String rg;
    
    @Column(name = "ORGAO_EXPEDIDOR")
    private String orgaoExpedidor;
    
    @Column(name = "DATA_EXPEDICAO")
	@Temporal(TemporalType.TIMESTAMP)
    private Date dataExpedicao;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "FLAG_RECEBE_OFERTAS_EMAIL")
	private Boolean flagRecebeOfertasEmail;
    
	@Column(name = "FLAG_RECEBE_OFERTAS_SMS_RES")
	private Boolean flagRecebeOfertasSmsRes;
	
	@Column(name = "FLAG_RECEBE_OFERTAS_SMS_COM")
	private Boolean flagRecebeOfertasSmsCom;
	
	@Column(name = "FLAG_RECEBE_OFERTAS_SMS_CEL")
	private Boolean flagRecebeOfertasSmsCel;

	@Column(name = "FLAG_RECEBE_CERTIFICADO_EMAIL")
	private Boolean flagRecebeCertificadoEmail;
	
	@Column(name = "DATA_IMPORTACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataImportacao;
	
	@Column(name = "EMPRESA")
	private String empresa;
	
	@Column(name = "CNPJ")
	private String cnpj;
	
    public ContatoMailing() {
    }

    public ContatoMailing(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Short getFlagEnabled() {
        return flagEnabled;
    }

    public void setFlagEnabled(Short flagEnabled) {
        this.flagEnabled = flagEnabled;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFoneComercial() {
        return foneComercial;
    }

    public void setFoneComercial(String foneComercial) {
        this.foneComercial = foneComercial;
    }

    public String getFoneResidencial() {
        return foneResidencial;
    }

    public void setFoneResidencial(String foneResidencial) {
        this.foneResidencial = foneResidencial;
    }

    public String getFoneCelular() {
        return foneCelular;
    }

    public void setFoneCelular(String foneCelular) {
        this.foneCelular = foneCelular;
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

    public String getDiaVencecimentoFatura() {
        return diaVencecimentoFatura;
    }

    public void setDiaVencecimentoFatura(String diaVencecimentoFatura) {
        this.diaVencecimentoFatura = diaVencecimentoFatura;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getValidadeCartao() {
        return validadeCartao;
    }

    public void setValidadeCartao(String validadeCartao) {
        this.validadeCartao = validadeCartao;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public LoteMailing getLoteMailing() {
        return loteMailing;
    }

    public void setLoteMailing(LoteMailing loteMailing) {
        this.loteMailing = loteMailing;
    }

    public BigInteger getIdLoteMailing() {
		return idLoteMailing;
	}

	public void setIdLoteMailing(BigInteger idLoteMailing) {
		this.idLoteMailing = idLoteMailing;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}
	
	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getFlagRecebeOfertasEmail() {
		if (flagRecebeOfertasEmail == null) {
			return Boolean.FALSE;
		}
		return flagRecebeOfertasEmail;
	}

	public void setFlagRecebeOfertasEmail(Boolean flagRecebeOfertasEmail) {
		this.flagRecebeOfertasEmail = flagRecebeOfertasEmail;
	}


	public Boolean getFlagRecebeOfertasSmsRes() {
		if (flagRecebeOfertasSmsRes == null) {
			return Boolean.FALSE;
		}
		return flagRecebeOfertasSmsRes;
	}

	public void setFlagRecebeOfertasSmsRes(Boolean flagRecebeOfertasSmsRes) {
		this.flagRecebeOfertasSmsRes = flagRecebeOfertasSmsRes;
	}

	public Boolean getFlagRecebeOfertasSmsCom() {
		if (flagRecebeOfertasSmsCom == null) {
			return Boolean.FALSE;
		}
		return flagRecebeOfertasSmsCom;
	}

	public void setFlagRecebeOfertasSmsCom(Boolean flagRecebeOfertasSmsCom) {
		this.flagRecebeOfertasSmsCom = flagRecebeOfertasSmsCom;
	}

	public Boolean getFlagRecebeOfertasSmsCel() {
		if (flagRecebeOfertasSmsCel == null) {
			return Boolean.FALSE;
		}
		return flagRecebeOfertasSmsCel;
	}

	public void setFlagRecebeOfertasSmsCel(Boolean flagRecebeOfertasSmsCel) {
		this.flagRecebeOfertasSmsCel = flagRecebeOfertasSmsCel;
	}

	public Boolean getFlagRecebeCertificadoEmail() {
		if (flagRecebeCertificadoEmail == null) {
			return Boolean.FALSE;
		}
		return flagRecebeCertificadoEmail;
	}

	public Date getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(Date dataImportacao) {
		this.dataImportacao = dataImportacao;
	}

	public void setFlagRecebeCertificadoEmail(Boolean flagRecebeCertificadoEmail) {
		this.flagRecebeCertificadoEmail = flagRecebeCertificadoEmail;
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
        if (!(object instanceof ContatoMailing)) {
            return false;
        }
        ContatoMailing other = (ContatoMailing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.ContatoMailing[ id=" + id + " ]";
    }
    
}
